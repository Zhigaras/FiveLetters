package com.zhigaras.fiveletters.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhigaras.fiveletters.presentation.compose.ui.theme.*

abstract class LetterType() {
    
    abstract val charSize: TextUnit
    abstract val width: Dp
    abstract val charPadding: Dp
    abstract val cornersRadius: Dp
    abstract val borderWidth: Dp
    
    object Card : LetterType() {
        override val charSize: TextUnit = 50.sp
        override val width: Dp = 70.dp
        override val charPadding: Dp = letterCardInnerPadding
        override val cornersRadius: Dp = letterCardCornerRadius
        override val borderWidth: Dp = 2.dp
    }
    
    object Key : LetterType() {
        override val charSize: TextUnit = 20.sp
        override val width: Dp = 0.dp
        override val charPadding: Dp = keyboardButtonInnerPadding
        override val cornersRadius: Dp = keyboardButtonCornerRadius
        override val borderWidth: Dp = 1.dp
    }
}

abstract class LetterItem() {
    
    abstract val type: LetterType
    abstract val borderColor: Color
    abstract val cardColor: Color
    abstract val charColor: Color
    abstract val angle: Float
    abstract val char: Char
    
    data class Default(override val type: LetterType, override val char: Char) : LetterItem() {
        override val borderColor: Color = if (type is LetterType.Card) yellow else gray
        override val cardColor: Color = black
        override val charColor: Color = white
        override val angle: Float = 0f
    }
    
    data class UserClicked(override val type: LetterType, override val char: Char) : LetterItem() {
        override val borderColor: Color = if (type is LetterType.Card) yellow else gray
        override val cardColor: Color = black
        override val charColor: Color = white
        override val angle: Float = 0f
    }
    
    data class Wrong(override val type: LetterType, override val char: Char) : LetterItem() {
        override val borderColor: Color = gray
        override val cardColor: Color = gray
        override val charColor: Color = white
        override val angle: Float = 180f
    }
    
    data class Right(override val type: LetterType, override val char: Char) : LetterItem() {
        override val borderColor: Color = white
        override val cardColor: Color = white
        override val charColor: Color = black
        override val angle: Float = 180f
    }
    
    data class Exact(override val type: LetterType, override val char: Char) : LetterItem() {
        override val borderColor: Color = yellow
        override val cardColor: Color = yellow
        override val charColor: Color = black
        override val angle: Float = 180f
    }
    
    override fun toString(): String {
        return this::class.java.simpleName + this.char
    }
}