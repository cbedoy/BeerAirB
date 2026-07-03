package com.mx.beerairb.data.repository

import com.mx.beerairb.data.model.BeerExperience

interface BeerRepository {
    fun getAllExperiences(): List<BeerExperience>
    fun getExperienceById(id: String): BeerExperience?
}
