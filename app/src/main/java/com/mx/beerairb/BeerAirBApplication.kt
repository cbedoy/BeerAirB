package com.mx.beerairb

import android.app.Application
import com.mx.beerairb.data.database.BeerAirBDatabase
import com.mx.beerairb.data.repository.BeerRepository
import com.mx.beerairb.data.repository.RoomBeerRepository

class BeerAirBApplication : Application() {

    val database: BeerAirBDatabase by lazy { BeerAirBDatabase.getInstance(this) }
    val repository: BeerRepository by lazy { RoomBeerRepository(database) }
}
