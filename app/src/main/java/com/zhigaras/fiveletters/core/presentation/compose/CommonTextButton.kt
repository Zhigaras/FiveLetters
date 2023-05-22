package com.zhigaras.fiveletters.core.presentation.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CommonTextButton(
    modifier: Modifier = Modifier,
    text: String,
    isAlternative: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    AnyContentButton(
        modifier = modifier,
        enabled = enabled,
        isAlternative = isAlternative,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = textStyle.copy(color = it),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}