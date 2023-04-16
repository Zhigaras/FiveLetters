package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.UserStatRepository
import com.zhigaras.fiveletters.model.UserStat
import kotlinx.coroutines.flow.*

class MenuViewModel(
    private val userStatRepository: UserStatRepository,
    private val dispatchers: DispatchersModule
) : ViewModel(), MenuScreenInteract {
    
    override fun userStatFlow(): Flow<UserStat> = flow {
        val stat = userStatRepository.getUserStatFlow()
        emitAll(stat)
    }.flowOn(dispatchers.io())
    
    
}

interface MenuScreenInteract {
    
    fun userStatFlow(): Flow<UserStat>
}