package com.zhigaras.fiveletters.feature.menu.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ServerValue
import com.kpstv.firebase.ValueEventResponse
import com.kpstv.firebase.extensions.valueEventFlow
import com.zhigaras.fiveletters.feature.menu.data.model.UserStatDto
import com.zhigaras.fiveletters.feature.menu.domain.UserStatRepository
import kotlinx.coroutines.flow.Flow
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
    
    override suspend fun getUserStatFlow(): Flow<ValueEventResponse> {
        return statReference.valueEventFlow()
    }
    
    override suspend fun tempPutUserStat() {
        statReference.setValue(UserStatDto()).await()
    }
    
    override suspend fun incrementGamesCount() {//todo may be synchronously
        incrementField(UserStatDto::gamesPlayed.name)
    }
    
    override suspend fun incrementWinsCount() { //todo may be synchronously
        incrementField(UserStatDto::wins.name)
    }
    
    private fun incrementField(field: String) {
        val update: MutableMap<String, Any> = hashMapOf(field to ServerValue.increment(1))
        statReference.updateChildren(update)
    }
    
    override suspend fun incrementAttempt(lineNumber: Int) {
        val update: MutableMap<String, Any> =
            hashMapOf(lineNumber.toString() to ServerValue.increment(1))
        statReference.child(UserStatDto::attempts.name).updateChildren(update)
    }
    
    companion object {
        const val USERS_PATH = "users"
    }
}