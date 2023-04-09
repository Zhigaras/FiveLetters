package com.zhigaras.fiveletters.presentation.compose.ui.screens.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel

@Composable
fun PlayScreen(
    viewModel: PlayViewModel
) {
    val gameState by viewModel.gameStateFlow.collectAsStateWithLifecycle()
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            LetterField(gameState = gameState)
        }
        Keyboard(
            gameState = gameState,
            onKeyClick = { viewModel.inputLetter(it) },
            onConfirmClick = { viewModel.confirmWord() },
            onBackspaceClick = { viewModel.removeLetter() }
        )
    }
}