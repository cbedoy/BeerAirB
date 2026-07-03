package com.mx.beerairb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.beerairb.BeerAirBApplication
import com.mx.beerairb.data.model.BeerExperience
import com.mx.beerairb.data.repository.BeerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BeerRepository
) : ViewModel() {

    private val _experiences = MutableStateFlow<List<BeerExperience>>(emptyList())
    val experiences: StateFlow<List<BeerExperience>> = _experiences.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filteredExperiences = MutableStateFlow<List<BeerExperience>>(emptyList())
    val filteredExperiences: StateFlow<List<BeerExperience>> = _filteredExperiences.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    init {
        loadExperiences()
    }

    private fun loadExperiences() {
        viewModelScope.launch {
            repository.getAllExperiences().collect { all ->
                _experiences.value = all
                applyFilters()
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        applyFilters()
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = if (_selectedCategory.value == category) null else category
        applyFilters()
    }

    fun onFavoriteToggle(id: String) {
        val exp = _experiences.value.find { it.id == id } ?: return
        viewModelScope.launch {
            repository.toggleFavorite(id, !exp.isFavorite)
        }
    }

    private fun applyFilters() {
        val query = _searchQuery.value
        val category = _selectedCategory.value
        _filteredExperiences.value = _experiences.value.filter { exp ->
            val matchesQuery = query.isBlank() ||
                exp.title.contains(query, ignoreCase = true) ||
                exp.location.contains(query, ignoreCase = true) ||
                exp.category.contains(query, ignoreCase = true) ||
                exp.hostName.contains(query, ignoreCase = true)
            val matchesCategory = category == null || exp.category == category
            matchesQuery && matchesCategory
        }
    }
}
