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
        _filteredExperiences.value = if (query.isBlank()) {
            _experiences.value
        } else {
            _experiences.value.filter { experience ->
                experience.title.contains(query, ignoreCase = true) ||
                    experience.location.contains(query, ignoreCase = true) ||
                    experience.category.contains(query, ignoreCase = true) ||
                    experience.hostName.contains(query, ignoreCase = true)
            }
        }
    }
}
