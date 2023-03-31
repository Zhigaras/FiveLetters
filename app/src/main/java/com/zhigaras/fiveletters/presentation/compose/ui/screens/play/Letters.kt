package com.zhigaras.fiveletters.presentation.compose.ui.screens.play

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import com.zhigaras.fiveletters.presentation.Letter
import com.zhigaras.fiveletters.presentation.LetterType


@Composable
fun Letter(
    modifier: Modifier = Modifier,
    letter: Letter
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

@Composable
fun AnimatedLetter(
    modifier: Modifier = Modifier,
    startLetter: Letter
) {
    var state: Letter by remember { mutableStateOf(startLetter) }
    val rotation by animateFloatAsState(
        targetValue = state.angle,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing,
        )
    )
    Box(modifier = Modifier
        .clickable { state = Letter.Wrong(type = LetterType.Card, char = 'w') }
        .graphicsLayer {
            rotationY = rotation
        }) {
        if (rotation <= 90f)
            Letter(letter = startLetter)
        else
            Letter(
                modifier = modifier.graphicsLayer { rotationY = 180f },
                letter = state
            )
    }
}

@Composable
fun LetterField(
    modifier: Modifier = Modifier
) {
    val letterField =
        List(6) { List(5) { Letter.Default(type = LetterType.Card, char = ' ') } }
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        letterField.forEach { letterRow ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                modifier = modifier.fillMaxWidth()
            ) {
                letterRow.forEach {
                    AnimatedLetter(startLetter = it)
                }
            }
        }
    }
}

