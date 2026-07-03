package com.mx.beerairb.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mx.beerairb.ui.theme.AmberPrimary
import com.mx.beerairb.ui.theme.ClayGray
import com.mx.beerairb.ui.theme.CreamBg

private data class BottomNavItem(
    val screen: Screen?,
    val icon: ImageVector,
    val label: String,
    val isFab: Boolean = false
)

private val bottomNavItems = listOf(
    BottomNavItem(Screen.Home, Icons.Default.Home, "Explorar"),
    BottomNavItem(Screen.Favorites, Icons.Default.Favorite, "Favoritos"),
    BottomNavItem(null, Icons.Default.LocalBar, "", isFab = true),
    BottomNavItem(Screen.Messages, Icons.Default.ChatBubble, "Mensajes"),
    BottomNavItem(Screen.Profile, Icons.Default.Person, "Perfil")
)

@Composable
fun MainScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Favorites.route,
        Screen.Messages.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 24.dp, bottomEnd = 24.dp),
                    color = CreamBg,
                    shadowElevation = 8.dp,
                    tonalElevation = 4.dp
                ) {
                    NavigationBar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = CreamBg,
                        tonalElevation = 0.dp
                    ) {
                        bottomNavItems.forEach { item ->
                            if (item.isFab) {
                                NavigationBarItem(
                                    icon = {
                                        Box(
                                            modifier = Modifier.size(48.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = "Cerveza",
                                                modifier = Modifier.size(28.dp),
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    },
                                    label = null,
                                    selected = false,
                                    onClick = { },
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = AmberPrimary
                                    )
                                )
                            } else {
                                val screen = requireNotNull(item.screen)
                                val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.label
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = item.label,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    },
                                    selected = selected,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = AmberPrimary,
                                        selectedTextColor = AmberPrimary,
                                        unselectedIconColor = ClayGray,
                                        unselectedTextColor = ClayGray,
                                        indicatorColor = CreamBg
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavGraph(navController = navController)
        }
    }
}
