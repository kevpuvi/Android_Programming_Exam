import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.androidexam.product_details.ProductDetailsViewModel
import com.example.androidexam.shopping_cart.CartViewModel
import com.example.androidexam.ui.theme.customBlue

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    cartViewModel: CartViewModel,
    productId: Int,
    onBackButtonClick: () -> Unit = {},
    navController: NavController
) {
    val loading = viewModel.loading.collectAsState()
    val productState = viewModel.selectedProduct.collectAsState()

    var productAddedToCart by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf(1) }

    LaunchedEffect(productId) {
        viewModel.setSelectedProduct(productId)
    }

    if (loading.value) {
        CircularProgressIndicator()
        return
    }

    val product = productState.value
    if (product == null) {
        Text(text = "Failed to get product details. Selected product object is NULL!", color = Color.Black)
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back",
                    tint = Color.Black
                )
            }

            // Grouping the shopping cart icon and the order history button together
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Shopping Cart Icon Button
                IconButton(
                    onClick = {
                        navController.navigate("shoppingCart")
                    }
                ) {
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
                        .height(40.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Order History",
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    )
                }
            }
        }

        AsyncImage(
            model = product.imageUrl,
            contentDescription = "Product Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Adjust the height as needed
                .padding(top = 16.dp), // Add padding at the top for space
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.title,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Rating: ${product.rating.rate} (${product.rating.count} reviews)",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Price: $${product.price}",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Category: ${product.category}",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = product.description,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Quantity controls

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            // Decrement Button
            Button(
                onClick = { if (quantity > 1) quantity-- },
                colors = ButtonDefaults.buttonColors(containerColor = customBlue)
            ) {
                Text(text = "-", color = Color.White) // The text is centered by default in a Button
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "$quantity", // Display selected quantity
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Increment Button
            Button(
                onClick = { quantity++ },
                colors = ButtonDefaults.buttonColors(containerColor = customBlue)
            ) {
                Text(text = "+", color = Color.White) // The text is centered by default in a Button
            }
        }

        // "Add to Cart" button with quantity
        Button(
            onClick = {
                // Add the product to the cart with the selected quantity
                for (i in 1..quantity) {
                    cartViewModel.addToCart(product)
                }
                productAddedToCart = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = customBlue)
        ) {
            Text(
                text = "Add to Cart ($quantity)", // Display selected quantity
                color = Color.White
            )
        }

        // Display the "Product added to the cart" text when productAddedToCart is true
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp), // Add padding as needed
            contentAlignment = Alignment.BottomCenter
        ) {
            // Only show this if the product has been added to the cart
            if (productAddedToCart) {
                Text(
                    text = "Product added to the cart!",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black // Use a color that stands out, like Green
                    ),
                    modifier = Modifier.padding(16.dp) // Add padding as needed
                )
            }
        }
    }
}
