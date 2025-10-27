package com.example.my_chicken_farm_android.data.api

import com.example.my_chicken_farm_android.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // GET requests for fetching data
    @GET("exec")
    suspend fun getAyamIndukan(
        @Query("path") path: String = "ayam_induk",
        @Query("userEmail") userEmail: String = "android@app.com"
    ): Response<ApiResponse<List<AyamIndukan>>>

    @GET("exec")
    suspend fun getBreeding(
        @Query("path") path: String = "breeding",
        @Query("userEmail") userEmail: String = "android@app.com"
    ): Response<ApiResponse<List<Breeding>>>

    @GET("exec")
    suspend fun getAyamAnakan(
        @Query("path") path: String = "ayam_anakan",
        @Query("userEmail") userEmail: String = "android@app.com",
        @Query("breeding_id") breedingId: String? = null
    ): Response<ApiResponse<List<AyamAnakan>>>

    // POST requests for mutations
    @POST("exec")
    @Headers("Content-Type: application/json")
    suspend fun addAyamIndukan(
        @Body request: Map<String, Any>
    ): Response<ApiResponse<AyamIndukan>>

    @POST("exec")
    @Headers("Content-Type: application/json")
    suspend fun updateAyamIndukan(
        @Body request: Map<String, Any>
    ): Response<ApiResponse<Any>>

    @POST("exec")
    @Headers("Content-Type: application/json")
    suspend fun deleteAyamIndukan(
        @Body request: Map<String, Any>
    ): Response<ApiResponse<Any>>

    @POST("exec")
    @Headers("Content-Type: application/json")
    suspend fun addBreeding(
        @Body request: Map<String, Any>
    ): Response<ApiResponse<Breeding>>

    @POST("exec")
    @Headers("Content-Type: application/json")
    suspend fun updateBreeding(
        @Body request: Map<String, Any>
    ): Response<ApiResponse<Any>>

    @POST("exec")
    @Headers("Content-Type: application/json")
    suspend fun deleteBreeding(
        @Body request: Map<String, Any>
    ): Response<ApiResponse<Any>>

    @POST("exec")
    @Headers("Content-Type: application/json")
    suspend fun addAyamAnakan(
        @Body request: Map<String, Any>
    ): Response<ApiResponse<AyamAnakan>>

    @POST("exec")
    @Headers("Content-Type: application/json")
    suspend fun updateAyamAnakan(
        @Body request: Map<String, Any>
    ): Response<ApiResponse<Any>>

    @POST("exec")
    @Headers("Content-Type: application/json")
    suspend fun deleteAyamAnakan(
        @Body request: Map<String, Any>
    ): Response<ApiResponse<Any>>
}
