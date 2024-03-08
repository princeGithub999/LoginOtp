package com.example.login_otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class VerifyCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifei_complite)

        Handler().postDelayed({
            startActivity(Intent(this,AllDitlesActivity::class.java))
        },3000)
    }
}