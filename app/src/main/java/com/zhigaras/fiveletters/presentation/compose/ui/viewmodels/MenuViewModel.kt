package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.UserStatRepository
import com.zhigaras.fiveletters.domain.RulesInteractor
import com.zhigaras.fiveletters.model.UserStat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MenuViewModel(
    private val userStatRepository: UserStatRepository,
    private val dispatchers: DispatchersModule,
    private val rulesInteractor: RulesInteractor
) : ViewModel(), MenuInteract {
    
    private val _rulesRowFlow = MutableStateFlow(rulesInteractor.getInitialRulesRow())
    val rulesRowFlow = _rulesRowFlow.asStateFlow()
    
    override fun userStatFlow() = flow {
        val stat = userStatRepository.getUserStatFlow()
        emitAll(stat)
    }.flowOn(dispatchers.io())
    
    override fun getRulesRow(index: Int) {
        viewModelScope.launch(dispatchers.io()) {
            _rulesRowFlow.value = rulesInteractor.getRulesRow(index)
        }
    }
    
}

interface MenuInteract {
    
    fun userStatFlow(): Flow<UserStat>
    
    fun getRulesRow(index: Int)
}