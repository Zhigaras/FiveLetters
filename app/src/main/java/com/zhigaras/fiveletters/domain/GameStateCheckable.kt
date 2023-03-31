package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.presentation.Letter

interface GameStateCheckable {
    
    fun checkGameState(word: List<Letter>, attemptNumber: Int): GameState
    
    class Base() : GameStateCheckable {
        
        override fun checkGameState(word: List<Letter>, attemptNumber: Int): GameState {
            if (word.all { it is Letter.Exact })
                return GameState.Win(word)
            if (attemptNumber == MAX_ATTEMPTS)
                return GameState.GameOver(word)
            return GameState.Progress(word)
        }
        
        companion object {
            private const val MAX_ATTEMPTS = 6
        }
    }
}