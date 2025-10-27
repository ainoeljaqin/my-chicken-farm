package com.example.my_chicken_farm_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_chicken_farm_android.data.model.AyamAnakan
import com.example.my_chicken_farm_android.data.repository.ChickenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AyamAnakanViewModel : ViewModel() {

    private val repository = ChickenRepository()

    private val _ayamList = MutableStateFlow<List<AyamAnakan>>(emptyList())
    val ayamList: StateFlow<List<AyamAnakan>> = _ayamList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadAyamAnakan()
    }

    fun loadAyamAnakan(breedingId: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getAyamAnakan(breedingId)
                .onSuccess { list ->
                    _ayamList.value = list
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }

            _isLoading.value = false
        }
    }

    fun addAyam(ayam: AyamAnakan, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.addAyamAnakan(ayam)
                .onSuccess {
                    loadAyamAnakan()
                    onSuccess()
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }

            _isLoading.value = false
        }
    }

    fun updateAyam(ayam: AyamAnakan, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.updateAyamAnakan(ayam)
                .onSuccess {
                    loadAyamAnakan()
                    onSuccess()
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }

            _isLoading.value = false
        }
    }

    fun deleteAyam(id: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.deleteAyamAnakan(id)
                .onSuccess {
                    loadAyamAnakan()
                    onSuccess()
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }

            _isLoading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}
