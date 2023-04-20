package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.MainRepository
import com.zhigaras.fiveletters.domain.GameStateController
import com.zhigaras.fiveletters.domain.KeyboardStateController
import com.zhigaras.fiveletters.model.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayViewModel(
    private val gameStateController: GameStateController,
    private val keyboardStateController: KeyboardStateController,
    private val mainRepository: MainRepository,
    private val dispatchers: DispatchersModule
) : ViewModel(), GameInteract {
    
    private val _gameStateFlow: MutableStateFlow<GameState> =
        MutableStateFlow(GameState.NotStartedYet())
    val gameStateFlow = _gameStateFlow.asStateFlow()
    
    private val _keyboardFlow = MutableStateFlow(keyboardStateController.getDefaultKeyboard())
    val keyboardFlow = _keyboardFlow.asStateFlow()
    
    override fun inputLetter(char: Char) {
        gameStateController.inputLetter(char.uppercaseChar()).let {
            _gameStateFlow.value = it
        }
    }
    
    override fun removeLetter() {
        gameStateController.removeLetter().let {
            _gameStateFlow.value = it
        }
    }
    
    override fun confirmWord() {
        viewModelScope.launch(dispatchers.io()) {
            val result =
                gameStateController.checkGameState { word -> mainRepository.updateWord(word) }
            _gameStateFlow.value = result
            if (result !is GameState.InvalidWord)
                keyboardStateController.updateKeyboard(gameStateController.getConfirmedRow().row)
                    .let {
                        _keyboardFlow.value = it
                    }
        }
    }
    
    override fun startNewGame() {
        viewModelScope.launch(dispatchers.io()) {
            val origin = mainRepository.randomWord()
            Log.d("AAA", origin.word)
            _gameStateFlow.value = gameStateController.newGame(origin)
            _keyboardFlow.value = keyboardStateController.getDefaultKeyboard()
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