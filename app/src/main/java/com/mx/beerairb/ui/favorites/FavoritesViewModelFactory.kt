package com.mx.beerairb.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mx.beerairb.BeerAirBApplication

class FavoritesViewModelFactory(
    private val application: BeerAirBApplication
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoritesViewModel(
            repository = application.repository
        ) as T
    }
}
