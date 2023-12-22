package com.umn.finalprojectkelompokminerva

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
//import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umn.finalprojectkelompokminerva.model.*

class ChatFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        navController = requireActivity().findNavController(R.id.nav_host_fragment)
        // Initialize the chatAdapter here
        chatAdapter = ChatAdapter(
            layoutInflater,
            GlideImageLoader(requireContext()),
            object : ChatAdapter.OnClickListener {
                override fun onItemClick(chat: ChatModel) {
                    showSelectionDialog(chat)
                }
            }
        )
        //Setup the adapter for the recycler view
        recyclerView.adapter = chatAdapter
        //Setup the layout manager for the recycler view
        //A layout manager is used to set the structure of the item views
        recyclerView.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)
        //Instantiate ItemTouchHelper for the swipe to delete callback and
        //attach it to the recycler view
//        val itemTouchHelper = ItemTouchHelper(chatAdapter.swipeToDeleteCallback)
//        itemTouchHelper.attachToRecyclerView(recyclerView)
        //add data to the model list in the adapter
        chatAdapter.setData(
            listOf(
                ChatModel(
                    "Ashley",
                    "Hi",
                    "https://cdn2.thecatapi.com/images/7dj.jpg"
                ),
                ChatModel(
                    "Kate",
                    "Hello",
                    "https://cdn2.thecatapi.com/images/7dj.jpg"
                ),
                ChatModel(
                    "Jane",
                    "Holla",
                    "https://cdn2.thecatapi.com/images/7dj.jpg"
                ),
                ChatModel(
                    "Mei",
                    "Ni Hao",
                    "https://cdn2.thecatapi.com/images/7dj.jpg"
                )
            )
        )


    }

    //This will create a pop up dialog when one of the items from the recycler view is clicked.
    private fun showSelectionDialog(chat: ChatModel) {
        val bundle = Bundle()
        bundle.putString("chatName", chat.chatName)
        navController.navigate(R.id.action_chatFragment_to_chatDetailFragment,bundle)
    }
}