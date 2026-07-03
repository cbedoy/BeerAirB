package com.mx.beerairb.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beer_experiences")
data class BeerExperienceEntity(
    @PrimaryKey val id: String,
    val title: String,
    val hostName: String,
    val description: String,
    val pricePerPerson: Double,
    val rating: Double,
    val reviewCount: Int,
    val imageUrl: String,
    val location: String,
    val distanceKm: Double,
    val dateRange: String,
    val duration: String,
    val category: String,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean,
    val amenitiesJson: String
)
