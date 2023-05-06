package com.zhigaras.fiveletters.model

import com.squareup.moshi.JsonClass
import com.zhigaras.fiveletters.Constants

abstract class LetterFieldState {
    
    abstract val result: List<RowState>
    
    abstract val progressState: ProgressState
    
    @JsonClass(generateAdapter = true)
    class Start : LetterFieldState() {
        override val result: List<RowState> =
            List(Constants.MAX_ROWS) {
                RowState.Empty(List(Constants.MAX_COLUMN) {
                    LetterState.Empty(type = LetterType.Card())
                })
            }
        override val progressState = ProgressState.JUST_STARTED
    }
    
    @JsonClass(generateAdapter = true)
    class Progress(override val result: List<RowState>) : LetterFieldState() {
        override val progressState = ProgressState.PROGRESS
    }
    
    @JsonClass(generateAdapter = true)
    class InvalidWord(override val result: List<RowState>) : LetterFieldState() {
        override val progressState = ProgressState.PROGRESS
    }
    
    
    @JsonClass(generateAdapter = true)
    class Failed(override val result: List<RowState>) : LetterFieldState() {
        override val progressState = ProgressState.ENDED
    }
    
    @JsonClass(generateAdapter = true)
    class Win(override val result: List<RowState>) : LetterFieldState() {
        override val progressState = ProgressState.ENDED
    }
}