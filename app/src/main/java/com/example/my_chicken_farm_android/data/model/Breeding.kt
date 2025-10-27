package com.example.my_chicken_farm_android.data.model

import com.google.gson.annotations.SerializedName

data class Breeding(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("pejantan_id")
    val pejantanId: String = "",

    @SerializedName("betina_id")
    val betinaId: String = "",

    @SerializedName("tanggal_kawin")
    val tanggalKawin: String = "",

    @SerializedName("tanggal_menetas")
    val tanggalMenetas: String = "",

    @SerializedName("jumlah_anakan")
    val jumlahAnakan: Int = 0,

    @SerializedName("status")
    val status: String = "aktif",

    @SerializedName("pemilik_email")
    val pemilikEmail: String = "android@app.com",

    // Field tambahan untuk display
    @SerializedName("pejantan_kode")
    val pejantanKode: String? = null,

    @SerializedName("betina_kode")
    val betinaKode: String? = null
)
