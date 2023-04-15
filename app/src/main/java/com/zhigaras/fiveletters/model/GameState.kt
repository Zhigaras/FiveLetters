package com.zhigaras.fiveletters.model

import com.zhigaras.fiveletters.Constants

abstract class GameState {
    
    abstract val result: List<RowState>
    
    abstract val inProgress: Boolean
    
    abstract class InProgress : GameState() {
        
        override val inProgress: Boolean = true
        
        class Start : InProgress() {
            override val result: List<RowState> =
                List(Constants.MAX_ROWS) {
                    RowState.Empty(List(Constants.MAX_COLUMN) {
                        LetterState.Default(
                            type = LetterType.Card,
                            char = ' '
                        )
                    })
                }
        }
        
        class Progress(override val result: List<RowState>) : InProgress()
        
        class InvalidWord(override val result: List<RowState>) : InProgress()
        
    }
    
    abstract class Ended : GameState() {
        
        override val inProgress: Boolean = false
        
        class Failed(override val result: List<RowState>) : Ended()
        
        class Win(override val result: List<RowState>) : Ended()
        
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GameState) return false
        if (this::class.java != other::class.java) return false
        return result == other.result
    }
    
    override fun hashCode(): Int {
        return result.hashCode()
    }
}