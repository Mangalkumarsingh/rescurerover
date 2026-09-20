package com.example.rescuerover.ui.home

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rescuerover.Model.garbageModel
import com.example.rescuerover.Model.model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel() {

    var firebaseDatabase = FirebaseDatabase.getInstance()
    private var adoptMutualLiveData = MutableLiveData<ArrayList<model>>()
    private var rescueMutableLiveData = MutableLiveData<ArrayList<model>>()
    private var garbageMutableLiveData = MutableLiveData<ArrayList<garbageModel>>()
    var adoptLiveData: LiveData<ArrayList<model>> = adoptMutualLiveData
    var rescueLiveData: LiveData<ArrayList<model>> = rescueMutableLiveData
    var garbageLivedata: LiveData<ArrayList<garbageModel>> = garbageMutableLiveData


    fun adopterData() {
        var dbRef = firebaseDatabase.getReference("user")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = arrayListOf<model>()
                list.clear()
                for (postSnapShot in snapshot.children) {
                    var post = postSnapShot.getValue(model::class.java)
                    list.add(post!!)
                }
                adoptMutualLiveData.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun rescueData() {
        var dbRef = firebaseDatabase.getReference("rescue_data")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = arrayListOf<model>()
                list.clear()
                for (postSnapShot in snapshot.children) {
                    var post = postSnapShot.getValue(model::class.java)
                    list.add(post!!)
                }
                rescueMutableLiveData.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun loadGarbageData() {
        var dbRef = firebaseDatabase.getReference("garbage_data")


        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = arrayListOf<garbageModel>()
                list.clear()
                for (postSnapShot in snapshot.children) {
                    var post = postSnapShot.getValue(garbageModel::class.java)
                    list.add(post!!)

                }
                garbageMutableLiveData.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}