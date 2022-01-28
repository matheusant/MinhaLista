package com.example.minhalista

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.minhalista.data.MyPreferences
import com.example.minhalista.databinding.ActivityHomeBinding
import com.example.minhalista.ui.ClientActivity
import com.example.minhalista.ui.ListActivity

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
        binding.btnChangeTheme.setOnClickListener { chooseThemeDialog() }
        binding.btnNext.setOnClickListener { nextActivity(ClientActivity::class.java) }
//        checboxChoseTheme()
    }

    private fun nextActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
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