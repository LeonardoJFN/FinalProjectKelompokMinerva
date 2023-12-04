package com.example.finalprojectkelompokminerva.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Mms.Inbox
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.finalprojectkelompokminerva.MainActivity
import com.example.finalprojectkelompokminerva.R
import com.example.finalprojectkelompokminerva.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.SignInMethodQueryResult
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    val auth = FirebaseAuth.getInstance()
    private var verificationId: String? = null
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
//        binding.sendOtp.showLoadingButton()
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)

        sigmInWithPhoneAuthCredential(credential)
    }

        private fun sendOtp(number: String) {
//        binding.sendOtp.showLoadingButton()

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                binding.sendOtp.showNormalButton()
                sigmInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                this@LoginActivity.verificationId = verificationId

//                binding.sendOtp.showNormalButton()

                binding.numberLayout.visibility = GONE
                binding.otpLayout.visibility = VISIBLE
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+62$number")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun sigmInWithPhoneAuthCredential(credential: PhoneAuthCredential){
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
//                    binding.sendOtp.showNormalButton()
                    startActivity(Intent(this, MainActivity::class.java))
                        finish()
                } else{
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }


}