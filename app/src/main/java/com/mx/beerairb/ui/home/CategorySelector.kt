package com.mx.beerairb.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Cabin
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.mx.beerairb.ui.theme.AmberPrimary
import com.mx.beerairb.ui.theme.ClayGray

data class BeerCategory(
    val name: String,
    val icon: ImageVector
)

private val categories = listOf(
    BeerCategory("Microbreweries", Icons.Default.Business),
    BeerCategory("Beer Glamping", Icons.Default.Cabin),
    BeerCategory("Taprooms", Icons.Default.LocalBar),
    BeerCategory("Hop Farms", Icons.Default.Nature)
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
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(categories, key = { it.name }) { category ->
            val isSelected = category.name == selectedCategory
            Surface(
                modifier = Modifier
                    .height(60.dp)
                    .width(130.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onCategorySelected(category.name) },
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) AmberPrimary else MaterialTheme.colorScheme.surface,
                tonalElevation = if (isSelected) 0.dp else 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = category.name,
                        modifier = Modifier.size(22.dp),
                        tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else ClayGray
                    )
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
