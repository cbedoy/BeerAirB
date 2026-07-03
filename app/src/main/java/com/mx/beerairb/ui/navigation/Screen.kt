package com.mx.beerairb.ui.navigation

sealed class Screen(val route: String, val label: String = "") {
    data object Home : Screen("home", "Explorar")
    data object Favorites : Screen("favorites", "Favoritos")
    data object Messages : Screen("messages", "Mensajes")
    data object Profile : Screen("profile", "Perfil")
    data object Map : Screen("map")
    data object Detail : Screen("detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }
    data object ChatDetail : Screen("chat/{chatId}") {
        fun createRoute(chatId: String) = "chat/$chatId"
    }
}
