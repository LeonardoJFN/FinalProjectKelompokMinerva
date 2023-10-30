package com.example.finalprojectkelompokminerva

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

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
    }

    override fun getItemCount() = data.size

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize your views here
    }
}
