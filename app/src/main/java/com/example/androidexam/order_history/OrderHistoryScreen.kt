import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.androidexam.data.Product
import com.example.androidexam.shopping_cart.CartViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(navController: NavController, cartViewModel: CartViewModel) {
    val orderHistory = cartViewModel.orderHistory.value // Get the orderHistory LiveData value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Order History") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            // Navigate back to the previous screen when the back button is clicked
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Product List",
                            tint = Color.Black // Set a contrasting color
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Order History",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Check if orderHistory is not null and not empty
            if (!orderHistory.isNullOrEmpty()) {
                items(orderHistory) { order ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Order placed on ${order.orderTime}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )

                            var totalOrderCost = 0.0

                            // Iterate through order items
                            order.items.forEach { product ->
                                Spacer(modifier = Modifier.height(8.dp))
                                ProductItem(product = product) // Display product details
                                // Add the price of the product to the total order cost
                                totalOrderCost += product.price
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Total Order Cost: $${"%.2f".format(totalOrderCost)}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            )
                        }
                    }
                }
            } else {
                // Display a message when orderHistory is empty
                item {
                    Text(
                        text = "Your order history is empty.",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductItem(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = "Product Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(64.dp)
                .clip(MaterialTheme.shapes.small)
                .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = product.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Text(
                text = "Price: $${product.price}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            )
        }
    }
}
