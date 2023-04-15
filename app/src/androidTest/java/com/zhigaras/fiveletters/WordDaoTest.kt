package com.zhigaras.fiveletters

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zhigaras.fiveletters.core.DatabaseModule
import com.zhigaras.fiveletters.data.WordDao
import com.zhigaras.fiveletters.data.WordDatabase
import com.zhigaras.fiveletters.model.Word
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class WordDaoTest {
    
    private lateinit var database: WordDatabase
    private lateinit var wordDao: WordDao
    private val testDatabaseSize = 5
    
    @Before
    fun setupDatabase() {
        database = DatabaseModule.Mock(ApplicationProvider.getApplicationContext())
            .provideDatabase()
        wordDao = database.getWordDao()
        runBlocking {
            for (i in 1..testDatabaseSize) {
                wordDao.insert(Word(i, "test$i", false))
            }
        }
    }
    
    @After
    fun closeDatabase() {
        database.close()
    }
    
    @Test
    fun getDictionarySize_test() = runBlocking {
        assertEquals(wordDao.getDictionarySize(), testDatabaseSize)
    }
    
    @Test
    fun isWordExist1_test() = runBlocking {
        assert(wordDao.isWordExist("test2"))
    }
    
    @Test
    fun isWordExist2_test() = runBlocking {
        assert(!wordDao.isWordExist("testWord 10"))
    }
    
    @Test
    fun update_test1() = runBlocking {
        wordDao.update(Word(1, "test1", true))
        val actual = wordDao.findWord("test1")
        assertEquals(actual.solvedByUser, true)
    }
    
    @Test
    fun getSolvedWordsCount_test1() = runBlocking {
        for (i in 1..3) {
            wordDao.update(Word(i, "test$i", true))
        }
        assertEquals(wordDao.getSolvedWordsCount(), 3)
    }
}