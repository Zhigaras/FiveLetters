package com.zhigaras.fiveletters.model

import com.zhigaras.fiveletters.presentation.Letter

abstract class GameState {
    
    abstract val result: List<Letter>
    
    object Start : GameState() {
        override val result: List<Letter> = emptyList()
    }
    
    data class Progress(
        override val result: List<Letter>
    ) : GameState()
    
    data class GameOver(
        override val result: List<Letter>
    ) : GameState()
    
    data class Win(
        override val result: List<Letter>
    ) : GameState()
}