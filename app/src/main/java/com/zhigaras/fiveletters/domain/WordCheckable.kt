package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.data.MainRepository
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.LetterType
import com.zhigaras.fiveletters.model.RowState

interface WordCheckable {
    
    suspend fun checkWord(word: List<LetterState>, origin: String): RowState
    
    fun checkRowState(row: List<LetterState>): RowState
    
    suspend fun isWordValid(word: List<LetterState>): Boolean
    
    class Base(private val mainRepository: MainRepository) : WordCheckable {
        
        override suspend fun checkWord(word: List<LetterState>, origin: String): RowState {
            if (mainRepository.isWordValid(word.map { it.char.lowercaseChar() }.joinToString(""))) {
                val originCharList = origin.toList()
                val uppercaseChars = word.map { it.char.uppercaseChar() }
                val result = emptyList<LetterState>().toMutableList()
                originCharList.zip(uppercaseChars) { o, w ->
                    if (w == o) {
                        result.add(LetterState.Exact(LetterType.Card, w))
                    } else if (originCharList.contains(w)) {
                        result.add(LetterState.Right(LetterType.Card, w))
                    } else {
                        result.add(LetterState.Wrong(LetterType.Card, w))
                    }
                }
                return checkRowState(result)
            }
            return RowState.Append.FullRow.InvalidWord(word.map { LetterState.InvalidWord(it.char) })
        }
        
        override fun checkRowState(row: List<LetterState>): RowState {
            if (row.all { it is LetterState.Exact }) {
                return RowState.Opened.Right(row)
            }
            return RowState.Opened.Wrong(row)
        }
        
        override suspend fun isWordValid(word: List<LetterState>): Boolean {
            return mainRepository.isWordValid(word.map { it.char }.joinToString(""))
        }
    }
}