package com.mx.beerairb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mx.beerairb.ui.navigation.MainScaffold
import com.mx.beerairb.ui.theme.BeerAirBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeerAirBTheme {
                MainScaffold()
            }
        }
    }
}
