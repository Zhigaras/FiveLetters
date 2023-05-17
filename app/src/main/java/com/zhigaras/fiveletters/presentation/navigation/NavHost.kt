package com.zhigaras.fiveletters.presentation.navigation

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.AuthScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.MenuScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.play.PlayScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.splash.SplashScreen
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.AuthViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.MenuViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FiveLettersNavHost(
    needToShowSplash: Boolean,
    playViewModel: PlayViewModel,
    menuViewModel: MenuViewModel,
    authViewModel: AuthViewModel,
    onFinish: () -> Unit
) {
    val isExpanded = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val navController = rememberAnimatedNavController()
    val gameState by playViewModel.getState().collectAsStateWithLifecycle()
    AnimatedNavHost(
        navController = navController,
//        startDestination = if (needToShowSplash) Destination.Splash.route else Destination.Menu.route,
        startDestination = Destination.Auth.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Destination.Auth.route) {
            AuthScreen(
                viewModel = authViewModel,
                onFinish = onFinish
            )
        }
        
        composable(
            route = Destination.Splash.route,
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {
            SplashScreen(
                navigateToMenu = { navController.navigateWithClearBackStack(Destination.Menu.route) }
            )
        }
        composable(
            route = Destination.Menu.route,
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            popEnterTransition = {
                slideIntoContainer(
                    animationSpec = tween(700),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(700),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            }) {
            MenuScreen(
                viewModel = menuViewModel,
                isExpanded = isExpanded,
                progressState = gameState.progressState,
                newGame = {
                    playViewModel.startNewGame()
                    navController.navigate(Destination.Play.route)
                },
                continueGame = { navController.navigate(Destination.Play.route) },
                onFinish = onFinish
            )
        }
        composable(
            route = Destination.Play.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(700),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(700),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            PlayScreen(
                viewModel = playViewModel,
                gameState = gameState,
                toMenuClick = { navController.popBackStack() },
                onNewGameClick = { playViewModel.startNewGame() }
            )
        }
    }
}

fun NavHostController.navigateWithClearBackStack(route: String) {
    navigate(route) {
        popUpTo(0)
    }
}