package com.example.smartmall.Screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.smartmall.R

@Composable
fun SplashScreen() {
    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1400),
        label = "splashAlpha"
    )

    val offsetYAnim by animateFloatAsState(
        targetValue = if (startAnimation) 0f else 42f,
        animationSpec = tween(durationMillis = 1400),
        label = "splashOffsetY"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo SmartMall",
            modifier = Modifier
                .fillMaxSize()
                .alpha(alphaAnim)
                .graphicsLayer {
                    translationY = offsetYAnim
                },
            contentScale = ContentScale.Crop
        )
    }
}
