package com.mx.beerairb.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.mx.beerairb.data.model.BeerExperience

@Composable
fun DetailScreen(
    experienceId: String,
    onBackClick: () -> Unit,
    viewModel: DetailViewModel
) {
    val experience by viewModel.experience.collectAsState()

    if (experience == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        DetailContent(
            experience = experience!!,
            onBackClick = onBackClick,
            onFavoriteToggle = { viewModel.toggleFavorite(experience!!.id) }
        )
    }
}

private const val MAX_HEADER_DP = 300f
private const val MIN_HEADER_DP = 120f

@Composable
fun DetailContent(
    experience: BeerExperience,
    onBackClick: () -> Unit = {},
    onFavoriteToggle: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var headerHeightDp by remember { mutableFloatStateOf(MAX_HEADER_DP) }
    val density = androidx.compose.ui.platform.LocalDensity.current

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = -available.y
                val deltaDp = with(density) { delta.toDp() }.value
                val newHeight = (headerHeightDp + deltaDp).coerceIn(MIN_HEADER_DP, MAX_HEADER_DP)
                val consumedDp = newHeight - headerHeightDp
                if (consumedDp != 0f) {
                    headerHeightDp = newHeight
                    return Offset(0f, -available.y)
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                val midPoint = (MAX_HEADER_DP + MIN_HEADER_DP) / 2f
                headerHeightDp = if (headerHeightDp > midPoint) MAX_HEADER_DP else MIN_HEADER_DP
                return Velocity.Zero
            }
        }
    }

    val parallaxOffset = ((headerHeightDp - MIN_HEADER_DP) * 0.3f).dp

    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeightDp.dp)
        ) {
            HeroImageHeader(
                imageUrl = experience.imageUrl,
                onBackClick = onBackClick,
                isFavorite = experience.isFavorite,
                onFavoriteToggle = onFavoriteToggle,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .nestedScroll(nestedScrollConnection)
        ) {
            Spacer(modifier = Modifier.height(headerHeightDp.dp - parallaxOffset))

            TitleRatingBlock(
                title = experience.title,
                rating = experience.rating,
                reviewCount = experience.reviewCount,
                description = experience.description
            )

            Spacer(modifier = Modifier.height(24.dp))

            AmenitiesRow(amenities = experience.amenities)

            Spacer(modifier = Modifier.height(24.dp))

            DateSelector()

            Spacer(modifier = Modifier.height(24.dp))

            BookingBar(price = experience.pricePerPerson)

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
