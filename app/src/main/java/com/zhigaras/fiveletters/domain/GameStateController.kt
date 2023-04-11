package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.Constants
import com.zhigaras.fiveletters.model.*

interface GameStateController {
    
    fun getStartGameState(): GameState
    
    fun inputLetter(char: Char): GameState
    
    fun removeLetter(): GameState
    
    fun checkGameState(origin: String): GameState
    
    fun checkRowState(row: List<LetterState>): RowState
    
    fun getConfirmedRow(): RowState
    
    class Base(
        private val stringConverter: StringConverter,
        private val wordCheckable: WordCheckable
    ) : GameStateController {
        
        private var gameState: GameState = GameState.InProgress.Start()
        private var cursorRow = 0
        private var cursorColumn = 0
        
        override fun getStartGameState() = GameState.InProgress.Start()
        
        override fun inputLetter(char: Char): GameState {
            if (cursorColumn != Constants.MAX_COLUMN) {
                val snapshot = gameState.result.toMutableList()
                val currentRow = snapshot[cursorRow].row.toMutableList()
                currentRow[cursorColumn] = LetterState.UserClicked(LetterType.Card, char)
                val rowState =
                    if (cursorColumn == Constants.MAX_COLUMN - 1)
                        RowState.Append.FullRow(currentRow.toList())
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
                val currentRow = snapshot[cursorRow].row.toMutableList()
                currentRow[cursorColumn] =
                    LetterState.Default(type = LetterType.Card, char = ' ', action = Action.REMOVE)
                snapshot[cursorRow] = RowState.Remove(currentRow.toList())
                gameState = GameState.InProgress.Progress(snapshot.toList())
            }
            return gameState
        }
        
        override fun checkGameState(origin: String): GameState {
            val snapshot = gameState.result.toMutableList()
            val wordToCheck = stringConverter.convertLetterToCharList(snapshot[cursorRow].row)
            val checkedWord = wordCheckable.checkWord(wordToCheck, origin)
            snapshot[cursorRow] = checkRowState(checkedWord)
            gameState = GameState.InProgress.Progress(snapshot.toList())
            if (snapshot.any { it is RowState.Opened.Right })
                gameState = GameState.Ended.Win(snapshot.toList())
            if (cursorRow == Constants.MAX_ROWS - 1)
                gameState = GameState.Ended.GameOver(snapshot.toList())
            cursorRow++
            cursorColumn = 0
            return gameState
        }
        
        override fun checkRowState(row: List<LetterState>): RowState {
            if (row.all { it is LetterState.Exact }) {
                return RowState.Opened.Right(row)
            }
            return RowState.Opened.Wrong(row)
        }
        
        override fun getConfirmedRow(): RowState = gameState.result.last { it is RowState.Opened }
    }
}