package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.MainRepository
import com.zhigaras.fiveletters.domain.GameStateController
import com.zhigaras.fiveletters.domain.KeyboardStateController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayViewModel(
    private val gameStateController: GameStateController,
    private val keyboardStateController: KeyboardStateController,
    private val mainRepository: MainRepository,
    private val dispatchers: DispatchersModule
) : ViewModel(), GameInteract {
    
    private var origin: String = ""
    
    private val _gameStateFlow = MutableStateFlow(gameStateController.newGame())
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
        gameStateController.checkGameState(origin).let {
            _gameStateFlow.value = it
        }
        keyboardStateController.updateKeyboard(gameStateController.getConfirmedRow().row).let {
            _keyboardFlow.value = it
        }
    }
    
    override suspend fun initRepository() {
        mainRepository.getDictionarySize()
        origin = mainRepository.randomWord().word
    }
    
    override fun startNewGame() {
        viewModelScope.launch(dispatchers.io()) {
            origin = mainRepository.randomWord().word
            _gameStateFlow.value = gameStateController.newGame()
            _keyboardFlow.value = keyboardStateController.getDefaultKeyboard()
        }
    }
}


interface GameInteract {
    
    fun confirmWord()
    
    fun inputLetter(char: Char)
    
    fun removeLetter()
    
    fun startNewGame()
    
    suspend fun initRepository()
    
}