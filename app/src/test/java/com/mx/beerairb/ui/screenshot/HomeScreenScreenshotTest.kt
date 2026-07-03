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
        title = "Langkawi Craft Brewery",
        hostName = "Carlos Mendoza",
        description = "Disfruta de un recorrido exclusivo por nuestra cervecería artesanal.",
        pricePerPerson = 450.0,
        rating = 4.8,
        reviewCount = 304,
        imageRes = 0,
        location = "Langkawi, Malaysia",
        distanceKm = 3446.0,
        dateRange = "7-12 Oct",
        duration = "2.5 hrs",
        category = "Microbreweries",
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
