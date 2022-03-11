package com.example.minhalista.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.minhalista.data.db.dao.ClientDAO
import com.example.minhalista.data.db.dao.ProductDAO
import com.example.minhalista.data.db.entity.ClientEntity
import com.example.minhalista.data.db.entity.ProductsEntity

@Database(entities = [ProductsEntity::class, ClientEntity::class], version = 3)
abstract class AppDatabase: RoomDatabase() {

    abstract val prodsDao: ProductDAO

    abstract val clientDao: ClientDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}