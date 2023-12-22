package com.umn.finalprojectkelompokminerva

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
//    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar))
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    as NavHostFragment
        val navController = navHostFragment.navController
//Creating top level destinations
//and adding them to the draw
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.profileFragment, R.id.matchFragment, R.id.likesFragment, R.id.chatFragment
//            ), findViewById(R.id.drawer_layout)
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        findViewById<NavigationView>(R.id.nav_view)
//            ?.setupWithNavController(navController)
// Added this part only
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            ?.setupWithNavController(navController)
    }
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) ||
//                super.onSupportNavigateUp()
//    }
}
