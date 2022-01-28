package com.example.minhalista.ui.list.client

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.minhalista.R
import com.example.minhalista.data.db.entity.ClientEntity
import com.example.minhalista.databinding.ClientListFragmentBinding
import com.example.minhalista.extensions.navigateWithAnimations

class ClientListFragment : Fragment() {

    private lateinit var binding: ClientListFragmentBinding

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

        val clientAdapter = ClientAdapter(
            listOf(
                ClientEntity(1, "Matheus", 150.55, "18/07/2020 16:33:14"),
                ClientEntity(2, "Maria", 350.55, "21/04/2021 16:33:14"),
                ClientEntity(3, "Josué", 3050.55, "15/10/2021 16:33:14"),
            )
        )

        binding.rvClientList.run {
            setHasFixedSize(true)
            adapter = clientAdapter
        }
    }
}