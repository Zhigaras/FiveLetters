package com.zhigaras.fiveletters.core

import android.content.Context
import androidx.room.Room
import com.zhigaras.fiveletters.data.WordDatabase

interface DatabaseModule {
    
    fun provideDatabase(): WordDatabase
    
    class Base(context: Context) : DatabaseModule {
        
        private val database by lazy {
            Room.databaseBuilder(
                context,
                WordDatabase::class.java,
                WordDatabase.DATABASE_NAME
            )
                .createFromAsset("databases/wordsRu.db")
                .build()
        }
        
        override fun provideDatabase(): WordDatabase = database
    }
    
    class Mock(context: Context) : DatabaseModule {
        
        private val database by lazy {
            Room.inMemoryDatabaseBuilder(
                context,
                WordDatabase::class.java
            ).build()
        }
        
        override fun provideDatabase(): WordDatabase = database
    }
}