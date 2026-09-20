package com.example.rescuerover.Activity.garbage.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rescuerover.Adapter.garbageAdapter
//import com.example.rescuerover.Adapter.ItemDeleteListener
import com.example.rescuerover.Adapter.rescueAdapter
import com.example.rescuerover.Model.garbageModel
import com.example.rescuerover.Model.model
import com.example.rescuerover.R
import com.example.rescuerover.databinding.FragmentHomeGarbageBinding
import com.example.rescuerover.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class homeGarbageFragment : Fragment() {

    lateinit var binding: FragmentHomeGarbageBinding
    lateinit var viewModel: HomeViewModel
    lateinit var adp: garbageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeGarbageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        binding.progress1.visibility = View.VISIBLE
        garbageLoadData()
        binding.progress1.visibility = View.GONE


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                fliter1(newText!!)
                return false
            }
        })
    }

    private fun fliter1(text: String) {
        var newList = ArrayList<garbageModel>()
        viewModel.garbageLivedata.observe(requireActivity(), Observer {
            for (post in it) {
                if (post.location!!.toUpperCase().contains(text.toUpperCase())) {
                    newList.add(post!!)
                }
                if (newList == null) {
                    Toast.makeText(requireContext(), "no data found", Toast.LENGTH_SHORT).show()
                } else {
                    adp.filterList(newList)
                }
            }
        })
    }

    fun garbageLoadData() {
        viewModel.garbageLivedata.observe(requireActivity(), Observer {
            binding.garbagelRecycle.layoutManager = LinearLayoutManager(requireContext())
            adp = garbageAdapter(requireContext(), it)
            binding.garbagelRecycle.adapter = adp
        })
        viewModel.loadGarbageData()
    }
}