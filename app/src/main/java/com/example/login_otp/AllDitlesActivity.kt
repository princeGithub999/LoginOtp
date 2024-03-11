package com.example.login_otp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.login_otp.databinding.ActivityAllDitlesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class AllDitlesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllDitlesBinding
    private var db = FirebaseFirestore.getInstance()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityAllDitlesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.doneButton.setOnClickListener {
            addDitles()
        }
        binding.goLoginTextView.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    private fun addDitles(){
        val id = UUID.randomUUID().toString()
        val auth = FirebaseAuth.getInstance()
        val name = binding.enterNameEditText.text.toString()
        val email = binding.enterEmailEditText.text.toString()
        val number = binding.enterNumberEditText.text.toString()

        if (name.isNotEmpty() || email.isNotEmpty() || number.isNotEmpty()){
            val map = hashMapOf(
                "id" to  id,
                "name" to name,
                "email" to email,
                "number" to number,
                "phone" to auth.currentUser?.phoneNumber
            )
            db.collection("manegar").document(id).set(map)
                .addOnSuccessListener {
                    Toast.makeText(this, "User Add", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,HomeActivity::class.java))
                }
                .addOnFailureListener {
                    Toast.makeText(this, "fleid", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this, "Any fleid is empty", Toast.LENGTH_SHORT).show()
        }
    }       
}