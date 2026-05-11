package com.example.smartmall.Parking

import retrofit2.http.GET

interface ParkingApi {

    @GET("api/parking")
    suspend fun getParking(): List<Plaza>
}