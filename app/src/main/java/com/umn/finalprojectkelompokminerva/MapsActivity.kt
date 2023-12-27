package com.umn.finalprojectkelompokminerva

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.umn.finalprojectkelompokminerva.databinding.ActivityMapsBinding
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("location")
    val phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber
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
                        }}}}
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        when {
            hasLocationPermission() -> getLastLocation()
                    shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) -> {
                showPermissionRationale {
                    requestPermissionLauncher
                        .launch(ACCESS_FINE_LOCATION)}}
            else -> requestPermissionLauncher
                .launch(ACCESS_FINE_LOCATION)}}
    private fun hasLocationPermission() =
        ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    private fun showPermissionRationale(positiveAction: () -> Unit) {
                AlertDialog.Builder(this)
                    .setTitle("Location permission")
                    .setMessage("This app will not work without knowing your current location")
                    .setPositiveButton(android.R.string.ok) { _, _ -> positiveAction() }
                    .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss()}
                    .create().show()}
    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val userLocation = LatLng(
                        location.latitude,
                        location.longitude
                    )
                    updateMapLocation(userLocation)
                    addMarkerAtLocation(userLocation, "You")
                }
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    val database = FirebaseDatabase.getInstance()

                    val myRef = database.getReference("users").child(phoneNumber.toString())

                    myRef.child("latitude").setValue(latitude)
                    myRef.child("longitude").setValue(longitude)
                }}}

    private fun updateMapLocation(location: LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
            location, 7f))}
    private fun addMarkerAtLocation(location: LatLng, title: String) {
        mMap.addMarker(MarkerOptions().title(title)
            .position(location))}
}