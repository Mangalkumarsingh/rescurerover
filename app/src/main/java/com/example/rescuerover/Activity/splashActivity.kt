package com.example.rescuerover.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.appcompat.app.AppCompatActivity

import com.example.rescuerover.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth

class splashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // val actionBar: ActionBar? = supportActionBar
//        actionBar!!.hide()
var auth=FirebaseAuth.getInstance().currentUser
        Handler(Looper.getMainLooper()).postDelayed({
            if(auth!=null){
                startActivity(Intent(this,selectActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this@splashActivity,loginActivity::class.java))
                finish()
            }


        },3000)

    }
}