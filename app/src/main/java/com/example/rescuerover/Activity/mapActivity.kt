package com.example.rescuerover.Activity

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.example.rescuerover.R
import com.example.rescuerover.databinding.ActivityMapBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.Locale


class mapActivity : AppCompatActivity(){
    var a: Marker?=null


    lateinit var binding: ActivityMapBinding

    var lat: Double? = null
    var lon: Double? = null
    lateinit var mapFragment: SupportMapFragment
    private var googleMap: GoogleMap? = null
    lateinit var flpc: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
     var loc:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT
        flpc = LocationServices.getFusedLocationProviderClient(this)
        var intent=intent
         loc=intent.getStringExtra("location")

      //  binding.progressBar2.visibility=View.VISIBLE
        map(loc!!)
       // binding.progressBar2.visibility=View.GONE
    }

fun getLocationFromAddress(context: Context, addressString: String): LatLng? {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address>? = try {
        geocoder.getFromLocationName(addressString, 1)
    } catch (e: Exception) {
        null
    }
    return addresses?.firstOrNull()?.let {
        lat=it.latitude
        lon=it.longitude
        LatLng(it.latitude, it.longitude)
    }
}

    fun map(a:String) {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            var loc1 = getLocationFromAddress(this,a)
            googleMap!!.addMarker(MarkerOptions().position(loc1!!).title("my position"))
            googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(loc1, 10f))



            googleMap!!.setOnMapClickListener {  li->
                googleMap!!.clear()



                //here we set the address in text view



            }

        })



    }

}