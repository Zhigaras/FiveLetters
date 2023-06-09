package com.zhigaras.fiveletters.feature.play.domain.model

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.squareup.moshi.JsonClass
import com.zhigaras.fiveletters.core.presentation.compose.theme.black
import com.zhigaras.fiveletters.core.presentation.compose.theme.gray
import com.zhigaras.fiveletters.core.presentation.compose.theme.red
import com.zhigaras.fiveletters.core.presentation.compose.theme.white
import com.zhigaras.fiveletters.core.presentation.compose.theme.yellow

abstract class LetterState {
    
    abstract val type: LetterType
    abstract val borderColor: Color
    abstract val cardColor: Color
    abstract val charColor: Color
    abstract val angle: Float
    abstract val char: Char
    abstract val grade: Int
    
    @Composable
    fun Letter(
        modifier: Modifier = Modifier
    ) {
        OutlinedCard(
            border = BorderStroke(width = type.borderWidth, color = borderColor),
            shape = RoundedCornerShape(type.cornersRadius),
            colors = CardDefaults.cardColors(
                containerColor = cardColor,
                contentColor = charColor
            ),
            modifier = modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = char.toString().uppercase(),
                    fontSize = type.charSize,
                    modifier = Modifier
                )
            }
        }
    }
    
    @Composable
    fun Key(
        modifier: Modifier = Modifier,
        onKeyClick: (Char) -> Unit
    ) {
        val buttonAnimationDuration = 500
        val (containerColor, contentColor, borderColor) = listOf(
            cardColor,
            charColor,
            borderColor
        ).map {
            animateColorAsState(targetValue = it, animationSpec = tween(buttonAnimationDuration))
        }
        OutlinedCard(
            border = BorderStroke(width = type.borderWidth, color = borderColor.value),
            shape = RoundedCornerShape(type.cornersRadius),
            colors = CardDefaults.cardColors(
                containerColor = containerColor.value,
                contentColor = contentColor.value
            ),
            modifier = modifier.clickable { onKeyClick(char) }
        ) {
            Text(
                text = char.toString().uppercase(),
                fontSize = type.charSize,
                modifier = Modifier
                    .padding(vertical = type.charPadding)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
    
    fun convertCardToKey(): LetterState {
        return when (this) {
            is Wrong -> Wrong(LetterType.Key(), char)
            is Right -> Right(LetterType.Key(), char)
            is Exact -> Exact(LetterType.Key(), char)
            else -> Default(LetterType.Key(), char)
        }
    }
    
    fun isBetter(oldLetter: LetterState): Boolean {
        return oldLetter.grade < grade
    }
    
    @JsonClass(generateAdapter = true)
    data class Empty(override val type: LetterType) : LetterState() {
        override val char: Char = ' '
        override val borderColor: Color = if (type is LetterType.Card) yellow else gray
        override val cardColor: Color = black
        override val charColor: Color = white
        override val angle: Float = 0f
        override val grade: Int = 0
    }
    
    @JsonClass(generateAdapter = true)
    data class Default(
        override val type: LetterType,
        override val char: Char,
    ) : LetterState() {
        override val borderColor: Color = if (type is LetterType.Card) yellow else gray
        override val cardColor: Color = black
        override val charColor: Color = white
        override val angle: Float = 0f
        override val grade: Int = 0
    }
    
    @JsonClass(generateAdapter = true)
    data class UserClicked(override val char: Char) : LetterState() {
        override val type: LetterType = LetterType.Card()
        override val borderColor: Color = yellow
        override val cardColor: Color = black
        override val charColor: Color = white
        override val angle: Float = 0f
        override val grade: Int = 2
    }
    
    @JsonClass(generateAdapter = true)
    data class Wrong(override val type: LetterType, override val char: Char) : LetterState() {
        override val borderColor: Color = gray
        override val cardColor: Color = gray
        override val charColor: Color = white
        override val angle: Float = 180f
        override val grade: Int = 3
    }
    
    @JsonClass(generateAdapter = true)
    data class Right(override val type: LetterType, override val char: Char) : LetterState() {
        override val borderColor: Color = white
        override val cardColor: Color = white
        override val charColor: Color = black
        override val angle: Float = 180f
        override val grade: Int = 4
    }
    
    @JsonClass(generateAdapter = true)
    data class Exact(override val type: LetterType, override val char: Char) : LetterState() {
        override val borderColor: Color = yellow
        override val cardColor: Color = yellow
        override val charColor: Color = black
        override val angle: Float = 180f
        override val grade: Int = 5
    }
    
    @JsonClass(generateAdapter = true)
    data class InvalidWord(override val char: Char) : LetterState() {
        override val type: LetterType = LetterType.Card()
        override val borderColor: Color = yellow
        override val cardColor: Color = black
        override val charColor: Color = red
        override val angle: Float = 0f
        override val grade: Int = 1
    }
}