package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhigaras.fiveletters.core.DispatchersModule
import com.zhigaras.fiveletters.data.UserStatRepository
import com.zhigaras.fiveletters.model.UserStat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuViewModel(
    private val userStatRepository: UserStatRepository,
    private val dispatchers: DispatchersModule
) : ViewModel(), MenuScreenInteract {
    
    private val _userStatFlow = MutableStateFlow(UserStat(0, 0f))
    val userStatFlow = _userStatFlow.asStateFlow()
    
    init {
        viewModelScope.launch(dispatchers.io()) {
            val dictionarySize = userStatRepository.getDictionarySize()
            userStatRepository.getSolvedWordsCount().collect {
                if (it != 0) _userStatFlow.value = UserStat(
                    wins = it,
                    winRate = it.toFloat() / dictionarySize
                )
            }
        }
    }
}

interface MenuScreenInteract {


}