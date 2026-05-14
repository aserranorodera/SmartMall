package com.example.smartmall.Components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun HomeCard(
    text: String,
    subtitle: String,
    imageRes: Int,
    animationDelayMillis: Int = 0,
    onClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val appearAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 420),
        label = "cardAppearAlpha"
    )

    val appearOffset by animateFloatAsState(
        targetValue = if (visible) 0f else 32f,
        animationSpec = tween(durationMillis = 420),
        label = "cardAppearOffset"
    )

    val pressedScale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "cardPressedScale"
    )

    LaunchedEffect(Unit) {
        delay(animationDelayMillis.toLong())
        visible = true
    }


    val gradientColors = listOf(
        Color(0xFF1E3C72),
        Color(0xFF2A5298)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(126.dp)
            .scale(pressedScale)
            .graphicsLayer {
                alpha = appearAlpha
                translationY = appearOffset
            }
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(26.dp),
                ambientColor = Color.Black.copy(alpha = 0.2f),
                spotColor = Color.Black.copy(alpha = 0.3f)
            )
            .clip(RoundedCornerShape(26.dp))
            .background(
                Brush.horizontalGradient(gradientColors)
            )
            .border(
                width = if (isPressed) 3.dp else 2.dp,
                color = Color.White.copy(alpha = if (isPressed) 0.9f else 0.6f),
                shape = RoundedCornerShape(26.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) { onClick() }
    ) {


        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 28.dp, y = 38.dp)
                .size(210.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = if (isPressed) 0.14f else 0.07f))
        )


        if (isPressed) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.08f))
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(imageRes),
                    contentDescription = text,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(22.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = ">",
                color = Color.White,
                fontSize = 42.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}