package com.zhigaras.fiveletters.feature.play.presentation

import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.presentation.BaseViewModel
import com.zhigaras.fiveletters.di.DispatchersModule
import com.zhigaras.fiveletters.feature.play.domain.MainRepository
import com.zhigaras.fiveletters.feature.play.domain.model.GameState
import com.zhigaras.fiveletters.feature.play.domain.usecases.GameStateController
import com.zhigaras.fiveletters.feature.play.domain.usecases.SaveStateUseCase
import kotlinx.coroutines.launch

class PlayViewModel(
    private val gameStateController: GameStateController,
    private val mainRepository: MainRepository,
    private val dispatchers: DispatchersModule,
    private val saveStateUseCase: SaveStateUseCase
) : BaseViewModel<GameState>(gameStateController.newGame()) {
    
    init {
        restoreState()
    }
    
     fun inputLetter(char: Char) {
        state = gameStateController.inputLetter(state, char)
    }
    
     fun removeLetter() {
        state = gameStateController.removeLetter(state)
    }
    
     fun confirmWord() {
        viewModelScope.launch(dispatchers.io()) {
            state =
                gameStateController.confirmWord(
                    gameState = state,
                    saveAttempts = { word -> mainRepository.update(word) },
                    incrementGamesCounter = { mainRepository.incrementGamesCounter() }
                )
        }
    }
    
     fun startNewGame() {
        viewModelScope.launch(dispatchers.io()) {
            val origin = mainRepository.getUnsolvedWord()
            state = gameStateController.newGame(origin)
        }
    }
    
     fun saveState() {
        viewModelScope.launch(dispatchers.io()) {
            saveStateUseCase.saveState(state)
        }
    }
    
     fun restoreState() {
        viewModelScope.launch(dispatchers.io()) {
            val restoredState = saveStateUseCase.restoreState()
            if (restoredState != null) state = restoredState
            else startNewGame()
        }
    }
}