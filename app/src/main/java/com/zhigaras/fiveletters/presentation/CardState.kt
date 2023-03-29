package com.zhigaras.fiveletters.presentation

import androidx.compose.ui.graphics.Color
import com.zhigaras.fiveletters.presentation.compose.ui.theme.black
import com.zhigaras.fiveletters.presentation.compose.ui.theme.gray
import com.zhigaras.fiveletters.presentation.compose.ui.theme.white
import com.zhigaras.fiveletters.presentation.compose.ui.theme.yellow


abstract class CardState {
    
    abstract val borderColor: Color
    abstract val cardColor: Color
    abstract val charColor: Color
    abstract val char: Char
    
    class Default(override val char: Char): CardState() {
        override val borderColor: Color = yellow
        override val cardColor: Color = black
        override val charColor: Color = white
    }
    
    class Wrong(override val char: Char): CardState() {
        override val borderColor: Color = gray
        override val cardColor: Color = gray
        override val charColor: Color = white
    }
    
    class Right(override val char: Char): CardState() {
        override val borderColor: Color = white
        override val cardColor: Color = white
        override val charColor: Color = black
    }
    
    class Exact(override val char: Char): CardState() {
        override val borderColor: Color = yellow
        override val cardColor: Color = yellow
        override val charColor: Color = black
    }
}