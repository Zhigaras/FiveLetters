package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.data.MainRepository
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.LetterType
import com.zhigaras.fiveletters.model.RowState

interface WordCheckable {
    
    suspend fun checkWord(word: List<Char>, origin: String): RowState
    
    fun checkDuplicates(word: List<LetterState>, origin: String): List<LetterState>
    
    fun checkRowState(row: List<LetterState>): RowState
    
    suspend fun isWordValid(word: List<LetterState>): Boolean
    
    class Base(private val mainRepository: MainRepository) : WordCheckable {
        
        override suspend fun checkWord(word: List<Char>, origin: String): RowState {
            if (mainRepository.isWordValid(word.map { it.lowercaseChar() }.joinToString(""))) {
                val originCharList = origin.toList()
                var result = emptyList<LetterState>().toMutableList()
                originCharList.zip(word.map { it }) { o, w ->
                    if (w == o) {
                        result.add(LetterState.Exact(LetterType.Card(), w))
                    } else if (originCharList.contains(w)) {
                        result.add(LetterState.Right(LetterType.Card(), w))
                    } else {
                        result.add(LetterState.Wrong(LetterType.Card(), w))
                    }
                }
                result = checkDuplicates(result, origin)
                return checkRowState(result.toList())
            }
            return RowState.InvalidWord(word.map { LetterState.InvalidWord(it) })
        }
        
        override fun checkDuplicates(
            word: List<LetterState>,
            origin: String
        ): MutableList<LetterState> {
            val notExactIndexes = emptyList<Int>().toMutableList()
            word.forEachIndexed { index, letter ->
                if (letter !is LetterState.Exact) notExactIndexes.add(index)
            }
            val notExactOriginChars =
                origin.filterIndexed { index, _ -> notExactIndexes.contains(index) }
            val updatedWord = emptyList<LetterState>().toMutableList()
            word.forEach {
                if (it is LetterState.Right && !notExactOriginChars.contains(it.char)) {
                    updatedWord.add(LetterState.Wrong(LetterType.Card(), it.char))
                } else {
                    updatedWord.add(it)
                }
            }
            return updatedWord
        }
        
        override fun checkRowState(row: List<LetterState>): RowState {
            if (row.all { it is LetterState.Exact }) {
                return RowState.Right(row)
            }
            return RowState.Wrong(row)
        }
        
        override suspend fun isWordValid(word: List<LetterState>): Boolean {
            return mainRepository.isWordValid(word.map { it.char }.joinToString(""))
        }
    }
}