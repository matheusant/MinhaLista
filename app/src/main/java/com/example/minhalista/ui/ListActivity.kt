package com.example.minhalista.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minhalista.databinding.ActivityListBinding

class ListActivity: AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}