package com.mx.beerairb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.mx.beerairb.BeerAirBApplication

class HomeViewModelFactory(
    private val application: BeerAirBApplication
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            repository = application.repository
        ) as T
    }
}
