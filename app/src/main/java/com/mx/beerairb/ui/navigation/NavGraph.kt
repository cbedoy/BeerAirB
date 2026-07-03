package com.mx.beerairb.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mx.beerairb.ui.chat.ChatDetailScreen
import com.mx.beerairb.ui.detail.DetailScreen
import com.mx.beerairb.ui.detail.DetailViewModel
import com.mx.beerairb.ui.detail.DetailViewModelFactory
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
            val viewModel: DetailViewModel = viewModel(
                factory = DetailViewModelFactory(id)
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
