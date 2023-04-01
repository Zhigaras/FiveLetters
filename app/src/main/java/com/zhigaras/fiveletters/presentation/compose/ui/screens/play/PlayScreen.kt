package com.zhigaras.fiveletters.presentation.compose.ui.screens.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel

@Composable
fun PlayScreen(
    viewModel: PlayViewModel
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            LetterField(gameState = viewModel.gameStateFlow)
        }
        Keyboard(
            onKeyClick = { viewModel.inputLetter(it) },
            onConfirmClick = { viewModel.confirmWord() },
            onBackspaceClick = { viewModel.removeLetter() }
        )
    }
}