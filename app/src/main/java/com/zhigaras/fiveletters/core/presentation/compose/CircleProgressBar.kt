package com.zhigaras.fiveletters.core.presentation.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.zhigaras.fiveletters.core.presentation.compose.theme.black

@Composable
fun CircleProgressBar(
    modifier: Modifier = Modifier,
    state: Boolean
) {
    AnimatedVisibility(
        visible = state,
        modifier = modifier
            .zIndex(1f)
            .clickable(enabled = false, onClick = {}),
        enter = fadeIn(tween(300)),
        exit = fadeOut(tween(300))
    ) {
        Box(
            modifier = Modifier
                .background(black.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}