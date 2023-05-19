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
import com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.signin.SignInScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.signin.SignInViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.MenuScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.MenuViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.screens.play.PlayScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.play.PlayViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.signup.SignUpScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.signup.SignUpViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.screens.splash.SplashScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FiveLettersNavHost(
    modifier: Modifier = Modifier,
    needToShowSplash: Boolean,
    needToAuth: Boolean,
    playViewModel: PlayViewModel,
    menuViewModel: MenuViewModel,
    signInViewModel: SignInViewModel,
    signUpViewModel: SignUpViewModel,
    showSnackBar: suspend (String) -> Unit,
    onFinish: () -> Unit
) {
    val isExpanded = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val navController = rememberAnimatedNavController()
    val gameState by playViewModel.getState().collectAsStateWithLifecycle()
    AnimatedNavHost(
        navController = navController,
        startDestination =
        if (needToShowSplash) Destination.Splash.route
        else if (needToAuth) Destination.SignIn.route
        else Destination.Menu.route,
//        startDestination = Destination.SignIn.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(
            route = Destination.Splash.route,
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {
            SplashScreen(
                navigateToNext = {
                    navController.navigateWithClearBackStack(
                        if (needToAuth) Destination.SignIn.route else Destination.SignIn.route
                    )
                }
            )
        }
        composable(route = Destination.SignIn.route) {
            SignInScreen(
                viewModel = signInViewModel,
                toSignUpScreen = { navController.navigate(Destination.SignUp.route) }
            )
        }
        composable(route = Destination.SignUp.route) {
            SignUpScreen(
                viewModel = signUpViewModel,
                showSnackBar = showSnackBar,
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