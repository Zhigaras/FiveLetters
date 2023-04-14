package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.Constants
import com.zhigaras.fiveletters.model.*

interface GameStateController {
    
    fun inputLetter(char: Char): GameState
    
    fun removeLetter(): GameState
    
    suspend fun checkGameState(origin: String): GameState
    
    fun getConfirmedRow(): RowState
    
    fun newGame(): GameState
    
    class Base(
        private val wordCheckable: WordCheckable
    ) : GameStateController {
        
        private lateinit var gameState: GameState
        private var cursorRow = 0
        private var cursorColumn = 0
        
        override fun inputLetter(char: Char): GameState {
            if (cursorColumn != Constants.MAX_COLUMN) {
                val snapshot = gameState.result.toMutableList()
                val currentRow = snapshot[cursorRow].row.toMutableList()
                currentRow[cursorColumn] = LetterState.UserClicked(char)
                val rowState =
                    if (cursorColumn == Constants.MAX_COLUMN - 1)
                        RowState.Append.FullRow.UncheckedWord(currentRow.toList())
                    else
                        RowState.Append.NotFullRow(currentRow.toList())
                snapshot[cursorRow] = rowState
                cursorColumn++
                gameState = GameState.InProgress.Progress(snapshot.toList())
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
                    LetterState.Default(type = LetterType.Card, char = ' ', action = Action.REMOVE)
                snapshot[cursorRow] = RowState.Remove(currentRow.toList())
                gameState = GameState.InProgress.Progress(snapshot.toList())
            }
            return gameState
        }
        
        override suspend fun checkGameState(origin: String): GameState {
            val snapshot = gameState.result.toMutableList()
            snapshot[cursorRow] = wordCheckable.checkWord(snapshot[cursorRow].row, origin)
            gameState = GameState.InProgress.Progress(snapshot.toList())
            if (cursorRow == Constants.MAX_ROWS - 1)
                gameState = GameState.Ended.Failed(snapshot.toList())
            if (snapshot.any { it is RowState.Opened.Right })
                gameState = GameState.Ended.Win(snapshot.toList())
            if (snapshot[cursorRow] is RowState.Append.FullRow.InvalidWord)
                gameState = GameState.InProgress.InvalidWord(snapshot.toList())
            if (gameState !is GameState.InProgress.InvalidWord) {
                cursorRow++
                cursorColumn = 0
            }
            return gameState
        }
        
        override fun getConfirmedRow(): RowState = gameState.result.last { it is RowState.Opened }
        
        override fun newGame(): GameState {
            gameState = GameState.InProgress.Start()
            cursorRow = 0
            cursorColumn = 0
            return gameState
        }
    }
}