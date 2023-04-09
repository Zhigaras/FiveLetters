package com.zhigaras.fiveletters.presentation.navigation

abstract class Destination {
    
    abstract val route: String
    
    object Splash : Destination() {
        override val route = "splashScreen"
    }
    
    object Welcome : Destination() {
        override val route = "welcomeScreen"
    }
    
    object Menu : Destination() {
        override val route = "menuScreen"
    }
    
    object Play : Destination() {
        override val route = "playScreen"
    }
    
}