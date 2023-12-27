package com.umn.finalprojectkelompokminerva.model

import java.sql.Time


data class ChatModel(
    val messageTime: Time,
    val chatName: String,
    val lastMessage: String,
    val imageUrl: String
)