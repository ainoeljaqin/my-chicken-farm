package com.example.my_chicken_farm_android.data.model

import com.google.gson.annotations.SerializedName

data class AyamIndukan(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("kode")
    val kode: String = "",

    @SerializedName("jenis_kelamin")
    val jenisKelamin: String = "", // "jantan" atau "betina"

    @SerializedName("ras")
    val ras: String = "",

    @SerializedName("warna")
    val warna: String = "",

    @SerializedName("tanggal_lahir")
    val tanggalLahir: String = "",

    @SerializedName("status")
    val status: String = "aktif", // untuk field baru jika diperlukan

    @SerializedName("pemilik_email")
    val pemilikEmail: String = "android@app.com" // default untuk Android app
)
