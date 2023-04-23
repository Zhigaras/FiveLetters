package com.zhigaras.fiveletters.model

import com.zhigaras.fiveletters.Constants

abstract class LetterFieldState {
    
    val label: String = this::class.java.simpleName
    
    abstract val result: List<RowState>
    
    abstract val inProgress: Boolean
    
    class NotStartedYet : LetterFieldState() {
        override val result: List<RowState> =
            List(Constants.MAX_ROWS) {
                RowState.Empty(List(Constants.MAX_COLUMN) {
                    LetterState.Empty(type = LetterType.Card())
                })
            }
        override val inProgress: Boolean = false
        
    }
    
    class Start : LetterFieldState() {
        override val result: List<RowState> =
            List(Constants.MAX_ROWS) {
                RowState.Empty(List(Constants.MAX_COLUMN) {
                    LetterState.Empty(type = LetterType.Card())
                })
            }
        override val inProgress: Boolean = true
    }
    
    class Progress(override val result: List<RowState>) : LetterFieldState() {
        override val inProgress: Boolean = true
    }
    
    class InvalidWord(override val result: List<RowState>) :
        LetterFieldState() {
        override val inProgress: Boolean = true
    }
    
    
    class Failed(override val result: List<RowState>) : LetterFieldState() {
        override val inProgress: Boolean = false
    }
    
    class Win(override val result: List<RowState>) : LetterFieldState() {
        override val inProgress: Boolean = false
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LetterFieldState) return false
        if (this::class.java != other::class.java) return false
        return result == other.result
    }
    
    override fun hashCode(): Int {
        return result.hashCode()
    }
}