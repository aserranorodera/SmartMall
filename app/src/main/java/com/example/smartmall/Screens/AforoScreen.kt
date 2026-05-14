package com.example.smartmall.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartmall.Aforo.AforoCard
import com.example.smartmall.Aforo.AforoViewModel

@Composable
fun AforoScreen(
    onBack: () -> Unit,
    onNavigateToZara: () -> Unit,
    viewModel: AforoViewModel = viewModel()
) {
    BackHandler { onBack() }

    val zonasAforo by viewModel.zonasAforo.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

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
                val viewportCenter =
                    (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
                visibleItems.minBy { item ->
                    val itemCenter = item.offset + item.size / 2
                    kotlin.math.abs(itemCenter - viewportCenter)
                }.index
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Header
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

        // Content
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                error != null && zonasAforo.isEmpty() -> {
                    Text(
                        text = error ?: "Error desconocido",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 16.sp
                    )
                }

                else -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(vertical = carouselPadding),
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        itemsIndexed(zonasAforo) { index, zona ->
                            AforoCard(
                                aforo = zona,
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
        }
    }
}