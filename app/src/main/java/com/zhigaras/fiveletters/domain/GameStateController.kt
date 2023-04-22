package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.Constants
import com.zhigaras.fiveletters.data.Alphabet
import com.zhigaras.fiveletters.model.*

interface GameStateController {
    
    fun inputLetter(gameState: GameState, char: Char): GameState
    
    fun removeLetter(gameState: GameState): GameState
    
    suspend fun checkGameState(
        gameState: GameState,
        saveAttempts: suspend (Word) -> Unit
    ): GameState
    
    fun getConfirmedRow(gameState: GameState): RowState
    
    fun newGame(origin: Word): GameState
    
    class Base(
        private val wordCheckable: WordCheckable
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
                    LetterState.Default(
                        type = LetterType.Card(),
                        char = ' ',
                        action = Action.REMOVE
                    )
                snapshot[gameState.rowCursor] = RowState.NotFullRow(currentRow.toList())
                val lettersField = LetterFieldState.Progress(snapshot.toList())
                return gameState.copy(letterFieldState = lettersField, columnCursor = columnCursor)
            }
            return gameState
        }
        
        override suspend fun checkGameState(
            gameState: GameState,
            saveAttempts: suspend (Word) -> Unit
        ): GameState {
            val snapshot = gameState.letterFieldState.result.toMutableList()
            snapshot[gameState.rowCursor] =
                wordCheckable.checkWord(
                    snapshot[gameState.rowCursor].row.map { it.char },
                    gameState.origin.word.uppercase()
                )
            if (snapshot[gameState.rowCursor] is RowState.InvalidWord)
                return gameState.copy(letterFieldState = LetterFieldState.InvalidWord(snapshot.toList()))
            
            if (snapshot.any { it is RowState.Right }) {
                saveAttempts(
                    gameState.origin.copy(solvedByUser = true, attempts = gameState.rowCursor + 1)
                )
                return gameState.copy(letterFieldState = LetterFieldState.Win(snapshot.toList()))
            }
            if (gameState.isRowLast)
                return gameState.copy(letterFieldState = LetterFieldState.Failed(snapshot.toList()))
            
            return gameState.copy(
                letterFieldState = LetterFieldState.Progress(snapshot.toList()),
                rowCursor = gameState.rowCursor + 1,
                columnCursor = 0
            )
        }
        
        override fun getConfirmedRow(gameState: GameState): RowState =
            gameState.letterFieldState.result.last { it.isRowOpened }
        
        override fun newGame(origin: Word): GameState {
            return GameState(
                letterFieldState = LetterFieldState.Start(),
                keyboard = Keyboard.Base(Alphabet.Base.Ru()),
                columnCursor = 0,
                rowCursor = 0,
                origin = origin
            )
        }
    }
}