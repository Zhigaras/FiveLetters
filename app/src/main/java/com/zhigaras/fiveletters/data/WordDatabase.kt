package com.zhigaras.fiveletters.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhigaras.fiveletters.model.Word

@Database(
    entities = [Word::class],
    version = 1,
    exportSchema = false
)
abstract class WordDatabase : RoomDatabase() {
    
    abstract fun getWordDao(): WordDao
    
    companion object {
        const val DATABASE_NAME = "word_database"
    }
}