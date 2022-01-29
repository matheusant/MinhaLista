package com.example.minhalista.ui.register.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhalista.repository.ClientRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ClientRegisterViewModel(
    private val client: ClientRepository
) : ViewModel() {

    private val _cliStateEventData = MutableLiveData<ClientState>()
    val cliStateEventData: LiveData<ClientState>
        get() = _cliStateEventData

    private val _messageEventData = MutableLiveData<String>()
    val messageEventData: LiveData<String>
        get() = _messageEventData

    fun addClient(name: String, date: String) = viewModelScope.launch {
        try {
            val id = client.insertClient(name, date)
            if (id > 0) {
                _cliStateEventData.value = ClientState.Inserted
                _messageEventData.value = "Inserido com sucesso"
            }
        } catch (ex: Exception) {
            _messageEventData.value = "Erro"
        }
    }

    sealed class ClientState {
        object Inserted : ClientState()
    }
}