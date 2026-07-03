package com.mx.beerairb.ui.screenshot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import com.mx.beerairb.data.model.BeerExperience
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
        imageRes = 0,
        location = "Valle de Bravo, Méx",
        duration = "5 hrs",
        category = "Recorrido"
    )

    @Composable
    private fun MockDetailContent(experience: BeerExperience) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .height(260.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🍺", style = MaterialTheme.typography.displayLarge)
                }
            }
            Column(modifier = Modifier.padding(20.dp)) {
                Text(experience.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text("★ ${experience.rating}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Text(experience.location, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Card(
                    modifier = Modifier.height(36.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(experience.category, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                }
                Text("Anfitrión: ${experience.hostName}", style = MaterialTheme.typography.titleSmall)
                Text(experience.description, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }

    @Test
    fun detailContent() {
        composeTestRule.setContent {
            MockDetailContent(experience = mockExperience)
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
