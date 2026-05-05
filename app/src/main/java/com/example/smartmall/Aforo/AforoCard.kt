package com.example.smartmall.Aforo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AforoCard(
    aforo: Aforo,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(150.dp),
    highlighted: Boolean = false,
    onClick: () -> Unit
) {
    val porcentaje = aforo.personasActuales.toFloat() / aforo.capacidadMaxima.toFloat()
    val porcentajeTexto = (porcentaje * 100).toInt()
    val estado = when {
        porcentaje >= 0.85f -> "Aforo alto"
        porcentaje >= 0.60f -> "Aforo medio"
        else -> "Aforo bajo"
    }
    val estadoColor = when {
        porcentaje >= 0.85f -> Color(0xFFFFB4A8)
        porcentaje >= 0.60f -> Color(0xFFFFE0A3)
        else -> Color(0xFFB9F4D4)
    }

    val scale by animateFloatAsState(
        targetValue = if (highlighted) 1.04f else 0.94f,
        label = "aforoCardScale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (highlighted) 1f else 0.68f,
        label = "aforoCardAlpha"
    )
    val elevation by animateDpAsState(
        targetValue = if (highlighted) 14.dp else 0.dp,
        label = "aforoCardElevation"
    )
    val borderColor by animateColorAsState(
        targetValue = if (highlighted) estadoColor else Color.Transparent,
        label = "aforoCardBorder"
    )
    val overlayAlpha by animateFloatAsState(
        targetValue = if (highlighted) 0.20f else 0.56f,
        label = "aforoCardOverlay"
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
        Image(
            painter = painterResource(aforo.imagen),
            contentDescription = aforo.nombre,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = overlayAlpha))
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = aforo.nombre,
                color = Color.White,
                fontSize = if (highlighted) 24.sp else 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$estado · $porcentajeTexto%",
                    color = estadoColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "${aforo.personasActuales}/${aforo.capacidadMaxima}",
                    color = Color.White.copy(alpha = 0.86f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { porcentaje.coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50)),
                color = estadoColor,
                trackColor = Color.White.copy(alpha = 0.24f),
                strokeCap = StrokeCap.Round
            )
        }
    }
}
