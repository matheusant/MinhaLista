package com.example.minhalista.ui.register.products

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.minhalista.data.db.AppDatabase
import com.example.minhalista.databinding.ProductRegisterFragmentBinding
import com.example.minhalista.extensions.hideKeyboard
import com.example.minhalista.repository.DatabaseDataSource
import com.example.minhalista.repository.ProductRepository
import com.example.minhalista.ui.list.products.ProductAdapter
import com.google.android.material.snackbar.Snackbar

class ProductRegisterFragment : Fragment() {

    private lateinit var binding: ProductRegisterFragmentBinding
    private var idClient: Long = 0

    private val viewModel: ProductsRegisterViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = AppDatabase.getDatabase(requireContext())
                val repository: ProductRepository = DatabaseDataSource(database)
                return ProductsRegisterViewModel(repository) as T
            }
        }
    }

    private val args: ProductRegisterFragmentArgs by navArgs()

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
        setupUI()
        setListeners()
    }

    private fun setListeners() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val price = binding.etPrice.text.toString()

            viewModel.addOrUpdateProducts(name, price.toDouble(), args.products?.id ?: 0)
        }
    }

    private fun setObserves() {
        viewModel.prodsStateEventData.observe(viewLifecycleOwner) { state ->
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

        viewModel.messageEventData.observe(viewLifecycleOwner) { message ->
            Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.allProdsEvent.observe(viewLifecycleOwner) { getProds ->
            val cliAdapter = ProductAdapter(getProds).apply {
                onItemClick = { prods ->
                    addClientProduct(prods.name, prods.price, idClient)
                }
            }

            binding.rvItemsList.run {
                setHasFixedSize(true)
                adapter = cliAdapter
            }
        }
    }

    private fun setupUI() {
        idClient = args.idClient

        args.products?.let { products ->
            binding.btnRegister.text = "Atualizar"
            binding.etName.setText(products.name)
            binding.etPrice.setText(products.price.toString())
        }

        if (idClient > 0) {
            binding.clRegisterItem.visibility = View.GONE
            binding.rvItemsList.visibility = View.VISIBLE
        } else {
            binding.clRegisterItem.visibility = View.VISIBLE
            binding.rvItemsList.visibility = View.GONE
        }
    }

    private fun refreshList() {
        viewModel.getAllProds()
    }

    private fun addClientProduct(name: String, price: Double, idClient: Long) {
        viewModel.insertClientProds(name, price, idClient)
    }

    override fun onResume() {
        super.onResume()
        refreshList()
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