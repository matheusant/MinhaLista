package com.example.minhalista

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.minhalista.data.MyPreferences
import com.example.minhalista.databinding.ActivityHomeBinding
import com.example.minhalista.extensions.navigateSafe
import com.example.minhalista.extensions.navigateWithAnimations
import com.example.minhalista.ui.home.HomeFragmentDirections
import com.example.minhalista.ui.list.client.ClientListFragment
import com.example.minhalista.ui.list.products.ProductsListFragment
import meow.bottomnavigation.MeowBottomNavigation

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    var clientTotal: Double = 0.0
    var clientID: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        checkTheme()
        setupUI()
    }

    private fun setupUI() {
//        binding.btnChangeTheme.setOnClickListener { chooseThemeDialog() }
//        binding.btnNext.setOnClickListener { nextActivity(ListActivity::class.java) }

        setSupportActionBar(binding.homeToolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fv_container) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfig = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfig)

        navController.addOnDestinationChangedListener { _, _, _ ->
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        }

        val bottomNav = binding.bottomNav

        bottomNav.add(MeowBottomNavigation.Model(1, R.drawable.ic_add, "Home"))
        bottomNav.add(MeowBottomNavigation.Model(2, R.drawable.ic_delete_24, "Produtos"))
        bottomNav.add(MeowBottomNavigation.Model(3, R.drawable.ic_back, "Clientes"))

//        loadFragment(ProductsListFragment())
        bottomNav.show(3, true)

        bottomNav.setOnShowListener {
            var fragment: Fragment? = null
            var directions: Int = R.id.action_homeFragment_to_productsListFragment
            when (it.id) {
                1 -> {
                    navController.navigateUp()
                    directions = R.id.action_homeFragment_to_productsListFragment
                }
                2 -> {
                    navController.navigateUp()
                    directions = R.id.action_homeFragment_to_clientListFragment2
                }
            }
            navController.navigateSafe(directions)
//            loadFragment(fragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    private fun loadFragment(fragment: Fragment?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fv_container, fragment!!, fragment.tag)
            .commit()
    }

    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Escolha um tema")
        val styles = arrayOf("Padrão do Sistema", "Escuro", "Claro")
        val checkedItem = MyPreferences(this).darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode = 1
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode = 2
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
            }

        }

        val dialog = builder.create()
        dialog.show()
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