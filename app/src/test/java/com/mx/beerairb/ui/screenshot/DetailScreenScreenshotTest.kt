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
        title = "Hop Farm Los Vázquez",
        hostName = "Miguel Ángel Ruiz",
        description = "Recorre los campos de lúpulo en Calvillo, Ags. Conoce el cultivo y termina con comida campestre.",
        pricePerPerson = 550.0,
        rating = 4.9,
        reviewCount = 98,
        imageUrl = "https://images.unsplash.com/photo-1535958636474-b021ee887b13?w=600&h=400&fit=crop",
        location = "Calvillo, Ags",
        distanceKm = 45.0,
        dateRange = "19-21 Oct",
        duration = "5 hrs",
        category = "Hop Farms",
        latitude = 21.85,
        longitude = -102.72,
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
