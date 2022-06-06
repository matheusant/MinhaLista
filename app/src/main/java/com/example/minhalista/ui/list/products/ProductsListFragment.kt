package com.example.minhalista.ui.list.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.minhalista.HomeActivity
import com.example.minhalista.R
import com.example.minhalista.data.db.AppDatabase
import com.example.minhalista.data.db.entity.ProductsEntity
import com.example.minhalista.databinding.ProductsListFragmentBinding
import com.example.minhalista.extensions.navigateWithAnimations
import com.example.minhalista.repository.DatabaseDataSource
import com.example.minhalista.repository.ProductRepository
import com.example.minhalista.ui.ListActivity

class ProductsListFragment : Fragment() {

    private lateinit var binding: ProductsListFragmentBinding
    private var idClient: Long = 0
    private lateinit var products: List<ProductsEntity>

    private val viewModel: ProductsListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = AppDatabase.getDatabase(requireContext())
                val repository: ProductRepository = DatabaseDataSource(database)
                return ProductsListViewModel(repository) as T
            }
        }
    }

    private val args: ProductsListFragmentArgs by navArgs()

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
            val prodAdapter = ProductAdapter(getProds).apply {
                onItemClick = { prods ->
                    val directions = ProductsListFragmentDirections
                        .actionProductsListFragmentToProductRegisterFragment(prods)
                    findNavController().navigateWithAnimations(directions)
                }

                onItemLongClick = { prods ->
                    deleteProd(prods.id)
                }
            }

            binding.rvProductList.run {
                setHasFixedSize(true)
                adapter = prodAdapter
            }
        }

        viewModel.allClientsProdsEvent.observe(viewLifecycleOwner) { getClientsProds ->
            products = getClientsProds
            val cliAdapter = ProductAdapter(getClientsProds).apply {
                onItemClick = { prodsClient ->
                    Toast.makeText(requireContext(), prodsClient.name, Toast.LENGTH_SHORT).show()
                }
            }

            binding.rvProductList.run {
                setHasFixedSize(true)
                adapter = cliAdapter
            }
        }

        viewModel.deleteProdEvent.observe(viewLifecycleOwner) {
            refreshList()
        }
    }

    private fun refreshList() {
        if (idClient > 0) {
            viewModel.getClientProds(idClient)
            binding.fbCalc.visibility = View.VISIBLE
            (activity as ListActivity).supportActionBar?.title = "Produtos de ${args.clients?.name}"
        } else {
            viewModel.getAllProds()
        }
    }

    private fun setupUI() {
        args.clients?.id?.let {
            idClient = it
        }
    }

    fun calcProds(): Double {
        val pAdapter = ProductAdapter(products)
        val total = pAdapter.grandTotal()
        return total
    }

    private fun deleteProd(id: Long) {
        viewModel.deleteProd(id)
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun setListeners() {
        binding.fbAdd.setOnClickListener {
            if (idClient > 0) {
                val directions = ProductsListFragmentDirections
                    .actionProductsListFragmentToProductRegisterFragment(idClient = idClient)
                findNavController().navigateWithAnimations(directions)
            } else {
                findNavController().navigateWithAnimations(R.id.action_productsListFragment_to_productRegisterFragment)
            }
        }

        binding.fbCalc.setOnClickListener {
            val act = (activity as ListActivity)
            act.clientTotal = calcProds()
            act.clientID = idClient
        }
    }
}