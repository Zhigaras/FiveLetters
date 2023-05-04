package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.MainRepository
import com.zhigaras.fiveletters.data.StateSaver
import com.zhigaras.fiveletters.domain.GameStateController
import com.zhigaras.fiveletters.model.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayViewModel(
    private val gameStateController: GameStateController,
    private val mainRepository: MainRepository,
    private val dispatchers: DispatchersModule,
    private val stateSaver: StateSaver.Mutable
) : ViewModel(), GameInteract {
    
    private val _gameStateFlow: MutableStateFlow<GameState> =
        MutableStateFlow(gameStateController.newGame())
    val gameStateFlow = _gameStateFlow.asStateFlow()
    
    override fun inputLetter(char: Char) {
        _gameStateFlow.value = gameStateController.inputLetter(_gameStateFlow.value, char)
    }
    
    override fun removeLetter() {
        _gameStateFlow.value = gameStateController.removeLetter(_gameStateFlow.value)
    }
    
    init {
        restoreState()
    }
    
    override fun confirmWord() {
        viewModelScope.launch(dispatchers.io()) {
            _gameStateFlow.value =
                gameStateController.confirmWord(
                    gameState = _gameStateFlow.value,
                    saveAttempts = { word -> mainRepository.update(word) },
                    incrementGamesCounter = { mainRepository.incrementGamesCounter() }
                )
        }
    }
    
    override fun startNewGame() {
        viewModelScope.launch(dispatchers.io()) {
            val origin = mainRepository.getUnsolvedWord()
            _gameStateFlow.value = gameStateController.newGame(origin)
        }
    }
    
    override fun saveState() {
        viewModelScope.launch(dispatchers.io()) {
            stateSaver.saveState(gameStateFlow.value)
        }
    }
    
    override fun restoreState() {
        viewModelScope.launch(dispatchers.io()) {
            val restoredState = stateSaver.restoreState()
            if (restoredState != null) _gameStateFlow.value = restoredState
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