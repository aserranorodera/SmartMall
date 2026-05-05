package com.example.smartmall.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmall.Parking.Plaza
import com.example.smartmall.R

@Composable
fun ParkingScreen(onBack: () -> Unit) {

    BackHandler {
        onBack()
    }

    val plazas = remember {
        mutableStateListOf(
            Plaza(1, true),
            Plaza(2, false),
            Plaza(3, true)
        )
    }

    val libres = plazas.count { it.libre } //Contador plazas libres

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.fotoparking),
            contentDescription = "Mapa Parking",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // TEXTO CONTADOR
        Text(
            text = "Plazas libres: $libres / ${plazas.size}",
            color = Color.White,
            fontSize = (screenWidth * 0.05f).value.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = screenHeight * 0.08f)
        )
        
        // PLAZA 1 - Posición relativa
        ParkingSpot(
            isFree = plazas[0].libre,
            modifier = Modifier
                .offset(
                    x = screenWidth * 0.44f,
                    y = screenHeight * 0.80f
                ),
            onClick = {
                plazas[0] = plazas[0].copy(libre = !plazas[0].libre)
            }
        )

        // PLAZA 2 - Posición relativa
        ParkingSpot(
            isFree = plazas[1].libre,
            modifier = Modifier
                .offset(
                    x = screenWidth * 0.44f,
                    y = screenHeight * 0.28f
                ),
            onClick = {
                plazas[1] = plazas[1].copy(libre = !plazas[1].libre)
            }
        )

        // PLAZA 3 - Posición relativa
        ParkingSpot(
            isFree = plazas[2].libre,
            modifier = Modifier
                .offset(
                    x = screenWidth * 0.68f,
                    y = screenHeight * 0.28f
                ),
            onClick = {
                plazas[2] = plazas[2].copy(libre = !plazas[2].libre)
            }
        )

        IconButton (
            onClick = { onBack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(screenWidth * 0.04f)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White,
                modifier = Modifier.size(screenWidth * 0.08f)
            )
        }
    }
}

@Composable
fun ParkingSpot(
    isFree: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    
    val color = if (isFree) Color.Green else Color.Red
    val spotSize = screenWidth * 0.06f

    Box(
        modifier = modifier
            .size(spotSize)
            .background(color, shape = CircleShape)
            .clickable {
                onClick()
            }
    )
}
