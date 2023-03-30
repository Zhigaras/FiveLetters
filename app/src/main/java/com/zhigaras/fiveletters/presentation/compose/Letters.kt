package com.zhigaras.fiveletters.presentation.compose

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.presentation.CardState


@Composable
fun Letter(
    modifier: Modifier = Modifier,
    cardState: CardState
) {
    OutlinedCard(
        border = BorderStroke(width = 2.dp, cardState.borderColor),
        colors = CardDefaults.cardColors(containerColor = cardState.cardColor),
        modifier = modifier
            .width(70.dp)
            .padding(horizontal = 2.dp)
    
    ) {
        Text(
            text = cardState.char.toString().uppercase(),
            style = MaterialTheme.typography.displayLarge,
            color = cardState.charColor,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun AnimatedLetter(
    modifier: Modifier = Modifier,
    startCardState: CardState
) {
    var state: CardState by remember { mutableStateOf(startCardState) }
    val rotation by animateFloatAsState(
        targetValue = state.angle,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing,
        )
    )
    Box(modifier = modifier
        .clickable { state = CardState.Exact('g') }
        .graphicsLayer {
            rotationY = rotation
        }) {
        if (rotation <= 90f)
            Letter(cardState = startCardState)
        else
            Letter(
                modifier = modifier.graphicsLayer {
                    rotationY = 180f
                },
                cardState = state
            )
    }
}

@Composable
fun LetterField(
    modifier: Modifier = Modifier
) {
    val letterField = List(6) { List(5) { CardState.Default() } }
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
                    AnimatedLetter(startCardState = it)
                }
            }
        }
    }
}

