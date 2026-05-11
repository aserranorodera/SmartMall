package com.example.smartmall.API

import com.example.smartmall.Aforo.AforoApi
import com.example.smartmall.Parking.Plaza
import retrofit2.http.GET

interface SmartMallApi {

    @GET("api/parking")
    suspend fun getParking(): List<Plaza>

    @GET("api/tiendas")
    suspend fun getTiendas(): List<AforoApi>
}