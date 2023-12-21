package com.example.finalprojectkelompokminerva.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.finalprojectkelompokminerva.GlideImageLoader
import com.example.finalprojectkelompokminerva.ImageLoader
import com.example.finalprojectkelompokminerva.R
import com.example.finalprojectkelompokminerva.utils.Config
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView


class   ProfileFragment : Fragment() {
    private lateinit var glideImageLoader: ImageLoader
    private lateinit var databaseReference: DatabaseReference
    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
        // Update the image in the Firebhase Storage
        if (imageUri != null) {
            uploadImage(imageUri)
        }
    }

    private fun uploadImage(imageUri: Uri) {
        Config.showDialog(requireContext())

        val storageRef = FirebaseStorage.getInstance().getReference("profile")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("profile.jpg")

        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                    // Update the image URL in the database
                    updateImageInDatabase(imageUrl.toString())
                }.addOnFailureListener {
                    Config.hideDialog()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateImageInDatabase(imageUrl: String) {
        val phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber
        val userRef = FirebaseDatabase.getInstance().getReference("users").child(phoneNumber.toString())
        userRef.child("image").setValue(imageUrl)
        val profileImage = view?.findViewById<ImageView>(R.id.profileImage)
        if (profileImage != null) {
            glideImageLoader.loadImage(imageUrl, profileImage)
        }

        Config.hideDialog()
        Toast.makeText(requireContext(), "Profile image updated successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        glideImageLoader = GlideImageLoader(requireContext())

        val phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber
        val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(phoneNumber.toString())

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // Check if the dataSnapshot exists and has children
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    // Access the data using dataSnapshot
                    val age = dataSnapshot.child("age").getValue(String::class.java)
                    val email = dataSnapshot.child("email").getValue(String::class.java)
                    val image = dataSnapshot.child("image").getValue(String::class.java)
                    val name = dataSnapshot.child("name").getValue(String::class.java)
                    println("Age: $age")
                    println("Email: $email")
                    println("Image: $image")
                    println("Name: $name")

                    // Do something with the retrieved values
                    view.findViewById<TextInputEditText>(R.id.editName)?.setText(name)
                    view.findViewById<TextView>(R.id.userPhoneNumber)?.setText(phoneNumber)
                    view.findViewById<TextInputEditText>(R.id.editEmail)?.setText(email)
                    view.findViewById<TextInputEditText>(R.id.editAge)?.setText(age)
                    val profileImage = view.findViewById<ImageView>(R.id.profileImage)
                    glideImageLoader.loadImage(image ?: "", profileImage)
                } else {
                    println("Data not found for the current user.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                println("Failed to read value: ${error.toException()}")
            }
        })
        val editButton = view.findViewById<Button>(R.id.editButton)
        editButton.setOnClickListener {
            // Get the updated values from the TextInputEditText fields
            val updatedName = view.findViewById<TextInputEditText>(R.id.editName)?.text.toString()
            val updatedEmail = view.findViewById<TextInputEditText>(R.id.editEmail)?.text.toString()
            val updatedAge = view.findViewById<TextInputEditText>(R.id.editAge)?.text.toString()

            // Update the values in the database
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(phoneNumber.toString())
            userRef.child("name").setValue(updatedName)
            userRef.child("email").setValue(updatedEmail)
            userRef.child("age").setValue(updatedAge)

            // Optionally, you can update the local views with the new values
            view.findViewById<TextView>(R.id.userPhoneNumber)?.setText(phoneNumber)
            view.findViewById<TextInputEditText>(R.id.editName)?.setText(updatedName)
            view.findViewById<TextInputEditText>(R.id.editEmail)?.setText(updatedEmail)
            view.findViewById<TextInputEditText>(R.id.editAge)?.setText(updatedAge)

            // Inform the user that the update was successful (you can use Toast or Snackbar)
            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
        }
        val profileImage = view.findViewById<CircleImageView>(R.id.profileImage)
        profileImage.setOnClickListener {
            selectImage.launch("image/*")
        }


    }

}