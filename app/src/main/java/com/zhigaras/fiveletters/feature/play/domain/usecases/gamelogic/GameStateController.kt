package com.zhigaras.fiveletters.feature.play.domain.usecases.gamelogic

import com.zhigaras.fiveletters.core.Constants
import com.zhigaras.fiveletters.feature.play.domain.model.GameState
import com.zhigaras.fiveletters.feature.play.domain.model.LetterFieldState
import com.zhigaras.fiveletters.feature.play.domain.model.ProgressState
import com.zhigaras.fiveletters.feature.play.domain.model.RowState

interface GameStateController {
    
    fun inputLetter(gameState: GameState, char: Char): GameState
    
    fun removeLetter(gameState: GameState): GameState
    
    suspend fun confirmWord(
        gameState: GameState,
        checkWord: suspend (String) -> Boolean,
        incrementAttempts: suspend (Int) -> Unit,
        incrementGamesCounter: suspend () -> Unit,
        incrementWinsCount: suspend () -> Unit
    ): GameState
    
    fun newGame(origin: String = ""): GameState
    
    class Base(
        private val rowStateController: RowStateController.Overall,
        private val keyboardStateController: KeyboardStateController,
    ) : GameStateController {
        
        override fun inputLetter(gameState: GameState, char: Char): GameState {
            if (gameState.columnCursor != Constants.MAX_COLUMN) {
                val snapshot = gameState.letterFieldState.result.toMutableList()
                val currentRow = snapshot[gameState.rowCursor]
                snapshot[gameState.rowCursor] =
                    rowStateController.inputLetter(gameState, char, currentRow)
                return gameState.copy(
                    letterFieldState = LetterFieldState.Progress(snapshot.toList()),
                    columnCursor = gameState.columnCursor + 1
                )
            }
            return gameState
        }
        
        override fun removeLetter(gameState: GameState): GameState {
            if (gameState.columnCursor != 0) {
                val columnCursor = gameState.columnCursor - 1
                val snapshot = gameState.letterFieldState.result.toMutableList()
                val currentRow = snapshot[gameState.rowCursor]
                snapshot[gameState.rowCursor] =
                    rowStateController.removeLetter(gameState, currentRow, columnCursor)
                val lettersField = LetterFieldState.Progress(snapshot.toList())
                return gameState.copy(letterFieldState = lettersField, columnCursor = columnCursor)
            }
            return gameState
        }
        
        override suspend fun confirmWord(
            gameState: GameState,
            checkWord: suspend (String) -> Boolean,
            incrementAttempts: suspend (Int) -> Unit,
            incrementGamesCounter: suspend () -> Unit,
            incrementWinsCount: suspend () -> Unit
        ): GameState {
            val snapshot = gameState.letterFieldState.result.toMutableList()
            val currentRow = snapshot[gameState.rowCursor]
            val isWordValid = checkWord(currentRow.row.map { it.char.lowercaseChar() }
                .joinToString(""))
            snapshot[gameState.rowCursor] =
                rowStateController.confirmWord(isWordValid, gameState.origin, currentRow)
            if (snapshot[gameState.rowCursor] is RowState.InvalidWord)
                return gameState.copy(letterFieldState = LetterFieldState.InvalidWord(snapshot.toList()))
            
            var letterField: LetterFieldState = LetterFieldState.Progress(snapshot.toList())
            
            if (gameState.isRowLast) {
                letterField = LetterFieldState.Failed(snapshot.toList())
            }
            
            if (snapshot.any { it is RowState.Right }) { //todo вместо any проверять currentRow??
                letterField = LetterFieldState.Win(snapshot.toList())
                incrementAttempts(gameState.rowCursor) //todo объединить increment attempts and wins??
                incrementWinsCount()
            }
            
            if (letterField.progressState == ProgressState.ENDED) incrementGamesCounter()
            
            val keyboard =
                keyboardStateController.updateKeyboard(
                    keyboard = gameState.keyboard,
                    openedLetters = letterField.result.last { it.isRowOpened }.row
                )
            
            return gameState.copy(
                letterFieldState = letterField,
                rowCursor = gameState.rowCursor + 1,
                columnCursor = 0,
                keyboard = keyboard
            )
        }
        
        override fun newGame(origin: String): GameState {
            return GameState(
                letterFieldState = LetterFieldState.Start(),
                keyboard = keyboardStateController.getDefaultKeyboard(),
                columnCursor = 0,
                rowCursor = 0,
                origin = origin
            )
        }
    }
}