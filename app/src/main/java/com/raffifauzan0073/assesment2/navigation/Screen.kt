package com.raffifauzan0073.assesment2.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")

}