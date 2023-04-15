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

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
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
                wordDao.insert(Word(i, "testWord $i", false))
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
    fun getWordWithOffset1_test() = runBlocking {
        val actual = wordDao.getWordWithOffset(1)
        assertEquals(actual.id, 2)
    }
    
    @Test
    fun getWordWithOffset3_test() = runBlocking {
        val actual = wordDao.getWordWithOffset(3)
        assertEquals(actual.id, 4)
    }
    
    @Test
    fun isWordExist1_test() = runBlocking {
        assert(wordDao.isWordExist("testWord 2"))
    }
    
    @Test
    fun isWordExist2_test() = runBlocking {
        assert(!wordDao.isWordExist("testWord 10"))
    }
}