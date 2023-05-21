package com.zhigaras.fiveletters.core.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithProgressBar(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    enabled: Boolean = true,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    AnyContentButton(
        modifier = modifier,
        enabled = enabled && !isLoading,
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = text,
                style = textStyle.copy(color = it),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center),
                textAlign = TextAlign.Center
            )
            if (isLoading) CircularProgressIndicator(
                modifier = Modifier
                    .size((textStyle.fontSize.value * 1.4).dp)
                    .align(Alignment.CenterEnd),
                color = it,
                strokeWidth = 2.dp
            )
        }
    }
}