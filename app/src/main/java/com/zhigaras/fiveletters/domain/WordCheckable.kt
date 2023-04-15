package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.data.MainRepository
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.LetterType
import com.zhigaras.fiveletters.model.RowState

interface WordCheckable {
    
    suspend fun checkWord(word: List<Char>, origin: String): RowState
    
    fun checkRowState(row: List<LetterState>): RowState
    
    suspend fun isWordValid(word: List<LetterState>): Boolean
    
    class Base(private val mainRepository: MainRepository) : WordCheckable {
        
        override suspend fun checkWord(word: List<Char>, origin: String): RowState {
            if (mainRepository.isWordValid(word.map { it.lowercaseChar() }.joinToString(""))) {
                val originCharList = origin.toList()
                val result = emptyList<LetterState>().toMutableList()
                originCharList.zip(word.map { it }) { o, w ->
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
            return RowState.Append.FullRow.InvalidWord(word.map { LetterState.InvalidWord(it) })
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