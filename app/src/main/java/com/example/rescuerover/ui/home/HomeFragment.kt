package com.example.rescuerover.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
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


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var auth: FirebaseAuth
    lateinit var dRef: DatabaseReference
    lateinit var adp: rescueAdapter
    //scrolling ke liye


    var array= ArrayList<model>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)


        _binding = FragmentHomeBinding.inflate(inflater, container, false)





        val root: View = binding.root


        homeViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        auth = FirebaseAuth.getInstance()
        array = arrayListOf()
        if (array == null) {
            binding.progress1.visibility = View.GONE
        } else {


            binding.progress1.visibility = View.VISIBLE
            binding.rescuerRecycle.layoutManager = LinearLayoutManager(requireContext())
            adp = rescueAdapter(requireContext(), array)

            binding.rescuerRecycle.adapter = adp


            dRef = FirebaseDatabase.getInstance().getReference("user")


            dRef.addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    array.clear()
                    for (postSnapShot in snapshot.children) {
                        var post = postSnapShot.getValue(model::class.java)
                        array.add(post!!)

                    }
                    adp.notifyDataSetChanged()
                    binding.progress1.visibility = View.INVISIBLE
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        }
    }


}