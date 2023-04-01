package com.zhigaras.fiveletters.model

import com.zhigaras.fiveletters.presentation.LetterItem
import com.zhigaras.fiveletters.presentation.LetterType

abstract class GameState {
    
    abstract val result: List<LetterItem>
    
    class Start : GameState() {
        override val result: List<LetterItem> =
            List(5) { LetterItem.Default(type = LetterType.Card, char = ' ') }
    }
    
    class Progress(
        override val result: List<LetterItem>
    ) : GameState()
    
    class GameOver(
        override val result: List<LetterItem>
    ) : GameState()
    
    class Win(
        override val result: List<LetterItem>
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