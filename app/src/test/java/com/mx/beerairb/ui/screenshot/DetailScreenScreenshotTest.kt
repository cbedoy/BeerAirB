package com.mx.beerairb.ui.screenshot

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import com.mx.beerairb.data.model.BeerAmenity
import com.mx.beerairb.data.model.BeerExperience
import com.mx.beerairb.ui.detail.DetailContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34])
class DetailScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<androidx.activity.ComponentActivity>()

    private val mockExperience = BeerExperience(
        id = "3",
        title = "La Cerveza del Valle",
        hostName = "Miguel Ángel Ruiz",
        description = "Visita a una granja cervecera en el Valle de México. Conoce el cultivo del lúpulo, el proceso de fermentación artesanal y termina con una comida campestre con maridaje.",
        pricePerPerson = 650.0,
        rating = 4.9,
        reviewCount = 157,
        imageRes = 0,
        location = "Valle de Bravo, Méx",
        distanceKm = 156.0,
        dateRange = "19-21 Oct",
        duration = "5 hrs",
        category = "Hop Farms",
        isFavorite = true,
        amenities = listOf(
            BeerAmenity("Glass", "Cata de bienvenida", 0),
            BeerAmenity("Keg", "Grifo propio", 1),
            BeerAmenity("Bed", "Hospedaje incluido", 2),
            BeerAmenity("Wifi", "WiFi / Pet Friendly", 3)
        )
    )

    @Test
    fun detailContent() {
        composeTestRule.setContent {
            DetailContent(experience = mockExperience)
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
