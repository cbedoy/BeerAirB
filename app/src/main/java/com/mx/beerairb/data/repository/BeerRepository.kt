package com.mx.beerairb.data.repository

import com.mx.beerairb.data.model.BeerExperience
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    fun getAllExperiences(): Flow<List<BeerExperience>>
    fun getExperienceById(id: String): Flow<BeerExperience?>
    suspend fun toggleFavorite(id: String, isFavorite: Boolean)
}
