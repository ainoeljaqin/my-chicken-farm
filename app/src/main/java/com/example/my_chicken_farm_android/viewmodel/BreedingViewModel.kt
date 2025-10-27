package com.example.my_chicken_farm_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_chicken_farm_android.data.model.Breeding
import com.example.my_chicken_farm_android.data.repository.ChickenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BreedingViewModel : ViewModel() {

    private val repository = ChickenRepository()

    private val _breedingList = MutableStateFlow<List<Breeding>>(emptyList())
    val breedingList: StateFlow<List<Breeding>> = _breedingList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadBreeding()
    }

    fun loadBreeding() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getBreeding()
                .onSuccess { list ->
                    _breedingList.value = list
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }

            _isLoading.value = false
        }
    }

    fun addBreeding(breeding: Breeding, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.addBreeding(breeding)
                .onSuccess {
                    loadBreeding()
                    onSuccess()
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }

            _isLoading.value = false
        }
    }

    fun updateBreeding(breeding: Breeding, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.updateBreeding(breeding)
                .onSuccess {
                    loadBreeding()
                    onSuccess()
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }

            _isLoading.value = false
        }
    }

    fun deleteBreeding(id: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.deleteBreeding(id)
                .onSuccess {
                    loadBreeding()
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
