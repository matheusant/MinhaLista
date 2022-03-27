package com.example.minhalista.ui.list.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.minhalista.R
import com.example.minhalista.data.db.AppDatabase
import com.example.minhalista.databinding.ClientListFragmentBinding
import com.example.minhalista.extensions.navigateWithAnimations
import com.example.minhalista.repository.ClientRepository
import com.example.minhalista.repository.DatabaseDataSource
import com.example.minhalista.ui.ListActivity

class ClientListFragment : Fragment() {

    private lateinit var binding: ClientListFragmentBinding

    private val viewModel: ClientListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = AppDatabase.getDatabase(requireContext())
                val repository: ClientRepository = DatabaseDataSource(database)
                return ClientListViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ClientListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fbAdd.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_clientListFragment_to_clientRegisterFragment)
        }

        setObserves()
    }

    private fun setObserves() {
        viewModel.allClientEventData.observe(viewLifecycleOwner) { getClients ->
            val clientAdapter = ClientAdapter(getClients).apply {
                onItemLongClick = {
                    deleteClient(it.id)
                }

                onItemClick = {
                    val directions = ClientListFragmentDirections.actionClientListFragmentToProductsListFragment(it)
                    findNavController().navigateWithAnimations(directions)
                }
            }

            binding.rvClientList.run {
                setHasFixedSize(true)
                adapter = clientAdapter
            }
        }

        viewModel.deleteEventData.observe(viewLifecycleOwner) {
            refreshList()
        }

        viewModel.updateEventData.observe(viewLifecycleOwner) {
            refreshList()
        }
    }

    private fun deleteClient(id: Long) {
        viewModel.deleteClient(id)
    }

    private fun refreshList() = viewModel.getAllClients()

    private fun updateTotal(total: Double, id: Long) {
        viewModel.updateTotal(total, id)
    }

    private fun refreshTotal() {
        val act = (activity as ListActivity)
        val cID = act.clientID
        val cTotal = act.clientTotal
        updateTotal(cTotal, cID)
    }

    override fun onResume() {
        super.onResume()
        refreshList()
        refreshTotal()
    }
}