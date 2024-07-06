package com.example.learn.utils

import android.Manifest
import android.R.attr.enabled
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.LiveData
import com.example.learn.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.SingleSubject
import io.reactivex.subjects.Subject


public class LocationCordinate(val lat: Double, val lng: Double);

class LocationService() {
    private lateinit var locationManager: LocationManager
    private lateinit var context: Context
    private lateinit var activity: MainActivity
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val locationChanged: PublishSubject<LocationCordinate> =
        PublishSubject.create<LocationCordinate>()

    constructor(context: Context, activity: MainActivity) : this() {
        this.context = context
        this.activity = activity
        this.locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        this.alertDialogBuilder = AlertDialog.Builder(context)
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
    }

    public fun getPermission() {
        ActivityCompat.requestPermissions(
            this.activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            1
        )
    }

//    public fun permissionChanged(result: Any): Boolean {
//        return true
//    }

    public fun hasPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this.context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    public fun isGpsEnabled(): Boolean {
        val locationManager: LocationManager =
            this.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    @SuppressLint("MissingPermission")
    public fun getLocation(): PublishSubject<LocationCordinate>? {
        if (!isGpsEnabled()) {
            alertDialogBuilder.setTitle("Error")
            alertDialogBuilder.setMessage("Gps is Off")
            alertDialogBuilder.show()
            return null
        }
        if (!hasPermission()) {
            getPermission()
            return null
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val coordinate = LocationCordinate(location.latitude, location.longitude)
                    this.locationChanged.onNext(coordinate)
                }

            }
        return this.locationChanged
    }

}