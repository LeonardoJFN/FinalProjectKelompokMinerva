
package com.umn.finalprojectkelompokminerva.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.umn.finalprojectkelompokminerva.GlideImageLoader
import com.umn.finalprojectkelompokminerva.R
import com.google.firebase.database.*
import androidx.compose.ui.graphics.asImageBitmap


val MaterialTheme.primary: androidx.compose.ui.graphics.Color
    @Composable
    get() = this.colors.primary

data class UserData(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val location: String,
    val image: String
)

class NearbyFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var usersList: MutableList<UserData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nearby, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        usersList = mutableListOf()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()

                for (userSnapshot in snapshot.children) {
                    val name = userSnapshot.child("name").getValue(String::class.java) ?: ""
                    val email = userSnapshot.child("email").getValue(String::class.java) ?: ""
                    val phoneNumber =
                        userSnapshot.child("phoneNumber").getValue(String::class.java) ?: ""
                    val location = userSnapshot.child("location").getValue(String::class.java)
                        ?: ""
                    val image = userSnapshot.child("image").getValue(String::class.java) ?: ""

                    usersList.add(UserData(name, email, phoneNumber, location, image))
                }

                updateLazyColumn(view)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to read data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateLazyColumn(view: View) {
        val lazyColumnView = view.findViewById<ComposeView>(R.id.lazyColumnView)
        lazyColumnView.setContent {
            NearbyList(usersList)
        }
    }

    @Composable
    private fun NearbyList(users: List<UserData>) {
        LazyColumn {
            items(items = users) { user ->
                UserItem(user)
            }
        }
    }

    @Composable
    private fun UserItem(user: UserData) {
        val context = LocalContext.current
        val imageLoader = GlideImageLoader(context)

        val profileImage = imageLoader.loadImageAsBitmap(user.image).asImageBitmap()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                bitmap = profileImage,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Name: ${user.name}",
                    style = typography.subtitle1
                )
                Text(
                    text = "Phone: ${user.phoneNumber}",
                    style = typography.body1
                )
            }
        }
    }
}
