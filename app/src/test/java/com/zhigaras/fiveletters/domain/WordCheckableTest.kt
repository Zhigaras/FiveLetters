package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.presentation.LetterItem
import com.zhigaras.fiveletters.presentation.LetterType
import org.junit.Assert.*
import org.junit.Test

class WordCheckableTest {
    
    @Test
    fun `test word checking 1`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "asert".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwert")
        val expected = listOf(
            LetterItem.Wrong(LetterType.Card, 'A'),
            LetterItem.Wrong(LetterType.Card, 'S'),
            LetterItem.Exact(LetterType.Card, 'E'),
            LetterItem.Exact(LetterType.Card, 'R'),
            LetterItem.Exact(LetterType.Card, 'T'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 2`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "zxcvb".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwert")
        val expected = listOf(
            LetterItem.Wrong(LetterType.Card, 'Z'),
            LetterItem.Wrong(LetterType.Card, 'X'),
            LetterItem.Wrong(LetterType.Card, 'C'),
            LetterItem.Wrong(LetterType.Card, 'V'),
            LetterItem.Wrong(LetterType.Card, 'B'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 3`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "trewq".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwert")
        val expected = listOf(
            LetterItem.Right(LetterType.Card, 'T'),
            LetterItem.Right(LetterType.Card, 'R'),
            LetterItem.Exact(LetterType.Card, 'E'),
            LetterItem.Right(LetterType.Card, 'W'),
            LetterItem.Right(LetterType.Card, 'Q'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 4`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "wertq".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwert")
        val expected = listOf(
            LetterItem.Right(LetterType.Card, 'W'),
            LetterItem.Right(LetterType.Card, 'E'),
            LetterItem.Right(LetterType.Card, 'R'),
            LetterItem.Right(LetterType.Card, 'T'),
            LetterItem.Right(LetterType.Card, 'Q'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 5`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "qqwtt".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwert")
        val expected = listOf(
            LetterItem.Exact(LetterType.Card, 'Q'),
            LetterItem.Right(LetterType.Card, 'Q'),
            LetterItem.Right(LetterType.Card, 'W'),
            LetterItem.Right(LetterType.Card, 'T'),
            LetterItem.Exact(LetterType.Card, 'T'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 6`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "qqwtq".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwqwq")
        val expected = listOf(
            LetterItem.Exact(LetterType.Card, 'Q'),
            LetterItem.Right(LetterType.Card, 'Q'),
            LetterItem.Right(LetterType.Card, 'W'),
            LetterItem.Wrong(LetterType.Card, 'T'),
            LetterItem.Exact(LetterType.Card, 'Q'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 7`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "qwdqc".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qqqqq")
        val expected = listOf(
            LetterItem.Exact(LetterType.Card, 'Q'),
            LetterItem.Wrong(LetterType.Card, 'W'),
            LetterItem.Wrong(LetterType.Card, 'D'),
            LetterItem.Exact(LetterType.Card, 'Q'),
            LetterItem.Wrong(LetterType.Card, 'C'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 8`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "олово".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "слово")
        val expected = listOf(
            LetterItem.Right(LetterType.Card, 'О'),
            LetterItem.Exact(LetterType.Card, 'Л'),
            LetterItem.Exact(LetterType.Card, 'О'),
            LetterItem.Exact(LetterType.Card, 'В'),
            LetterItem.Exact(LetterType.Card, 'О'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 9`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "оплот".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "слово")
        val expected = listOf(
            LetterItem.Right(LetterType.Card, 'О'),
            LetterItem.Wrong(LetterType.Card, 'П'),
            LetterItem.Right(LetterType.Card, 'Л'),
            LetterItem.Right(LetterType.Card, 'О'),
            LetterItem.Wrong(LetterType.Card, 'Т'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 10`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "окова".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "слово")
        val expected = listOf(
            LetterItem.Right(LetterType.Card, 'О'),
            LetterItem.Wrong(LetterType.Card, 'К'),
            LetterItem.Exact(LetterType.Card, 'О'),
            LetterItem.Exact(LetterType.Card, 'В'),
            LetterItem.Wrong(LetterType.Card, 'А'),
        )
        assertEquals(expected, actual)
    }
    
}