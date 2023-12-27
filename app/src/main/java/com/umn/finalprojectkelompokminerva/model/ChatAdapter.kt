package com.umn.finalprojectkelompokminerva.model

import android.view.LayoutInflater
import android.view.ViewGroup
//import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.umn.finalprojectkelompokminerva.ImageLoader
import com.umn.finalprojectkelompokminerva.ChatViewHolder
import com.umn.finalprojectkelompokminerva.R

class ChatAdapter(private val layoutInflater: LayoutInflater, private val imageLoader: ImageLoader, private val onClickListener: OnClickListener ) : RecyclerView.Adapter<ChatViewHolder>() {
    private val chats = mutableListOf<ChatModel>()
    fun setData(newChats: List<ChatModel>) {
        chats.clear()
        chats.addAll(newChats)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = layoutInflater.inflate(R.layout.chat_list, parent, false)
        return ChatViewHolder(view, imageLoader, object:
            ChatViewHolder.OnClickListener{
            override fun onClick(chat: ChatModel) =
                onClickListener.onItemClick(chat)
        })
    }
    override fun getItemCount() = chats.size
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bindData(chats[position])
    }
    interface OnClickListener {
        fun onItemClick(chat: ChatModel)
    }
}