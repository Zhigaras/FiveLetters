package com.zhigaras.fiveletters.presentation.compose.ui.screens.play

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.RowState

@Composable
fun LetterField(
    modifier: Modifier = Modifier,
    gameState: GameState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        gameState.result.forEach { letterRow ->
            LetterRow(letterRow = letterRow)
        }
    }
}

@Composable
fun LetterRow(
    modifier: Modifier = Modifier,
    letterRow: RowState
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        modifier = modifier.fillMaxWidth()
    ) {
        letterRow.row.forEach {
            val state = remember(key1 = it.char) { it }
            if (letterRow is RowState.Right || letterRow is RowState.Wrong)
                FlippableLetter(newLetter = it, state = state)
            else {
                Letter(letter = it)
            }
        }
    }
}

@Composable
fun FlippableLetter(
    modifier: Modifier = Modifier,
    newLetter: LetterState,
    state: LetterState
) {
    val flipRotation = remember { Animatable(0f) }
    val animationSpec = tween<Float>(2000, easing = FastOutSlowInEasing)
    LaunchedEffect(key1 = true) {
        flipRotation.animateTo(targetValue = newLetter.angle, animationSpec = animationSpec)
    }
    Box(modifier = Modifier
        .graphicsLayer {
            rotationY = flipRotation.value
        }) {
        if (flipRotation.value <= 90f)
            Letter(letter = state)
        else
            Letter(modifier = modifier.graphicsLayer { rotationY = 180f }, letter = newLetter)
    }
}

@Composable
fun Letter(
    modifier: Modifier = Modifier,
    letter: LetterState
) {
    OutlinedCard(
        border = BorderStroke(width = letter.type.borderWidth, color = letter.borderColor),
        shape = RoundedCornerShape(letter.type.cornersRadius),
        colors = CardDefaults.cardColors(
            containerColor = letter.cardColor,
            contentColor = letter.charColor
        ),
        modifier = modifier.width(letter.type.width)
    ) {
        Text(
            text = letter.char.toString().uppercase(),
            fontSize = letter.type.charSize,
            modifier = Modifier
                .padding(letter.type.charPadding)
                .align(Alignment.CenterHorizontally)
        )
    }
}

