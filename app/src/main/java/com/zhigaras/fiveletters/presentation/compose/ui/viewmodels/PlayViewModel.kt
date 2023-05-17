package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.MainRepository
import com.zhigaras.fiveletters.data.StateSaver
import com.zhigaras.fiveletters.domain.play.GameStateController
import com.zhigaras.fiveletters.model.play.GameState
import kotlinx.coroutines.launch

class PlayViewModel(
    private val gameStateController: GameStateController,
    private val mainRepository: MainRepository,
    private val dispatchers: DispatchersModule,
    private val stateSaver: StateSaver.Mutable
) : BaseViewModel<GameState>(gameStateController.newGame()), GameInteract {
    
    override fun inputLetter(char: Char) {
        state = gameStateController.inputLetter(state, char)
    }
    
    override fun removeLetter() {
        state = gameStateController.removeLetter(state)
    }
    
    init {
        restoreState()
    }
    
    override fun confirmWord() {
        viewModelScope.launch(dispatchers.io()) {
            state =
                gameStateController.confirmWord(
                    gameState = state,
                    saveAttempts = { word -> mainRepository.update(word) },
                    incrementGamesCounter = { mainRepository.incrementGamesCounter() }
                )
        }
    }
    
    override fun startNewGame() {
        viewModelScope.launch(dispatchers.io()) {
            val origin = mainRepository.getUnsolvedWord()
            state = gameStateController.newGame(origin)
        }
    }
    
    override fun saveState() {
        viewModelScope.launch(dispatchers.io()) {
            stateSaver.saveState(state)
        }
    }
    
    override fun restoreState() {
        viewModelScope.launch(dispatchers.io()) {
            val restoredState = stateSaver.restoreState()
            if (restoredState != null) state = restoredState
            else startNewGame()
        }
    }
}

interface GameInteract {
    
    fun confirmWord()
    
    fun inputLetter(char: Char)
    
    fun removeLetter()
    
    fun startNewGame()
    
    fun saveState()
    
    fun restoreState()
}