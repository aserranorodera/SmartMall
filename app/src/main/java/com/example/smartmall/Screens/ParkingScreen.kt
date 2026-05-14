package com.example.smartmall.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartmall.Parking.ParkingViewModel
import com.example.smartmall.R

@Composable
fun ParkingScreen(
    onBack: () -> Unit,
    viewModel: ParkingViewModel = viewModel()
) {

    BackHandler {
        onBack()
    }

    val plazas by viewModel.parkingList

    val libres = plazas.count { !it.ocupada }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.fotoparking),
            contentDescription = "Mapa Parking",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // CONTADOR
        Text(
            text = "Plazas libres: $libres / ${plazas.size}",
            color = Color.White,
            fontSize = (screenWidth * 0.05f).value.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = screenHeight * 0.08f)
        )

        // DEBUG opcional (puedes quitarlo luego)
        Text(
            text = "Plazas: ${plazas.size}",
            color = Color.Yellow,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = screenHeight * 0.02f)
        )

        // PLAZAS (SIEMPRE visibles)
        ParkingSpot(
            ocupada = plazas.getOrNull(0)?.ocupada,
            modifier = Modifier.offset(
                x = screenWidth * 0.44f,
                y = screenHeight * 0.80f
            )
        )

        ParkingSpot(
            ocupada = plazas.getOrNull(1)?.ocupada,
            modifier = Modifier.offset(
                x = screenWidth * 0.44f,
                y = screenHeight * 0.28f
            )
        )

        ParkingSpot(
            ocupada = plazas.getOrNull(2)?.ocupada,
            modifier = Modifier.offset(
                x = screenWidth * 0.68f,
                y = screenHeight * 0.28f
            )
        )

        // BOTÓN BACK
        IconButton(
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
    ocupada: Boolean?,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val color = when (ocupada) {
        null -> Color.White   // 👈 mientras carga
        true -> Color.Red
        false -> Color.Green
    }

    val spotSize = screenWidth * 0.06f

    Box(
        modifier = modifier
            .size(spotSize)
            .background(color, shape = CircleShape)
    )
}