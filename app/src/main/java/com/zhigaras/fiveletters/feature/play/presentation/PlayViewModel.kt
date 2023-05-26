package com.zhigaras.fiveletters.feature.play.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.presentation.BaseViewModel
import com.zhigaras.fiveletters.di.DispatchersModule
import com.zhigaras.fiveletters.feature.play.domain.model.GameState
import com.zhigaras.fiveletters.feature.play.domain.usecases.gamelogic.GameStateController
import com.zhigaras.fiveletters.feature.play.domain.usecases.SaveStateUseCase
import com.zhigaras.fiveletters.feature.play.domain.usecases.UpdateUserStateUseCase
import com.zhigaras.fiveletters.feature.play.domain.usecases.WordsUseCase
import kotlinx.coroutines.launch

class PlayViewModel(
    private val gameStateController: GameStateController,
    private val updateUserStateUseCase: UpdateUserStateUseCase,
    private val wordsUseCase: WordsUseCase,
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
                    checkWord = { wordsUseCase.isWordValid(it) },
                    incrementAttempts = { line -> updateUserStateUseCase.incrementAttempt(line) },
                    incrementGamesCounter = { updateUserStateUseCase.incrementGamesCount() },
                    incrementWinsCount = { updateUserStateUseCase.incrementWinsCount() }
                )
        }
    }
    
    fun startNewGame() {
        viewModelScope.launch(dispatchers.io()) {
            val origin = wordsUseCase.getNewWord() // todo replace with new repo
            Log.d("AAA word", origin)
            state = gameStateController.newGame(origin)
        }
    }
    
    fun saveState() {
        viewModelScope.launch(dispatchers.io()) {
            saveStateUseCase.saveState(state)
        }
    }
    
    private fun restoreState() {
        viewModelScope.launch(dispatchers.io()) {
            val restoredState = saveStateUseCase.restoreState()
            if (restoredState != null) state = restoredState
            else startNewGame()
        }
    }
}