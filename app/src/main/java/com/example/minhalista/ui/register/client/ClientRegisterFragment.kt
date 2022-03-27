package com.example.minhalista.ui.register.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.minhalista.data.db.AppDatabase
import com.example.minhalista.databinding.ClientRegisterFragmentBinding
import com.example.minhalista.extensions.hideKeyboard
import com.example.minhalista.repository.ClientRepository
import com.example.minhalista.repository.DatabaseDataSource
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class ClientRegisterFragment : Fragment() {

    private lateinit var binding: ClientRegisterFragmentBinding

    private val viewModel: ClientRegisterViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = AppDatabase.getDatabase(requireContext())
                val repository: ClientRepository = DatabaseDataSource(database)
                return ClientRegisterViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ClientRegisterFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserves()
        setupUI()
    }

    private fun setObserves() {
        viewModel.cliStateEventData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ClientRegisterViewModel.ClientState.Inserted -> {
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

    private fun setupUI() {
        binding.btnRegisterClient.setOnClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val currentDate = sdf.format(Date())
            val name = binding.etCliName.text.toString()

            viewModel.addClient(name, currentDate)
        }
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }
}