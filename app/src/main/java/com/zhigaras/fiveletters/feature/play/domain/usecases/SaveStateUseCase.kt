package com.zhigaras.fiveletters.feature.play.domain.usecases

import com.zhigaras.fiveletters.feature.play.domain.GameStateRepository
import com.zhigaras.fiveletters.feature.play.domain.model.GameState

interface SaveStateUseCase {
    
    suspend fun saveState(state: GameState)
    
    suspend fun restoreState(): GameState?
    
    class Base(private val gameStateRepository: GameStateRepository): SaveStateUseCase {
    
        override suspend fun saveState(state: GameState) {
            gameStateRepository.saveState(state)
        }
    
        override suspend fun restoreState(): GameState? {
            return gameStateRepository.restoreState()
        }
    }
}