package com.mx.beerairb.ui.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.mx.beerairb.data.model.BeerExperience
import com.mx.beerairb.ui.theme.AmberPrimary
import com.mx.beerairb.ui.theme.ClayGray
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

private const val DEFAULT_LAT = 21.8460
private const val DEFAULT_LNG = -102.3044

@Composable
fun BreweryMapScreen(
    experiences: List<BeerExperience>,
    onBackClick: () -> Unit,
    onExperienceClick: (String) -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var hasLocationPermission by remember { mutableStateOf(false) }
    var mapViewRef by remember { mutableStateOf<MapView?>(null) }

    val filteredExperiences = remember(searchQuery, experiences) {
        if (searchQuery.isBlank()) experiences
        else experiences.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                it.location.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions.values.all { it }
        mapViewRef?.let { mv ->
            mv.controller.setZoom(14.0)
            mv.controller.setCenter(GeoPoint(DEFAULT_LAT, DEFAULT_LNG))
            mv.invalidate()
        }
    }

    LaunchedEffect(Unit) {
        Configuration.getInstance().apply {
            userAgentValue = context.packageName
        }
        hasLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 16.dp, top = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar"
                )
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            placeholder = { Text("Buscar cervecerías, taprooms...") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AmberPrimary,
                cursorColor = AmberPrimary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (!hasLocationPermission) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "\uD83D\uDCCD",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Activa tu ubicación",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Para mostrarte las cervecerías y taprooms cerca de ti",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ClayGray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            permissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary)
                    ) {
                        Text(
                            text = "Activar ubicación",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        } else {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                factory = { ctx ->
                    MapView(ctx).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(14.0)
                        controller.setCenter(GeoPoint(DEFAULT_LAT, DEFAULT_LNG))
                        setBuiltInZoomControls(false)
                        mapViewRef = this
                    }
                },
                update = { mapView ->
                    mapView.overlays.removeAll { it is Marker || it is MapEventsOverlay }
                    filteredExperiences.forEach { exp ->
                        val marker = Marker(mapView).apply {
                            position = GeoPoint(exp.latitude, exp.longitude)
                            title = exp.title
                            snippet = "${exp.category} · ${exp.location}"
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        }
                        marker.setOnMarkerClickListener { _, _ ->
                            onExperienceClick(exp.id)
                            true
                        }
                        mapView.overlays.add(marker)
                    }
                    mapView.invalidate()
                }
            )
        }
    }
}
