package com.zhigaras.fiveletters.data

import androidx.room.Dao
import androidx.room.Query
import com.zhigaras.fiveletters.model.Word

@Dao
interface WordDao {
    
    @Query("SELECT COUNT(*) FROM words_ru")
    suspend fun getDictionarySize(): Int
    
    @Query("SELECT * FROM words_ru LIMIT 1 OFFSET :num")
    suspend fun getRandomWord(num: Int): Word

}