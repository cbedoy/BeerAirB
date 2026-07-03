package com.mx.beerairb.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mx.beerairb.ui.theme.AmberPrimary
import com.mx.beerairb.ui.theme.ClayGray

data class BeerCategory(
    val name: String,
    val icon: ImageVector
)

private val categories = listOf(
    "Microbreweries",
    "Beer Glamping",
    "Taprooms",
    "Hop Farms",
    "Brewpubs",
    "Beer Tours"
)

@Composable
fun CategorySelector(
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories, key = { it }) { category ->
            val isSelected = category == selectedCategory
            Surface(
                modifier = Modifier
                    .clickable { onCategorySelected(category) },
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) AmberPrimary else MaterialTheme.colorScheme.surface,
                tonalElevation = if (isSelected) 0.dp else 2.dp
            ) {
                Text(
                    text = category,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else ClayGray
                )
            }
        }
    }
}
