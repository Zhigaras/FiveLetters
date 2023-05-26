package com.zhigaras.fiveletters.feature.menu.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.zhigaras.fiveletters.feature.menu.data.model.UserStatDto
import com.zhigaras.fiveletters.feature.menu.domain.UserStatRepository
import com.zhigaras.fiveletters.feature.menu.domain.model.Achievement
import com.zhigaras.fiveletters.feature.menu.domain.model.Rank
import kotlinx.coroutines.tasks.await

class UserStatRepositoryImpl(
    database: DatabaseReference,
    auth: FirebaseAuth// todo provide currentUserId????
) : UserStatRepository {
    
    private val currentUserId = auth.currentUser!!.uid
    private val statReference = database.child(USERS_PATH).child(currentUserId)
    
    override suspend fun getUserStat(): UserStatDto? {
        return statReference
            .get()
            .await()
            .getValue(UserStatDto::class.java)
    }
    
    override suspend fun tempPutUserStat() {
        statReference.setValue(
            UserStatDto(
                gamesPlayed = 3,
                wins = 2,
                rank = Rank.BEGINNER,
                achievements = listOf(Achievement.FIRST_WIN, Achievement.SECOND_WIN),
                attempts = listOf(0, 0, 3, 4, 5, 6)
            )
        ).await()
    }
    
    override suspend fun incrementGamesCount() {
        incrementField(UserStatDto::gamesPlayed.name)
    }
    
    override suspend fun incrementWinsCount() {
        incrementField(UserStatDto::wins.name)
    }
    
    private suspend inline fun incrementField(field: String) {
        statReference.child(field).let {
            val currentValue = it.get().await().value as Long
            it.setValue(currentValue + 1)
        }
    }
    
    override suspend fun incrementAttempt(lineNumber: Int) {
        statReference.child(UserStatDto::attempts.name).let {
            val currentValue = it.get().await().value as MutableList<Int>
            val thisLineAttempts = currentValue[lineNumber]
            currentValue[lineNumber] = thisLineAttempts + 1
            it.setValue(currentValue)
        }
    }
    
    companion object {
        const val USERS_PATH = "users"
    }
}