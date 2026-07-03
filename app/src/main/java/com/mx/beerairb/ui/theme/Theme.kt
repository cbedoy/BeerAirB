package com.mx.beerairb.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AmberPrimary,
    onPrimary = Color.White,
    primaryContainer = GoldPale,
    onPrimaryContainer = DarkToasted,
    secondary = AmberLight,
    onSecondary = Color.White,
    secondaryContainer = CreamSoft,
    onSecondaryContainer = DarkToasted,
    tertiary = ClayGray,
    onTertiary = Color.White,
    tertiaryContainer = BlueGray,
    onTertiaryContainer = DarkToasted,
    background = CreamBg,
    onBackground = DarkToasted,
    surface = Color.White,
    onSurface = DarkToasted,
    surfaceVariant = CreamBg,
    onSurfaceVariant = ClayGray,
    outline = ClayGray.copy(alpha = 0.3f),
    outlineVariant = ClayGray.copy(alpha = 0.15f),
)

@Composable
fun BeerAirBTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
