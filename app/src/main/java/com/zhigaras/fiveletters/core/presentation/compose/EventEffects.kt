package com.zhigaras.fiveletters.core.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.zhigaras.fiveletters.core.presentation.UiText
import com.zhigaras.fiveletters.feature.auth.domain.model.ErrorEvent

@Composable
fun ErrorEffect(
    event: ErrorEvent,
    onConsumed: () -> Unit,
    action: suspend (UiText) -> Unit
) {
    LaunchedEffect(key1 = event) {
        if (event.isVisible) {
            action(event.message)
            onConsumed()
        }
    }
}