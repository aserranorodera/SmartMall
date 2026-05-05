package com.example.smartmall.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmall.R
import kotlinx.coroutines.launch

@Composable
fun BeataPastaScreen(onBack: () -> Unit) {

    BackHandler {
        onBack()
    }

    var personas by remember { mutableStateOf("2") }
    var hora by remember { mutableStateOf("21:00") }
    var nombre by remember { mutableStateOf("") }
    var reservaConfirmada by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F0)),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.beatapasta),
                    contentDescription = "Beata Pasta",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.15f),
                                    Color.Black.copy(alpha = 0.65f)
                                )
                            )
                        )
                )

                IconButton(
                    onClick = { onBack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .background(Color.Black.copy(alpha = 0.35f), RoundedCornerShape(50))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Beata Pasta",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Pasta fresca, pizzas y postres italianos",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }

        item {
            RestaurantInfo(
                onAperitivoClick = { coroutineScope.launch { listState.animateScrollToItem(2) } },
                onComidaClick = { coroutineScope.launch { listState.animateScrollToItem(3) } },
                onPostreClick = { coroutineScope.launch { listState.animateScrollToItem(4) } },
                onBebidaClick = { coroutineScope.launch { listState.animateScrollToItem(5) } },
                onReservasClick = { coroutineScope.launch { listState.animateScrollToItem(6) } }
            )
        }

        item {
            SectionTitle("Menu completo")
            MenuCategory(
                title = "Aperitivos",
                items = listOf(
                    MenuItem("Pan de ajo", "Pan tostado con ajo, perejil y aceite de oliva", "3,90 EUR"),
                    MenuItem("Bruschetta", "Tomate, albahaca fresca y mozzarella", "4,50 EUR"),
                    MenuItem("Provolone al horno", "Queso fundido con tomate y oregano", "6,20 EUR")
                )
            )
        }

        item {
            FoodMenuCategory(
                sections = listOf(
                    MenuSection(
                        title = "Ensaladas",
                        items = listOf(
                            MenuItem("Ensalada cesar", "Lechuga romana, pollo, parmesano, croutons y salsa cesar", "8,90 EUR"),
                            MenuItem("Ensalada caprese", "Tomate, mozzarella fresca, albahaca y aceite de oliva", "8,50 EUR"),
                            MenuItem("Ensalada mixta", "Lechuga, tomate, cebolla, atun, huevo y aceitunas", "7,90 EUR"),
                            MenuItem("Ensalada de burrata", "Burrata, rucula, tomate cherry y pesto", "10,50 EUR")
                        )
                    ),
                    MenuSection(
                        title = "Pizzas",
                        items = listOf(
                            MenuItem("Pizza margarita", "Tomate, mozzarella y albahaca fresca", "9,90 EUR"),
                            MenuItem("Pizza prosciutto", "Tomate, mozzarella y jamon cocido", "10,90 EUR"),
                            MenuItem("Pizza cuatro quesos", "Mozzarella, gorgonzola, parmesano y provolone", "11,50 EUR"),
                            MenuItem("Pizza pepperoni", "Tomate, mozzarella y pepperoni", "11,20 EUR"),
                            MenuItem("Pizza barbacoa", "Salsa barbacoa, carne, bacon y mozzarella", "12,00 EUR"),
                            MenuItem("Pizza carbonara", "Nata, bacon, champinones, cebolla y parmesano", "12,20 EUR"),
                            MenuItem("Pizza vegetal", "Calabacin, pimiento, cebolla, champinones y aceitunas", "10,90 EUR"),
                            MenuItem("Pizza diavola", "Salami picante, tomate, mozzarella y guindilla", "11,90 EUR"),
                            MenuItem("Pizza tonno", "Atun, cebolla, tomate y mozzarella", "11,40 EUR"),
                            MenuItem("Pizza hawaiana", "Jamon cocido, pina, tomate y mozzarella", "10,90 EUR"),
                            MenuItem("Pizza trufa", "Crema de trufa, mozzarella, champinones y parmesano", "13,50 EUR")
                        )
                    ),
                    MenuSection(
                        title = "Pasta",
                        items = listOf(
                            MenuItem("Spaghetti carbonara", "Pasta con huevo, bacon y parmesano", "10,90 EUR"),
                            MenuItem("Tagliatelle bolognesa", "Salsa de tomate, carne y hierbas italianas", "11,50 EUR"),
                            MenuItem("Penne arrabbiata", "Tomate picante, ajo y aceite de oliva", "9,90 EUR"),
                            MenuItem("Fettuccine alfredo", "Salsa cremosa de parmesano", "10,80 EUR"),
                            MenuItem("Ravioli ricotta e spinaci", "Rellenos de ricotta y espinacas con salsa suave", "12,20 EUR"),
                            MenuItem("Tortellini panna e prosciutto", "Tortellini con nata y jamon", "11,90 EUR"),
                            MenuItem("Gnocchi al pesto", "Gnocchi de patata con pesto genoves", "10,70 EUR"),
                            MenuItem("Linguine alle vongole", "Pasta larga con almejas, ajo y perejil", "13,90 EUR"),
                            MenuItem("Maccheroni cuatro quesos", "Salsa cremosa de quesos italianos", "11,40 EUR"),
                            MenuItem("Lasagna della casa", "Capas de pasta, carne, bechamel y queso gratinado", "12,50 EUR"),
                            MenuItem("Spaghetti frutti di mare", "Marisco, tomate, ajo y perejil", "14,20 EUR"),
                            MenuItem("Rigatoni amatriciana", "Tomate, panceta, cebolla y pecorino", "11,80 EUR")
                        )
                    )
                )
            )
        }

        item {
            MenuCategory(
                title = "Postres",
                items = listOf(
                    MenuItem("Tiramisu", "Cafe, mascarpone y cacao", "4,90 EUR"),
                    MenuItem("Panna cotta", "Con coulis de frutos rojos", "4,50 EUR"),
                    MenuItem("Cannoli siciliano", "Relleno de ricotta dulce", "4,80 EUR")
                )
            )
        }

        item {
            MenuCategory(
                title = "Bebidas",
                items = listOf(
                    MenuItem("Agua mineral", "Botella 50 cl", "1,80 EUR"),
                    MenuItem("Coca-cola", "Botella 50 cl", "2,80 EUR"),
                    MenuItem("Coca-cola Cero", "Botella 50 cl", "2,50 EUR"),
                    MenuItem("Nestea", "Botella 50 cl", "2,80 EUR"),
                    MenuItem("Fanta de Naranja", "Botella 50 cl", "2,80 EUR"),
                    MenuItem("Fanta de Limon", "Botella 50 cl", "2,80 EUR"),
                    MenuItem("Sprite", "Botella 50 cl", "2,80 EUR"),
                    MenuItem("Mahou", "Botella 50 cl", "3,00 EUR"),
                    MenuItem("Cerveza italiana", "Peroni bien fria", "3,20 EUR"),
                    MenuItem("Copa de vino", "Tinto o blanco de la casa", "3,50 EUR")
                )
            )
        }

        item {
            SectionTitle("Reservar mesa")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = {
                            nombre = it
                            reservaConfirmada = false
                        },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = personas,
                        onValueChange = {
                            personas = it.filter { caracter -> caracter.isDigit() }
                            reservaConfirmada = false
                        },
                        label = { Text("Numero de personas") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = hora,
                        onValueChange = {
                            hora = it
                            reservaConfirmada = false
                        },
                        label = { Text("Hora de la reserva") },
                        placeholder = { Text("Ej: 21:00") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Button(
                        onClick = { reservaConfirmada = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB43B2A))
                    ) {
                        Text("Confirmar reserva")
                    }

                    if (reservaConfirmada) {
                        Text(
                            text = "Reserva preparada para $personas personas a las $hora.",
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RestaurantInfo(
    onAperitivoClick: () -> Unit,
    onComidaClick: () -> Unit,
    onPostreClick: () -> Unit,
    onBebidaClick: () -> Unit,
    onReservasClick: () -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { InfoChip("Aperitivo", onAperitivoClick) }
        item { InfoChip("Comida", onComidaClick) }
        item { InfoChip("Postre", onPostreClick) }
        item { InfoChip("Bebida", onBebidaClick) }
        item { InfoChip("Reservas", onReservasClick) }
    }
}

@Composable
private fun InfoChip(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = Color(0xFF6D2C22),
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color.White)
            .border(1.dp, Color(0xFFE7C7BE), RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    )
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = Color(0xFF271512),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 10.dp)
    )
}

@Composable
private fun MenuCategory(title: String, items: List<MenuItem>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                color = Color(0xFFB43B2A),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            items.forEach { item ->
                MenuRow(item)
            }
        }
    }
}

@Composable
private fun FoodMenuCategory(sections: List<MenuSection>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Comida",
                color = Color(0xFFB43B2A),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            sections.forEach { section ->
                Text(
                    text = section.title,
                    color = Color(0xFF6D2C22),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 6.dp)
                )

                section.items.forEach { item ->
                    MenuRow(item)
                }
            }
        }
    }
}

@Composable
private fun MenuRow(item: MenuItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
        ) {
            Text(
                text = item.nombre,
                color = Color(0xFF271512),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = item.descripcion,
                color = Color(0xFF6F5B56),
                fontSize = 13.sp
            )
        }
        Text(
            text = item.precio,
            color = Color(0xFF271512),
            fontWeight = FontWeight.Bold
        )
    }
}

private data class MenuSection(
    val title: String,
    val items: List<MenuItem>
)

private data class MenuItem(
    val nombre: String,
    val descripcion: String,
    val precio: String
)
