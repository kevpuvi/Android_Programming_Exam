package com.example.androidexam.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidexam.data.Product
import com.example.androidexam.data.ProductRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    // Create a mutable state flow to hold the list of products
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    // Expose the products as an immutable state flow
    val products = _products.asStateFlow()

    // Initialize the view model by loading products
    init {
        loadProducts()
    }

    // Function to load products from the repository
    private fun loadProducts() {
        viewModelScope.launch {
            // Retrieve the list of products from the repository
            ProductRepo.getProducts()?.let {
                // Update the products state flow with the retrieved list
                _products.value = it
            }
        }
    }
}
