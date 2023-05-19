package com.zhigaras.fiveletters.feature.menu.domain

import com.zhigaras.fiveletters.feature.play.domain.model.RowState

interface RulesRepository {
    
    fun getRulesRows(): List<RowState>
    
}