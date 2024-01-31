package com.example.androidexam

import OrderHistoryScreen
import ProductDetailsScreen
import ProductListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidexam.product_details.ProductDetailsViewModel
import com.example.androidexam.product_list.ProductListViewModel
import com.example.androidexam.shopping_cart.CartViewModel
import com.example.androidexam.shopping_cart.ShoppingCartScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                // Create a NavHostController to manage navigation
                val navController = rememberNavController()

                // Create a shared CartViewModel scoped to the Activity
                val cartViewModel: CartViewModel = viewModel()

                NavHost(navController = navController, startDestination = "productList") {
                    composable("productList") {
                        // ViewModel for ProductList
                        val productListViewModel: ProductListViewModel = viewModel()
                        ProductListScreen(
                            viewModel = productListViewModel,
                            onProductClick = { productId ->
                                // When a product is clicked, navigate to the product details screen
                                navController.navigate("productDetails/$productId")
                            },
                            navController = navController
                        )
                    }
                    composable("productDetails/{productId}") { backStackEntry ->
                        // ViewModel for ProductDetails
                        val productDetailsViewModel: ProductDetailsViewModel = viewModel()
                        val productId =
                            backStackEntry.arguments?.getString("productId")?.toIntOrNull()

                        // Make sure productId is not null before navigating to ProductDetailsScreen
                        productId?.let {
                            ProductDetailsScreen(
                                viewModel = productDetailsViewModel,
                                cartViewModel = cartViewModel, // Pass the shared CartViewModel
                                productId = it,
                                onBackButtonClick = { navController.navigateUp() },
                                navController = navController
                            )
                        }
                    }
                    composable("shoppingCart") {
                        ShoppingCartScreen(
                            cartViewModel = cartViewModel, // Pass the shared CartViewModel
                            navController = navController
                        )
                    }
                    composable("orderHistory") {
                        // Navigate to the OrderHistoryScreen and pass the CartViewModel
                        OrderHistoryScreen(navController, cartViewModel)
                    }
                }
            }
        }
    }

    @Composable
    fun MyAppTheme(content: @Composable () -> Unit) {
        MaterialTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                content()
            }
        }
    }
}
