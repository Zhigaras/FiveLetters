package com.zhigaras.fiveletters.presentation.navigation

abstract class Destination {
    
    abstract val route: String
    
    object SplashScreen : Destination() {
        override val route = "splashScreen"
    }
    
    object WelcomeScreen : Destination() {
        override val route = "welcomeScreen"
    }
    
    object MenuScreen : Destination() {
        override val route = "menuScreen"
    }
    
    object PlayScreen : Destination() {
        override val route = "playScreen"
    }
    
}