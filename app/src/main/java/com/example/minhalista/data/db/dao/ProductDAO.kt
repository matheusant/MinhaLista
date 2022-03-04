package com.example.minhalista.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.minhalista.data.db.entity.ProductsEntity

@Dao
interface ProductDAO {

    @Insert
    suspend fun insert(prod: ProductsEntity): Long

    @Update
    suspend fun update(prod: ProductsEntity)

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM products")
    suspend fun deleteAll()

    @Query("SELECT * FROM products")
    suspend fun getAll(): List<ProductsEntity>

    @Query("SELECT * FROM products WHERE id_client = :id_client")
    suspend fun getAllById(id_client: Long): List<ProductsEntity>
}