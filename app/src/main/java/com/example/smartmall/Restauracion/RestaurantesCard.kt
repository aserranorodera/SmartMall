package com.example.smartmall.Restauracion

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RestauranteCard(
    restaurante: Restaurante,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(150.dp),
    highlighted: Boolean = false,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (highlighted) 1.04f else 0.94f,
        label = "restaurantCardScale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (highlighted) 1f else 0.68f,
        label = "restaurantCardAlpha"
    )
    val elevation by animateDpAsState(
        targetValue = if (highlighted) 14.dp else 0.dp,
        label = "restaurantCardElevation"
    )
    val borderColor by animateColorAsState(
        targetValue = if (highlighted) Color(0xFFB9F4D4) else Color.Transparent,
        label = "restaurantCardBorder"
    )
    val overlayAlpha by animateFloatAsState(
        targetValue = if (highlighted) 0.20f else 0.56f,
        label = "restaurantCardOverlay"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .shadow(elevation, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, borderColor, RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {

        // IMAGEN DE FONDO
        Image(
            painter = painterResource(restaurante.imagen),
            contentDescription = restaurante.nombre,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // OSCURECER IMAGEN (para que se lea el texto)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = overlayAlpha))
        )

        // TEXTO
        Text(
            text = restaurante.nombre,
            color = Color.White,
            fontSize = if (highlighted) 24.sp else 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}
