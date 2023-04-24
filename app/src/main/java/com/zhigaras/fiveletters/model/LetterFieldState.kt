package com.zhigaras.fiveletters.model

import com.zhigaras.fiveletters.Constants

abstract class LetterFieldState {
    
    val label: String = this::class.java.simpleName
    
    abstract val result: List<RowState>
    
    abstract val progressState: ProgressState
    
    class Start : LetterFieldState() {
        override val result: List<RowState> =
            List(Constants.MAX_ROWS) {
                RowState.Empty(List(Constants.MAX_COLUMN) {
                    LetterState.Empty(type = LetterType.Card())
                })
            }
        override val progressState = ProgressState.JUST_STARTED
    }
    
    class Progress(override val result: List<RowState>) : LetterFieldState() {
        override val progressState = ProgressState.PROGRESS
    }
    
    class InvalidWord(override val result: List<RowState>) :
        LetterFieldState() {
        override val progressState = ProgressState.PROGRESS
    }
    
    
    class Failed(override val result: List<RowState>) : LetterFieldState() {
        override val progressState = ProgressState.ENDED
    }
    
    class Win(override val result: List<RowState>) : LetterFieldState() {
        override val progressState = ProgressState.ENDED
    }
}