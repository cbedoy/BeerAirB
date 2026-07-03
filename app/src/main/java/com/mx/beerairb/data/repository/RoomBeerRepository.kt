package com.mx.beerairb.data.repository

import com.mx.beerairb.data.database.BeerAirBDatabase
import com.mx.beerairb.data.database.BeerExperienceEntity
import com.mx.beerairb.data.model.BeerAmenity
import com.mx.beerairb.data.model.BeerExperience
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

class RoomBeerRepository(
    database: BeerAirBDatabase
) : BeerRepository {

    private val dao = database.beerExperienceDao()

    override fun getAllExperiences(): Flow<List<BeerExperience>> {
        return dao.getAllExperiences().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getExperienceById(id: String): Flow<BeerExperience?> {
        return dao.getExperienceById(id).map { it?.toDomain() }
    }

    override fun getFavoriteExperiences(): Flow<List<BeerExperience>> {
        return dao.getFavoriteExperiences().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        dao.updateFavorite(id, isFavorite)
    }
}

private fun BeerExperienceEntity.toDomain(): BeerExperience {
    val amenities = try {
        val arr = JSONArray(amenitiesJson)
        val list = mutableListOf<BeerAmenity>()
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            list.add(
                BeerAmenity(
                    iconDescription = obj.getString("iconDescription"),
                    label = obj.getString("label"),
                    badgeColorIndex = obj.getInt("badgeColorIndex")
                )
            )
        }
        list
    } catch (e: Exception) {
        emptyList()
    }

    return BeerExperience(
        id = id,
        title = title,
        hostName = hostName,
        description = description,
        pricePerPerson = pricePerPerson,
        rating = rating,
        reviewCount = reviewCount,
        imageUrl = imageUrl,
        location = location,
        distanceKm = distanceKm,
        dateRange = dateRange,
        duration = duration,
        category = category,
        isFavorite = isFavorite,
        amenities = amenities
    )
}
