package com.example.androidexam.data

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// Define the Rating data class to hold rating information
data class Rating(
    val rate: Double,   // The rating value
    val count: Int      // The number of ratings
)

// Define the Product data class to represent a product
data class Product(
    @PrimaryKey         // Marks the 'id' field as the primary key for Room database
    val id: Int,        // Unique identifier for the product
    val title: String,  // Product title or name
    val price: Double,  // Product price
    val description: String, // Product description
    val category: String,   // Product category
    @SerializedName("image") val imageUrl: String, // Product image URL, using a serialized name for mapping
    val rating: Rating  // Product rating, using the Rating data class
)
