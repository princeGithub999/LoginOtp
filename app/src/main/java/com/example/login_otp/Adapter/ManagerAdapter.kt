package com.example.login_otp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login_otp.R

class ManagerAdapter(val context: Context, private val userList: List<ManagerModle>) :
    RecyclerView.Adapter<ManagerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.name_textView)
        val userEmail: TextView = itemView.findViewById(R.id.email_textView)
        val userNumber: TextView = itemView.findViewById(R.id.number_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_data_list, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text = userList[position].name
        holder.userEmail.text = userList[position].email
        holder.userNumber.text = userList[position].number
    }


}


