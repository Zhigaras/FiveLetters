package com.zhigaras.fiveletters.presentation.compose.ui.screens.play

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.model.Keyboard
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.RowState
import com.zhigaras.fiveletters.presentation.compose.ui.theme.*

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    gameState: GameState,
    keyboard: Keyboard,
    onKeyClick: (Char) -> Unit,
    onConfirmClick: () -> Unit,
    onBackspaceClick: () -> Unit
) {
    val isConfirmButtonEnabled = remember { mutableStateOf(false) }
    isConfirmButtonEnabled.value = gameState.result.any { it is RowState.Append.FullRow }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        keyboard.keys.forEachIndexed { index, row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
            ) {
                if (index == keyboard.keys.lastIndex)
                    ConfirmWordButton(
                        modifier = Modifier.weight(17f),
                        isEnabled = isConfirmButtonEnabled.value,
                        onConfirmClick = onConfirmClick
                    )
                row.forEach {
//                    val state = remember(key1 = it) { it }
                    Key(modifier = Modifier.weight(10f), letter = it, onKeyClick = onKeyClick)
                }
                if (index == keyboard.keys.lastIndex)
                    BackspaceButton(
                        modifier = Modifier.weight(17f),
                        letter = row.first(),
                        onBackspaceClick = onBackspaceClick
                    )
            }
        }
    }
}

@Composable
fun Key(
    modifier: Modifier = Modifier,
    letter: LetterState,
    onKeyClick: (Char) -> Unit
) {
    OutlinedCard(
        border = BorderStroke(width = letter.type.borderWidth, color = letter.borderColor),
        shape = RoundedCornerShape(letter.type.cornersRadius),
        colors = CardDefaults.cardColors(
            containerColor = letter.cardColor,
            contentColor = letter.charColor
        ),
        modifier = modifier.clickable { onKeyClick(letter.char) }
    ) {
        Text(
            text = letter.char.toString().uppercase(),
            fontSize = letter.type.charSize,
            modifier = Modifier
                .padding(vertical = letter.type.charPadding)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun FlippableKey(
    modifier: Modifier = Modifier,
    newLetter: LetterState,
    oldLetter: LetterState,
    onKeyClick: (Char) -> Unit
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
            Key(letter = oldLetter, onKeyClick = onKeyClick)
        else
            Key(
                modifier = modifier.graphicsLayer { rotationY = 180f },
                letter = newLetter,
                onKeyClick = onKeyClick
            )
    }
}

@Composable
fun ConfirmWordButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    onConfirmClick: () -> Unit
) {
    val buttonAnimationDuration = 500
    val containerColor = animateColorAsState(
        targetValue = if (isEnabled) yellow else black,
        animationSpec = tween(buttonAnimationDuration)
    )
    val contentColor = animateColorAsState(
        targetValue = if (isEnabled) black else white,
        animationSpec = tween(buttonAnimationDuration)
    )
    val borderColor = animateColorAsState(
        targetValue = if (isEnabled) yellow else gray,
        animationSpec = tween(buttonAnimationDuration)
    )
    OutlinedCard(
        border = BorderStroke(width = 1.dp, borderColor.value),
        shape = RoundedCornerShape(keyboardButtonCornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = containerColor.value,
            contentColor = contentColor.value
        ),
        modifier = modifier
            .fillMaxHeight()
            .clickable(enabled = isEnabled) { onConfirmClick() }
    ) {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = stringResource(R.string.confirm_word),
            modifier = Modifier
                .fillMaxSize()
                .padding(keyboardButtonInnerPadding)
        )
    }
}

@Composable
fun BackspaceButton(
    modifier: Modifier = Modifier,
    letter: LetterState,
    onBackspaceClick: () -> Unit
) {
    OutlinedCard(
        border = BorderStroke(width = 1.dp, color = letter.borderColor),
        shape = RoundedCornerShape(letter.type.cornersRadius),
        colors = CardDefaults.cardColors(
            containerColor = letter.cardColor,
            contentColor = letter.charColor
        ),
        modifier = modifier
            .fillMaxHeight()
            .clickable { onBackspaceClick() }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.clear_letter),
            modifier = Modifier
                .fillMaxSize()
                .padding(keyboardButtonInnerPadding)
        )
    }
}