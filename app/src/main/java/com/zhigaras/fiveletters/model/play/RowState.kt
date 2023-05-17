package com.zhigaras.fiveletters.model.play

import com.squareup.moshi.JsonClass

abstract class RowState {
    
    abstract val row: List<LetterState>
    abstract val isRowOpened: Boolean
    
    fun <T : LetterState> select(clazz: Class<T>): String {
        return row.filterIsInstance(clazz).map { it.char }.joinToString(", ")
    }
    
    @JsonClass(generateAdapter = true)
    data class Empty(override val row: List<LetterState>) : RowState() {
        override val isRowOpened: Boolean = false
    }
    
    @JsonClass(generateAdapter = true)
    data class UncheckedWord(override val row: List<LetterState>) : RowState() {
        override val isRowOpened: Boolean = false
    }
    
    @JsonClass(generateAdapter = true)
    data class InvalidWord(override val row: List<LetterState>) : RowState() {
        override val isRowOpened: Boolean = false
    }
    
    @JsonClass(generateAdapter = true)
    data class NotFullRow(override val row: List<LetterState>) : RowState() {
        override val isRowOpened: Boolean = false
    }
    
    @JsonClass(generateAdapter = true)
    data class Wrong(override val row: List<LetterState>) : RowState() {
        override val isRowOpened: Boolean = true
    }
    
    @JsonClass(generateAdapter = true)
    data class Right(override val row: List<LetterState>) : RowState() {
        override val isRowOpened: Boolean = true
    }
}