package com.example.smartmall.Parking

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmall.API.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ParkingViewModel : ViewModel() {

    private val _parkingList = mutableStateOf<List<Plaza>>(emptyList())
    val parkingList: State<List<Plaza>> = _parkingList

    init {
        startPolling()
    }

    private fun startPolling() {
        viewModelScope.launch {
            while (true) {
                try {
                    val response = RetrofitInstance.api.getParking()
                    Log.d("API", "Datos recibidos: $response")
                    _parkingList.value = response
                } catch (e: Exception) {
                    Log.e("API", "Error obteniendo parking: ${e.message}")
                }

                delay(1000)
            }
        }
    }
}