package com.example.rescuerover.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rescuerover.Activity.callActivity
import com.example.rescuerover.Model.model
import com.example.rescuerover.databinding.ShowListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.io.IOException
import java.net.URL

class AdoptUserProduct(var context: Context, var data:ArrayList<model>):RecyclerView.Adapter<AdoptUserProduct.myViewHolder>()  {

    var model= model()
    var a: URL?=null
    inner class myViewHolder(var binding: ShowListBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdoptUserProduct.myViewHolder {
        return myViewHolder(ShowListBinding.inflate(LayoutInflater.from(context),parent,false))
    }





    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: AdoptUserProduct.myViewHolder, @SuppressLint("RecyclerView") position: Int) {

        var show=data[position]


        holder.binding.bread.text=show.aName
        holder.binding.location.text=show.location
        holder.binding.rescueName.text=show.pName
        holder.binding.date1.text= show.date.toString()

        Picasso.get().load(show.image).into(holder.binding.listImage)

        holder.binding.delete.setOnClickListener {
            // var a = data[position]
            var id = show.id



                var fir = FirebaseDatabase.getInstance().getReference("user").child(id!!)
                fir.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange( dataSnapshot: DataSnapshot) {
                        // remove the value at reference

                            dataSnapshot.ref.removeValue()
                        Toast.makeText(context, "data delete successfully", Toast.LENGTH_SHORT).show()
                            notifyItemRemoved(position)


                    }

                    override fun onCancelled( databaseError: DatabaseError) {}
                })

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}