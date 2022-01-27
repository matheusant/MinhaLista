package com.example.minhalista.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minhalista.R
import com.example.minhalista.databinding.ProductRegisterFragmentBinding
import com.example.minhalista.databinding.ProductsListFragmentBinding

class ProductRegisterFragment : Fragment() {

    private lateinit var binding: ProductRegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductRegisterFragmentBinding.inflate(layoutInflater)
        return binding.root
    }
}