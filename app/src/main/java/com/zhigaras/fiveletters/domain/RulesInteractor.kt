package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.Constants
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.LetterType
import com.zhigaras.fiveletters.model.RowState
import com.zhigaras.fiveletters.model.Word

interface RulesInteractor {
    
    fun getInitialRulesRow(): RowState
    
    suspend fun getRulesRow(index: Int): RowState
    
    class Base(
        private val rowStateController: RowStateController.Confirm
    ) : RulesInteractor {
        
        private val rulesOrigin = Word(1, "песня", false, 0)
        
        private val rulesWords = listOf("блеск", "пенал", "песня")
        
        private val rulesRowsList = rulesWords.map { word ->
            RowState.UncheckedWord(word.map { LetterState.UserClicked(it) })
        }
        
        override suspend fun getRulesRow(index: Int): RowState {
            return rowStateController.confirmWord(rulesOrigin, rulesRowsList[index])
        }
    
        override fun getInitialRulesRow(): RowState {
            return RowState.Empty(List(Constants.MAX_COLUMN) {
                LetterState.Empty(type = LetterType.Card())
            })
        }
    
    
    }
}