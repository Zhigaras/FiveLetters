package com.zhigaras.fiveletters.model

import androidx.compose.ui.graphics.Color
import com.zhigaras.fiveletters.presentation.compose.ui.theme.black
import com.zhigaras.fiveletters.presentation.compose.ui.theme.gray
import com.zhigaras.fiveletters.presentation.compose.ui.theme.white
import com.zhigaras.fiveletters.presentation.compose.ui.theme.yellow

abstract class LetterState() {
    
    abstract val type: LetterType
    abstract val borderColor: Color
    abstract val cardColor: Color
    abstract val charColor: Color
    abstract val angle: Float
    abstract val char: Char
    abstract val action: Action
    
    data class Default(
        override val type: LetterType,
        override val char: Char,
        override val action: Action = Action.INIT
    ) : LetterState() {
        override val borderColor: Color = if (type is LetterType.Card) yellow else gray
        override val cardColor: Color = black
        override val charColor: Color = white
        override val angle: Float = 0f
    }
    
    data class UserClicked(override val type: LetterType, override val char: Char) : LetterState() {
        override val borderColor: Color = if (type is LetterType.Card) yellow else gray
        override val cardColor: Color = black
        override val charColor: Color = white
        override val angle: Float = 0f
        override val action: Action = Action.APPEND
    }
    
    data class Wrong(override val type: LetterType, override val char: Char) : LetterState() {
        override val borderColor: Color = gray
        override val cardColor: Color = gray
        override val charColor: Color = white
        override val angle: Float = 180f
        override val action: Action = Action.CONFIRM
    }
    
    data class Right(override val type: LetterType, override val char: Char) : LetterState() {
        override val borderColor: Color = white
        override val cardColor: Color = white
        override val charColor: Color = black
        override val angle: Float = 180f
        override val action: Action = Action.CONFIRM
    }
    
    data class Exact(override val type: LetterType, override val char: Char) : LetterState() {
        override val borderColor: Color = yellow
        override val cardColor: Color = yellow
        override val charColor: Color = black
        override val angle: Float = 180f
        override val action: Action = Action.CONFIRM
    }
    
    override fun toString(): String {
        return this::class.java.simpleName + this.char
    }
}