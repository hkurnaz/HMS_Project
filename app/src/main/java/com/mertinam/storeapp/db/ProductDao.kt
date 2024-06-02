package com.mertinam.storeapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mertinam.storeapp.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    suspend fun getAll(): List<Product>

    @Query("SELECT * FROM products WHERE title = :title")
    suspend fun findByName(title: String): Product

    @Insert
    suspend fun insertAll(list: List<Product>)
}