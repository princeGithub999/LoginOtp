package com.example.login_otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.login_otp.databinding.SpalceScreenActivityBinding
import com.google.firebase.auth.FirebaseAuth

class SplaceScreenActivity : AppCompatActivity() {

    private lateinit var binding: SpalceScreenActivityBinding
     var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SpalceScreenActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            if (auth.currentUser != null) {
                startActivity(Intent(this, AllDitlesActivity::class.java))
            }else{
                startActivity(Intent(this,LoginActivity::class.java))
            }
            finish()
        }, 3000)

    }
}