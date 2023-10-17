package com.example.kotlin_pr3.UI.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_pr3.R
import com.example.kotlin_pr3.UI.StateHolder.Adapters.PhotoListAdapter
import com.example.kotlin_pr3.databinding.Fragment3Binding
import java.io.File

class Fragment3 : Fragment() {
    private lateinit var binding: Fragment3Binding
    private lateinit var adapter: PhotoListAdapter
    private val FILENAME = "Date.txt"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment3Binding.inflate(inflater, container, false)
        val view = binding.root

        adapter = PhotoListAdapter()

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val buttonToFragment1 = view.findViewById<Button>(R.id.button_back_fragment3)

        buttonToFragment1.setOnClickListener {
            findNavController().navigate(R.id.action_fragment32_to_fragment1)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPhotoList()
    }

    private fun loadPhotoList() {
        val picturesDir = File(requireContext().getExternalFilesDir(null), "Pictures/CameraX-Image")
        val file = File(picturesDir, FILENAME)

        if (file.exists()) {
            val lines = file.readLines()
            adapter.submitList(lines)
        }
    }
}

