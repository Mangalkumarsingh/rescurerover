package com.example.rescuerover.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.rescuerover.Activity.garbage.garbageActivity
import com.example.rescuerover.Activity.rescue.rescueActivity
import com.example.rescuerover.MainActivity
import com.example.rescuerover.databinding.ActivitySelectBinding

class selectActivity : AppCompatActivity() {
    lateinit var binding: ActivitySelectBinding

    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardView2.setOnClickListener {
          //  startActivity(Intent(this,MainActivity::class.java))
            var intent=Intent(this,MainActivity::class.java)
            intent.putExtra("adoptId","11")
            startActivity(intent)
        }
        binding.cardView3.setOnClickListener {
            var intent=Intent(this,rescueActivity::class.java)
            intent.putExtra("rescuetId","12")
            startActivity(intent)
        }
        binding.cardView4.setOnClickListener {
            var intent=Intent(this,garbageActivity::class.java)
            intent.putExtra("garbageId","13")
            startActivity(intent)
        }
    }

    override fun onBackPressed() {

        if(doubleBackToExitPressedOnce){
            finishAffinity()
            super.onBackPressed()

        }
        doubleBackToExitPressedOnce=true
        Toast.makeText(this, "double press to exit...", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({doubleBackToExitPressedOnce=false},2000)

    }
}