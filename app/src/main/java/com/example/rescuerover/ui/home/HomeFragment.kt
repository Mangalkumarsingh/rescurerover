package com.example.rescuerover.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.rescuerover.Adapter.rescueAdapter
import com.example.rescuerover.Model.model
import com.example.rescuerover.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var viewModel: HomeViewModel
    lateinit var adp: rescueAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        binding.progress1.visibility = View.VISIBLE
        loadAdoptData()


        _binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter1(newText!!)
                return false
            }
        })
    }


    fun loadAdoptData() {
        viewModel.adoptLiveData.observe(requireActivity(), Observer {

            binding.rescuerRecycle.layoutManager = LinearLayoutManager(requireContext())
            adp = rescueAdapter(requireContext(), it)
            binding.rescuerRecycle.adapter = adp
            binding.progress1.visibility = View.GONE

        })
        viewModel.adopterData()
    }

    private fun filter1(newText: String?) {
        var filterList = ArrayList<model>()
        viewModel.adoptLiveData.observe(requireActivity(), Observer {
            for (petName in it) {
                if (petName.aName?.toLowerCase()!!.contains(newText!!.toLowerCase())) {
                    filterList.add(petName)
                }
                if (filterList == null) {
                    Toast.makeText(requireContext(), "no data found", Toast.LENGTH_SHORT).show()
                } else {
                    adp.filterList(filterList)
                }
            }
        })

    }
}