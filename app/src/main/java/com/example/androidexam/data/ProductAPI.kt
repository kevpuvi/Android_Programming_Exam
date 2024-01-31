package com.example.androidexam.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// Define an interface for making HTTP requests related to products
interface ProductAPI {
    // Define a suspend function to fetch all products from the API
    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    // Define a suspend function to fetch a product by its ID from the API
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId: Int): Response<Product>
}
