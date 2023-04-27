package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
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

class MenuViewModel(
    private val userStatRepository: UserStatRepository,
    private val dispatchers: DispatchersModule,
    rulesInteractor: RulesInteractor
) : ViewModel(), MenuInteract {
    
    private val _rulesRowFlow = MutableStateFlow(rulesInteractor.getRulesRows())
    val rulesRowFlow = _rulesRowFlow.asStateFlow()
    
    override fun userStatFlow() = flow {
        val stat = userStatRepository.getUserStatFlow()
        emitAll(stat)
    }.flowOn(dispatchers.io())
    
}

interface MenuInteract {
    
    fun userStatFlow(): Flow<UserStat>
    
}