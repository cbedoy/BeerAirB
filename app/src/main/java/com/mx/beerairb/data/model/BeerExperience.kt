package com.mx.beerairb.data.model

data class BeerExperience(
    val id: String,
    val title: String,
    val hostName: String,
    val description: String,
    val pricePerPerson: Double,
    val rating: Double,
    val imageRes: Int,
    val location: String,
    val duration: String,
    val category: String
)
