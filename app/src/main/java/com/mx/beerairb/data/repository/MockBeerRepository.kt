package com.mx.beerairb.data.repository

import com.mx.beerairb.R
import com.mx.beerairb.data.model.BeerAmenity
import com.mx.beerairb.data.model.BeerExperience

class MockBeerRepository : BeerRepository {

    private val experiences = listOf(
        BeerExperience(
            id = "1",
            title = "Langkawi Craft Brewery",
            hostName = "Carlos Mendoza",
            description = "Disfruta de los sonidos de la naturaleza mientras te hospedas en esta plataforma flotante conectada directamente a nuestro taproom artesanal. Incluye degustación de 5 estilos diferentes, explicación del proceso de elaboración y maridaje con botanas locales.",
            pricePerPerson = 450.0,
            rating = 4.8,
            reviewCount = 304,
            imageRes = R.drawable.ic_beer_1,
            location = "Langkawi, Malaysia",
            distanceKm = 3446.0,
            dateRange = "7-12 Oct",
            duration = "2.5 hrs",
            category = "Microbreweries",
            isFavorite = false,
            amenities = listOf(
                BeerAmenity("Glass", "Cata de bienvenida", 0),
                BeerAmenity("Keg", "Grifo propio", 1),
                BeerAmenity("Bed", "Hospedaje incluido", 2),
                BeerAmenity("Wifi", "WiFi / Pet Friendly", 3)
            )
        ),
        BeerExperience(
            id = "2",
            title = "Barrel & Hops Tasting Room",
            hostName = "Ana Torres",
            description = "Una experiencia de cata guiada por una maestra cervecera certificada. Prueba 8 cervezas de barril artesanal mientras aprendes a identificar notas y estilos en nuestro taproom exclusivo.",
            pricePerPerson = 350.0,
            rating = 4.6,
            reviewCount = 218,
            imageRes = R.drawable.ic_beer_2,
            location = "Condesa, CDMX",
            distanceKm = 3.2,
            dateRange = "12-15 Oct",
            duration = "2 hrs",
            category = "Taprooms",
            isFavorite = true,
            amenities = listOf(
                BeerAmenity("Glass", "Cata de bienvenida", 0),
                BeerAmenity("Keg", "Grifo propio", 1),
                BeerAmenity("Wifi", "WiFi / Pet Friendly", 3)
            )
        ),
        BeerExperience(
            id = "3",
            title = "Hop Farm Valley Experience",
            hostName = "Miguel Ángel Ruiz",
            description = "Visita a una granja de lúpulo en el Valle de México. Conoce el cultivo del lúpulo, el proceso de fermentación artesanal y termina con una comida campestre con maridaje.",
            pricePerPerson = 650.0,
            rating = 4.9,
            reviewCount = 157,
            imageRes = R.drawable.ic_beer_3,
            location = "Valle de Bravo, Méx",
            distanceKm = 156.0,
            dateRange = "19-21 Oct",
            duration = "5 hrs",
            category = "Hop Farms",
            isFavorite = false,
            amenities = listOf(
                BeerAmenity("Glass", "Cata de bienvenida", 0),
                BeerAmenity("Bed", "Hospedaje incluido", 2),
                BeerAmenity("Keg", "Grifo propio", 1)
            )
        ),
        BeerExperience(
            id = "4",
            title = "Noche de Taproom Roma",
            hostName = "Sofía Ramírez",
            description = "Una velada en nuestro taproom con acceso ilimitado a 12 llaves de cerveza artesanal, música en vivo y una tabla de quesos artesanales.",
            pricePerPerson = 280.0,
            rating = 4.4,
            reviewCount = 432,
            imageRes = R.drawable.ic_beer_4,
            location = "Roma Norte, CDMX",
            distanceKm = 1.8,
            dateRange = "14-16 Oct",
            duration = "3 hrs",
            category = "Taprooms",
            isFavorite = false,
            amenities = listOf(
                BeerAmenity("Glass", "Cata de bienvenida", 0),
                BeerAmenity("Bed", "Hospedaje incluido", 2),
                BeerAmenity("Wifi", "WiFi / Pet Friendly", 3)
            )
        ),
        BeerExperience(
            id = "5",
            title = "Cerveza Casera Workshop Glamping",
            hostName = "Jorge Camarena",
            description = "Aprende a hacer tu propia cerveza desde cero mientras te hospedas en nuestras cabañas. Te llevas tu kit de inicio y la receta para replicarla en casa.",
            pricePerPerson = 890.0,
            rating = 4.7,
            reviewCount = 89,
            imageRes = R.drawable.ic_beer_5,
            location = "Coyoacán, CDMX",
            distanceKm = 12.5,
            dateRange = "26-28 Oct",
            duration = "4 hrs",
            category = "Beer Glamping",
            isFavorite = true,
            amenities = listOf(
                BeerAmenity("Glass", "Cata de bienvenida", 0),
                BeerAmenity("Keg", "Grifo propio", 1),
                BeerAmenity("Bed", "Hospedaje incluido", 2)
            )
        ),
        BeerExperience(
            id = "6",
            title = "Microbrewery Route Juárez",
            hostName = "Fernanda López",
            description = "Recorrido guiado por 4 microcervecerías artesanales en la colonia Juárez. Transporte incluido, degustación en cada parada y un beer passport de recuerdo.",
            pricePerPerson = 520.0,
            rating = 4.5,
            reviewCount = 276,
            imageRes = R.drawable.ic_beer_6,
            location = "Juárez, CDMX",
            distanceKm = 2.1,
            dateRange = "5-8 Nov",
            duration = "4 hrs",
            category = "Microbreweries",
            isFavorite = false,
            amenities = listOf(
                BeerAmenity("Glass", "Cata de bienvenida", 0),
                BeerAmenity("Wifi", "WiFi / Pet Friendly", 3)
            )
        )
    )

    override fun getAllExperiences(): List<BeerExperience> = experiences

    override fun getExperienceById(id: String): BeerExperience? = experiences.find { it.id == id }
}
