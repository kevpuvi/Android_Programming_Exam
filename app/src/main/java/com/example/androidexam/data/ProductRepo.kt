package com.example.androidexam.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton object for accessing the ProductAPI
object ProductRepo {
    // Base URL for the Fake Store API
    private const val BASE_URL = "https://fakestoreapi.com/"

    // Lazy initialization of the ProductAPI instance using Retrofit
    private val productAPI: ProductAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductAPI::class.java)
    }

    // Suspend function to fetch all products from the API
    suspend fun getProducts(): List<Product>? {
        return try {
            val response = productAPI.getAllProducts()
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    // Suspend function to fetch a product by its ID from the API
    suspend fun getProductById(productId: Int): Product? {
        return try {
            val response = productAPI.getProductById(productId)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }
}
