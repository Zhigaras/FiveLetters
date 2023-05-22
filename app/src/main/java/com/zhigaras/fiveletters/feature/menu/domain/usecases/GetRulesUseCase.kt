package com.zhigaras.fiveletters.feature.menu.domain.usecases

import com.zhigaras.fiveletters.feature.menu.domain.RulesRepository
import com.zhigaras.fiveletters.feature.play.domain.model.RowState

interface GetRulesUseCase {
    
    fun getRulesRows(): List<RowState>
    
    class Base(private val rulesRepository: RulesRepository): GetRulesUseCase {
    
        override fun getRulesRows(): List<RowState> {
            return rulesRepository.getRulesRows()
        }
    }
    
}