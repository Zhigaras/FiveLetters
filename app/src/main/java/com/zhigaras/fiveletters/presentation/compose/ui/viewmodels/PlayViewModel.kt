package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.zhigaras.fiveletters.data.Repository
import com.zhigaras.fiveletters.domain.GameStateCheckable
import com.zhigaras.fiveletters.domain.StringConverter
import com.zhigaras.fiveletters.domain.WordCheckable
import com.zhigaras.fiveletters.model.GameState
import com.zhigaras.fiveletters.presentation.LetterItem
import com.zhigaras.fiveletters.presentation.LetterType

class PlayViewModel(
    private val stringConverter: StringConverter,
    private val wordCheckable: WordCheckable,
    private val gameStateCheckable: GameStateCheckable,
    private val repository: Repository
) : ViewModel(), Interact {
    
    private var cursorRow: Int = 0
    private var cursorColumn = 0
    private var origin: String = ""
    
    private val _gameStateFlow = mutableStateListOf<GameState>().also { list ->
        repeat(MAX_ROWS) {
            list.add(GameState.Start())
        }
    }
    val gameStateFlow: List<GameState> = _gameStateFlow
    
    init {
        getNewOrigin()
    }
    
    override fun inputLetter(char: Char) {
        if (cursorColumn != MAX_COLUMN) {
            val tempList = _gameStateFlow[cursorRow].result.toMutableList()
            tempList[cursorColumn] = LetterItem.UserClicked(LetterType.Card, char)
            _gameStateFlow[cursorRow] = GameState.Progress(tempList)
            cursorColumn++
        }
    }
    
    override fun removeLetter() {
        if (cursorColumn != 0) {
            cursorColumn--
            val tempList = _gameStateFlow[cursorRow].result.toMutableList()
            tempList[cursorColumn] = LetterItem.Default(LetterType.Card, ' ')
            _gameStateFlow[cursorRow] = GameState.Progress(tempList)
        }
    }
    
    override fun confirmWord() {
        val wordToCheck = stringConverter.convertLetterToCharList(_gameStateFlow[cursorRow].result)
        wordCheckable.checkWord(wordToCheck, origin).let {
            _gameStateFlow[cursorRow] = gameStateCheckable.checkGameState(it, cursorRow)
        }
        cursorRow++
        cursorColumn = 0
    }
    
    override fun getNewOrigin() {
        origin = repository.randomWord()
    }
    
    companion object {
        private const val MAX_ROWS = 6
        private const val MAX_COLUMN = 5
    }
}

interface Interact {
    
    fun confirmWord()
    
    fun getNewOrigin()
    
    fun inputLetter(char: Char)
    
    fun removeLetter()
}