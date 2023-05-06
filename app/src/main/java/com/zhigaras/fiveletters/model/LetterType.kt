package com.zhigaras.fiveletters.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.squareup.moshi.JsonClass
import com.zhigaras.fiveletters.presentation.compose.ui.theme.keyboardButtonCornerRadius
import com.zhigaras.fiveletters.presentation.compose.ui.theme.keyboardButtonInnerPadding
import com.zhigaras.fiveletters.presentation.compose.ui.theme.letterCardCornerRadius
import com.zhigaras.fiveletters.presentation.compose.ui.theme.letterCardInnerPadding

abstract class LetterType {
    
    abstract val charSize: TextUnit
    abstract val charPadding: Dp
    abstract val cornersRadius: Dp
    abstract val borderWidth: Dp
    
    @JsonClass(generateAdapter = true)
    class Card : LetterType() {
        override val charSize: TextUnit = 35.sp
        override val charPadding: Dp = letterCardInnerPadding
        override val cornersRadius: Dp = letterCardCornerRadius
        override val borderWidth: Dp = 2.dp
    }
    
    @JsonClass(generateAdapter = true)
    class Key : LetterType() {
        override val charSize: TextUnit = 20.sp
        override val charPadding: Dp = keyboardButtonInnerPadding
        override val cornersRadius: Dp = keyboardButtonCornerRadius
        override val borderWidth: Dp = 1.dp
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LetterType) return false
        
        if (charSize != other.charSize) return false
        if (charPadding != other.charPadding) return false
        if (cornersRadius != other.cornersRadius) return false
        if (borderWidth != other.borderWidth) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = charSize.hashCode()
        result = 31 * result + charPadding.hashCode()
        result = 31 * result + cornersRadius.hashCode()
        result = 31 * result + borderWidth.hashCode()
        return result
    }
    
    
}