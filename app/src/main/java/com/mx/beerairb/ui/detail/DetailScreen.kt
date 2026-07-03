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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mx.beerairb.data.model.BeerExperience

private const val MAX_HEADER_HEIGHT = 300f
private const val MIN_HEADER_HEIGHT = 120f
private const val COLLAPSE_RANGE = MAX_HEADER_HEIGHT - MIN_HEADER_HEIGHT

@Composable
fun DetailScreen(
    experienceId: String,
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = viewModel()
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

@Composable
fun DetailContent(
    experience: BeerExperience,
    onBackClick: () -> Unit = {},
    onFavoriteToggle: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var headerHeightPx by remember { mutableFloatStateOf(MAX_HEADER_HEIGHT) }
    val density = LocalDensity.current

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = -available.y
                val newHeight = (headerHeightPx + delta).coerceIn(
                    MIN_HEADER_HEIGHT,
                    MAX_HEADER_HEIGHT
                )
                val consumed = newHeight - headerHeightPx
                if (consumed != 0f) {
                    headerHeightPx = newHeight
                    return Offset(0f, -consumed)
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                val midPoint = (MAX_HEADER_HEIGHT + MIN_HEADER_HEIGHT) / 2f
                headerHeightPx = if (headerHeightPx > midPoint) MAX_HEADER_HEIGHT else MIN_HEADER_HEIGHT
                return Velocity.Zero
            }
        }
    }

    val headerHeightDp = with(density) { headerHeightPx.toDp() }
    val parallaxOffset = with(density) {
        ((headerHeightPx - MIN_HEADER_HEIGHT) * 0.3f).toDp()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeightDp)
        ) {
            HeroImageHeader(
                imageUrl = experience.imageUrl,
                onBackClick = onBackClick,
                isFavorite = experience.isFavorite,
                onFavoriteToggle = onFavoriteToggle,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .nestedScroll(nestedScrollConnection)
        ) {
            Spacer(modifier = Modifier.height(headerHeightDp - parallaxOffset))

            TitleRatingBlock(
                title = experience.title,
                rating = experience.rating,
                reviewCount = experience.reviewCount,
                description = experience.description
            )

            Spacer(modifier = Modifier.height(16.dp))

            AmenitiesRow(amenities = experience.amenities)

            DateSelector()

            BookingBar(price = experience.pricePerPerson)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
