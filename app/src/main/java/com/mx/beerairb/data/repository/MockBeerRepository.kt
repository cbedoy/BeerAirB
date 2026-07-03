package com.mx.beerairb.data.repository

import com.mx.beerairb.R
import com.mx.beerairb.data.model.BeerExperience

class MockBeerRepository : BeerRepository {

    private val experiences = listOf(
        BeerExperience(
            id = "1",
            title = "Cervecería Chapultepec",
            hostName = "Carlos Mendoza",
            description = "Disfruta de un recorrido exclusivo por nuestra cervecería artesanal en el corazón de Chapultepec. Incluye degustación de 5 estilos diferentes, explicación del proceso de elaboración y maridaje con botanas locales.",
            pricePerPerson = 450.0,
            rating = 4.8,
            imageRes = R.drawable.ic_beer_1,
            location = "Chapultepec, CDMX",
            duration = "2.5 hrs",
            category = "Recorrido"
        ),
        BeerExperience(
            id = "2",
            title = "Barrel & Hops Tasting",
            hostName = "Ana Torres",
            description = "Una experiencia de cata guiada por una maestra cervecera certificada. Prueba 8 cervezas de barril artesanal mientras aprendes a identificar notas y estilos.",
            pricePerPerson = 350.0,
            rating = 4.6,
            imageRes = R.drawable.ic_beer_2,
            location = "Condesa, CDMX",
            duration = "2 hrs",
            category = "Cata"
        ),
        BeerExperience(
            id = "3",
            title = "La Cerveza del Valle",
            hostName = "Miguel Ángel Ruiz",
            description = "Visita a una granja cervecera en el Valle de México. Conoce el cultivo del lúpulo, el proceso de fermentación artesanal y termina con una comida campestre con maridaje.",
            pricePerPerson = 650.0,
            rating = 4.9,
            imageRes = R.drawable.ic_beer_3,
            location = "Valle de Bravo, Méx",
            duration = "5 hrs",
            category = "Recorrido"
        ),
        BeerExperience(
            id = "4",
            title = "Noche de Taproom",
            hostName = "Sofía Ramírez",
            description = "Una velada en nuestro taproom con acceso ilimitado a 12 llaves de cerveza artesanal, música en vivo y una tabla de quesos artesanales.",
            pricePerPerson = 280.0,
            rating = 4.4,
            imageRes = R.drawable.ic_beer_4,
            location = "Roma Norte, CDMX",
            duration = "3 hrs",
            category = "Taproom"
        ),
        BeerExperience(
            id = "5",
            title = "Taller de Cerveza Casera",
            hostName = "Jorge Camarena",
            description = "Aprende a hacer tu propia cerveza desde cero. Te llevas tu kit de inicio y la receta para replicarla en casa. Incluye todos los ingredientes y equipo.",
            pricePerPerson = 890.0,
            rating = 4.7,
            imageRes = R.drawable.ic_beer_5,
            location = "Coyoacán, CDMX",
            duration = "4 hrs",
            category = "Taller"
        ),
        BeerExperience(
            id = "6",
            title = "Ruta de Cervecerías",
            hostName = "Fernanda López",
            description = "Recorrido guiado por 4 cervecerías artesanales en la colonia Juárez. Transporte incluido, degustación en cada parada y un beer passport de recuerdo.",
            pricePerPerson = 520.0,
            rating = 4.5,
            imageRes = R.drawable.ic_beer_6,
            location = "Juárez, CDMX",
            duration = "4 hrs",
            category = "Recorrido"
        )
    )

    override fun getAllExperiences(): List<BeerExperience> = experiences

    override fun getExperienceById(id: String): BeerExperience? = experiences.find { it.id == id }
}
