package com.example.finalprojectkelompokminerva

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.finalprojectkelompokminerva.databinding.ActivityMapsBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("location")
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                    isGranted -> if (isGranted) {
                getLastLocation()
            } else {
                        showPermissionRationale {
                            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
                        }
            }
            }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        when {
            hasLocationPermission() -> getLastLocation()
                    shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) -> {
                showPermissionRationale {
                    requestPermissionLauncher
                        .launch(ACCESS_FINE_LOCATION)
                }
            }
            else -> requestPermissionLauncher
                .launch(ACCESS_FINE_LOCATION)
        }
    }
    private fun hasLocationPermission() =
        ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    private fun showPermissionRationale(positiveAction: () -> Unit) {
                AlertDialog.Builder(this)
                    .setTitle("Location permission")
                    .setMessage("This app will not work without knowing your current location")
                    .setPositiveButton(android.R.string.ok) { _, _ -> positiveAction() }
                    .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss()
                    }
                    .create().show()
    }
    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation
//If successfully fetched the location information from the API
            .addOnSuccessListener { location: Location? ->
                location?.let {
//Set the user location from latitude and longitude                    coordinate
                    val userLocation = LatLng(location.latitude,
                        location.longitude)
//Move camera to that location
                    updateMapLocation(userLocation)
//Add a marker at the location position
                    addMarkerAtLocation(userLocation, "You")
                } // Store location data in Firebase
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    // Get a reference to the database
                    val database = FirebaseDatabase.getInstance()

                    // Get a reference to the "location" child
                    val myRef = database.getReference("location")

                    // Set the latitude and longitude
                    myRef.child("latitude").setValue(latitude)
                    myRef.child("longitude").setValue(longitude)
                }
            }
    }

    private fun updateMapLocation(location: LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
            location, 7f))
    }
    private fun addMarkerAtLocation(location: LatLng, title: String) {
        mMap.addMarker(MarkerOptions().title(title)
            .position(location))
    }
}