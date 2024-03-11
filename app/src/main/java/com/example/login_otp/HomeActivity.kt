package com.example.login_otp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login_otp.Adapter.ManagerAdapter
import com.example.login_otp.Adapter.ManagerModle
import com.example.login_otp.databinding.ActivityHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var managerAdapter: ManagerAdapter
    private var db = FirebaseFirestore.getInstance()
    private val array = ArrayList<ManagerModle>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()

        managerAdapter = ManagerAdapter(this, array)
        binding.recycleView.adapter = managerAdapter


    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData() {
        db.collection("manegar").get()


            .addOnSuccessListener { result ->
                val userList = ArrayList<ManagerModle>()
                for (document in result) {
                    val manegerFile = document.toObject<ManagerModle>()
                    userList.add(manegerFile)
                }

                managerAdapter = ManagerAdapter(this, userList)
                binding.recycleView.layoutManager = LinearLayoutManager(this)

                binding.recycleView.adapter = managerAdapter
                managerAdapter.notifyDataSetChanged()
                Toast.makeText(this, "get Data", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            }
    }
}