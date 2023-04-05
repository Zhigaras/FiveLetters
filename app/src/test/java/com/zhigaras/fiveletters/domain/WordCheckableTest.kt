package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.LetterType
import org.junit.Assert.*
import org.junit.Test

class WordCheckableTest {
    
    @Test
    fun `test word checking 1`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "asert".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwert")
        val expected = listOf(
            LetterState.Wrong(LetterType.Card, 'A'),
            LetterState.Wrong(LetterType.Card, 'S'),
            LetterState.Exact(LetterType.Card, 'E'),
            LetterState.Exact(LetterType.Card, 'R'),
            LetterState.Exact(LetterType.Card, 'T'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 2`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "zxcvb".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwert")
        val expected = listOf(
            LetterState.Wrong(LetterType.Card, 'Z'),
            LetterState.Wrong(LetterType.Card, 'X'),
            LetterState.Wrong(LetterType.Card, 'C'),
            LetterState.Wrong(LetterType.Card, 'V'),
            LetterState.Wrong(LetterType.Card, 'B'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 3`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "trewq".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwert")
        val expected = listOf(
            LetterState.Right(LetterType.Card, 'T'),
            LetterState.Right(LetterType.Card, 'R'),
            LetterState.Exact(LetterType.Card, 'E'),
            LetterState.Right(LetterType.Card, 'W'),
            LetterState.Right(LetterType.Card, 'Q'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 4`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "wertq".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwert")
        val expected = listOf(
            LetterState.Right(LetterType.Card, 'W'),
            LetterState.Right(LetterType.Card, 'E'),
            LetterState.Right(LetterType.Card, 'R'),
            LetterState.Right(LetterType.Card, 'T'),
            LetterState.Right(LetterType.Card, 'Q'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 5`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "qqwtt".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwert")
        val expected = listOf(
            LetterState.Exact(LetterType.Card, 'Q'),
            LetterState.Right(LetterType.Card, 'Q'),
            LetterState.Right(LetterType.Card, 'W'),
            LetterState.Right(LetterType.Card, 'T'),
            LetterState.Exact(LetterType.Card, 'T'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 6`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "qqwtq".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qwqwq")
        val expected = listOf(
            LetterState.Exact(LetterType.Card, 'Q'),
            LetterState.Right(LetterType.Card, 'Q'),
            LetterState.Right(LetterType.Card, 'W'),
            LetterState.Wrong(LetterType.Card, 'T'),
            LetterState.Exact(LetterType.Card, 'Q'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 7`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "qwdqc".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "qqqqq")
        val expected = listOf(
            LetterState.Exact(LetterType.Card, 'Q'),
            LetterState.Wrong(LetterType.Card, 'W'),
            LetterState.Wrong(LetterType.Card, 'D'),
            LetterState.Exact(LetterType.Card, 'Q'),
            LetterState.Wrong(LetterType.Card, 'C'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 8`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "олово".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "слово")
        val expected = listOf(
            LetterState.Right(LetterType.Card, 'О'),
            LetterState.Exact(LetterType.Card, 'Л'),
            LetterState.Exact(LetterType.Card, 'О'),
            LetterState.Exact(LetterType.Card, 'В'),
            LetterState.Exact(LetterType.Card, 'О'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 9`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "оплот".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "слово")
        val expected = listOf(
            LetterState.Right(LetterType.Card, 'О'),
            LetterState.Wrong(LetterType.Card, 'П'),
            LetterState.Right(LetterType.Card, 'Л'),
            LetterState.Right(LetterType.Card, 'О'),
            LetterState.Wrong(LetterType.Card, 'Т'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 10`() {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "окова".toList()
        val actual = wordCheckable.checkWord(wordToCheck, "слово")
        val expected = listOf(
            LetterState.Right(LetterType.Card, 'О'),
            LetterState.Wrong(LetterType.Card, 'К'),
            LetterState.Exact(LetterType.Card, 'О'),
            LetterState.Exact(LetterType.Card, 'В'),
            LetterState.Wrong(LetterType.Card, 'А'),
        )
        assertEquals(expected, actual)
    }
    
}