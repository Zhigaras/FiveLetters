package com.zhigaras.fiveletters.presentation.compose

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.presentation.Letter
import com.zhigaras.fiveletters.presentation.LetterState
import com.zhigaras.fiveletters.presentation.LetterType


@Composable
fun Letter(
    modifier: Modifier = Modifier,
    letter: Letter
) {
    OutlinedCard(
        border = BorderStroke(width = 2.dp, letter.state.borderColor),
        colors = CardDefaults.cardColors(containerColor = letter.state.cardColor),
        modifier = modifier
            .width(letter.type.width)
            .padding(horizontal = 2.dp)
    ) {
        Text(
            text = letter.state.char.toString().uppercase(),
            fontSize = letter.type.charSize,
            color = letter.state.charColor,
            modifier = Modifier
                .padding(8.dp)
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
        targetValue = state.state.angle,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing,
        )
    )
    Box(modifier = modifier
        .clickable { state = Letter(type = LetterType.Card(), state = LetterState.Exact('w')) }
        .graphicsLayer {
            rotationY = rotation
        }) {
        if (rotation <= 90f)
            Letter(letter = startLetter)
        else
            Letter(
                modifier = modifier
                    .graphicsLayer {
                        rotationY = 180f
                    },
                letter = state
            )
    }
}

@Composable
fun LetterField(
    modifier: Modifier = Modifier
) {
    val letterField =
        List(6) { List(5) { Letter(type = LetterType.Card(), state = LetterState.Default()) } }
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        letterField.forEach { letterRow ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxWidth()
            ) {
                letterRow.forEach {
                    AnimatedLetter(startLetter = it)
                }
            }
        }
    }
}

