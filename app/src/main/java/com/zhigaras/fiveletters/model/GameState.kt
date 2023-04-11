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
        
    }
    
    abstract class Ended : GameState() {
        
        override val inProgress: Boolean = false
        
        class Failed(override val result: List<RowState>) : Ended()
        
        class Win(override val result: List<RowState>) : Ended()
        
    }
    
    override fun toString(): String {
        return result.map { it.toString() }.joinToString { "\n" }
    }
    
    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other.hashCode()
    }
    
    override fun hashCode(): Int {
        return result.hashCode()
    }
}