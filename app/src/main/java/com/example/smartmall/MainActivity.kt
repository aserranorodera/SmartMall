package com.example.smartmall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.smartmall.Screens.AforoScreen
import com.example.smartmall.Screens.BeataPastaScreen
import com.example.smartmall.Screens.HomeScreen
import com.example.smartmall.Screens.ParkingScreen
import com.example.smartmall.Screens.RestaurantesScreen
import com.example.smartmall.Screens.ZaraScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var currentScreen by remember { mutableStateOf("home") }

            when (currentScreen) {

                "home" -> HomeScreen(
                    onNavigateToParking = {
                        currentScreen = "parking"
                    },
                    onNavigateToAforo = {
                        currentScreen = "aforo"
                    },
                    onNavigateToRestaurantes = {
                        currentScreen = "restaurantes"
                    }
                )

                "parking" -> {
                    ParkingScreen(
                        onBack = { currentScreen = "home" }
                    )
                }

                "aforo" -> {
                    AforoScreen(
                        onBack = { currentScreen = "home" },
                        onNavigateToZara = {
                            currentScreen = "zara"
                        }
                    )
                }

                "zara" -> {
                    ZaraScreen(
                        onBack = { currentScreen = "aforo" }
                    )
                }

                "restaurantes" -> {
                    RestaurantesScreen(
                        onBack = { currentScreen = "home" },
                        onNavigateToBeataPasta = {
                            currentScreen = "beata"
                        }
                    )
                }

                "beata" ->
                    BeataPastaScreen (
                    onBack = { currentScreen = "restaurantes" }
                )
            }
        }
    }
}
