package com.example.minhalista.repository

import com.example.minhalista.data.db.AppDatabase
import com.example.minhalista.data.db.entity.ClientEntity
import com.example.minhalista.data.db.entity.ProductsEntity

class DatabaseDataSource(
    private val database: AppDatabase
): ProductRepository, ClientRepository {
    override suspend fun insertProds(name: String, price: Double): Long {
        val prod = ProductsEntity(
            name = name,
            price = price
        )

        return database.prodsDao.insert(prod)
    }

    override suspend fun updateProds(id: Long, name: String, price: Double) {
        val prod = ProductsEntity(
            id = id,
            name = name,
            price = price
        )

        database.prodsDao.update(prod)
    }

    override suspend fun deleteProd(id: Long) {
        database.prodsDao.delete(id)
    }

    override suspend fun deleteAllProds() {
        database.prodsDao.deleteAll()
    }

    override suspend fun getAllProds(): List<ProductsEntity> {
        return database.prodsDao.getAll()
    }

    override suspend fun insertClient(name: String, date: String): Long {
        val client = ClientEntity(
            name = name,
            date = date
        )
        return database.clientDao.insert(client)
    }

    override suspend fun updateClient(id: Long, name: String, date: String) {
        val client = ClientEntity(
            name = name,
            date = date
        )
        database.clientDao.update(client)
    }

    override suspend fun deleteClient(id: Long) {
        database.clientDao.delete(id)
    }

    override suspend fun deleteAllClients() {
        database.clientDao.deleteAll()
    }

    override suspend fun getAllClients(): List<ClientEntity> {
        return database.clientDao.getAll()
    }
}