package com.mx.beerairb.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mx.beerairb.BeerAirBApplication
import com.mx.beerairb.ui.chat.ChatDetailScreen
import com.mx.beerairb.ui.detail.DetailScreen
import com.mx.beerairb.ui.detail.DetailViewModelFactory
import com.mx.beerairb.ui.favorites.FavoritesScreen
import com.mx.beerairb.ui.home.HomeScreen
import com.mx.beerairb.ui.home.HomeViewModelFactory
import com.mx.beerairb.ui.messages.MessagesScreen
import com.mx.beerairb.ui.profile.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController) {
    val app = LocalContext.current.applicationContext as BeerAirBApplication
    val homeViewModel: com.mx.beerairb.ui.home.HomeViewModel = viewModel(factory = HomeViewModelFactory(app))

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onExperienceClick = { id ->
                    navController.navigate(Screen.Detail.createRoute(id))
                },
                viewModel = homeViewModel
            )
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen()
        }
        composable(Screen.Messages.route) {
            MessagesScreen(
                onChatClick = { chatId ->
                    navController.navigate(Screen.ChatDetail.createRoute(chatId))
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it / 3 },
                    animationSpec = tween(200)
                ) + fadeOut(animationSpec = tween(200))
            }
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            val viewModel: com.mx.beerairb.ui.detail.DetailViewModel = viewModel(
                factory = DetailViewModelFactory(app, id)
            )
            DetailScreen(
                experienceId = id,
                onBackClick = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.ChatDetail.route,
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it / 3 },
                    animationSpec = tween(200)
                ) + fadeOut(animationSpec = tween(200))
            }
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: return@composable
            ChatDetailScreen(
                chatId = chatId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
