package com.umn.finalprojectkelompokminerva.model

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umn.finalprojectkelompokminerva.databinding.UserItemLayoutBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessageUserAdapter(
    private val context: Context,
    private val list: ArrayList<String>,
    private val chatKey: List<String>
) : RecyclerView.Adapter<MessageUserAdapter.MessageUserViewHolder>() {

    inner class MessageUserViewHolder(val binding: UserItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageUserViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = UserItemLayoutBinding.inflate(inflater, parent, false)
        return MessageUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageUserViewHolder, position: Int) {
        FirebaseDatabase.getInstance().getReference("users")
            .child(list[position]).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val data = snapshot.getValue(UserModel::class.java)
                            if (data != null) {
                                Glide.with(context).load(data.image).into(holder.binding.userImage)
                                holder.binding.userName.text = data.name
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
    }

    override fun getItemCount(): Int {
        return list.size
    }
}


    override fun getItemCount(): Int {
        return list.size
    }

}