package com.example.my_chicken_farm_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_chicken_farm_android.data.model.AyamIndukan
import com.example.my_chicken_farm_android.data.repository.ChickenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AyamIndukanViewModel : ViewModel() {

    private val repository = ChickenRepository()

    private val _ayamList = MutableStateFlow<List<AyamIndukan>>(emptyList())
    val ayamList: StateFlow<List<AyamIndukan>> = _ayamList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadAyamIndukan()
    }

    fun loadAyamIndukan() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getAyamIndukan()
                .onSuccess { list ->
                    _ayamList.value = list
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }

            _isLoading.value = false
        }
    }

    fun addAyam(ayam: AyamIndukan, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.addAyamIndukan(ayam)
                .onSuccess {
                    loadAyamIndukan()
                    onSuccess()
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }

            _isLoading.value = false
        }
    }

    fun updateAyam(ayam: AyamIndukan, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.updateAyamIndukan(ayam)
                .onSuccess {
                    loadAyamIndukan()
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

            repository.deleteAyamIndukan(id)
                .onSuccess {
                    loadAyamIndukan()
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
