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

    fun getAllProds() = viewModelScope.launch {
        _allProdsEvent.postValue(prods.getAllProds())
    }
}