package com.mx.beerairb.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.beerairb.data.model.BeerExperience
import com.mx.beerairb.data.repository.BeerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val experienceId: String,
    private val repository: BeerRepository
) : ViewModel() {

    private val _experience = MutableStateFlow<BeerExperience?>(null)
    val experience: StateFlow<BeerExperience?> = _experience.asStateFlow()

    init {
        loadExperience()
    }

    private fun loadExperience() {
        viewModelScope.launch {
            repository.getExperienceById(experienceId).collect { exp ->
                _experience.value = exp
            }
        }
    }

    fun toggleFavorite(id: String) {
        val exp = _experience.value ?: return
        viewModelScope.launch {
            repository.toggleFavorite(id, !exp.isFavorite)
        }
    }
}
