package com.example.rescuerover.Activity.rescue

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.rescuerover.Activity.dataInputActivity
import com.example.rescuerover.Activity.loginActivity
import com.example.rescuerover.R
import com.example.rescuerover.databinding.ActivityRescueBinding
import com.google.firebase.auth.FirebaseAuth

class rescueActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRescueBinding
    lateinit var auth: FirebaseAuth
    var RESCUE_DATA="12"

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRescueBinding.inflate(layoutInflater)
        setContentView(binding.root)
      auth=FirebaseAuth.getInstance()
        setSupportActionBar(binding.appBarRescue.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_rescue)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeRescueFragment, R.id.profileRescueFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.rescue, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.logout->{
                auth.signOut()
                startActivity(Intent(this, loginActivity::class.java))
                true
            }
            R.id.rescueAdd->{
                var intent= Intent(this, rescueInputActivity::class.java)
                intent.putExtra("rescueId",RESCUE_DATA)
                startActivity(intent)
                true
            }


            else->{ super.onOptionsItemSelected(item) }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_rescue)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}