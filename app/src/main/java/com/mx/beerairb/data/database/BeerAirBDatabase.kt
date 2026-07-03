package com.mx.beerairb.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mx.beerairb.data.model.BeerAmenity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@Database(entities = [BeerExperienceEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BeerAirBDatabase : RoomDatabase() {

    abstract fun beerExperienceDao(): BeerExperienceDao

    companion object {
        @Volatile
        private var INSTANCE: BeerAirBDatabase? = null

        fun getInstance(context: Context): BeerAirBDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): BeerAirBDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                BeerAirBDatabase::class.java,
                "beerairb_database"
            )
                .fallbackToDestructiveMigration()
                .addCallback(SeedCallback())
                .build()
        }

        private fun amenitiesToJson(amenities: List<BeerAmenity>): String {
            val arr = JSONArray()
            amenities.forEach { a ->
                val obj = JSONObject()
                obj.put("iconDescription", a.iconDescription)
                obj.put("label", a.label)
                obj.put("badgeColorIndex", a.badgeColorIndex)
                arr.put(obj)
            }
            return arr.toString()
        }

        private class SeedCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        database.beerExperienceDao().insertAll(seedData())
                    }
                }
            }
        }

        private fun seedData(): List<BeerExperienceEntity> {
            return listOf(
                BeerExperienceEntity("1", "Cervecería San Pancho", "Carlos Mendoza", "Disfruta de una cata guiada en el corazón de Aguascalientes. Conoce el proceso artesanal mientras pruebas 5 estilos diferentes acompañados de botanas típicas hidrocálidas.", 320.0, 4.7, 156, "https://images.unsplash.com/photo-1566633806327-68e152aaf26d?w=600&h=400&fit=crop", "San Francisco de los Romo, Ags", 8.5, "8-10 Oct", "2.5 hrs", "Microbreweries", 21.92, -102.27, false, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Keg", "Grifo propio", 1), BeerAmenity("Wifi", "WiFi / Pet Friendly", 3)))),
                BeerExperienceEntity("2", "Barrel & Hops Taproom Centro", "Ana Torres", "Taproom en el centro histórico con 12 llaves de cerveza artesanal. Incluye tabla de quesos artesanales de la región y visita guiada a la fábrica.", 280.0, 4.5, 203, "https://images.unsplash.com/photo-1571746867546-4bb4b9b0a7b2?w=600&h=400&fit=crop", "Centro, Aguascalientes", 1.2, "12-15 Oct", "2 hrs", "Taprooms", 21.88, -102.30, true, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Keg", "Grifo propio", 1), BeerAmenity("Wifi", "WiFi / Pet Friendly", 3)))),
                BeerExperienceEntity("3", "Hop Farm Los Vázquez", "Miguel Ángel Ruiz", "Recorre los campos de lúpulo en el municipio de Calvillo. Conoce el cultivo, la cosecha y la producción artesanal, termina con una comida campestre.", 550.0, 4.9, 98, "https://images.unsplash.com/photo-1535958636474-b021ee887b13?w=600&h=400&fit=crop", "Calvillo, Ags", 45.0, "19-21 Oct", "5 hrs", "Hop Farms", 21.85, -102.72, false, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Bed", "Hospedaje incluido", 2), BeerAmenity("Keg", "Grifo propio", 1)))),
                BeerExperienceEntity("4", "Noche de Taproom Santa Fe", "Sofía Ramírez", "Velada en el barrio de Santa Fe con acceso ilimitado a cerveza artesanal, música en vivo y botanas de la región.", 220.0, 4.3, 312, "https://images.unsplash.com/photo-1558642452-9d2a7deb7f62?w=600&h=400&fit=crop", "Santa Fe, Aguascalientes", 2.5, "14-16 Oct", "3 hrs", "Taprooms", 21.84, -102.31, false, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Bed", "Hospedaje incluido", 2), BeerAmenity("Wifi", "WiFi / Pet Friendly", 3)))),
                BeerExperienceEntity("5", "Cerveza Casera Glamping Aguascalientes", "Jorge Camarena", "Hospédate en una cabaña ecológica mientras aprendes a hacer tu propia cerveza. Incluye fogata, malteada y kit para llevar.", 780.0, 4.8, 67, "https://images.unsplash.com/photo-1586999768265-24af89630739?w=600&h=400&fit=crop", "Jesús María, Ags", 15.0, "26-28 Oct", "6 hrs", "Beer Glamping", 21.96, -102.36, true, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Keg", "Grifo propio", 1), BeerAmenity("Bed", "Hospedaje incluido", 2)))),
                BeerExperienceEntity("6", "Microbrewery Tour Aguascalientes", "Fernanda López", "Recorrido guiado por 3 microcervecerías de la ciudad. Transporte incluido, degustación en cada parada y un beer passport de recuerdo.", 450.0, 4.6, 184, "https://images.unsplash.com/photo-1518173946687-a36f968f7e1c?w=600&h=400&fit=crop", "Aguascalientes, Ags", 3.0, "5-8 Nov", "4 hrs", "Microbreweries", 21.88, -102.29, false, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Wifi", "WiFi / Pet Friendly", 3))))
            )
        }
    }
}
