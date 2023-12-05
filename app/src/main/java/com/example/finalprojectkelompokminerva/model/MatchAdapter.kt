package com.example.finalprojectkelompokminerva.model

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectkelompokminerva.R
import com.example.finalprojectkelompokminerva.activity.MessageActivity
import com.example.finalprojectkelompokminerva.databinding.ItemMatchBinding
import com.example.finalprojectkelompokminerva.databinding.ActivityLoginBinding
import com.example.finalprojectkelompokminerva.databinding.ActivityMessageBinding

class MatchAdapter : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    // Replace this with your actual data
    private val data = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val item = data[position]
        // Bind your data here

        holder.binding.chat.setOnClickListener {
            val inte = Intent(context, MessageActivity::class.java)
            inte.putExtra("userId", list[position].number)
            context.startActivity(inte)
        }

    }

    override fun getItemCount() = data.size

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize your views here
    }
}
