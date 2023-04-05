package com.zhigaras.fiveletters.model

import com.zhigaras.fiveletters.Constants

abstract class GameState {
    
    abstract val result: List<RowState>
    
    class Start : GameState() {
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
    
    class Progress(
        override val result: List<RowState>
    ) : GameState()
    
    class GameOver(
        override val result: List<RowState>
    ) : GameState()
    
    class Win(
        override val result: List<RowState>
    ) : GameState()
    
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