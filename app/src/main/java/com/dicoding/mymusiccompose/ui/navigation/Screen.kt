package com.dicoding.mymusiccompose.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object DetailMusic : Screen("home/{id}") {
        fun createRoute(id: Long) = "home/$id"
    }
}