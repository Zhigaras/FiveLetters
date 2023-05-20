package com.zhigaras.fiveletters.core.presentation.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zhigaras.fiveletters.core.presentation.compose.theme.semiTransparentBlack

@Composable
fun CircleProgressBar(
    modifier: Modifier = Modifier,
    state: Boolean
) {
    AnimatedVisibility(
        visible = state,
        enter = fadeIn(tween(300)),
        exit = fadeOut(tween(300))
    ) {
        Box(
            modifier = modifier
                .background(semiTransparentBlack),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}