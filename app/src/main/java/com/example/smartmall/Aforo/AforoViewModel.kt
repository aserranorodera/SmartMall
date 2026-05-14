package com.example.smartmall.Aforo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmall.API.RetrofitInstance
import com.example.smartmall.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AforoViewModel : ViewModel() {

    private val _zonasAforo = MutableStateFlow<List<Aforo>>(emptyList())
    val zonasAforo: StateFlow<List<Aforo>> = _zonasAforo

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    companion object {
        private const val POLLING_INTERVAL_MS = 1_000L

        // Mapa de nombre de tienda → recurso de imagen local
        private val imagenPorNombre: Map<String, Int> = mapOf(
            "Apple"     to R.drawable.apple,
            "Bershka"   to R.drawable.bershka,
            "Besson"    to R.drawable.besson,
            "Courir"    to R.drawable.courir,
            "Druni"     to R.drawable.druni,
            "Fnac"      to R.drawable.fnac,
            "Hollister" to R.drawable.hollister,
            "Kiko"      to R.drawable.kiko,
            "Lacoste"   to R.drawable.lacoste,
            "Mango"     to R.drawable.mango,
            "Pull&Bear" to R.drawable.pull,
            "Zara"      to R.drawable.zara,
            "ZaraHome"  to R.drawable.zarahome,
        )
    }

    init {
        startPolling()
    }

    private fun startPolling() {
        viewModelScope.launch {
            while (true) {
                fetchTiendas()
                delay(POLLING_INTERVAL_MS)
            }
        }
    }

    private suspend fun fetchTiendas() {
        try {
            val response = RetrofitInstance.api.getTiendas()

            val tiendas = response
                .mapNotNull { api ->
                    val imagen = imagenPorNombre[api.nombre] ?: return@mapNotNull null
                    Aforo(
                        nombre = api.nombre,
                        imagen = imagen,
                        personasActuales = api.aforoActual,
                        capacidadMaxima = api.aforoMaximo
                    )
                }
                .sortedBy { it.nombre }

            _zonasAforo.value = tiendas
            _error.value = null
        } catch (e: Exception) {
            _error.value = "Error al cargar el aforo"
        } finally {
            _isLoading.value = false
        }
    }
}
