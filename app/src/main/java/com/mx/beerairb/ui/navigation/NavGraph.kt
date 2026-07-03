package com.mx.beerairb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mx.beerairb.ui.detail.DetailScreen
import com.mx.beerairb.ui.favorites.FavoritesScreen
import com.mx.beerairb.ui.home.HomeScreen
import com.mx.beerairb.ui.messages.MessagesScreen
import com.mx.beerairb.ui.profile.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onExperienceClick = { id ->
                    navController.navigate(Screen.Detail.createRoute(id))
                }
            )
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen()
        }
        composable(Screen.Messages.route) {
            MessagesScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            DetailScreen(
                experienceId = id,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
