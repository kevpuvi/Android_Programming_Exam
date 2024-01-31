package com.example.androidexam.shopping_cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidexam.data.Product

// ViewModel for managing the shopping cart and order history
class CartViewModel : ViewModel() {
    // MutableLiveData to hold the current cart items
    private val _cartItems = MutableLiveData<List<Product>>(emptyList())
    val cartItems: LiveData<List<Product>> = _cartItems

    // MutableLiveData to hold the order history
    private val _orderHistory = MutableLiveData<List<Order>>() // Order history
    val orderHistory: LiveData<List<Order>> = _orderHistory

    // Function to add a product to the cart
    fun addToCart(product: Product) {
        val updatedCartItems = _cartItems.value.orEmpty().toMutableList()
        updatedCartItems.add(product)
        _cartItems.value = updatedCartItems
    }

    // Function to remove a product from the cart
    fun removeFromCart(product: Product) {
        val updatedCartItems = _cartItems.value.orEmpty().toMutableList()
        updatedCartItems.remove(product)
        _cartItems.value = updatedCartItems
    }

    // Function to place an order
    fun placeOrder() {
        val cartItems = _cartItems.value.orEmpty()
        if (cartItems.isNotEmpty()) {
            val order = Order(cartItems, System.currentTimeMillis()) // Create an order
            val updatedOrderHistory = _orderHistory.value.orEmpty().toMutableList()
            updatedOrderHistory.add(order)
            _orderHistory.value = updatedOrderHistory
            clearCart() // Clear the cart after placing an order
        }
    }

    // Function to clear the cart
    fun clearCart() {
        _cartItems.value = emptyList()
    }
}

// Data class to represent an order with items and order time
data class Order(val items: List<Product>, val orderTime: Long)
