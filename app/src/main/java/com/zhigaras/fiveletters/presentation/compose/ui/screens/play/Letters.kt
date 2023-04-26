package com.zhigaras.fiveletters.presentation.compose.ui.screens.play

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.model.LetterFieldState
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.RowState
import com.zhigaras.fiveletters.presentation.compose.ui.theme.playScreenMaxWidth

@Composable
fun LetterField(
    modifier: Modifier = Modifier,
    letterFieldState: LetterFieldState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        modifier = modifier
            .padding(8.dp)
            .widthIn(max = playScreenMaxWidth)
    ) {
        letterFieldState.result.forEach { letterRow ->
            val rowState = remember { letterRow }
            LetterRow(newRow = letterRow, oldRow = rowState)
        }
    }
}

@Composable
fun LetterRow(
    modifier: Modifier = Modifier,
    newRow: RowState,
    oldRow: RowState
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        modifier = modifier.fillMaxWidth()
    ) {
        newRow.row.forEach {
            val state = remember(key1 = it.char) { it }
            val weightModifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
            if (newRow == oldRow) {
                it.Letter(modifier = weightModifier)
            } else {
                when {
                    newRow.isRowOpened -> FlippableLetter(
                        modifier = weightModifier,
                        newLetter = it,
                        oldLetter = state
                    )
                    
                    newRow is RowState.InvalidWord -> InvalidWordLetter(
                        modifier = weightModifier,
                        letter = it
                    )
                    
                    else -> it.Letter(
                        modifier = weightModifier
                    )
                }
            }
        }
    }
}

@Composable
fun FlippableLetter(
    modifier: Modifier = Modifier,
    newLetter: LetterState,
    oldLetter: LetterState
) {
    val flipRotation = remember { Animatable(0f) }
    val animationSpec = tween<Float>(2000, easing = FastOutSlowInEasing)
    LaunchedEffect(key1 = true) {
        flipRotation.animateTo(targetValue = newLetter.angle, animationSpec = animationSpec)
    }
    Box(modifier = modifier
        .graphicsLayer {
            rotationY = flipRotation.value
        }) {
        if (flipRotation.value <= 90f)
            oldLetter.Letter()
        else
            newLetter.Letter(modifier = Modifier.graphicsLayer { rotationY = 180f })
    }
}

@Composable
fun InvalidWordLetter(
    modifier: Modifier = Modifier,
    letter: LetterState
) {
    val rotation = remember { Animatable(0f) }
    LaunchedEffect(key1 = true) {
        rotation.animateTo(
            targetValue = 50f,
            animationSpec = tween(600, easing = EaseInOutBounce)
        )
        rotation.animateTo(
            targetValue = 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }
    Box(modifier = modifier
        .graphicsLayer {
            rotationY = rotation.value
        }) {
        letter.Letter()
    }
}

