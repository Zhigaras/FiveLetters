package com.zhigaras.fiveletters.core.navigation

abstract class Destination {
    
    abstract val route: String
    
    object Auth : Destination() {
        override val route = "auth"
    }
    
    object Menu : Destination() {
        override val route = "menuScreen"
    }
    
    object Play : Destination() {
        override val route = "playScreen"
    }
}