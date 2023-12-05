//package com.example.finalprojectkelompokminerva.model
//
//import android.content.Context
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.finalprojectkelompokminerva.R
//import com.example.finalprojectkelompokminerva.databinding.ActivityMessageBinding
//import com.example.finalprojectkelompokminerva.databinding.UserItemLayoutBinding
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//class MessageUserAdapter(val context: Context, val list: ArrayList<String>, val chatKey : List<String> ) :
//        RecyclerView.Adapter<MessageUserAdapter.MessageUserViewHolder>() {
//        inner class MessageUserViewHolder(val binding: UserItemLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageUserViewHolder {
//        return MessageUserViewHolder(UserItemLayoutBinding.inflate(LayoutInflater.inflate(LayoutInflater.from(context), parent, false)))
//    }
//
//    override fun onBindViewHolder(holder: MessageUserViewHolder, position: Int) {
//
//        FirebaseDatabase.getInstance().getReference("users")
//                .child(list[position]).addListenerForSingleValueEvent(
//                    object  : ValueEventListener{
//                        override fun onDataChange(snapshot: DataSnapshot) {
//
//                            if (snapshot.exists()){
//                                val data = snapshot.getValue(User::class.java)
//
//                                Glide.with(context).load(data!!.image).into(holder.binding.userImage)
//
//                                holder.binding.userName.text = data!!.name
//                            }
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
//                        }
//
//                    }
//                )
//
//
//
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//}