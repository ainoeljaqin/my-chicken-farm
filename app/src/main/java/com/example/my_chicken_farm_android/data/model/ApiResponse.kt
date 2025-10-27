package com.example.my_chicken_farm_android.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("success")
    val success: Boolean = false,

    @SerializedName("data")
    val data: T? = null,

    @SerializedName("error")
    val error: String? = null
)
