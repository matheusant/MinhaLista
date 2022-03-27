package com.example.minhalista.ui.list.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhalista.data.db.AppDatabase
import com.example.minhalista.data.db.entity.ClientEntity
import com.example.minhalista.repository.ClientRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ClientListViewModel(
    private val client: ClientRepository
) : ViewModel() {

    private val _allClientEventData = MutableLiveData<List<ClientEntity>>()
    val allClientEventData: LiveData<List<ClientEntity>>
        get() = _allClientEventData

    private val _deleteEventData = MutableLiveData<Unit>()
    val deleteEventData: LiveData<Unit>
        get() = _deleteEventData

    private val _updateEventData = MutableLiveData<Unit>()
    val updateEventData: LiveData<Unit>
        get() = _updateEventData

    fun getAllClients() = viewModelScope.launch {
        _allClientEventData.postValue(client.getAllClients())
    }

    fun updateTotal(total: Double, id: Long) = viewModelScope.launch {
        _updateEventData.postValue(client.updateClient(total, id))
    }

    fun deleteClient(id: Long) = viewModelScope.launch {
        try {
            if (id > 0) {
                _deleteEventData.postValue(client.deleteClient(id))
            }
        } catch (ex: Exception) {

        }
    }
}