package com.zhigaras.fiveletters.presentation.compose.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zhigaras.fiveletters.R

@Composable
fun SplashScreen() {
    
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.splash_animation))
    val logoAnimationState =
        animateLottieCompositionAsState(composition = composition)
    LottieAnimation(
        composition = composition,
        progress = { logoAnimationState.progress },
        clipToCompositionBounds = true
    )
    
}