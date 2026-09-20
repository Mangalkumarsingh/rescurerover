package com.example.rescuerover.Activity.rescue.ui

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

import com.example.rescuerover.Adapter.rescueAdapter
import com.example.rescuerover.Model.model
import com.example.rescuerover.R
import com.example.rescuerover.databinding.FragmentHomeBinding
import com.example.rescuerover.databinding.FragmentHomeRescueBinding
import com.example.rescuerover.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class homeRescueFragment : Fragment() {
    lateinit var binding: FragmentHomeRescueBinding

    lateinit var auth: FirebaseAuth
    lateinit var viewModel: HomeViewModel
    lateinit var adp: rescueAdapter

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
        binding = FragmentHomeRescueBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        binding.progress1.visibility = View.VISIBLE
        loadRescueData()
        binding.progress1.visibility = View.GONE

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
            filter12(newText!!)
                return false
            }
        })

    }

    private fun filter12(text:String) {
        var newList=ArrayList<model>()
         viewModel.rescueLiveData.observe(requireActivity(), Observer {

             for (post in it){
                 if (post.aName?.toLowerCase()!!.contains(text!!.toLowerCase())) {
                     newList.add(post)
                 }
                 if (newList == null) {
                     Toast.makeText(requireContext(), "no data found", Toast.LENGTH_SHORT).show()
                 } else {
                     adp.filterList(newList)
                 }
             }
         })
    }

    fun loadRescueData() {
        viewModel.rescueLiveData.observe(requireActivity(), Observer {
            binding.RescueAnimalRecycle.layoutManager = LinearLayoutManager(requireContext())
            adp = rescueAdapter(requireContext(), it)
            binding.RescueAnimalRecycle.adapter = adp
        })
        viewModel.rescueData()
    }
}