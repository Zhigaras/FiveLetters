package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.zhigaras.fiveletters.domain.GameStateCheckable
import com.zhigaras.fiveletters.domain.WordCheckable
import com.zhigaras.fiveletters.domain.StringConverter
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.presentation.Letter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayViewModel(
    private val stringConverter: StringConverter,
    private val wordCheckable: WordCheckable,
    private val gameStateCheckable: GameStateCheckable
) : ViewModel(), Interact {
    
    private val attemptNumber: Int = 1
    
    private val _gameStateFlow = MutableStateFlow<GameState>(GameState.Start)
    val gameStateFlow = _gameStateFlow.asStateFlow()
    
    override fun checkWord(letters: List<Letter>) {
        val wordToCheck = stringConverter.convertLetterToCharList(letters)
        wordCheckable.checkWord(wordToCheck).let {
            _gameStateFlow.value = gameStateCheckable.checkGameState(it, attemptNumber)
        }
    }
}

interface Interact {
    
    fun checkWord(letters: List<Letter>)
}