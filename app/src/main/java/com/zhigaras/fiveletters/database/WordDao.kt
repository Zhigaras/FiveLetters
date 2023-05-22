package com.zhigaras.fiveletters.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zhigaras.fiveletters.feature.play.domain.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao : MainDao, UserStatDao, TestDao

interface MainDao {
    
    @Query("SELECT * FROM words_ru WHERE solvedByUser = 0 ORDER BY RANDOM() LIMIT 1")
    suspend fun getUnsolvedWord(): Word
    
    @Query("SELECT EXISTS(SELECT * FROM WORDS_RU WHERE word = :word)")
    suspend fun isWordExist(word: String): Boolean
    
    @Update
    suspend fun update(word: Word)
    
}

interface UserStatDao {
    
    @Query("SELECT COUNT(*) FROM words_ru")
    suspend fun getDictionarySize(): Int
    
    @Query("SELECT COUNT(*) FROM words_ru WHERE solvedByUser = 1")
    fun getSolvedWordsCount(): Flow<Int>
    
    @Query("SELECT AVG(attempts) FROM words_ru WHERE solvedByUser = 1")
    fun getAverageAttempt(): Flow<Float?>
    
}

interface TestDao {
    
    @Insert
    suspend fun insert(word: Word)
    
    @Query("SELECT * FROM WORDS_RU WHERE word = :word")
    suspend fun findWord(word: String): Word
}