package com.mx.beerairb.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mx.beerairb.data.model.BeerAmenity
import com.mx.beerairb.ui.theme.BlueGray
import com.mx.beerairb.ui.theme.CreamSoft
import com.mx.beerairb.ui.theme.GoldPale
import com.mx.beerairb.ui.theme.GreenLight

private val badgeColors = listOf(CreamSoft, GreenLight, GoldPale, BlueGray)

private val iconChars = listOf("\uD83E\uDD5A", "\uD83E\uDDEA", "\uD83D\uDECF\uFE0F", "\uD83D\uDCF6")

@Composable
fun AmenitiesRow(
    amenities: List<BeerAmenity>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Including",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            amenities.take(4).forEachIndexed { index, amenity ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = badgeColors.getOrElse(amenity.badgeColorIndex) { BlueGray }
                    ) {
                        Text(
                            text = iconChars.getOrElse(index) { "\uD83C\uDF7A" },
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = amenity.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                }
            }
        }
    }
}
