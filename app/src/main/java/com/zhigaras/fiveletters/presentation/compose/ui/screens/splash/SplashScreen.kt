package com.zhigaras.fiveletters.presentation.compose.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.model.Username
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.AuthViewModel

@Composable
fun SplashScreen(
    viewModel: AuthViewModel,
    navigateToWelcome: () -> Unit,
    navigateToGreetings: (String) -> Unit,
    navigateToMenu: () -> Unit
) {
    val user by viewModel.userFlow.collectAsState()
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val circleRadius = 50
    val alpha = remember { Animatable(0f) }
    val position = remember { Animatable(-500f) }
    val scale = remember { Animatable(circleRadius.toFloat()) }
    LaunchedEffect(key1 = true) {
        viewModel.checkUsername()
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000, easing = Ease)
        )
        position.animateTo(
            targetValue = (screenHeight / 2 - circleRadius).toFloat(),
            animationSpec = tween(1200, easing = EaseInQuint)
        )
        position.animateTo(
            targetValue = 0f,
            animationSpec = tween(600, easing = EaseOutQuint) //EaseOutBack?
        )
        scale.animateTo(
            targetValue = maxOf(screenHeight, screenWidth).toFloat(),
            animationSpec = tween(1500)
        )
        when (user) {
            is Username.Loaded.NeedNameRequest -> navigateToWelcome()
            is Username.Loaded.Unspecified -> navigateToMenu()
            is Username.Loaded.Specified -> navigateToGreetings((user as Username.Loaded.Specified).name)
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.logo),
            modifier = Modifier
                .alpha(alpha.value)
                .fillMaxSize()
                .padding(16.dp)
        )
        Box(
            modifier = Modifier
                .absoluteOffset(y = position.value.dp)
                .requiredSize((scale.value * 2).dp)
                .clip(RoundedCornerShape(scale.value.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}