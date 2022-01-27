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
    private val _prodsStateEventData = MutableLiveData<ProdState>()
    val prodsStateEventData: LiveData<ProdState>
        get() = _prodsStateEventData

    private val _messageEventData = MutableLiveData<String>()
    val messageEventData: LiveData<String>
        get() = _messageEventData

    fun addOrUpdateProducts(name: String, price: Double, id: Long = 0) = viewModelScope.launch {
        if (id > 0) {
            updateProduct(id, name, price)
        } else {
            insertProduct(name, price)
        }
    }

    private fun insertProduct(name: String, price: Double) = viewModelScope.launch {
        try {
            val id = prods.insertProds(name, price)
            if (id > 0) {
                _prodsStateEventData.value = ProdState.Inserted
                _messageEventData.value = "Produto inserido com sucesso"
            }
        } catch (ex: Exception) {
            _messageEventData.value = "Erro ao inserir"
        }
    }

    private fun updateProduct(id: Long, name: String, price: Double) = viewModelScope.launch {
        try {
            prods.updateProds(id, name, price)
            _prodsStateEventData.value = ProdState.Update
            _messageEventData.value = "Produto atualizado com sucesso"
        } catch (ex: Exception) {
            _messageEventData.value = "Erro ao atualizar"
        }
    }

    sealed class ProdState {
        object Inserted : ProdState()
        object Update : ProdState()
    }
}