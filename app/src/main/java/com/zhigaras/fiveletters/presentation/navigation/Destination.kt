package com.zhigaras.fiveletters.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

abstract class Destination {
    
    abstract val route: String
    
    object Splash : Destination() {
        override val route = "splashScreen"
    }
    
    object Welcome : Destination() {
        override val route = "welcomeScreen"
    }
    
    object Greetings : Destination() {
        override val route = "greetingsScreen"
        const val usernameArgs = "username"
        val routeWithArgs = "$route/{$usernameArgs}"
        val arguments = listOf(
            navArgument(usernameArgs) { type = NavType.StringType }
        )
    }
    
    object Menu : Destination() {
        override val route = "menuScreen"
    }
    
    object Play : Destination() {
        override val route = "playScreen"
    }
    
}