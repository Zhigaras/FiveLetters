package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.zhigaras.fiveletters.data.Repository
import com.zhigaras.fiveletters.domain.GameStateController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayViewModel(
    private val gameStateController: GameStateController,
    private val repository: Repository
) : ViewModel(), Interact {
    
    private var origin: String = ""

    private val _gameStateFlow = MutableStateFlow(gameStateController.getStartGameState())
    val gameStateFlow = _gameStateFlow.asStateFlow()
    
    init {
        getNewOrigin()
    }
    
    override fun inputLetter(char: Char) {
        gameStateController.inputLetter(char).let {
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
    }
    
    override fun getNewOrigin() {
        origin = repository.randomWord()
    }
}


interface Interact {
    
    fun confirmWord()
    
    fun getNewOrigin()
    
    fun inputLetter(char: Char)
    
    fun removeLetter()
    
}