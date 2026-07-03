package com.mx.beerairb.data.model

data class BeerExperience(
    val id: String,
    val title: String,
    val hostName: String,
    val description: String,
    val pricePerPerson: Double,
    val rating: Double,
    val reviewCount: Int,
    val imageRes: Int,
    val location: String,
    val distanceKm: Double,
    val dateRange: String,
    val duration: String,
    val category: String,
    val isFavorite: Boolean,
    val amenities: List<BeerAmenity>
)
