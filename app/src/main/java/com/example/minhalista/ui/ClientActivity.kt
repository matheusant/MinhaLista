package com.example.minhalista.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.minhalista.R
import com.example.minhalista.databinding.ActivityClientBinding

class ClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appToolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_client_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfig = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfig)

        navController.addOnDestinationChangedListener { _, _, _ ->
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}