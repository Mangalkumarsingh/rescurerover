package com.example.rescuerover

import android.content.Context
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rescuerover.databinding.ActivityMapshowBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class mapshow : AppCompatActivity() {
    var a1: Marker?=null


    lateinit var binding: ActivityMapshowBinding
    var location_id = 43
    var latitude: Double? = null
    var longitude: Double? = null
    lateinit var mapFragment: SupportMapFragment
    private var googleMap: GoogleMap? = null
    lateinit var flpc: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapshowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT
        flpc = LocationServices.getFusedLocationProviderClient(this)

        var intent=intent
        var loc=intent.getStringExtra("location")

            map(loc!!)





    }


    fun getLocationFromAddress(context: Context, addressString: String): LatLng? {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>? = try {
            geocoder.getFromLocationName(addressString, 1)
        } catch (e: Exception) {
            null
        }
        return addresses?.firstOrNull()?.let {
            latitude=it.latitude
            longitude=it.longitude
            LatLng(it.latitude, it.longitude)
        }
    }
    fun map(a:String) {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map1) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            var loc1 =getLocationFromAddress(this,a)
            googleMap!!.addMarker(MarkerOptions().position(loc1!!).title("my position"))
            googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(loc1, 13f))





        })



    }
}