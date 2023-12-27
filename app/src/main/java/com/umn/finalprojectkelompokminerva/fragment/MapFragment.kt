package com.umn.finalprojectkelompokminerva.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.umn.finalprojectkelompokminerva.MapsActivity
import com.umn.finalprojectkelompokminerva.R
class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val btnShowMap = view.findViewById<Button>(R.id.btnShowMap)
        btnShowMap.setOnClickListener {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}