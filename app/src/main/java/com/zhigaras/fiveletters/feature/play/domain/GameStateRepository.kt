package com.zhigaras.fiveletters.feature.play.domain

import com.zhigaras.fiveletters.feature.play.domain.model.GameState

interface GameStateRepository {
    
    suspend fun saveState(state: GameState)
    
    suspend fun restoreState(): GameState?
    
}