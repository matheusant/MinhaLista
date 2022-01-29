package com.example.minhalista.repository

import com.example.minhalista.data.db.entity.ClientEntity

interface ClientRepository {

    suspend fun insertClient(name: String, date: String): Long

    suspend fun updateClient(id: Long, name: String, date: String)

    suspend fun deleteClient(id: Long)

    suspend fun deleteAllClients()

    suspend fun getAllClients(): List<ClientEntity>
    
}