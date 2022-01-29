package com.example.minhalista.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.minhalista.data.db.entity.ClientEntity

@Dao
interface ClientDAO {

    @Insert
    suspend fun insert(client: ClientEntity): Long

    @Update
    suspend fun update(client: ClientEntity)

    @Query("DELETE FROM client WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM client")
    suspend fun deleteAll()

    @Query("SELECT * FROM client")
    suspend fun getAll(): List<ClientEntity>
}