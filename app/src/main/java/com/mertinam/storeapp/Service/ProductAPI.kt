package com.mertinam.storeapp.Service

import com.mertinam.storeapp.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductAPI {

    //https://fakestoreapi.com/docs

    @GET("products")
    fun getAllProducts(): Call<List<Product>>


}