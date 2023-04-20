package com.zhigaras.fiveletters.presentation.compose.ui.screens.play

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel
import kotlinx.coroutines.delay

@Composable
fun PlayScreen(
    viewModel: PlayViewModel,
    toMenuClick: () -> Unit,
    onNewGameClick: () -> Unit
) {
    val gameState by viewModel.gameStateFlow.collectAsStateWithLifecycle()
    val keyboard by viewModel.keyboardFlow.collectAsStateWithLifecycle()
    val showDialog = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = gameState.inProgress) {
        delay(700)
        showDialog.value = !gameState.inProgress
    }
    if (showDialog.value) {
        EndGameDialog(
            onDismiss = { showDialog.value = false },
            toMenuClick = { showDialog.value = false; toMenuClick() },
            onNewGameClick = onNewGameClick
        ) {
            when (gameState) {
                is GameState.Ended.Failed -> FailDialogContent()
                is GameState.Ended.Win -> WinDialogContent()
            }
        }
    }
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
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.BottomCenter
        ) {
            AnimatedContent(
                targetState = gameState.inProgress,
                transitionSpec = {
                    fadeIn(animationSpec = tween(1000)) togetherWith fadeOut(tween(1000))
                }
            ) { inProgress ->
                if (inProgress)
                    Keyboard(
                        gameState = gameState,
                        keyboard = keyboard,
                        onKeyClick = { viewModel.inputLetter(it) },
                        onConfirmClick = { viewModel.confirmWord() },
                        onBackspaceClick = { viewModel.removeLetter() }
                    )
                else KeyboardStub(onBackClick = toMenuClick, onNewGameClick = onNewGameClick)
            }
        }
    }
}