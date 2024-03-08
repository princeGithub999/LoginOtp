package com.example.login_otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.login_otp.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var verificationID = ""
    private var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createAccountTextView.setOnClickListener {
            startActivity(Intent(this,AllDitlesActivity::class.java))
            finish()
        }
        binding.sendOtpButton.setOnClickListener {
            sendOtp()
        }
    }
    fun sendOtp(){
         var number = ""
        number = "+91${binding.enterNumberEditText.text}"
        val phoneAuthOptions = PhoneAuthOptions.newBuilder()
            .setActivity(this)
            .setPhoneNumber(number)
            .setTimeout(30L,TimeUnit.SECONDS)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Toast.makeText(this@LoginActivity, "Verification compledt", Toast.LENGTH_SHORT).show()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(this@LoginActivity, "${p0.message}Verification Failed ", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationId, token)
                    verificationID = verificationId

                    val intent = Intent(this@LoginActivity,VerifideActivity::class.java)
                    intent.putExtra("OTP",verificationId)
                    intent.putExtra("ResentToken",token)
                    intent.putExtra("number",number)
                    startActivity(intent)
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
    }
}