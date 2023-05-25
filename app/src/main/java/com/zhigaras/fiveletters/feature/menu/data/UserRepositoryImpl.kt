package com.zhigaras.fiveletters.feature.menu.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.zhigaras.fiveletters.feature.menu.domain.UserRepository

class UserRepositoryImpl(private val auth: FirebaseAuth) : UserRepository {
    
    override suspend fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}