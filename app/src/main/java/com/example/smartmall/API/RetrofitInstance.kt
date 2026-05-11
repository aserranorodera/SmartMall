package com.example.smartmall.API

import com.example.smartmall.API.SmartMallApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://smartmall.onrender.com/"

    val api: SmartMallApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SmartMallApi::class.java)
    }
}