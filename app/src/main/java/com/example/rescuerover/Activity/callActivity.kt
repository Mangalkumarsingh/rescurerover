package com.example.rescuerover.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.rescuerover.databinding.ActivityCallBinding
import com.example.rescuerover.mapshow
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class callActivity : AppCompatActivity() {
    lateinit var binding: ActivityCallBinding
var PICK_FROM_GALLERY = 1
    lateinit  var image1:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //enableEdgeToEdge()


       var intent=intent
        image1= intent.getStringExtra("image1").toString()
        //var image1=intent.getParcelableExtra<Bitmap>("image1")
       var bread=intent.getStringExtra("bread")
       var location=intent.getStringExtra("location")
       var photograph=intent.getStringExtra("name")


        binding.button2.setOnClickListener {
            startActivity(Intent(this,selectActivity::class.java))
            finish()
        }

     //  ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PICK_FROM_GALLERY)
//
//        }

       // Picasso.get().load(image1).into(binding.listImg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
           loadGlide()
        }else {

oldLoadGlide()
        }






      //  Glide.with(this@callActivity).load(image1).into(binding.listImg)

            binding.listBread.text=bread
            binding.listLocation.text=location
            binding.listPhotographer.text=photograph

//binding.listImg.setImageBitmap(image1)

binding.earthGloal.setOnClickListener {
    var intent=Intent(this,mapshow::class.java)
    intent.putExtra("location",location)
    startActivity(intent)
}


        binding.call.setOnClickListener {
            call(it)
        }

    }

    private fun oldLoadGlide() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                    Glide.with(this@callActivity).load(image1).into(binding.listImg)

                }

                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(this, "Error occurred! ", Toast.LENGTH_SHORT).show()
            }
            .onSameThread()
            .check()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun loadGlide() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_MEDIA_IMAGES
            ).withListener(object :MultiplePermissionsListener{
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    Glide.with(this@callActivity).load(image1).into(binding.listImg)
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {

                }

            })

    }

    fun call(view: View) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + "9835379533")
        startActivity(dialIntent)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Glide.with(this).load(image1).into(binding.listImg)
            } else {
                // Permission denied, handle user rejection
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}