package com.example.finalprojectkelompokminerva

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectkelompokminerva.model.ChatModel

class ChatViewHolder(private val containerView: View, private val imageLoader: ImageLoader, private val onClickListener: OnClickListener) : RecyclerView.ViewHolder(containerView) {
    //containerView is the container layout of each item list
    //Here findViewById is used to get the reference of each views inside the container
    private val chatLastMessageView: TextView by lazy {
        containerView.findViewById(R.id.chat_last_message) }
//    private val messageTimeView: TextView by lazy {
//        containerView.findViewById(R.id.message_time) }
    private val chatNameView: TextView by lazy {
        containerView.findViewById(R.id.chat_name) }
    private val chatPhotoView: ImageView by lazy {
        containerView.findViewById(R.id.chat_photo) }
    //This function is called in the adapter to provide the binding function
    fun bindData(chat: ChatModel) {
        //Override the onClickListener function
        containerView.setOnClickListener {
            //Here we are using the onClickListener passed from the Adapter
            onClickListener.onClick(chat)
        }

        imageLoader.loadImage(chat.imageUrl, chatPhotoView)
        chatNameView.text = chat.chatName
        chatLastMessageView.text = chat.lastMessage
//        messageTime.text = chat.messageTime
    }
    //Declare an onClickListener interface
    interface OnClickListener {
        fun onClick(chat: ChatModel)
    }

}