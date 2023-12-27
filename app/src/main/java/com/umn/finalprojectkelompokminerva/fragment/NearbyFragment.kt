package com.umn.finalprojectkelompokminerva.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.umn.finalprojectkelompokminerva.R
import com.umn.finalprojectkelompokminerva.model.UserModel
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class NearbyFragment : Fragment() {

    private val viewModel: NearbyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate your layout here
        return inflater.inflate(R.layout.fragment_nearby, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the current user's ID
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        // Fetch the list of nearby users from the ViewModel
        currentUserId?.let { userId ->
            viewModel.getNearbyUsers(userId).observe(viewLifecycleOwner, { users ->
                // Update your UI here with the list of users
                // For example, you could use an Adapter to display each user in a RecyclerView
            })
        }
    }
}


class NearbyViewModel : ViewModel() {

    private val userRepository = UserRepository()

    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371.0 // radius of the earth in kilometers

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2).pow(2.0) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }

    fun getNearbyUsers(currentUserId: String): LiveData<List<UserModel>> {
        // Get the current user's location
        val currentUserLocation = userRepository.getCurrentUserLocation(currentUserId)

        // Fetch the list of users from the repository
        return currentUserLocation.switchMap { location ->
            userRepository.getUsers().map { users ->
                // Filter the users based on their distance from the current user
                users.filter { user ->
                    val userLocation = user.latitude?.let { user.longitude?.let { it1 -> LatLng(it.toDouble(), it1.toDouble()) } }
                    val distance = userLocation?.let {
                        calculateDistance(
                            location.latitude, location.longitude,
                            it.latitude, userLocation.longitude
                        )
                    }

                    // Only include users who are within a certain distance
                    distance?.let { it <= 10 } ?: false
                }
            }
        }
    }
}

    class UserRepository {

        private val database = FirebaseDatabase.getInstance()
        private val usersRef = database.getReference("users")

        fun getCurrentUserLocation(currentUserId: String): LiveData<LatLng> {
            val currentUserLocation = MutableLiveData<LatLng>()

            usersRef.child(currentUserId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val latitude = dataSnapshot.child("latitude").value?.toString()?.toDoubleOrNull()
                    val longitude = dataSnapshot.child("longitude").value?.toString()?.toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        currentUserLocation.value = LatLng(latitude, longitude)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle possible errors.
                }
            })

            return currentUserLocation
        }

        fun getUsers(): LiveData<List<UserModel>> {
            val users = MutableLiveData<List<UserModel>>()

            usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userList = mutableListOf<UserModel>()
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(UserModel::class.java)
                        user?.let { userList.add(it) }
                    }
                    users.value = userList
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle possible errors.
                }
            })

            return users
        }
    }