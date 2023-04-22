package com.zhigaras.fiveletters.model

import com.zhigaras.fiveletters.Constants

data class GameState(
    val letterFieldState: LetterFieldState,
    val keyboard: Keyboard,
    val rowCursor: Int,
    val columnCursor: Int,
    val origin: Word
) {
    val isRowLast get() = rowCursor == Constants.MAX_ROWS - 1
    val isColumnLast get() = columnCursor == Constants.MAX_COLUMN - 1
}
