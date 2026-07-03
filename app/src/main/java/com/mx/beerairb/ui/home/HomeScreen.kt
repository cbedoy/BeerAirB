package com.mx.beerairb.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onExperienceClick: (String) -> Unit,
    viewModel: HomeViewModel
) {
    val experiences by viewModel.filteredExperiences.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChanged = viewModel::onSearchQueryChanged
        )

        CategorySelector(
            selectedCategory = selectedCategory,
            onCategorySelected = viewModel::onCategorySelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nearby Taprooms / Experiences",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
        ) {
            items(experiences, key = { it.id }) { experience ->
                NearbyTaproomCard(
                    experience = experience,
                    onClick = { onExperienceClick(experience.id) },
                    onFavoriteToggle = { viewModel.onFavoriteToggle(experience.id) }
                )
            }
        }

        ExploreBanner(onStartClick = { })
    }
}
