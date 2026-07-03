package com.mx.beerairb.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.beerairb.data.model.BeerExperience
import com.mx.beerairb.data.repository.BeerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: BeerRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<BeerExperience>>(emptyList())
    val favorites: StateFlow<List<BeerExperience>> = _favorites.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavoriteExperiences().collect { list ->
                _favorites.value = list
            }
        }
    }

    fun onFavoriteToggle(id: String) {
        viewModelScope.launch {
            repository.toggleFavorite(id, false)
        }
    }
}
