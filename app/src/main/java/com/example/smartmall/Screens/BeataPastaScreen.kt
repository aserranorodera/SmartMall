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
import androidx.compose.runtime.LaunchedEffect
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeataPastaScreen(onBack: () -> Unit) {

    BackHandler {
        onBack()
    }

    var personas by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var fechaReserva by remember { mutableStateOf("") }
    var fechaReservaMillis by remember { mutableStateOf<Long?>(null) }
    var mostrarDatePicker by remember { mutableStateOf(false) }
    var horaReserva by remember { mutableStateOf("") }
    var horasExpanded by remember { mutableStateOf(false) }
    var reservaConfirmada by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val hoyUtcMillis = remember { todayStartUtcMillis() }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = fechaReservaMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= hoyUtcMillis
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year >= Calendar.getInstance().get(Calendar.YEAR)
            }
        }
    )
    val horasReserva = remember {
        buildReservationHours()
    }
    val horasDisponibles = remember(fechaReservaMillis) {
        availableReservationHours(
            selectedDateMillis = fechaReservaMillis,
            hours = horasReserva,
            todayUtcMillis = hoyUtcMillis
        )
    }

    LaunchedEffect(horasDisponibles) {
        if (horaReserva.isNotEmpty() && horaReserva !in horasDisponibles) {
            horaReserva = ""
        }
    }

    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { selectedMillis ->
                            fechaReservaMillis = selectedMillis
                            fechaReserva = selectedMillis.formatReservationDate()
                            reservaConfirmada = false
                            mensajeError = ""
                        }

                        mostrarDatePicker = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { mostrarDatePicker = false }
                ) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

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
                    MenuItem("Pan de ajo", "Pan tostado con ajo, perejil y aceite de oliva", "3,90 €"),
                    MenuItem("Bruschetta", "Tomate, albahaca fresca y mozzarella", "4,50 €"),
                    MenuItem("Provolone al horno", "Queso fundido con tomate y oregano", "6,20 €")
                )
            )
        }

        item {
            FoodMenuCategory(
                sections = listOf(
                    MenuSection(
                        title = "Ensaladas",
                        items = listOf(
                            MenuItem("Ensalada cesar", "Lechuga romana, pollo, parmesano, croutons y salsa cesar", "8,90 €"),
                            MenuItem("Ensalada caprese", "Tomate, mozzarella fresca, albahaca y aceite de oliva", "8,50 €"),
                            MenuItem("Ensalada mixta", "Lechuga, tomate, cebolla, atun, huevo y aceitunas", "7,90 €"),
                            MenuItem("Ensalada de burrata", "Burrata, rucula, tomate cherry y pesto", "10,50 €")
                        )
                    ),
                    MenuSection(
                        title = "Pizzas",
                        items = listOf(
                            MenuItem("Pizza margarita", "Tomate, mozzarella y albahaca fresca", "9,90 €"),
                            MenuItem("Pizza prosciutto", "Tomate, mozzarella y jamon cocido", "10,90 €"),
                            MenuItem("Pizza cuatro quesos", "Mozzarella, gorgonzola, parmesano y provolone", "11,50 €"),
                            MenuItem("Pizza pepperoni", "Tomate, mozzarella y pepperoni", "11,20 €"),
                            MenuItem("Pizza barbacoa", "Salsa barbacoa, carne, bacon y mozzarella", "12,00 €"),
                            MenuItem("Pizza carbonara", "Nata, bacon, champinones, cebolla y parmesano", "12,20 €"),
                            MenuItem("Pizza vegetal", "Calabacin, pimiento, cebolla, champinones y aceitunas", "10,90 €"),
                            MenuItem("Pizza diavola", "Salami picante, tomate, mozzarella y guindilla", "11,90 €"),
                            MenuItem("Pizza tonno", "Atun, cebolla, tomate y mozzarella", "11,40 €"),
                            MenuItem("Pizza hawaiana", "Jamon cocido, pina, tomate y mozzarella", "10,90 €"),
                            MenuItem("Pizza trufa", "Crema de trufa, mozzarella, champinones y parmesano", "13,50 €")
                        )
                    ),
                    MenuSection(
                        title = "Pasta",
                        items = listOf(
                            MenuItem("Spaghetti carbonara", "Pasta con huevo, bacon y parmesano", "10,90 €"),
                            MenuItem("Tagliatelle bolognesa", "Salsa de tomate, carne y hierbas italianas", "11,50 €"),
                            MenuItem("Penne arrabbiata", "Tomate picante, ajo y aceite de oliva", "9,90 €"),
                            MenuItem("Fettuccine alfredo", "Salsa cremosa de parmesano", "10,80 €"),
                            MenuItem("Ravioli ricotta e spinaci", "Rellenos de ricotta y espinacas con salsa suave", "12,20 €"),
                            MenuItem("Tortellini panna e prosciutto", "Tortellini con nata y jamon", "11,90 €"),
                            MenuItem("Gnocchi al pesto", "Gnocchi de patata con pesto genoves", "10,70 €"),
                            MenuItem("Linguine alle vongole", "Pasta larga con almejas, ajo y perejil", "13,90 €"),
                            MenuItem("Maccheroni cuatro quesos", "Salsa cremosa de quesos italianos", "11,40 €"),
                            MenuItem("Lasagna della casa", "Capas de pasta, carne, bechamel y queso gratinado", "12,50 €"),
                            MenuItem("Spaghetti frutti di mare", "Marisco, tomate, ajo y perejil", "14,20 €"),
                            MenuItem("Rigatoni amatriciana", "Tomate, panceta, cebolla y pecorino", "11,80 €")
                        )
                    )
                )
            )
        }

        item {
            MenuCategory(
                title = "Postres",
                items = listOf(
                    MenuItem("Tiramisu", "Cafe, mascarpone y cacao", "4,90 €"),
                    MenuItem("Panna cotta", "Con coulis de frutos rojos", "4,50 €"),
                    MenuItem("Cannoli siciliano", "Relleno de ricotta dulce", "4,80 €")
                )
            )
        }

        item {
            MenuCategory(
                title = "Bebidas",
                items = listOf(
                    MenuItem("Agua mineral", "Botella 50 cl", "1,80 €"),
                    MenuItem("Coca-cola", "Botella 50 cl", "2,80 €"),
                    MenuItem("Coca-cola Cero", "Botella 50 cl", "2,50 €"),
                    MenuItem("Nestea", "Botella 50 cl", "2,80 €"),
                    MenuItem("Fanta de Naranja", "Botella 50 cl", "2,80 €"),
                    MenuItem("Fanta de Limon", "Botella 50 cl", "2,80 €"),
                    MenuItem("Sprite", "Botella 50 cl", "2,80 €"),
                    MenuItem("Mahou", "Botella 50 cl", "3,00 €"),
                    MenuItem("Cerveza italiana", "Peroni bien fria", "3,20 €"),
                    MenuItem("Copa de vino", "Tinto o blanco de la casa", "3,50 €")
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
                            mensajeError = ""
                        },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = correo,
                        onValueChange = {
                            correo = it
                            reservaConfirmada = false
                            mensajeError = ""
                        },
                        label = { Text("Correo electronico") },
                        placeholder = { Text("Ej: nombre@email.com") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    OutlinedTextField(
                        value = personas,
                        onValueChange = {
                            personas = it.filter { caracter -> caracter.isDigit() }
                            reservaConfirmada = false
                            mensajeError = ""
                        },
                        label = { Text("Numero de personas") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Box {
                        OutlinedTextField(
                            value = fechaReserva,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Dia de la reserva") },
                            placeholder = { Text("Selecciona un dia") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { mostrarDatePicker = true }
                        )
                    }

                    ExposedDropdownMenuBox(
                        expanded = horasExpanded,
                        onExpandedChange = {
                            if (horasDisponibles.isNotEmpty()) {
                                horasExpanded = !horasExpanded
                            }
                        }
                    ) {
                        OutlinedTextField(
                            value = horaReserva,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Hora de la reserva") },
                            placeholder = {
                                Text(
                                    if (horasDisponibles.isEmpty()) {
                                        "No hay horas disponibles"
                                    } else {
                                        "Selecciona una hora"
                                    }
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = horasExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            singleLine = true
                        )

                        ExposedDropdownMenu(
                            expanded = horasExpanded,
                            onDismissRequest = { horasExpanded = false }
                        ) {
                            horasDisponibles.forEach { hora ->
                                DropdownMenuItem(
                                    text = { Text(hora) },
                                    onClick = {
                                        horaReserva = hora
                                        horasExpanded = false
                                        reservaConfirmada = false
                                        mensajeError = ""
                                    }
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            val numeroPersonas = personas.toIntOrNull() ?: 0

                            mensajeError = when {
                                nombre.isBlank() -> "Introduce tu nombre."
                                correo.isBlank() -> "Introduce tu correo electronico."
                                !correo.isValidEmail() -> "Introduce un correo electronico valido."
                                personas.isBlank() -> "Introduce el numero de personas."
                                numeroPersonas <= 0 -> "El numero de personas debe ser mayor que 0."
                                fechaReserva.isBlank() -> "Introduce el dia de la reserva."
                                horasDisponibles.isEmpty() -> "No quedan horas disponibles para ese dia."
                                horaReserva.isBlank() -> "Selecciona la hora de la reserva."
                                else -> ""
                            }

                            reservaConfirmada = mensajeError.isEmpty()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB43B2A))
                    ) {
                        Text("Confirmar reserva")
                    }

                    if (mensajeError.isNotEmpty()) {
                        Text(
                            text = mensajeError,
                            color = Color(0xFFB43B2A),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    if (reservaConfirmada) {
                        Text(
                            text = "Reserva preparada para $nombre, $personas personas el $fechaReserva a las $horaReserva.",
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
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 2.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFB43B2A))
                        .padding(horizontal = 14.dp, vertical = 7.dp)
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

private fun String.isValidEmail(): Boolean {
    val email = trim()
    val atIndex = email.indexOf('@')
    val lastAtIndex = email.lastIndexOf('@')
    val dotAfterAt = email.indexOf('.', startIndex = atIndex + 1)

    return atIndex > 0 &&
        atIndex == lastAtIndex &&
        dotAfterAt > atIndex + 1 &&
        dotAfterAt < email.lastIndex
}

private fun todayStartUtcMillis(): Long {
    val localToday = Calendar.getInstance()
    val utcToday = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    utcToday.set(
        localToday.get(Calendar.YEAR),
        localToday.get(Calendar.MONTH),
        localToday.get(Calendar.DAY_OF_MONTH),
        0,
        0,
        0
    )
    utcToday.set(Calendar.MILLISECOND, 0)

    return utcToday.timeInMillis
}

private fun buildReservationHours(): List<String> {
    return buildList {
        var hora = 12
        var minuto = 30

        while (hora < 23 || hora == 23 && minuto == 0) {
            val horaTexto = hora.toString().padStart(2, '0')
            val minutoTexto = minuto.toString().padStart(2, '0')
            add("$horaTexto:$minutoTexto")

            minuto += 15
            if (minuto >= 60) {
                hora++
                minuto -= 60
            }
        }
    }
}

private fun availableReservationHours(
    selectedDateMillis: Long?,
    hours: List<String>,
    todayUtcMillis: Long
): List<String> {
    if (selectedDateMillis == null || selectedDateMillis > todayUtcMillis) {
        return hours
    }

    val now = Calendar.getInstance()
    val currentMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)

    return hours.filter { hour ->
        hour.toMinutesOfDay() >= currentMinutes
    }
}

private fun String.toMinutesOfDay(): Int {
    val parts = split(":")
    val hours = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val minutes = parts.getOrNull(1)?.toIntOrNull() ?: 0

    return hours * 60 + minutes
}

private fun Long.formatReservationDate(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES")).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.format(this)
}
