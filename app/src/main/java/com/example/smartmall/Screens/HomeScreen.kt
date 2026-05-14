package com.example.smartmall.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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

        Spacer(modifier = Modifier.height(46.dp))

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
