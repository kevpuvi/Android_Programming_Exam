package com.example.androidexam.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidexam.data.Product
import com.example.androidexam.data.ProductRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel : ViewModel() {
    // State flow to represent loading state
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    // State flow to hold the selected product
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    // Function to set the selected product by its ID
    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch {
            // Set loading state to true
            _loading.value = true
            // Fetch the product by its ID using the repository
            _selectedProduct.value = ProductRepo.getProductById(productId)
            // Set loading state back to false when the operation is complete
            _loading.value = false
        }
    }
}
