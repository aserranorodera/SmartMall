package com.example.smartmall.Aforo

import com.google.gson.annotations.SerializedName

data class AforoApi(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("aforoActual") val aforoActual: Int,
    @SerializedName("aforoMaximo") val aforoMaximo: Int
)
