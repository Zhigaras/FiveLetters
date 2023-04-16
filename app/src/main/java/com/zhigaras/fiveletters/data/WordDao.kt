package com.zhigaras.fiveletters.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zhigaras.fiveletters.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    
    @Query("SELECT COUNT(*) FROM words_ru")
    suspend fun getDictionarySize(): Int
    
    @Query("SELECT COUNT(*) FROM words_ru WHERE solvedByUser = 1")
    fun getSolvedWordsCount(): Flow<Int>
    
    @Query("SELECT * FROM words_ru WHERE solvedByUser = 0 ORDER BY RANDOM() LIMIT 1")
    suspend fun getUnsolvedWord(): Word
    
    @Query("SELECT EXISTS(SELECT * FROM WORDS_RU WHERE word = :word)")
    suspend fun isWordExist(word: String): Boolean
    
    @Query("SELECT AVG(attempts) FROM words_ru WHERE solvedByUser = 1")
    fun getAverageAttempt(): Flow<Float?>
    
    @Update
    suspend fun update(word: Word)
    
    @Insert
    suspend fun insert(word: Word)
    
    @Query("SELECT * FROM WORDS_RU WHERE word = :word")
    suspend fun findWord(word: String): Word
}