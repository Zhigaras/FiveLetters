package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.presentation.LetterItem

interface GameStateCheckable {
    
    fun checkGameState(word: List<LetterItem>, attemptNumber: Int): GameState
    
    class Base() : GameStateCheckable {
        
        override fun checkGameState(word: List<LetterItem>, attemptNumber: Int): GameState {
            if (word.all { it is LetterItem.Exact })
                return GameState.Win(word)
            if (attemptNumber == MAX_ATTEMPTS - 1)
                return GameState.GameOver(word)
            return GameState.Progress(word)
        }
        
        companion object {
            private const val MAX_ATTEMPTS = 6
        }
    }
}