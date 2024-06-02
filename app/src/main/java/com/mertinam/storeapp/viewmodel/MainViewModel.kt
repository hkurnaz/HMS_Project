package com.mertinam.storeapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertinam.storeapp.Service.ProductAPIService
import com.mertinam.storeapp.db.ProductDao
import com.mertinam.storeapp.db.ProductDatabase
import com.mertinam.storeapp.model.Product
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val productAPI = ProductAPIService()

    val productData = MutableLiveData<List<Product>>()
    val productLoad = MutableLiveData<Boolean>()
    val productError = MutableLiveData<Boolean>()

    private var productDatabase : ProductDatabase? = null
    private var productDao : ProductDao? = null
    val product = MutableLiveData<Product>()


    init {
        productDatabase = ProductDatabase.getInstance(application)
        productDao = productDatabase?.productDao()
    }

    fun getDataFromAPI(){
        productLoad.value = true
        
        productAPI.getData().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                productLoad.value = false
                productError.value = false
                productData.value = response.body()

            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                productLoad.value = false
                productError.value = true


            }
        })

    }
    fun insertAll(list : List<Product>) = viewModelScope.launch{
        productDao?.insertAll(list)
    }

    fun findByName(name :String ) = viewModelScope.launch{
       product.value = productDao?.findByName(name)
    }


}