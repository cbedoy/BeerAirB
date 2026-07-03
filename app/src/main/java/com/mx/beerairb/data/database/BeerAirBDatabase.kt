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
                BeerExperienceEntity("1", "Langkawi Craft Brewery", "Carlos Mendoza", "Disfruta de los sonidos de la naturaleza mientras te hospedas en esta plataforma flotante conectada directamente a nuestro taproom artesanal. Incluye degustación de 5 estilos diferentes, explicación del proceso de elaboración y maridaje con botanas locales.", 450.0, 4.8, 304, "https://images.unsplash.com/photo-1566633806327-68e152aaf26d?w=600&h=400&fit=crop", "Langkawi, Malaysia", 3446.0, "7-12 Oct", "2.5 hrs", "Microbreweries", 6.32, 99.85, false, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Keg", "Grifo propio", 1), BeerAmenity("Bed", "Hospedaje incluido", 2), BeerAmenity("Wifi", "WiFi / Pet Friendly", 3)))),
                BeerExperienceEntity("2", "Barrel & Hops Tasting Room", "Ana Torres", "Una experiencia de cata guiada por una maestra cervecera certificada. Prueba 8 cervezas de barril artesanal mientras aprendes a identificar notas y estilos en nuestro taproom exclusivo.", 350.0, 4.6, 218, "https://images.unsplash.com/photo-1571746867546-4bb4b9b0a7b2?w=600&h=400&fit=crop", "Condesa, CDMX", 3.2, "12-15 Oct", "2 hrs", "Taprooms", 19.41, -99.17, true, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Keg", "Grifo propio", 1), BeerAmenity("Wifi", "WiFi / Pet Friendly", 3)))),
                BeerExperienceEntity("3", "Hop Farm Valley Experience", "Miguel Ángel Ruiz", "Visita a una granja de lúpulo en el Valle de México. Conoce el cultivo del lúpulo, el proceso de fermentación artesanal y termina con una comida campestre con maridaje.", 650.0, 4.9, 157, "https://images.unsplash.com/photo-1535958636474-b021ee887b13?w=600&h=400&fit=crop", "Valle de Bravo, Méx", 156.0, "19-21 Oct", "5 hrs", "Hop Farms", 19.19, -100.13, false, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Bed", "Hospedaje incluido", 2), BeerAmenity("Keg", "Grifo propio", 1)))),
                BeerExperienceEntity("4", "Noche de Taproom Roma", "Sofía Ramírez", "Una velada en nuestro taproom con acceso ilimitado a 12 llaves de cerveza artesanal, música en vivo y una tabla de quesos artesanales.", 280.0, 4.4, 432, "https://images.unsplash.com/photo-1558642452-9d2a7deb7f62?w=600&h=400&fit=crop", "Roma Norte, CDMX", 1.8, "14-16 Oct", "3 hrs", "Taprooms", 19.42, -99.16, false, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Bed", "Hospedaje incluido", 2), BeerAmenity("Wifi", "WiFi / Pet Friendly", 3)))),
                BeerExperienceEntity("5", "Cerveza Casera Workshop Glamping", "Jorge Camarena", "Aprende a hacer tu propia cerveza desde cero mientras te hospedas en nuestras cabañas. Te llevas tu kit de inicio y la receta para replicarla en casa.", 890.0, 4.7, 89, "https://images.unsplash.com/photo-1586999768265-24af89630739?w=600&h=400&fit=crop", "Coyoacán, CDMX", 12.5, "26-28 Oct", "4 hrs", "Beer Glamping", 19.34, -99.16, true, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Keg", "Grifo propio", 1), BeerAmenity("Bed", "Hospedaje incluido", 2)))),
                BeerExperienceEntity("6", "Microbrewery Route Juárez", "Fernanda López", "Recorrido guiado por 4 microcervecerías artesanales en la colonia Juárez. Transporte incluido, degustación en cada parada y un beer passport de recuerdo.", 520.0, 4.5, 276, "https://images.unsplash.com/photo-1518173946687-a36f968f7e1c?w=600&h=400&fit=crop", "Juárez, CDMX", 2.1, "5-8 Nov", "4 hrs", "Microbreweries", 19.43, -99.15, false, amenitiesToJson(listOf(BeerAmenity("Glass", "Cata de bienvenida", 0), BeerAmenity("Wifi", "WiFi / Pet Friendly", 3))))
            )
        }
    }
}
