package com.example.minhalista.ui.register.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhalista.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductsRegisterViewModel(
    private val prods: ProductRepository
) : ViewModel() {
    private val _subsStateEventData = MutableLiveData<ProdState>()
    val subsStateEventData: LiveData<ProdState>
        get() = _subsStateEventData

    private val _messageEventData = MutableLiveData<String>()
    val messageEventData: LiveData<String>
        get() = _messageEventData

    fun addOrUpdateProducts(name: String, price: Double, id: Long = 0) = viewModelScope.launch {
        if (id > 0) {

        } else {
            insertProduct(name, price)
        }
    }

    private fun insertProduct(name: String, price: Double) = viewModelScope.launch {
        try {
            val id = prods.insertProds(name, price)
            if (id > 0) {
                _subsStateEventData.value = ProdState.Inserted
                _messageEventData.value = "Produto inserido com sucesso"
            }
        } catch (ex: Exception) {
            _messageEventData.value = "Erro ao inserir"
        }
    }

    sealed class ProdState {
        object Inserted: ProdState()
        object Update: ProdState()
    }
}