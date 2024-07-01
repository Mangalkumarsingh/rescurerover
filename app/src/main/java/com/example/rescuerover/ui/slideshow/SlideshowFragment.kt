package com.example.rescuerover.ui.slideshow

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rescuerover.Adapter.AdoptUserProduct
import com.example.rescuerover.Model.Register
import com.example.rescuerover.Model.model
import com.example.rescuerover.databinding.FragmentSlideshowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    lateinit var dbRef:DatabaseReference
    var arrayData=ArrayList<model>()
    var model1=model()
    lateinit var adp:AdoptUserProduct
    var register=Register()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // Toast.makeText(requireContext(), "mangal${productUserId}", Toast.LENGTH_LONG).show()
        _binding?.userProductRecycle?.layoutManager=LinearLayoutManager(requireContext())
        adp= AdoptUserProduct(requireContext(),arrayData)
        _binding?.userProductRecycle?.adapter=adp
        dbRef=FirebaseDatabase.getInstance().reference
        dbRef.child("user").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
               // var productUserId=snapshot.children

            arrayData.clear()
                for (postSnapshot in snapshot.children){
                    var productUserId=postSnapshot.child("productuserId").getValue<String>()
                     Log.d("oneMangal", "onViewCreated: ${productUserId}")
                    var collectedData=postSnapshot.getValue(model::class.java)
                    if (FirebaseAuth.getInstance().currentUser?.uid!!.equals(productUserId)){
                        arrayData.add(collectedData!!)
                    }
                }
adp.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
               // TODO("Not yet implemented")
            }
        })



    }

}
