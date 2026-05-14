package com.example.smartmall.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartmall.Aforo.AforoViewModel
import com.example.smartmall.R

@Composable
fun ZaraScreen(
    onBack: () -> Unit,
    viewModel: AforoViewModel = viewModel()
) {
    BackHandler { onBack() }

    val zonasAforo by viewModel.zonasAforo.collectAsState()
    val zara = zonasAforo.find { it.nombre == "Zara" }

    PantallaAforoZara(
        aforoActual = zara?.personasActuales ?: 0,
        capacidadMaxima = zara?.capacidadMaxima ?: 300,
        horario = "L-S: 10:00 - 22:00 / D: 11:00 - 21:00",
        telefono = "912 345 678",
        onBack = onBack
    )
}

@Composable
fun PantallaAforoZara(
    aforoActual: Int,
    capacidadMaxima: Int,
    horario: String,
    telefono: String,
    onBack: () -> Unit
) {
    val colorOcupacion = obtenerColorOcupacion(aforoActual, capacidadMaxima)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.fondoblanco),
                contentDescription = "Fondo Zara",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.05f),
                                Color.Black.copy(alpha = 0.20f),
                                Color.Black.copy(alpha = 0.66f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { onBack() },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.DarkGray
                        )
                    }

                    Text(
                        text = "ZARA",
                        color = Color.DarkGray,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(78.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = colorOcupacion
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = aforoActual.toString(),
                        fontSize = 72.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorOcupacion
                    )

                    Text(
                        text = "Aforo de Personas",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(color = Color.White.copy(alpha = 0.45f))

                    Spacer(modifier = Modifier.height(16.dp))

                    InfoItem(
                        iconText = "H",
                        titulo = "Horario",
                        valor = horario,
                        contentColor = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoItem(
                        icon = Icons.Default.Phone,
                        titulo = "Telefono",
                        valor = telefono,
                        contentColor = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun InfoItem(
    icon: ImageVector,
    titulo: String,
    valor: String,
    contentColor: Color = Color.DarkGray
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = contentColor)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = titulo, fontSize = 14.sp, color = contentColor.copy(alpha = 0.68f))
            Text(text = valor, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = contentColor)
        }
    }
}

@Composable
fun InfoItem(
    iconText: String,
    titulo: String,
    valor: String,
    contentColor: Color = Color.DarkGray
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = iconText, color = contentColor, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = titulo, fontSize = 14.sp, color = contentColor.copy(alpha = 0.68f))
            Text(text = valor, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = contentColor)
        }
    }
}

fun obtenerColorOcupacion(aforo: Int, capacidadMaxima: Int): Color {
    val porcentaje = aforo.toFloat() / capacidadMaxima.toFloat()
    return when {
        porcentaje < 0.5f -> Color(0xFF2E7D32)
        porcentaje < 0.8f -> Color(0xFFF57C00)
        else -> Color(0xFFC62828)
    }
}
