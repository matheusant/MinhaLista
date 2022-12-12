package com.example.minhalista.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.minhalista.data.MyPreferences
import com.example.minhalista.databinding.FragmentSettingsBinding

class SettingsFragment: Fragment() {

    private val binding: FragmentSettingsBinding by lazy {
        FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.btnChangeTheme.setOnClickListener { chooseThemeDialog() }
        binding.btnNext.setOnClickListener { nextActivity(ListActivity::class.java) }
    }

    private fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Escolha um tema")
        val styles = arrayOf("Padrão do Sistema", "Escuro", "Claro")
        val checkedItem = MyPreferences(requireContext()).darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(requireContext()).darkMode = 0
                    (activity as AppCompatActivity?)!!.delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(requireContext()).darkMode = 1
                    (activity as AppCompatActivity?)!!.delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(requireContext()).darkMode = 2
                    (activity as AppCompatActivity?)!!.delegate.applyDayNight()
                    dialog.dismiss()
                }
            }

        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun nextActivity(activity: Class<*>) {
        val intent = Intent(requireContext(), activity)
        startActivity(intent)
    }
}