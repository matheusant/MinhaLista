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

    override suspend fun insertClientProds(name: String, price: Double, id_client: Long): Long {
        val cProd = ProductsEntity(
            name = name,
            price = price,
            id_client = id_client
        )

        return database.prodsDao.insert(cProd)
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

    override suspend fun getClientProds(id_client: Long): List<ProductsEntity> {
        return database.prodsDao.getAllById(id_client)
    }

    override suspend fun insertClient(name: String, date: String): Long {
        val client = ClientEntity(
            name = name,
            date = date
        )
        return database.clientDao.insert(client)
    }

    override suspend fun updateClient(total: Double, id: Long) {
        database.clientDao.update(total, id)
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