package com.zhigaras.fiveletters.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.MenuScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.play.PlayScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.splash.SplashScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.welcome.WelcomeScreen
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.AuthViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.MenuViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.WelcomeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FiveLettersNavHost(
    authViewModel: AuthViewModel,
    welcomeViewModel: WelcomeViewModel,
    playViewModel: PlayViewModel,
    menuViewModel: MenuViewModel
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Destination.Splash.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(
            route = Destination.Splash.route,
            enterTransition = {
                fadeIn(animationSpec = tween(1000))
            }
        ) {
            SplashScreen(
                viewModel = authViewModel,
                navigateToWelcome = { navController.navigate(Destination.Welcome.route) },
                navigateToMenu = { navController.navigate(Destination.Menu.route) }
            )
        }
        composable(
            route = Destination.Welcome.route,
            enterTransition = {
                slideInVertically(animationSpec = tween(5)) //TODO check
            }
        ) {
            WelcomeScreen(
                viewModel = welcomeViewModel,
                navigateToMenu = { navController.navigate(Destination.Menu.route) }
            )
        }
        composable(route = Destination.Menu.route) {
            MenuScreen(
                viewModel = menuViewModel,
                navigateToPlay = {
                    playViewModel.startNewGame()
                    navController.navigate(Destination.Play.route)
                })
        }
        composable(route = Destination.Play.route) {
            PlayScreen(
                viewModel = playViewModel,
                toMenuClick = { navController.popBackStack() },
                onNewGameClick = { playViewModel.startNewGame() }
            )
        }
    }
}