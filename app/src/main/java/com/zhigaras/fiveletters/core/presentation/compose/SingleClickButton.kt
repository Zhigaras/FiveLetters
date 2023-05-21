package com.zhigaras.fiveletters.core.presentation.compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle


@Composable
fun SingleClickButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    clickDisabledPeriod: Long = 1000L,
    onClick: () -> Unit
) {
    var lastClickTime by remember { mutableStateOf(0L) }
    
    CommonTextButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        onClick = {
            if (lastClickTime + clickDisabledPeriod < System.currentTimeMillis()) {
                onClick()
                lastClickTime = System.currentTimeMillis()
            }
        }
    )
}