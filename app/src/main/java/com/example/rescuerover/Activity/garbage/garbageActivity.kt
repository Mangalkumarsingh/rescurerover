package com.example.rescuerover.Activity.garbage

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
import com.example.rescuerover.databinding.ActivityGarbageBinding
import com.google.firebase.auth.FirebaseAuth

class garbageActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityGarbageBinding
    var GARBAGE_DATA="13"
lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGarbageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarGarbage.toolbar)
        auth=FirebaseAuth.getInstance()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_garbage)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeGarbageFragment, R.id.profileGarbageFragment), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.garbage, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.logout->{
                auth.signOut()
                startActivity(Intent(this, loginActivity::class.java))
                true
            }
            R.id.garbageAdd->{
                var intent=Intent(this,garbageInputActivity::class.java)
                intent.putExtra("garbageId",GARBAGE_DATA)
                startActivity(intent)
                true
            }


            else->{ super.onOptionsItemSelected(item) }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_garbage)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}