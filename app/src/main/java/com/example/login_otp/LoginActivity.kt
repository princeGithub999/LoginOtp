package com.example.login_otp

import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.login_otp.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
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
    private fun sendOtp(){
        var number = ""
        if (number.isEmpty()){
            number = "+91${binding.enterNumberEditText.text}"
            val phoneAuthOptions = PhoneAuthOptions.newBuilder()
                .setActivity(this)
                .setPhoneNumber(number)
                .setTimeout(30L,TimeUnit.SECONDS)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        Toast.makeText(this@LoginActivity, "Verification compledt", Toast.LENGTH_SHORT).show()
                        checkUserPhone()
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
        }else{
            Toast.makeText(this, "Any filed is empty", Toast.LENGTH_SHORT).show()
        }

    }
    fun checkUserPhone(){
        FirebaseFirestore.getInstance().collection("manegar").whereEqualTo("phone",auth.currentUser?.phoneNumber).get()
            .addOnSuccessListener {
                if (it.documents.isEmpty()){
                    startActivity(Intent(this,HomeActivity::class.java))
                    Toast.makeText(this, "verify success", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    startActivity(Intent(this,AllDitlesActivity::class.java))
                    finish()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
                Log.e(TAG,"User UPLOADING Failure",e)
            }
    }
}