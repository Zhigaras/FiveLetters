package com.zhigaras.fiveletters

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zhigaras.fiveletters.core.DatabaseModule
import com.zhigaras.fiveletters.data.WordDao
import com.zhigaras.fiveletters.data.WordDatabase
import com.zhigaras.fiveletters.model.Word
import kotlinx.coroutines.flow.first
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
    }
    
    @After
    fun closeDatabase() {
        database.close()
    }
    
    @Test
    fun getDictionarySize_test() = runBlocking {
        for (i in 1..testDatabaseSize) {
            wordDao.insert(Word(i, "test$i", false, 0, ""))
        }
        assertEquals(testDatabaseSize, wordDao.getDictionarySize())
    }
    
    @Test
    fun isWordExist1_test() = runBlocking {
        for (i in 1..testDatabaseSize) {
            wordDao.insert(Word(i, "test$i", false, 0,""))
        }
        assert(wordDao.isWordExist("test2"))
    }
    
    @Test
    fun isWordExist2_test() = runBlocking {
        for (i in 1..testDatabaseSize) {
            wordDao.insert(Word(i, "test$i", false, 0,""))
        }
        assert(!wordDao.isWordExist("testWord 10"))
    }
    
    @Test
    fun update_test1() = runBlocking {
        for (i in 1..testDatabaseSize) {
            wordDao.insert(Word(i, "test$i", false, 0,""))
        }
        wordDao.update(Word(1, "test1", true, 1,""))
        val actual = wordDao.findWord("test1")
        assertEquals(actual.solvedByUser, true)
    }
    
    @Test
    fun getSolvedWordsCount_test1() = runBlocking {
        for (i in 1..testDatabaseSize) {
            wordDao.insert(Word(i, "test$i", false, 0, ""))
            wordDao.insert(Word(i + testDatabaseSize, "test${i + testDatabaseSize}", true, 3, ""))
        }
        assertEquals(testDatabaseSize, wordDao.getSolvedWordsCount().first())
    }
    
    @Test
    fun getAverageAttempts_test1() = runBlocking {
        for (i in 1..testDatabaseSize) {
            wordDao.insert(Word(i, "test$i", false, 0, ""))
            wordDao.insert(Word(i + testDatabaseSize, "test${i + testDatabaseSize}", true, 3, ""))
        }
        val actual = wordDao.getAverageAttempt().first()
        val expected = 3f
        assertEquals(expected, actual)
    }
    
    @Test
    fun getAverageAttempts_test2() = runBlocking {
        for (i in 1..testDatabaseSize) {
            wordDao.insert(Word(i, "test$i", false, 0, ""))
            wordDao.insert(Word(i + testDatabaseSize, "test${i + testDatabaseSize}", true, i, ""))
        }
        val actual = wordDao.getAverageAttempt().first()
        val expected = 3f
        assertEquals(expected, actual)
    }
    
    @Test
    fun getAverageAttempts_test3() = runBlocking {
        for (i in 1..testDatabaseSize) {
            wordDao.insert(Word(i, "test$i", false, 0, ""))
        }
        wordDao.insert(Word(6, "test6", true, 2, ""))
        wordDao.insert(Word(7, "test7", true, 5, ""))
        wordDao.insert(Word(8, "test8", true, 4, ""))
        wordDao.insert(Word(9, "test9", true, 5, ""))
        wordDao.insert(Word(10, "test10", true, 3, ""))
        
        val actual = wordDao.getAverageAttempt().first()
        val expected = 3.8f
        assertEquals(expected, actual)
    }
    
    @Test
    fun getAverageAttempts_test4() = runBlocking {
        for (i in 1..testDatabaseSize) {
            wordDao.insert(Word(i, "test$i", false, 0, ""))
        }
        val actual = wordDao.getAverageAttempt().first()
        val expected = null
        assertEquals(expected, actual)
    }
}