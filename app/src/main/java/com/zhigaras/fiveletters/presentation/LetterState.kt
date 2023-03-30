package com.zhigaras.fiveletters.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhigaras.fiveletters.presentation.compose.ui.theme.black
import com.zhigaras.fiveletters.presentation.compose.ui.theme.gray
import com.zhigaras.fiveletters.presentation.compose.ui.theme.white
import com.zhigaras.fiveletters.presentation.compose.ui.theme.yellow

class Letter(val type: LetterType, val state: LetterState)


abstract class LetterType() {
    
    abstract val charSize: TextUnit
    abstract val width: Dp
    
    class Card() : LetterType() {
        override val charSize: TextUnit = 60.sp
        override val width: Dp = 70.dp
    }
    
    class Key() : LetterType() {
        override val charSize: TextUnit = 10.sp
        override val width: Dp = 10.dp
    }
    
}

abstract class LetterState() {
    
    abstract val borderColor: Color
    abstract val cardColor: Color
    abstract val charColor: Color
    abstract val angle: Float
    abstract val char: Char
    
    class Default() : LetterState() {
        override val borderColor: Color = yellow
        override val cardColor: Color = black
        override val charColor: Color = white
        override val char: Char = ' '
        override val angle: Float = 0f
    }
    
    class UserClicked(override val char: Char) : LetterState() {
        override val borderColor: Color = yellow
        override val cardColor: Color = black
        override val charColor: Color = white
        override val angle: Float = 0f
    }
    
     class Wrong(override val char: Char) : LetterState() {
         override val borderColor: Color = gray
         override val cardColor: Color = gray
         override val charColor: Color = white
         override val angle: Float = 180f
     }
    
    class Right(override val char: Char) : LetterState() {
        override val borderColor: Color = white
        override val cardColor: Color = white
        override val charColor: Color = black
        override val angle: Float = 180f
    }
    
    class Exact(override val char: Char) : LetterState() {
        override val borderColor: Color = yellow
        override val cardColor: Color = yellow
        override val charColor: Color = black
        override val angle: Float = 180f
    }
}