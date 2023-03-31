package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.presentation.Letter
import com.zhigaras.fiveletters.presentation.LetterType
import org.junit.Assert.*
import org.junit.Test

class WordCheckableTest {
    
    @Test
    fun `test word checking 1`() {
        val wordCheckable = WordCheckable.Base("qwert")
        val wordToCheck = "asert".toList()
        val actual = wordCheckable.checkWord(wordToCheck)
        val expected = listOf(
            Letter.Wrong(LetterType.Card, 'A'),
            Letter.Wrong(LetterType.Card, 'S'),
            Letter.Exact(LetterType.Card, 'E'),
            Letter.Exact(LetterType.Card, 'R'),
            Letter.Exact(LetterType.Card, 'T'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 2`() {
        val wordCheckable = WordCheckable.Base("qwert")
        val wordToCheck = "zxcvb".toList()
        val actual = wordCheckable.checkWord(wordToCheck)
        val expected = listOf(
            Letter.Wrong(LetterType.Card, 'Z'),
            Letter.Wrong(LetterType.Card, 'X'),
            Letter.Wrong(LetterType.Card, 'C'),
            Letter.Wrong(LetterType.Card, 'V'),
            Letter.Wrong(LetterType.Card, 'B'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 3`() {
        val wordCheckable = WordCheckable.Base("qwert")
        val wordToCheck = "trewq".toList()
        val actual = wordCheckable.checkWord(wordToCheck)
        val expected = listOf(
            Letter.Right(LetterType.Card, 'T'),
            Letter.Right(LetterType.Card, 'R'),
            Letter.Exact(LetterType.Card, 'E'),
            Letter.Right(LetterType.Card, 'W'),
            Letter.Right(LetterType.Card, 'Q'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 4`() {
        val wordCheckable = WordCheckable.Base("qwert")
        val wordToCheck = "wertq".toList()
        val actual = wordCheckable.checkWord(wordToCheck)
        val expected = listOf(
            Letter.Right(LetterType.Card, 'W'),
            Letter.Right(LetterType.Card, 'E'),
            Letter.Right(LetterType.Card, 'R'),
            Letter.Right(LetterType.Card, 'T'),
            Letter.Right(LetterType.Card, 'Q'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 5`() {
        val wordCheckable = WordCheckable.Base("qwert")
        val wordToCheck = "qqwtt".toList()
        val actual = wordCheckable.checkWord(wordToCheck)
        val expected = listOf(
            Letter.Exact(LetterType.Card, 'Q'),
            Letter.Right(LetterType.Card, 'Q'),
            Letter.Right(LetterType.Card, 'W'),
            Letter.Right(LetterType.Card, 'T'),
            Letter.Exact(LetterType.Card, 'T'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 6`() {
        val wordCheckable = WordCheckable.Base("qwqwq")
        val wordToCheck = "qqwtq".toList()
        val actual = wordCheckable.checkWord(wordToCheck)
        val expected = listOf(
            Letter.Exact(LetterType.Card, 'Q'),
            Letter.Right(LetterType.Card, 'Q'),
            Letter.Right(LetterType.Card, 'W'),
            Letter.Wrong(LetterType.Card, 'T'),
            Letter.Exact(LetterType.Card, 'Q'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 7`() {
        val wordCheckable = WordCheckable.Base("qqqqq")
        val wordToCheck = "qwdqc".toList()
        val actual = wordCheckable.checkWord(wordToCheck)
        val expected = listOf(
            Letter.Exact(LetterType.Card, 'Q'),
            Letter.Wrong(LetterType.Card, 'W'),
            Letter.Wrong(LetterType.Card, 'D'),
            Letter.Exact(LetterType.Card, 'Q'),
            Letter.Wrong(LetterType.Card, 'C'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 8`() {
        val wordCheckable = WordCheckable.Base("слово")
        val wordToCheck = "олово".toList()
        val actual = wordCheckable.checkWord(wordToCheck)
        val expected = listOf(
            Letter.Right(LetterType.Card, 'О'),
            Letter.Exact(LetterType.Card, 'Л'),
            Letter.Exact(LetterType.Card, 'О'),
            Letter.Exact(LetterType.Card, 'В'),
            Letter.Exact(LetterType.Card, 'О'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 9`() {
        val wordCheckable = WordCheckable.Base("слово")
        val wordToCheck = "оплот".toList()
        val actual = wordCheckable.checkWord(wordToCheck)
        val expected = listOf(
            Letter.Right(LetterType.Card, 'О'),
            Letter.Wrong(LetterType.Card, 'П'),
            Letter.Right(LetterType.Card, 'Л'),
            Letter.Right(LetterType.Card, 'О'),
            Letter.Wrong(LetterType.Card, 'Т'),
        )
        assertEquals(expected, actual)
    }
    
    @Test
    fun `test word checking 10`() {
        val wordCheckable = WordCheckable.Base("слово")
        val wordToCheck = "окова".toList()
        val actual = wordCheckable.checkWord(wordToCheck)
        val expected = listOf(
            Letter.Right(LetterType.Card, 'О'),
            Letter.Wrong(LetterType.Card, 'К'),
            Letter.Exact(LetterType.Card, 'О'),
            Letter.Exact(LetterType.Card, 'В'),
            Letter.Wrong(LetterType.Card, 'А'),
        )
        assertEquals(expected, actual)
    }
    
}