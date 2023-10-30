package com.example.finalprojectkelompokminerva

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MatchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)

        toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MatchAdapter()

        val fab: FloatingActionButton = view.findViewById(R.id.fab_filter)
        fab.setOnClickListener { view ->
            val popup = PopupMenu(context, view)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.filter_menu, popup.menu)//ganti R.menu.filter_menu, popup.menu dengan menu anda sendiri
            popup.show()
        }

        return view
    }
