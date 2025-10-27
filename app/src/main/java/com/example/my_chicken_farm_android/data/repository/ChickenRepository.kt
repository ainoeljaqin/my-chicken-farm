package com.example.my_chicken_farm_android.data.repository

import com.example.my_chicken_farm_android.data.api.RetrofitInstance
import com.example.my_chicken_farm_android.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChickenRepository {

    private val api = RetrofitInstance.api
    private val userEmail = "android@app.com"

    // Ayam Indukan operations
    suspend fun getAyamIndukan(): Result<List<AyamIndukan>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getAyamIndukan()
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()?.data ?: emptyList())
            } else {
                Result.failure(Exception(response.body()?.error ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addAyamIndukan(ayam: AyamIndukan): Result<AyamIndukan> = withContext(Dispatchers.IO) {
        try {
            val request = mapOf(
                "action" to "add_ayam_induk",
                "userEmail" to userEmail,
                "kode" to ayam.kode,
                "jenis_kelamin" to ayam.jenisKelamin,
                "ras" to ayam.ras,
                "warna" to ayam.warna,
                "tanggal_lahir" to ayam.tanggalLahir
            )
            val response = api.addAyamIndukan(request)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()?.data ?: ayam)
            } else {
                Result.failure(Exception(response.body()?.error ?: "Failed to add"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateAyamIndukan(ayam: AyamIndukan): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val request = mapOf(
                "action" to "update_ayam_induk",
                "userEmail" to userEmail,
                "id" to ayam.id,
                "kode" to ayam.kode,
                "jenis_kelamin" to ayam.jenisKelamin,
                "ras" to ayam.ras,
                "warna" to ayam.warna,
                "tanggal_lahir" to ayam.tanggalLahir
            )
            val response = api.updateAyamIndukan(request)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(true)
            } else {
                Result.failure(Exception(response.body()?.error ?: "Failed to update"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAyamIndukan(id: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val request = mapOf(
                "action" to "delete_ayam_induk",
                "userEmail" to userEmail,
                "id" to id
            )
            val response = api.deleteAyamIndukan(request)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(true)
            } else {
                Result.failure(Exception(response.body()?.error ?: "Failed to delete"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Breeding operations
    suspend fun getBreeding(): Result<List<Breeding>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getBreeding()
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()?.data ?: emptyList())
            } else {
                Result.failure(Exception(response.body()?.error ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addBreeding(breeding: Breeding): Result<Breeding> = withContext(Dispatchers.IO) {
        try {
            val request = mapOf(
                "action" to "add_breeding",
                "userEmail" to userEmail,
                "pejantan_id" to breeding.pejantanId,
                "betina_id" to breeding.betinaId,
                "tanggal_kawin" to breeding.tanggalKawin,
                "tanggal_menetas" to breeding.tanggalMenetas,
                "jumlah_anakan" to breeding.jumlahAnakan
            )
            val response = api.addBreeding(request)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()?.data ?: breeding)
            } else {
                Result.failure(Exception(response.body()?.error ?: "Failed to add"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateBreeding(breeding: Breeding): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val request = mapOf(
                "action" to "update_breeding",
                "userEmail" to userEmail,
                "id" to breeding.id,
                "pejantan_id" to breeding.pejantanId,
                "betina_id" to breeding.betinaId,
                "tanggal_kawin" to breeding.tanggalKawin,
                "tanggal_menetas" to breeding.tanggalMenetas,
                "jumlah_anakan" to breeding.jumlahAnakan
            )
            val response = api.updateBreeding(request)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(true)
            } else {
                Result.failure(Exception(response.body()?.error ?: "Failed to update"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteBreeding(id: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val request = mapOf(
                "action" to "delete_breeding",
                "userEmail" to userEmail,
                "id" to id
            )
            val response = api.deleteBreeding(request)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(true)
            } else {
                Result.failure(Exception(response.body()?.error ?: "Failed to delete"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Ayam Anakan operations
    suspend fun getAyamAnakan(breedingId: String? = null): Result<List<AyamAnakan>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getAyamAnakan(breedingId = breedingId)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()?.data ?: emptyList())
            } else {
                Result.failure(Exception(response.body()?.error ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addAyamAnakan(ayam: AyamAnakan): Result<AyamAnakan> = withContext(Dispatchers.IO) {
        try {
            val request = mapOf(
                "action" to "add_ayam_anakan",
                "userEmail" to userEmail,
                "breeding_id" to ayam.breedingId,
                "kode" to ayam.kode,
                "jenis_kelamin" to ayam.jenisKelamin,
                "warna" to ayam.warna,
                "status" to ayam.status
            )
            val response = api.addAyamAnakan(request)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()?.data ?: ayam)
            } else {
                Result.failure(Exception(response.body()?.error ?: "Failed to add"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateAyamAnakan(ayam: AyamAnakan): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val request = mapOf(
                "action" to "update_ayam_anakan",
                "userEmail" to userEmail,
                "id" to ayam.id,
                "breeding_id" to ayam.breedingId,
                "kode" to ayam.kode,
                "jenis_kelamin" to ayam.jenisKelamin,
                "warna" to ayam.warna,
                "status" to ayam.status
            )
            val response = api.updateAyamAnakan(request)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(true)
            } else {
                Result.failure(Exception(response.body()?.error ?: "Failed to update"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAyamAnakan(id: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val request = mapOf(
                "action" to "delete_ayam_anakan",
                "userEmail" to userEmail,
                "id" to id
            )
            val response = api.deleteAyamAnakan(request)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(true)
            } else {
                Result.failure(Exception(response.body()?.error ?: "Failed to delete"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
