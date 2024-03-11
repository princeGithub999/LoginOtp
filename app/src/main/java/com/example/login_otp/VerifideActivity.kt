package com.example.login_otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.login_otp.databinding.ActivityVerifideBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken

class VerifideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifideBinding
    private var auth = FirebaseAuth.getInstance()
    private lateinit var otp: String
    private lateinit var number: String
    private lateinit var resendingToken: ForceResendingToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        otp = intent.getStringExtra("OTP").toString()
        number = intent.getStringExtra("number").toString()
        resendingToken = ((intent.getParcelableExtra("ResentToken") as? ForceResendingToken)!!)
        binding.completVerifyButton.setOnClickListener {
            verifyOtp()
        }
    }
    private fun verifyOtp() {
        val otpText = binding.pinview.text.toString()
        val phoneAuthCredential = PhoneAuthProvider.getCredential(otp, otpText)
        auth.signInWithCredential(phoneAuthCredential)
            .addOnSuccessListener {
                Toast.makeText(this, "Verify Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, VerifyCompleteActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Verify fleid", Toast.LENGTH_SHORT).show()
            }
    }
}