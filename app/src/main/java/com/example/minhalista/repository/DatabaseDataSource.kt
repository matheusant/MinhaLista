package com.example.minhalista.repository

import com.example.minhalista.data.db.dao.ProductDAO
import com.example.minhalista.data.db.entity.ProductsEntity

class DatabaseDataSource(
    private val prodsDao: ProductDAO
): ProductRepository {
    override suspend fun insertProds(name: String, price: Double): Long {
        val prod = ProductsEntity(
            name = name,
            price = price
        )

        return prodsDao.insert(prod)
    }

    override suspend fun updateProds(id: Long, name: String, price: Double) {
        val prod = ProductsEntity(
            id = id,
            name = name,
            price = price
        )

        prodsDao.update(prod)
    }

    override suspend fun deleteProd(id: Long) {
        prodsDao.delete(id)
    }

    override suspend fun deleteAllProds() {
        prodsDao.deleteAll()
    }

    override suspend fun getAllProds(): List<ProductsEntity> {
        return prodsDao.getAll()
    }
}