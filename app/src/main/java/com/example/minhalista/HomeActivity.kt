package com.example.minhalista

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.minhalista.data.MyPreferences
import com.example.minhalista.databinding.ActivityHomeBinding
import com.example.minhalista.ui.SettingsFragment
import com.example.minhalista.ui.ShoppingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        checkTheme()
        setupUI()
    }

    private fun setupUI() {

        val bottomNav = binding.bottomNav
        bottomNav.add(MeowBottomNavigation.Model(1, R.drawable.ic_monetization_on_24))
        bottomNav.add(MeowBottomNavigation.Model(2, R.drawable.ic_settings_24))
        bottomNav.add(MeowBottomNavigation.Model(3, R.drawable.ic_map_24))
        bottomNav.add(MeowBottomNavigation.Model(4, R.drawable.ic_shopping_cart_24))
        bottomNav.add(MeowBottomNavigation.Model(5, R.drawable.ic_notifications_24))

        bottomNav.setCount(5, "15")

        bottomNav.setOnShowListener {
            var fragment: Fragment? = null
            when (it.id) {
                1 -> fragment = ShoppingFragment()
                2 -> fragment = SettingsFragment()
                3 -> fragment = ShoppingFragment()
                4 -> fragment = SettingsFragment()
                5 -> fragment = ShoppingFragment()
            }
            loadFragment(fragment)
        }
        bottomNav.show(3, true)

        bottomNav.setOnClickMenuListener {
//            Toast.makeText(this, "Você clicou em ${it.id}", Toast.LENGTH_SHORT).show()
        }

        bottomNav.setOnReselectListener {
//            Toast.makeText(this, "Você re-clicou em ${it.id}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadFragment(fragment: Fragment?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fv_container, fragment!!, null)
            .commit()
    }

//    private fun checboxChoseTheme() {
//
//        switchCheckedMode()
//
//        binding.swChangeTheme.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                MyPreferences(this).darkMode = 1
//                MyPreferences(this).switchState = true
//                delegate.applyDayNight()
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                MyPreferences(this).darkMode = 2
//                MyPreferences(this).switchState = false
//                delegate.applyDayNight()
//            }
//        }
//    }

//    private fun switchCheckedMode() {
//        when(MyPreferences(this).switchState) {
//            true -> {
//                binding.swChangeTheme.isChecked = true
//            }
//            false -> {
//                binding.swChangeTheme.isChecked = false
//            }
//        }
//    }

    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
        }
    }
}