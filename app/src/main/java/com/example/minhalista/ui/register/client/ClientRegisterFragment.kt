package com.example.minhalista.ui.register.client

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minhalista.R

class ClientRegisterFragment : Fragment() {

    companion object {
        fun newInstance() = ClientRegisterFragment()
    }

    private lateinit var viewModel: ClientRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.client_register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClientRegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}