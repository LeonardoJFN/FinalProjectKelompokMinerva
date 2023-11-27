package com.example.finalprojectkelompokminerva.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalprojectkelompokminerva.R
import com.example.finalprojectkelompokminerva.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    val auth = FirebaseAuth.getInstance()
    private val verificationId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendOtp.setOnClickListener{
            if(binding.userNumber.text!!.isEmpty()){
                binding.userNumber.error = "Please enter your number"
            } else {
                sendOtp(binding.userNumber.text.toString())
            }
        }

        binding.verifyOtp.setOnClickListener {
            if(binding.userOtp.text!!.isEmpty()){
                binding.userOtp.error = "Please enter your number"
            } else {
                verifyOtp(binding.userOtp.text.toString())
            }
        }

    }

    private fun verifyOtp(otp: String) {

    }

    private fun sendOtp(number: String) {
        binding.sendOtp.showLoadingButton()

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {

            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {

            }
        }
    }


}