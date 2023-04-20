package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.Constants
import com.zhigaras.fiveletters.model.*

interface GameStateController {
    
    fun inputLetter(char: Char): GameState
    
    fun removeLetter(): GameState
    
    suspend fun checkGameState(saveAttempts: suspend (Word) -> Unit): GameState
    
    fun getConfirmedRow(): RowState
    
    fun newGame(origin: Word): GameState
    
    class Base(
        private val wordCheckable: WordCheckable
    ) : GameStateController {
        
        private lateinit var gameState: GameState
        private var cursorRow = 0
        private var cursorColumn = 0
        private lateinit var origin: Word
        
        override fun inputLetter(char: Char): GameState {
            if (cursorColumn != Constants.MAX_COLUMN) {
                val snapshot = gameState.result.toMutableList()
                val currentRow = snapshot[cursorRow].row.toMutableList()
                currentRow[cursorColumn] = LetterState.UserClicked(char)
                val rowState =
                    if (cursorColumn == Constants.MAX_COLUMN - 1)
                        RowState.UncheckedWord(currentRow.toList())
                    else
                        RowState.NotFullRow(currentRow.toList())
                snapshot[cursorRow] = rowState
                cursorColumn++
                gameState = GameState.Progress(snapshot.toList(), origin.word)
            }
            return gameState
        }
        
        override fun removeLetter(): GameState {
            if (cursorColumn != 0) {
                cursorColumn--
                val snapshot = gameState.result.toMutableList()
                val currentRow: MutableList<LetterState> =
                    snapshot[cursorRow].row.map { LetterState.UserClicked(it.char) }.toMutableList()
                currentRow[cursorColumn] =
                    LetterState.Default(
                        type = LetterType.Card(),
                        char = ' ',
                        action = Action.REMOVE
                    )
                snapshot[cursorRow] = RowState.NotFullRow(currentRow.toList())
                gameState = GameState.Progress(snapshot.toList(), origin.word)
            }
            return gameState
        }
        
        override suspend fun checkGameState(saveAttempts: suspend (Word) -> Unit): GameState {
            val snapshot = gameState.result.toMutableList()
            snapshot[cursorRow] =
                wordCheckable.checkWord(
                    snapshot[cursorRow].row.map { it.char },
                    origin.word.uppercase()
                )
            gameState = GameState.Progress(snapshot.toList(), origin.word)
            if (cursorRow == Constants.MAX_ROWS - 1)
                gameState = GameState.Failed(snapshot.toList(), origin.word)
            if (snapshot.any { it is RowState.Right }) {
                gameState = GameState.Win(snapshot.toList(), origin.word)
                saveAttempts(origin.copy(solvedByUser = true, attempts = cursorRow + 1))
            }
            if (snapshot[cursorRow] is RowState.InvalidWord)
                gameState = GameState.InvalidWord(snapshot.toList(), origin.word)
            if (gameState !is GameState.InvalidWord) {
                cursorRow++
                cursorColumn = 0
            }
            return gameState
        }
        
        override fun getConfirmedRow(): RowState = gameState.result.last { it.isRowOpened }
        
        override fun newGame(origin: Word): GameState {
            this.origin = origin
            gameState = GameState.Start(origin.word)
            cursorRow = 0
            cursorColumn = 0
            return gameState
        }
    }
}