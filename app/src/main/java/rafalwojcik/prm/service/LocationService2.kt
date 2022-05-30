package rafalwojcik.prm.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationRequest
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import rafalwojcik.prm.activity.MainActivity

class LocationService2(var locationCallback : (Location)->Unit) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun getLocation(context: Context){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                println(location)
                if (location != null) {
                    locationCallback.invoke(location)
                }
            }
            .addOnFailureListener { println("failure") }
    }
}