package com.mx.beerairb.ui.screenshot

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import com.mx.beerairb.data.model.BeerAmenity
import com.mx.beerairb.data.model.BeerExperience
import com.mx.beerairb.ui.home.NearbyTaproomCard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34])
class HomeScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<androidx.activity.ComponentActivity>()

    private val mockExperience = BeerExperience(
        id = "1",
        title = "Cervecería San Pancho",
        hostName = "Carlos Mendoza",
        description = "Disfruta de una cata guiada en el corazón de Aguascalientes.",
        pricePerPerson = 320.0,
        rating = 4.7,
        reviewCount = 156,
        imageUrl = "https://images.unsplash.com/photo-1566633806327-68e152aaf26d?w=600&h=400&fit=crop",
        location = "San Francisco de los Romo, Ags",
        distanceKm = 8.5,
        dateRange = "8-10 Oct",
        duration = "2.5 hrs",
        category = "Microbreweries",
        latitude = 21.92,
        longitude = -102.27,
        isFavorite = false,
        amenities = listOf(
            BeerAmenity("Glass", "Cata de bienvenida", 0),
            BeerAmenity("Keg", "Grifo propio", 1)
        )
    )

    @Test
    fun nearbyTaproomCard() {
        composeTestRule.setContent {
            NearbyTaproomCard(
                experience = mockExperience,
                onClick = {},
                onFavoriteToggle = {}
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
