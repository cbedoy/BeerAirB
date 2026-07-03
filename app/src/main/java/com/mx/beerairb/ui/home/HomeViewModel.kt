package com.mx.beerairb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.beerairb.data.model.BeerExperience
import com.mx.beerairb.data.repository.BeerRepository
import com.mx.beerairb.data.repository.MockBeerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BeerRepository = MockBeerRepository()
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
            val all = repository.getAllExperiences()
            _experiences.value = all
            _filteredExperiences.value = all
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
        _experiences.value = _experiences.value.map { exp ->
            if (exp.id == id) exp.copy(isFavorite = !exp.isFavorite) else exp
        }
        applyFilters()
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
