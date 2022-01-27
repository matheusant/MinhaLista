package com.example.minhalista.ui.register.products

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.minhalista.data.db.AppDatabase
import com.example.minhalista.data.db.dao.ProductDAO
import com.example.minhalista.databinding.ProductRegisterFragmentBinding
import com.example.minhalista.extensions.hideKeyboard
import com.example.minhalista.repository.DatabaseDataSource
import com.example.minhalista.repository.ProductRepository
import com.google.android.material.snackbar.Snackbar

class ProductRegisterFragment : Fragment() {

    private lateinit var binding: ProductRegisterFragmentBinding

    private val viewModel: ProductsRegisterViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val prodsDao: ProductDAO = AppDatabase.getDatabase(requireContext()).prodsDao
                val repository: ProductRepository = DatabaseDataSource(prodsDao)
                return ProductsRegisterViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductRegisterFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserves()
        setListeners()
    }

    private fun setListeners() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val price = binding.etPrice.text.toString()

            viewModel.addOrUpdateProducts(name, price.toDouble())
        }
    }

    private fun setObserves() {
        viewModel.subsStateEventData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProductsRegisterViewModel.ProdState.Inserted,
                is ProductsRegisterViewModel.ProdState.Update -> {
                    clearFields()
                    hideKeyboard()
                    requireView().requestFocus()

                    findNavController().popBackStack()
                }
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        binding.etName.text?.clear()
        binding.etPrice.text?.clear()
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }
}