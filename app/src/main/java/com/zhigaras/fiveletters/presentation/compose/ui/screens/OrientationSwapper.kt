package com.zhigaras.fiveletters.presentation.compose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OrientationSwapper(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    content: List<@Composable (Modifier) -> Unit>
) {
    if (isExpanded) {
        Row(modifier = modifier) {
            content.forEach { it.invoke(Modifier.fillMaxSize().weight(1f)) }
        }
    } else {
        Column(modifier = modifier) {
            content.forEach { it.invoke(Modifier.fillMaxSize().weight(1f)) }
        }
    }
    
}