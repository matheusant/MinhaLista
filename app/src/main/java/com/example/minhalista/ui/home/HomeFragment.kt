package com.example.minhalista.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.minhalista.R
import com.example.minhalista.databinding.FragmentHomeBinding
import com.example.minhalista.extensions.navigateWithAnimations

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.btnClients.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_homeFragment_to_clientListFragment2)
        }

        binding.btnProducts.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_homeFragment_to_productsListFragment)
        }
    }

}