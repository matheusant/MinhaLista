package com.example.minhalista.ui.list.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhalista.data.db.entity.ProductsEntity
import com.example.minhalista.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductsListViewModel(
    private val prods: ProductRepository
) : ViewModel() {

    private val _allProdsEvent = MutableLiveData<List<ProductsEntity>>()
    val allProdsEvent: LiveData<List<ProductsEntity>>
        get() = _allProdsEvent

    private val _allClientsProdsEvent = MutableLiveData<List<ProductsEntity>>()
    val allClientsProdsEvent: LiveData<List<ProductsEntity>>
        get() = _allClientsProdsEvent

    private val _deleteProdEvent = MutableLiveData<Unit>()
    val deleteProdEvent: LiveData<Unit>
        get() = _deleteProdEvent

    fun getAllProds() = viewModelScope.launch {
        _allProdsEvent.postValue(prods.getAllProds())
    }

    fun getClientProds(id_client: Long) = viewModelScope.launch {
        _allClientsProdsEvent.postValue(prods.getClientProds(id_client))
    }

    fun deleteProd(id: Long) = viewModelScope.launch {
        try {
            if (id > 0){
                _deleteProdEvent.postValue(prods.deleteProd(id))
            }
        } catch (ex: Exception) {}
    }
}