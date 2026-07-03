package com.mx.beerairb.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mx.beerairb.data.model.BeerExperience

@Composable
fun DetailScreen(
    experienceId: String,
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = viewModel()
) {
    val experience by viewModel.experience.collectAsState()

    if (experience == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeroImageHeader(
            imageUrl = experience.imageUrl,
            onBackClick = onBackClick,
            isFavorite = experience.isFavorite,
            onFavoriteToggle = onFavoriteToggle
        )

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
