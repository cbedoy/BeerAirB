package com.mx.beerairb.ui.screenshot

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.robolectric.annotation.Config
import com.github.takahirom.roborazzi.captureRoboImage
import com.mx.beerairb.data.model.BeerExperience
import com.mx.beerairb.ui.home.ExperienceCard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34])
class HomeScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<androidx.activity.ComponentActivity>()

    private val mockExperience = BeerExperience(
        id = "1",
        title = "Cervecería Chapultepec",
        hostName = "Carlos Mendoza",
        description = "Disfruta de un recorrido exclusivo por nuestra cervecería artesanal.",
        pricePerPerson = 450.0,
        rating = 4.8,
        imageRes = 0,
        location = "Chapultepec, CDMX",
        duration = "2.5 hrs",
        category = "Recorrido"
    )

    @Test
    fun experienceCard() {
        composeTestRule.setContent {
            ExperienceCard(
                experience = mockExperience,
                onClick = {}
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
