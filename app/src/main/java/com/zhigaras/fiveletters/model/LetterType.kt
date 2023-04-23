package com.zhigaras.fiveletters.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhigaras.fiveletters.presentation.compose.ui.theme.keyboardButtonCornerRadius
import com.zhigaras.fiveletters.presentation.compose.ui.theme.keyboardButtonInnerPadding
import com.zhigaras.fiveletters.presentation.compose.ui.theme.letterCardCornerRadius
import com.zhigaras.fiveletters.presentation.compose.ui.theme.letterCardInnerPadding

abstract class LetterType {
    
    val label: String = this::class.java.simpleName
    abstract val charSize: TextUnit
    abstract val width: Dp
    abstract val charPadding: Dp
    abstract val cornersRadius: Dp
    abstract val borderWidth: Dp
    
    class Card : LetterType() {
        override val charSize: TextUnit = 50.sp
        override val width: Dp = 70.dp
        override val charPadding: Dp = letterCardInnerPadding
        override val cornersRadius: Dp = letterCardCornerRadius
        override val borderWidth: Dp = 2.dp
    }
    
    class Key : LetterType() {
        override val charSize: TextUnit = 20.sp
        override val width: Dp = 0.dp
        override val charPadding: Dp = keyboardButtonInnerPadding
        override val cornersRadius: Dp = keyboardButtonCornerRadius
        override val borderWidth: Dp = 1.dp
    }
}