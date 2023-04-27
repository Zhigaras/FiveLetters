package com.zhigaras.fiveletters.model

abstract class RowState {
    
    abstract val row: List<LetterState>
    abstract val isRowOpened: Boolean
    
    fun <T: LetterState>select(clazz: Class<T>): String {
        return row.filterIsInstance(clazz).map { it.char }.joinToString(", ")
    }
    
    data class Empty(override val row: List<LetterState>) : RowState() {
        override val isRowOpened: Boolean = false
    }
    data class UncheckedWord(override val row: List<LetterState>) : RowState(){
        override val isRowOpened: Boolean = false
    }
    
    data class InvalidWord(override val row: List<LetterState>) : RowState(){
        override val isRowOpened: Boolean = false
    }
    
    data class NotFullRow(override val row: List<LetterState>) : RowState(){
        override val isRowOpened: Boolean = false
    }
    
    data class Wrong(override val row: List<LetterState>) : RowState(){
        override val isRowOpened: Boolean = true
    }
    
    data class Right(override val row: List<LetterState>) : RowState(){
        override val isRowOpened: Boolean = true
    }
}