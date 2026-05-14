package com.example.smartmall.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmall.Components.HomeCard
import com.example.smartmall.Parking.SensorCard
import com.example.smartmall.R

private val SmartMallTitleBlue = Color(0xFF0B2F5B)

@Composable
fun HomeScreen(
    onNavigateToParking: () -> Unit,
    onNavigateToAforo: () -> Unit,
    onNavigateToRestaurantes: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    val searchResults = remember(searchText) {
        val query = searchText.trim().lowercase()

        if (query.isBlank()) {
            emptyList()
        } else {
            listOf("Parking", "Aforo", "Restaurantes").filter {
                it.lowercase().contains(query)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(R.drawable.fondodelmain),
            contentDescription = "Fondo principal",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Overlay suave para mantener legible el texto sin tapar el fondo.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0F2027).copy(alpha = 0.18f),
                            Color(0xFF203A43).copy(alpha = 0.12f),
                            Color(0xFF2C5364).copy(alpha = 0.10f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Spacer(modifier = Modifier.height(42.dp))

        ShoppingBagMark()

        Text(
            text = "SMARTMALL",
            color = SmartMallTitleBlue,
            fontSize = 38.sp,
            fontWeight = FontWeight.Black,
            fontStyle = FontStyle.Italic,
            letterSpacing = 0.sp
        )

        Spacer(modifier = Modifier.height(34.dp))

        SearchBar(
            value = searchText,
            onValueChange = { searchText = it }
        )

        SearchResults(
            results = searchResults,
            onParkingClick = onNavigateToParking,
            onAforoClick = onNavigateToAforo,
            onRestaurantesClick = onNavigateToRestaurantes
        )

        Spacer(modifier = Modifier.height(if (searchResults.isEmpty()) 46.dp else 18.dp))

            HomeCard(
                text = "Parking",
                subtitle = "Ver disponibilidad",
                imageRes = R.drawable.parking,
                animationDelayMillis = 120
            ) {
                onNavigateToParking()
            }

        Spacer(modifier = Modifier.height(24.dp))

        HomeCard (
            text = "Aforo",
            subtitle = "Estado en tiempo real",
            imageRes = R.drawable.aforo,
            animationDelayMillis = 240
        ) {
            onNavigateToAforo()
        }

        Spacer(modifier = Modifier.height(24.dp))

        HomeCard(
            text = "Restauración",
            subtitle = "Explorar opciones",
            imageRes = R.drawable.restaurante,
            animationDelayMillis = 360
        ) {
            onNavigateToRestaurantes()
        }
        }
    }
}

@Composable
private fun ShoppingBagMark() {
    Box(
        modifier = Modifier
            .width(44.dp)
            .height(48.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(18.dp)
                .align(Alignment.TopCenter)
                .border(4.dp, SmartMallTitleBlue, RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
        )

        Box(
            modifier = Modifier
                .width(42.dp)
                .height(34.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(4.dp, SmartMallTitleBlue, RoundedCornerShape(4.dp))
        )
    }
}

@Composable
private fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
            .clip(RoundedCornerShape(31.dp))
            .background(Color.White.copy(alpha = 0.16f))
            .border(2.dp, Color.White.copy(alpha = 0.30f), RoundedCornerShape(31.dp))
            .padding(horizontal = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Buscar",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(
                    text = "Buscar...",
                    color = Color.White.copy(alpha = 0.62f),
                    fontSize = 20.sp
                )
            }

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp
                ),
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SearchResults(
    results: List<String>,
    onParkingClick: () -> Unit,
    onAforoClick: () -> Unit,
    onRestaurantesClick: () -> Unit
) {
    if (results.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Color.White.copy(alpha = 0.14f))
            .border(1.dp, Color.White.copy(alpha = 0.24f), RoundedCornerShape(22.dp))
            .padding(vertical = 8.dp)
    ) {
        results.forEach { result ->
            val subtitle = when (result) {
                "Parking" -> "Ver disponibilidad"
                "Aforo" -> "Estado en tiempo real"
                else -> "Explorar opciones"
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        when (result) {
                            "Parking" -> onParkingClick()
                            "Aforo" -> onAforoClick()
                            "Restaurantes" -> onRestaurantesClick()
                        }
                    }
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) {
                Text(
                    text = result,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    color = Color.White.copy(alpha = 0.68f),
                    fontSize = 13.sp
                )
            }
        }
    }
}
