package com.example.minhalista.ui.list.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.minhalista.R
import com.example.minhalista.data.db.AppDatabase
import com.example.minhalista.data.db.dao.ProductDAO
import com.example.minhalista.data.db.entity.ProductsEntity
import com.example.minhalista.databinding.ProductsListFragmentBinding
import com.example.minhalista.repository.DatabaseDataSource
import com.example.minhalista.repository.ProductRepository

class ProductsListFragment : Fragment() {

    private lateinit var binding: ProductsListFragmentBinding

    private val viewModel: ProductsListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val prodsDao: ProductDAO = AppDatabase.getDatabase(requireContext()).prodsDao
                val repository: ProductRepository = DatabaseDataSource(prodsDao)
                return ProductsListViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductsListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserves()
        setupUI()
        setListeners()
    }

    private fun setObserves() {
        viewModel.allProdsEvent.observe(viewLifecycleOwner) { getProds ->
            val prodAdapter = ProductAdapter(getProds)

            binding.rvProductList.run {
                setHasFixedSize(true)
                adapter = prodAdapter
            }
        }
    }

    private fun setupUI() {

    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllProds()
    }

    private fun setListeners() {
        binding.fbAdd.setOnClickListener {
            findNavController().navigate(R.id.action_productsListFragment_to_productRegisterFragment)
        }
    }
}