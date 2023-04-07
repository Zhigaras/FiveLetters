package com.zhigaras.fiveletters.presentation.compose.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.presentation.compose.ui.theme.yellow

@Composable
fun SplashScreen() {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val circleRadius = 50
    val alpha = remember { Animatable(0f) }
    val position = remember { Animatable(-500f) }
    val scale = remember { Animatable(circleRadius.toFloat()) }
    LaunchedEffect(key1 = true) {
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
    }
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = stringResource(R.string.logo),
        modifier = Modifier.alpha(alpha.value)
    )
    Box(
        modifier = Modifier
            .absoluteOffset(y = position.value.dp)
            .requiredSize((scale.value * 2).dp)
            .clip(RoundedCornerShape(scale.value.dp))
            .background(yellow)
    )
}