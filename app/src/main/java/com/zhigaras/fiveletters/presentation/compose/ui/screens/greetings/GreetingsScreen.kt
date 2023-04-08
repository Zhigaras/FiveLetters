package com.zhigaras.fiveletters.presentation.compose.ui.screens.greetings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.zhigaras.fiveletters.R
import kotlinx.coroutines.delay

@Composable
fun GreetingsScreen(
    name: String,
    onTimeout: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        delay(1000L)
        onTimeout()
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.hi_name, name),
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}