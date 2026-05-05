package com.example.smartmall.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmall.R
import com.example.smartmall.Restauracion.Restaurante
import com.example.smartmall.Restauracion.RestauranteCard

@Composable
fun AforoScreen(
    onBack: () -> Unit,
    onNavigateToZara: () -> Unit
) {

    BackHandler {
        onBack()
    }

    val zonasAforo = listOf(
        Restaurante("Apple", R.drawable.apple),
        Restaurante("Bershka", R.drawable.bershka),
        Restaurante("Besson", R.drawable.besson),
        Restaurante("Courir", R.drawable.courir),
        Restaurante("Druni", R.drawable.druni),
        Restaurante("Fnac", R.drawable.fnac),
        Restaurante("Hollister", R.drawable.hollister),
        Restaurante("Kiko", R.drawable.kiko),
        Restaurante("Lacoste", R.drawable.lacoste),
        Restaurante("Mango", R.drawable.mango),
        Restaurante("Pull&Bear", R.drawable.pull),
        Restaurante("Zara", R.drawable.zara),
        Restaurante("ZaraHome", R.drawable.zarahome),
    )

    val listState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val carouselPadding = configuration.screenHeightDp.dp * 0.28f
    val highlightedIndex by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo

            if (visibleItems.isEmpty()) {
                0
            } else {
                val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
                visibleItems.minBy { item ->
                    val itemCenter = item.offset + item.size / 2
                    kotlin.math.abs(itemCenter - viewportCenter)
                }.index
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 4.dp, end = 16.dp)
        ) {
            IconButton(
                onClick = { onBack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }

            Text(
                text = "Aforo",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = carouselPadding),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            itemsIndexed(zonasAforo) { index, zona ->
                RestauranteCard(
                    restaurante = zona,
                    highlighted = index == highlightedIndex,
                    onClick = {
                        if (zona.nombre == "Zara") {
                            onNavigateToZara()
                        }
                    }
                )
            }
        }
    }
}
