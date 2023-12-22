package com.umn.finalprojectkelompokminerva.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.umn.finalprojectkelompokminerva.databinding.ActivityMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView4.setOnClickListener {
            if (binding.yourMessage.text!!.isEmpty()) {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
            } else {
                sendMessage(binding.yourMessage.text.toString())
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun sendMessage(msg: String) {
        val receiverId = intent.getStringExtra("userId")
        val senderId = FirebaseAuth.getInstance().currentUser!!.phoneNumber

        val chatId = senderId + receiverId
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val map = hashMapOf<String, String>()
        map["message"] = msg
        map["senderId"] = senderId!!
        map["CurrentTime"] = currentTime
        map["CurrentDate"] = currentDate

        val reference = FirebaseDatabase.getInstance().getReference("chats").child(chatId)


            reference.child(reference.push().key!!).setValue(map).addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.yourMessage.text = null
                    Toast.makeText(this, "Message Sended", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Message failed to send", Toast.LENGTH_SHORT).show()
                }
            }
    }
}