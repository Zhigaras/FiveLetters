package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import android.util.Log
import androidx.compose.foundation.text.NewTextRendering1_5
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.Alphabet
import com.zhigaras.fiveletters.data.MainRepository
import com.zhigaras.fiveletters.domain.GameStateController
import com.zhigaras.fiveletters.domain.KeyboardStateController
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.model.Keyboard
import com.zhigaras.fiveletters.model.LetterFieldState
import com.zhigaras.fiveletters.model.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayViewModel(
    private val gameStateController: GameStateController,
    private val keyboardStateController: KeyboardStateController,
    private val mainRepository: MainRepository,
    private val dispatchers: DispatchersModule
) : ViewModel(), GameInteract {
    
    private val _letterFieldStateFlow: MutableStateFlow<GameState> =
        MutableStateFlow(
            GameState(
                letterFieldState = LetterFieldState.Start(),
                keyboard = Keyboard.Base(Alphabet.Base.Ru()),
                columnCursor = 0,
                rowCursor = 0,
                origin = Word(1, "слово", false, 0)
            )
        )
    val gameStateFlow = _letterFieldStateFlow.asStateFlow()
    
    private val _keyboardFlow = MutableStateFlow(keyboardStateController.getDefaultKeyboard())
    val keyboardFlow = _keyboardFlow.asStateFlow()
    
    override fun inputLetter(char: Char) {
        _letterFieldStateFlow.value =
            gameStateController.inputLetter(_letterFieldStateFlow.value, char)
    }
    
    override fun removeLetter() {
        _letterFieldStateFlow.value = gameStateController.removeLetter(_letterFieldStateFlow.value)
    }
    
    override fun confirmWord() {
        viewModelScope.launch(dispatchers.io()) {
            _letterFieldStateFlow.value =
                gameStateController.checkGameState(_letterFieldStateFlow.value) { word ->
                    mainRepository.update(word)
                }
//            val result =
//                gameStateController.checkGameState { word -> mainRepository.update(word) }
//            _letterFieldStateFlow.value = result
//            if (result !is LetterFieldState.InvalidWord)
//                keyboardStateController.updateKeyboard(gameStateController.getConfirmedRow().row)
//                    .let {
//                        _keyboardFlow.value = it
//                    }
        }
    }
    
    override fun startNewGame() {
        viewModelScope.launch(dispatchers.io()) {
            val origin = mainRepository.getUnsolvedWord()
            Log.d("AAA", origin.word)
            _letterFieldStateFlow.value = gameStateController.newGame(origin)
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