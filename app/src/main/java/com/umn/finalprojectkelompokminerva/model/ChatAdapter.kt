package com.umn.finalprojectkelompokminerva.model

import android.view.LayoutInflater
import android.view.ViewGroup
//import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.umn.finalprojectkelompokminerva.ImageLoader
import com.umn.finalprojectkelompokminerva.ChatViewHolder
import com.umn.finalprojectkelompokminerva.R

class ChatAdapter(private val layoutInflater: LayoutInflater, private val imageLoader: ImageLoader, private val onClickListener: OnClickListener ) : RecyclerView.Adapter<ChatViewHolder>() {
    //Delete Callback Instantiation
//    val swipeToDeleteCallback = SwipeToDeleteCallback()
    //Mutable list for storing all the list data
    private val chats = mutableListOf<ChatModel>()
    //A function to set the mutable list
    fun setData(newChats: List<ChatModel>) {
        chats.clear()
        chats.addAll(newChats)
        //This is used to tell the adapter that there's a data change in the mutable list
        notifyDataSetChanged()
    }
//    fun removeItem(position: Int) {
//        chats.removeAt(position)
//        notifyItemRemoved(position)
//    }

    //A view holder is used to bind the data to the layout views
    //onCreateViewHolder is instantiating the view holder it self
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = layoutInflater.inflate(R.layout.chat_list, parent, false)
        return ChatViewHolder(view, imageLoader, object:
            ChatViewHolder.OnClickListener{
            //Here we are passing the onClickListener function to the View Holder
            override fun onClick(chat: ChatModel) =
                //Here we are using the onClickListener passed from the MainActivity
                onClickListener.onItemClick(chat)
        })
    }
    //This is used to get the amount of data/item in the list
    override fun getItemCount() = chats.size
    //This is used to bind each data to each layout views
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        //The holder parameter stores our previously created ViewHolder
        //The holder.bindData function is declared in the ChatViewHolder
        holder.bindData(chats[position])
    }

    //Declare an onClickListener interface
    interface OnClickListener {
        fun onItemClick(chat: ChatModel)
    }
    //You can declare a class inside a class using the inner keyword
    //Declare a class for the swipe functionality
//    inner class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(0,
//        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//        //This is used if item lists can be moved
//        //Since we don't need that, we can set to false
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean = false
//        //This is used to determine which directions are allowed
//        override fun getMovementFlags(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder
//        ) = if (viewHolder is ChatViewHolder) {
//            //Here, if we're not touching our phone, left and right are allowed
//            makeMovementFlags(
//                ItemTouchHelper.ACTION_STATE_IDLE,
//                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//                //Here, if we're swiping our phone, left and right are allowed
//            ) or makeMovementFlags(
//                ItemTouchHelper.ACTION_STATE_SWIPE,
//                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//            )
//            //Other gestures are not allowed (Drag, etc.)
//        } else {
//            0
//        }
//        //This is used for swipe detection
//        //If a swipe is detected, then remove item
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            val position = viewHolder.adapterPosition
//            removeItem(position)
//        }
//    }


}