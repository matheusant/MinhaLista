package com.example.minhalista.ui.list.client

import android.app.AlertDialog
import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.minhalista.R
import com.example.minhalista.data.db.AppDatabase
import com.example.minhalista.databinding.ClientListFragmentBinding
import com.example.minhalista.extensions.navigateWithAnimations
import com.example.minhalista.repository.ClientRepository
import com.example.minhalista.repository.DatabaseDataSource
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.lang.Exception

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
                    gerarQrCode(it.name, it.date)
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
    }

    private fun gerarQrCode(name: String, date: String) {
        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix: BitMatrix
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap

        try {
            bitMatrix = multiFormatWriter.encode("Nome: $name \nData: $date", BarcodeFormat.QR_CODE,300,300)
            bitmap = barcodeEncoder.createBitmap(bitMatrix)
            showDialog(bitmap)
        } catch (ex: Exception) {

        }
    }

    private fun showDialog(bitmap: Bitmap?) {
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_qr_code, null, false)
        val dialog = AlertDialog.Builder(activity).setView(dialogView)

        val image = dialogView.findViewById<ImageView>(R.id.iv_qr)
        image.setImageBitmap(bitmap)
        val dd: AlertDialog = dialog.create()
        dd.show()
    }

    private fun deleteClient(id: Long) {
        viewModel.deleteClient(id)
    }

    private fun refreshList() = viewModel.getAllClients()

    override fun onResume() {
        super.onResume()
        refreshList()
    }
}