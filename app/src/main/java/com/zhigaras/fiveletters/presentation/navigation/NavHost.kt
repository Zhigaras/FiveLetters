package com.zhigaras.fiveletters.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.MenuScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.play.PlayScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.splash.SplashScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.welcome.WelcomeScreen
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.WelcomeViewModel

@Composable
fun FiveLettersNavHost(
    welcomeViewModel: WelcomeViewModel,
    playViewModel: PlayViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.SplashScreen.route) {
        composable(Destination.SplashScreen.route) {
            SplashScreen()
        }
        composable(Destination.WelcomeScreen.route) {
            WelcomeScreen(viewModel = welcomeViewModel)
        }
        composable(Destination.MenuScreen.route) {
            MenuScreen()
        }
        composable(Destination.PlayScreen.route) {
            PlayScreen(viewModel = playViewModel)
        }
    }
}