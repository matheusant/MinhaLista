package com.example.minhalista.ui.list.products

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.minhalista.R
import com.example.minhalista.data.db.ProductsEntity
import com.example.minhalista.databinding.ProductsListFragmentBinding

class ProductsListFragment : Fragment() {

    private lateinit var binding: ProductsListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductsListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setListeners()
    }

    private fun setupUI() {
        val prodAdapter = ProductAdapter(
            listOf(
                ProductsEntity(1, "Coca Cola", 9.49),
                ProductsEntity(2, "Fanta", 8.49),
                ProductsEntity(2, "Wafer", 3.49),
            )
        )

        binding.rvProductList.run {
            setHasFixedSize(true)
            adapter = prodAdapter
        }
    }

    private fun setListeners() {
        binding.fbAdd.setOnClickListener {
            findNavController().navigate(R.id.action_productsListFragment_to_productRegisterFragment)
        }
    }
}