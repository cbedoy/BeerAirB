package com.mx.beerairb.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.beerairb.data.model.BeerExperience
import com.mx.beerairb.data.repository.BeerRepository
import com.mx.beerairb.data.repository.MockBeerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: BeerRepository = MockBeerRepository()
) : ViewModel() {

    private val experienceId: String = savedStateHandle.get<String>("id") ?: ""

    private val _experience = MutableStateFlow<BeerExperience?>(null)
    val experience: StateFlow<BeerExperience?> = _experience.asStateFlow()

    init {
        loadExperience()
    }

    private fun loadExperience() {
        viewModelScope.launch {
            _experience.value = repository.getExperienceById(experienceId)
        }
    }

    fun toggleFavorite(id: String) {
        _experience.value = _experience.value?.let { exp ->
            if (exp.id == id) exp.copy(isFavorite = !exp.isFavorite) else exp
        }
    }
}
