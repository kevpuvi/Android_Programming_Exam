package com.example.androidexam.shopping_cart

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.androidexam.data.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(
    cartViewModel: CartViewModel,
    navController: NavHostController
) {
    val onBackButtonClick: () -> Unit = {
        navController.navigateUp()
    }

    // Observe the cart items using LiveData and convert to Composable state
    val cartItems by cartViewModel.cartItems.observeAsState(emptyList())

    // Calculate the total price of items in the cart
    val totalPrice = cartItems.sumOf { it.price }

    // State to track if the order placed message should be visible
    var orderPlacedMessageVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Shopping Cart") },
                navigationIcon = {
                    IconButton(onClick = onBackButtonClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back",
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = { navController.navigate("orderHistory") },
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
                        content = {
                            Text(
                                text = "Order History",
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (cartItems.isEmpty()) {
                // Display a message when the cart is empty
                Text(
                    text = "Your shopping cart is empty.",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    items(cartItems) { product ->
                        // Display cart items in a LazyColumn
                        CartItem(product = product, onRemoveClick = { cartViewModel.removeFromCart(product) })
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Display the total price and "Place order" button
                    Text(
                        text = "Total: $${"%.2f".format(totalPrice)}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Button(
                        onClick = {
                            cartViewModel.placeOrder() // Place the order
                            orderPlacedMessageVisible = true // Set the message visibility
                        },
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text("Place order")
                    }
                }
            }

            if (orderPlacedMessageVisible) {
                // Display a message when the order is placed successfully
                Text(
                    text = "Order placed successfully!",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun CartItem(product: Product, onRemoveClick: () -> Unit) {
    // Display an individual cart item
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.title,
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Price: $${product.price}",
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = product.imageUrl,
                contentDescription = "Product Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )
            IconButton(onClick = onRemoveClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from cart",
                    tint = Color.Red
                )
            }
        }
    }
}
