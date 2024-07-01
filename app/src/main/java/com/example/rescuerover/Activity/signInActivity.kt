package com.example.rescuerover.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.rescuerover.Model.Register
import com.example.rescuerover.R
import com.example.rescuerover.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class signInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    lateinit var auth: FirebaseAuth
    lateinit var dbRef:DatabaseReference

    var register=Register()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
auth=FirebaseAuth.getInstance()

        binding.backToLogin.setOnClickListener {
            startActivity(Intent(this,loginActivity::class.java))
        }

       binding.signBtn.setOnClickListener {

           auth.createUserWithEmailAndPassword(binding.signEmail.text.toString(),binding.signPassword.text.toString()).addOnCompleteListener {
               if (it.isSuccessful){
                   loadData()
               }
           }
       }


        binding.signImage.setOnClickListener {
            var intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }

    }

    private fun loadData() {
        var z=FirebaseAuth.getInstance().currentUser?.uid
        var user=FirebaseAuth.getInstance().currentUser!!.uid
        register.userId=z.toString()
        register.name=binding.signName.text.toString()
        register.email=binding.signEmail.text.toString()
        register.password=binding.signPassword.text.toString()
       dbRef=FirebaseDatabase.getInstance().getReference("register_users")
        dbRef.child(user).setValue(register).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this, "setData", Toast.LENGTH_SHORT).show()

            }
            else
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        }

    }
    var select: Uri?=null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK){
            select=data!!.data
            binding.signImage.setImageURI(select)
            register.image=select.toString()
            loadImage()
        }

    }

    private fun loadImage() {
        if(select==null) return
        var file= UUID.randomUUID().toString()
       // binding.progressBar2.visibility= View.VISIBLE
        var ref= Firebase.storage.reference.child("register_images/$file")
        ref.putFile(select!!).addOnSuccessListener {
          //  binding.progressBar2.visibility= View.INVISIBLE
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            ref.downloadUrl.addOnSuccessListener {


            }
        }
    }
}