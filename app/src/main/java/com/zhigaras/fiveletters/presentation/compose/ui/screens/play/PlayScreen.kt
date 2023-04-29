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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.model.LetterFieldState
import com.zhigaras.fiveletters.model.ProgressState
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel
import kotlinx.coroutines.delay

@Composable
fun PlayScreen(
    viewModel: PlayViewModel,
    gameState: GameState,
    toMenuClick: () -> Unit,
    onNewGameClick: () -> Unit
) {
    var dialogScaleValue by rememberSaveable { mutableStateOf(0f) }
    var showEndGameDialog by rememberSaveable { mutableStateOf(false) }
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_mobile))
    var showCongratulationAnimation by rememberSaveable { mutableStateOf(false) }
    if (dialogScaleValue != 1f)
        LaunchedEffect(key1 = gameState.progressState) {
            delay(700)
            showCongratulationAnimation = gameState.letterFieldState is LetterFieldState.Win
            delay(400)
            showEndGameDialog = gameState.progressState == ProgressState.ENDED
        }
    
    if (showEndGameDialog) {
        EndGameDialog(
            origin = gameState.origin.word,
            scale = dialogScaleValue,
            onDismiss = { showEndGameDialog = false },
            toMenuClick = { showEndGameDialog = false; toMenuClick() },
            onNewGameClick = {
                onNewGameClick()
                dialogScaleValue = 0f
                showCongratulationAnimation = false
            }
        ) {
            when (gameState.letterFieldState) {
                is LetterFieldState.Failed -> FailDialogContent()
                is LetterFieldState.Win -> WinDialogContent()
            }
        }
        dialogScaleValue = 1f
    }
    
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        AdBanner(modifier = Modifier.fillMaxWidth(), isTest = true)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            LetterField(letterFieldState = gameState.letterFieldState)
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AnimatedContent(
                targetState = gameState.progressState,
                transitionSpec = {
                    fadeIn(animationSpec = tween(1000)) togetherWith fadeOut(tween(1000))
                }
            ) { progressState ->
                if (progressState != ProgressState.ENDED)
                    Keyboard(
                        gameState = gameState,
                        onKeyClick = { viewModel.inputLetter(it) },
                        onConfirmClick = { viewModel.confirmWord() },
                        onBackspaceClick = { viewModel.removeLetter() }
                    )
                else KeyboardStub(
                    onBackClick = toMenuClick,
                    onNewGameClick = {
                        onNewGameClick()
                        dialogScaleValue = 0f
                        showEndGameDialog = false
                        showCongratulationAnimation = false
                    }
                )
            }
        }
    }
    if (showCongratulationAnimation)
        LottieAnimation(composition = lottieComposition, iterations = 2)
}