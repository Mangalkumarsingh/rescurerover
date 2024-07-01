package com.example.rescuerover.Activity.rescue.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.rescuerover.Adapter.rescueAdapter
import com.example.rescuerover.Model.model
import com.example.rescuerover.R
import com.example.rescuerover.databinding.FragmentHomeBinding
import com.example.rescuerover.databinding.FragmentHomeRescueBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class homeRescueFragment : Fragment()  {
lateinit var binding:FragmentHomeRescueBinding

    lateinit var auth: FirebaseAuth
    lateinit var dRef: DatabaseReference
    lateinit var adp: rescueAdapter
    var array= ArrayList<model>()

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
        binding=FragmentHomeRescueBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progress1.visibility=View.VISIBLE
        auth=FirebaseAuth.getInstance()
        array=arrayListOf()
        binding.RescueAnimalRecycle.layoutManager= LinearLayoutManager(requireContext())
        adp= rescueAdapter(requireContext(),array)
        binding.RescueAnimalRecycle.adapter=adp

        dRef= FirebaseDatabase.getInstance().getReference("rescue_data")


        dRef.addValueEventListener(object: ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                array.clear()
                for (postSnapShot in snapshot.children){
                    var post=postSnapShot.getValue(model::class.java)
                    array.add(post!!)

                }
                adp.notifyDataSetChanged()
                binding.progress1.visibility=View.INVISIBLE
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

//    override fun onItemDelete(position: Int) {
//        adp.deleteData(position)
//    }

}