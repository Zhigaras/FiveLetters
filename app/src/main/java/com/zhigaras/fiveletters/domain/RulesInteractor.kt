package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.RowState
import com.zhigaras.fiveletters.model.Word

interface RulesInteractor {
    
    fun getRulesRows(): List<RowState>
    
    class Base(
        private val rowStateController: RowStateController.Confirm
    ) : RulesInteractor {
        
        private val rulesOrigin = Word(1, "песня", false, 0)
        
        private val rulesWords = listOf("блеск", "пенал", "песня")
        
        private val rulesRowsList: List<RowState> = rulesWords.map { word ->
            RowState.UncheckedWord(word.map { LetterState.UserClicked(it.uppercaseChar()) })
        }
        
        override fun getRulesRows(): List<RowState> {
            return rulesRowsList.map {
                rowStateController.confirmWord(true, rulesOrigin, it)
            }
        }
    }
}