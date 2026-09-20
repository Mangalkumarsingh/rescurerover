package com.example.rescuerover.Activity

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.example.rescuerover.Model.Register
import com.example.rescuerover.Model.model
import com.example.rescuerover.databinding.ActivityDataInputBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.iceteck.silicompressorr.FileUtils
import com.iceteck.silicompressorr.SiliCompressor
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.Calendar
import java.util.Locale
import java.util.UUID


class dataInputActivity : AppCompatActivity() {
    lateinit var binding: ActivityDataInputBinding
    var model = model()
    var register = Register()
    var uri: Uri? = null
    var imageUri: String? = null
    lateinit var auth: FirebaseAuth
    lateinit var dbRef: DatabaseReference
    var location_id = 43
    var adoptid: String? = null
    var rescueid: String? = null
    var garbageid: String? = null
    var latitude: Double? = null
    var longitude: Double? = null
    lateinit var flpc: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        adoptid = intent.getStringExtra("adoptId")
        rescueid = intent.getStringExtra("rescueId")
        garbageid = intent.getStringExtra("garbageId")


        auth = FirebaseAuth.getInstance()
        flpc = LocationServices.getFusedLocationProviderClient(this)
        //get exactlocation
        binding.currentLocation.setOnClickListener {
            checkLocationPermission()
            Toast.makeText(this, "location Get", Toast.LENGTH_SHORT).show()
        }

        //location close
        //spinner
        var AreaLocation = arrayOf(
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chhattisgarh",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttarakhand",
            "Uttar Pradesh",
            "West Bengal",
            "Andaman and Nicobar Islands",
            "Chandigarh",
            "Dadra and Nagar Haveli",
            "Daman and Diu",
            "Delhi",
            "Lakshadweep",
            "Puducherry"
        )

        var locationAdapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, AreaLocation)
        binding.autoText.setAdapter(locationAdapter)

        //over there

        binding.button1.setOnClickListener {

            // projectId===== rescue-rover-c2462


            binding.progressBar2.visibility = View.VISIBLE
            loadData()
            //adoptImage(0)
            binding.progressBar2.visibility = View.GONE
            finish()


        }


        //spinner close

        binding.imageShow.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }


    }


    @SuppressLint("SimpleDateFormat")
    fun loadData() {

        var random = UUID.randomUUID().toString()
        // var user1=auth.currentUser!!.uid
        var auth = FirebaseAuth.getInstance().currentUser?.uid
        val date = Calendar.getInstance().time
        model.ProductuserId = auth
        model.id = random
        model.aName = binding.name.text.toString()
        model.color1 = binding.color.text.toString()
        model.comment1 = binding.comment2.text.toString()
        model.location = binding.autoText.text.toString()
        model.pName = binding.photographer.text.toString()
        model.exactLocation = binding.exactAddress.text.toString()
        model.date = date
        model.image = imageUri


//database code


        dbRef = FirebaseDatabase.getInstance().getReference("user")
        dbRef.child(random!!).setValue(model).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "load data in data base", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //location code

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            //WHEN PERMISSION IS ALREADY GRANTED
            checkGPS()
        } else {

            // WHEN PERMISSION IS DENIED

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                location_id
            )

        }
    }

    private fun checkGPS() {
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000
        var builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        var result = LocationServices.getSettingsClient(this.applicationContext)
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener {
            try {
                //when gps is on
                var response = it.getResult(ApiException::class.java)
                getUserLocation()
            } catch (e: ApiException) {
                //when gps is off
                e.printStackTrace()


                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {

                        //here we send the request for enable the gps
                        var resolveApiException = e as ResolvableApiException
                        resolveApiException.startResolutionForResult(this, 200)

                    } catch (sendIntentException: IntentSender.SendIntentException) {
                        
                    }
                }


            }
        }

    }

    private fun getUserLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        flpc?.lastLocation?.addOnCompleteListener {
            val location = it.getResult()
            if (location != null) {
                try {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    latitude = location.latitude
                    longitude = location.longitude
                    val address =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)

                    //here we set the address in text view
                    var address_line = address?.get(0)?.getAddressLine(0)
                    binding.exactAddress.setText(address_line)
                    //  val address_location= address?.get(0)?.getAddressLine(0)
                    //  openLocation(address_location.toString())


                } catch (e: IOException) {

                }
            }
        }

    }

    //location code ends here


    //image


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK) {
            uri = data!!.data
            binding.imageShow.setImageURI(uri)
            compressAndUploadImage(uri!!)
            // adoptImage(0)


        }


    }


//storage code



    private fun compressAndUploadImage(imagePath: Uri) {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true // Only decode bounds, not full image

        BitmapFactory.decodeStream(contentResolver.openInputStream(imagePath), null, options)

        // Calculate inSampleSize based on desired dimensions
        val requiredWidth = 400 // Adjust as needed (consider max image size for database)
        val requiredHeight = 250  // Adjust as needed

        val widthRatio = options.outWidth / requiredWidth.toFloat()
        val heightRatio = options.outHeight / requiredHeight.toFloat()

        val inSampleSize = Math.max(widthRatio, heightRatio).toInt()

        options.inSampleSize = inSampleSize
        options.inJustDecodeBounds = false // Decode full image now

        val bitmap =
            BitmapFactory.decodeStream(contentResolver.openInputStream(imagePath), null, options)

        // Compress bitmap using your preferred compression method (e.g., JPEG)
        val outputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, outputStream) // Adjust quality as needed

        val compressedByteArray = outputStream.toByteArray()

        // Upload compressed image to Firebase Storage
        var file1 = UUID.randomUUID().toString()
        var ref = Firebase.storage.reference.child("adoptImages/$file1")
        ref.putBytes(compressedByteArray).addOnSuccessListener {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            var uriTask = it.storage.downloadUrl
            while (!uriTask.isComplete);
            var urlImg = uriTask.result
            imageUri = urlImg.toString()
        }
    }

}