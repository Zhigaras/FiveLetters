package com.zhigaras.fiveletters.feature.play.domain.model

import com.squareup.moshi.JsonClass
import com.zhigaras.fiveletters.core.Constants

@JsonClass(generateAdapter = true)
data class GameState(
    val letterFieldState: LetterFieldState,
    val keyboard: Keyboard,
    val rowCursor: Int,
    val columnCursor: Int,
    val origin: Word
) {
    val isRowLast get() = rowCursor == Constants.MAX_ROWS - 1
    val isColumnLast get() = columnCursor == Constants.MAX_COLUMN - 1
    val progressState get() = letterFieldState.progressState
}
