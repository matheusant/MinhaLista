package com.example.minhalista.repository

import com.example.minhalista.data.db.entity.ProductsEntity

interface ProductRepository {

    suspend fun insertProds(name: String, price: Double): Long

    suspend fun updateProds(id: Long, name: String, price: Double)

    suspend fun deleteProd(id: Long)

    suspend fun deleteAllProds()

    suspend fun getAllProds(): List<ProductsEntity>
}