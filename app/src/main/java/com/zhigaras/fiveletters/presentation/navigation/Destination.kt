package com.zhigaras.fiveletters.presentation.navigation

abstract class Destination {
    
    abstract val route: String
    
    object Menu : Destination() {
        override val route = "menuScreen"
    }
    
    object Play : Destination() {
        override val route = "playScreen"
    }
    
}