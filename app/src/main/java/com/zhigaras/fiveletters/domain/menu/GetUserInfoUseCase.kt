package com.zhigaras.fiveletters.domain.menu

import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.UiText
import com.zhigaras.fiveletters.data.AuthRepository
import com.zhigaras.fiveletters.model.menu.MenuUser

interface GetUserInfoUseCase {
    
    fun getUser(): MenuUser
    
    class Base(private val authRepository: AuthRepository) : GetUserInfoUseCase {
        
        override fun getUser(): MenuUser {
            val currentUser = authRepository.getCurrentUser()
            return MenuUser(
                name = currentUser?.displayName?.let { UiText.Dynamic(it) }
                    ?: UiText.Resource(R.string.unnamed),
                email = currentUser?.email ?: "a;kgjadfk" //TODO delete hardcode
            )
        }
    }
}