
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.androidexam.data.Product
import com.example.androidexam.product_list.ProductListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    onProductClick: (Int) -> Unit,
    navController: NavHostController // Correct type for NavController
) {
    // Collect the list of products as a state
    val products = viewModel.products.collectAsState().value

    // Create the screen layout
    Scaffold(
        topBar = {
            // Create a small top app bar
            SmallTopAppBar(
                title = {
                    // Create a row to hold the title and dropdown icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Products") // Display "Products" as the title

                        // Empty Dropdown Menu (TODO: Add dropdown logic)
                        Box(
                            modifier = Modifier.clickable { /* Add your dropdown menu logic here */ },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                },
                actions = {
                    // Add a shopping cart icon button
                    IconButton(onClick = { navController.navigate("shoppingCart") }) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Shopping Cart"
                        )
                    }

                    // "Order History" Button
                    Spacer(Modifier.width(16.dp))
                    Button(
                        onClick = { navController.navigate("orderHistory") },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(40.dp) // Keep the height consistent
                            .border(1.dp, Color.Black, RoundedCornerShape(4.dp)), // Add a border
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White) // Set a clean, simple color
                    ) {
                        Text(
                            text = "Order History",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium, // Bold might be too strong, Medium is cleaner
                                color = Color.Black
                            )
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // Create a LazyColumn for the product list
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 6.dp)
        ) {
            // Display each product as a list item
            items(products) { product ->
                ProductItem(product = product, onProductClick = { onProductClick(product.id) })
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onProductClick: () -> Unit) {
    // Create a card for each product
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable(onClick = onProductClick) // Use the provided onProductClick lambda
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        // Create a row to display product information
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display the product image using AsyncImage
            AsyncImage(
                model = product.imageUrl,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(100.dp)
                    .weight(1f),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(8.dp))

            // Create a column to display product details
            Column(
                modifier = Modifier
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                // Display the product title with limited lines and ellipsis for overflow
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Display the product category
                Text(
                    text = " ${product.category}",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Display the product price
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
