package com.zhigaras.fiveletters.core.navigation

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInExpo
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
import com.zhigaras.fiveletters.feature.auth.presentation.SharedAuthScreen
import com.zhigaras.fiveletters.feature.auth.presentation.resetpassword.ResetPasswordViewModel
import com.zhigaras.fiveletters.feature.auth.presentation.signin.SignInViewModel
import com.zhigaras.fiveletters.feature.auth.presentation.signup.SignUpViewModel
import com.zhigaras.fiveletters.feature.menu.presentation.MenuScreen
import com.zhigaras.fiveletters.feature.menu.presentation.MenuViewModel
import com.zhigaras.fiveletters.feature.menu.presentation.rules.RulesViewModel
import com.zhigaras.fiveletters.feature.play.presentation.PlayScreen
import com.zhigaras.fiveletters.feature.play.presentation.PlayViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FiveLettersNavHost(
    modifier: Modifier = Modifier,
    needToAuth: Boolean,
    playViewModel: PlayViewModel,
    menuViewModel: MenuViewModel,
    signInViewModel: SignInViewModel,
    signUpViewModel: SignUpViewModel,
    resetPasswordViewModel: ResetPasswordViewModel,
    rulesViewModel: RulesViewModel,
    showSnackBar: suspend (String) -> Unit,
    onFinish: () -> Unit
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val navController = rememberAnimatedNavController()
    val gameState by playViewModel.getState().collectAsStateWithLifecycle()
    val animationDuration = 800
    
    AnimatedNavHost(
        navController = navController,
//        startDestination = if (needToAuth) Destination.Auth.route else Destination.Menu.route,
        startDestination = Destination.Auth.route,
        modifier = modifier.fillMaxSize(),
    ) {
        composable(
            route = Destination.Auth.route,
            enterTransition = {
                scaleIn(
                    animationSpec = tween(
                        durationMillis = animationDuration,
                        easing = EaseInExpo
                    )
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(animationDuration))
            }
        ) {
            SharedAuthScreen(
                signInViewModel = signInViewModel,
                signUpViewModel = signUpViewModel,
                resetPasswordViewModel = resetPasswordViewModel,
                navigateToMenu = { navController.navigateWithClearBackStack(Destination.Menu.route) },
                onFinish = onFinish,
                showSnackBar = showSnackBar
            )
        }
        composable(
            route = Destination.Menu.route,
            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
        ) {
            MenuScreen(
                viewModel = menuViewModel,
                isExpanded = isLandscape,
                rulesViewModel = rulesViewModel,
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
                    animationSpec = tween(animationDuration),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(animationDuration),
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