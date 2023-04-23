package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.MainRepository
import com.zhigaras.fiveletters.domain.GameStateController
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.model.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayViewModel(
    private val gameStateController: GameStateController,
    private val mainRepository: MainRepository,
    private val dispatchers: DispatchersModule
) : ViewModel(), GameInteract {
    
    private val _gameStateFlow: MutableStateFlow<GameState> =
        MutableStateFlow(gameStateController.newGame(Word(1, "слово", false, 0)))
    val gameStateFlow = _gameStateFlow.asStateFlow()
    
    override fun inputLetter(char: Char) {
        _gameStateFlow.value = gameStateController.inputLetter(_gameStateFlow.value, char)
    }
    
    override fun removeLetter() {
        _gameStateFlow.value = gameStateController.removeLetter(_gameStateFlow.value)
    }
    
    override fun confirmWord() {
        viewModelScope.launch(dispatchers.io()) {
            _gameStateFlow.value =
                gameStateController.confirmWord(_gameStateFlow.value) { word ->
                    mainRepository.update(word)
                }
        }
    }
    
    override fun startNewGame() {
        viewModelScope.launch(dispatchers.io()) {
            val origin = mainRepository.getUnsolvedWord()
            Log.d("AAA", origin.word)
            _gameStateFlow.value = gameStateController.newGame(origin)
            mainRepository.incrementGamesCounter()
        }
    }
}

interface GameInteract {
    
    fun confirmWord()
    
    fun inputLetter(char: Char)
    
    fun removeLetter()
    
    fun startNewGame()
}