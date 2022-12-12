package com.example.minhalista.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.minhalista.databinding.FragmentShoppingBinding

class ShoppingFragment: Fragment() {

    private val binding: FragmentShoppingBinding by lazy {
        FragmentShoppingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

}