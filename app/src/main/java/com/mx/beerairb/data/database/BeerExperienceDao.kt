package com.mx.beerairb.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerExperienceDao {
    @Query("SELECT * FROM beer_experiences ORDER BY distanceKm ASC")
    fun getAllExperiences(): Flow<List<BeerExperienceEntity>>

    @Query("SELECT * FROM beer_experiences WHERE id = :id")
    fun getExperienceById(id: String): Flow<BeerExperienceEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(experiences: List<BeerExperienceEntity>)

    @Update
    suspend fun update(experience: BeerExperienceEntity)

    @Query("UPDATE beer_experiences SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)
}
