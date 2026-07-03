package com.mx.beerairb.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mx.beerairb.data.repository.MockBeerRepository

class DetailViewModelFactory(
    private val experienceId: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(
            experienceId = experienceId,
            repository = MockBeerRepository()
        ) as T
    }
}
