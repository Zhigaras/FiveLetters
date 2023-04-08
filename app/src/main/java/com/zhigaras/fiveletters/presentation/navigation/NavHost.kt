package com.zhigaras.fiveletters.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zhigaras.fiveletters.presentation.compose.ui.screens.greetings.GreetingsScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.MenuScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.play.PlayScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.splash.SplashScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.welcome.WelcomeScreen
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.AuthViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.WelcomeViewModel

@Composable
fun FiveLettersNavHost(
    authViewModel: AuthViewModel,
    welcomeViewModel: WelcomeViewModel,
    playViewModel: PlayViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.Splash.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Destination.Splash.route) {
            SplashScreen(
                viewModel = authViewModel,
                navigateToWelcome = { navController.navigate(Destination.Welcome.route) },
                navigateToGreetings = { username ->
                    navController.navigate("${Destination.Greetings.route}/$username")
                },
                navigateToMenu = { navController.navigate(Destination.Menu.route) }
            )
        }
        composable(route = Destination.Welcome.route) {
            WelcomeScreen(
                viewModel = welcomeViewModel,
                navigateToMenu = { navController.navigate(Destination.Menu.route) }
            )
        }
        composable(
            route = Destination.Greetings.routeWithArgs,
            arguments = Destination.Greetings.arguments
        ) { navBackStackEntry ->
            val username =
                navBackStackEntry.arguments?.getString(Destination.Greetings.usernameArgs) ?: ""
            GreetingsScreen(
                name = username,
                onTimeout = { navController.navigate(Destination.Menu.route) }
            )
        }
        composable(route = Destination.Menu.route) {
            MenuScreen()
        }
        composable(route = Destination.Play.route) {
            PlayScreen(viewModel = playViewModel)
        }
    }
}