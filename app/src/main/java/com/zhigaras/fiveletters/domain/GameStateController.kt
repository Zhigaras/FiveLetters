package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.Constants
import com.zhigaras.fiveletters.model.*

interface GameStateController {
    
    fun inputLetter(gameState: GameState, char: Char): GameState
    
    fun removeLetter(gameState: GameState): GameState
    
    suspend fun confirmWord(
        gameState: GameState,
        saveAttempts: suspend (Word) -> Unit,
        incrementGamesCounter: suspend () -> Unit
    ): GameState
    
    fun newGame(origin: Word): GameState
    
    class Base(
        private val wordCheckable: WordCheckable,
        private val keyboardStateController: KeyboardStateController
    ) : GameStateController {
        
        override fun inputLetter(gameState: GameState, char: Char): GameState {
            if (gameState.columnCursor != Constants.MAX_COLUMN) {
                val snapshot = gameState.letterFieldState.result.toMutableList()
                val currentRow = snapshot[gameState.rowCursor].row.toMutableList()
                currentRow[gameState.columnCursor] = LetterState.UserClicked(char)
                val rowState =
                    if (gameState.isColumnLast)
                        RowState.UncheckedWord(currentRow.toList())
                    else
                        RowState.NotFullRow(currentRow.toList())
                snapshot[gameState.rowCursor] = rowState
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
                val currentRow: MutableList<LetterState> =
                    snapshot[gameState.rowCursor].row.map { LetterState.UserClicked(it.char) }
                        .toMutableList()
                currentRow[columnCursor] =
                    LetterState.Empty(type = LetterType.Card())
                snapshot[gameState.rowCursor] = RowState.NotFullRow(currentRow.toList())
                val lettersField = LetterFieldState.Progress(snapshot.toList())
                return gameState.copy(letterFieldState = lettersField, columnCursor = columnCursor)
            }
            return gameState
        }
        
        override suspend fun confirmWord(
            gameState: GameState,
            saveAttempts: suspend (Word) -> Unit,
            incrementGamesCounter: suspend () -> Unit
        ): GameState {
            val snapshot = gameState.letterFieldState.result.toMutableList()
            snapshot[gameState.rowCursor] =
                wordCheckable.checkWord(
                    snapshot[gameState.rowCursor].row.map { it.char },
                    gameState.origin.word.uppercase()
                )
            if (snapshot[gameState.rowCursor] is RowState.InvalidWord)
                return gameState.copy(letterFieldState = LetterFieldState.InvalidWord(snapshot.toList()))
            
            var letterField: LetterFieldState = LetterFieldState.Progress(snapshot.toList())
            
            if (gameState.isRowLast) {
                letterField = LetterFieldState.Failed(snapshot.toList())
            }
            
            if (snapshot.any { it is RowState.Right }) {
                saveAttempts(
                    gameState.origin.copy(solvedByUser = true, attempts = gameState.rowCursor + 1)
                )
                letterField = LetterFieldState.Win(snapshot.toList())
            }
            
            if (!letterField.inProgress) incrementGamesCounter()
            
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
        
        override fun newGame(origin: Word): GameState {
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