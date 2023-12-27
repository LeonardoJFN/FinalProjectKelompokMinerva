package com.umn.finalprojectkelompokminerva

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umn.finalprojectkelompokminerva.model.ChatModel

class ChatViewHolder(private val containerView: View, private val imageLoader: ImageLoader, private val onClickListener: OnClickListener) : RecyclerView.ViewHolder(containerView) {

    private val chatLastMessageView: TextView by lazy {
        containerView.findViewById(R.id.chat_last_message) }
    private val chatNameView: TextView by lazy {
        containerView.findViewById(R.id.chat_name) }
    private val chatPhotoView: ImageView by lazy {
        containerView.findViewById(R.id.chat_photo) }
    fun bindData(chat: ChatModel) {
        containerView.setOnClickListener {
            onClickListener.onClick(chat)
        }

        imageLoader.loadImage(chat.imageUrl, chatPhotoView)
        chatNameView.text = chat.chatName
        chatLastMessageView.text = chat.lastMessage
    }
    interface OnClickListener {
        fun onClick(chat: ChatModel)
    }

}