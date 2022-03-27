package com.example.minhalista.data.db.dao

import androidx.room.*
import com.example.minhalista.data.db.entity.ClientEntity

@Dao
interface ClientDAO {

    @Insert
    suspend fun insert(client: ClientEntity): Long

    @Query("UPDATE client SET total = :total WHERE id = :id")
    suspend fun update(total: Double, id: Long)

    @Query("DELETE FROM client WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM client")
    suspend fun deleteAll()

    @Query("SELECT * FROM client")
    suspend fun getAll(): List<ClientEntity>
}