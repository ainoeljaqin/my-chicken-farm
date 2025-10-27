package com.example.my_chicken_farm_android.data.model

import com.google.gson.annotations.SerializedName

data class AyamAnakan(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("breeding_id")
    val breedingId: String = "",

    @SerializedName("kode")
    val kode: String = "",

    @SerializedName("jenis_kelamin")
    val jenisKelamin: String = "",

    @SerializedName("warna")
    val warna: String = "",

    @SerializedName("status")
    val status: String = "hidup", // "hidup", "mati", "dijual"

    @SerializedName("pemilik_email")
    val pemilikEmail: String = "android@app.com"
)
