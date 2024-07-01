package com.example.rescuerover.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rescuerover.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class loginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
auth=FirebaseAuth.getInstance()

        binding.goToLogin.setOnClickListener {
            startActivity(Intent(this@loginActivity,signInActivity::class.java))
        }
//authentication code


    binding.loginBtn.setOnClickListener {
        auth.signInWithEmailAndPassword(binding.loginEmail.text.toString(),binding.loginPassword.text.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                startActivity(Intent(this,selectActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this, "wrong email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    }
}