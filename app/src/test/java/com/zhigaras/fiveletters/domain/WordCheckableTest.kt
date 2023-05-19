package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.feature.play.domain.model.LetterState
import com.zhigaras.fiveletters.feature.play.domain.model.LetterType
import com.zhigaras.fiveletters.feature.play.domain.model.RowState
import com.zhigaras.fiveletters.feature.play.domain.usecases.WordCheckable
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class WordCheckableTest {

    @Test
    fun `test word checking 1`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "ASERT".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "QWERT")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Wrong(LetterType.Card(), 'A'),
                LetterState.Wrong(LetterType.Card(), 'S'),
                LetterState.Exact(LetterType.Card(), 'E'),
                LetterState.Exact(LetterType.Card(), 'R'),
                LetterState.Exact(LetterType.Card(), 'T'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 2`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "ZXCVB".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "QWERT")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Wrong(LetterType.Card(), 'Z'),
                LetterState.Wrong(LetterType.Card(), 'X'),
                LetterState.Wrong(LetterType.Card(), 'C'),
                LetterState.Wrong(LetterType.Card(), 'V'),
                LetterState.Wrong(LetterType.Card(), 'B'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 3`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "TREWQ".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "QWERT")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Right(LetterType.Card(), 'T'),
                LetterState.Right(LetterType.Card(), 'R'),
                LetterState.Exact(LetterType.Card(), 'E'),
                LetterState.Right(LetterType.Card(), 'W'),
                LetterState.Right(LetterType.Card(), 'Q'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 4`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "WERTQ".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "QWERT")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Right(LetterType.Card(), 'W'),
                LetterState.Right(LetterType.Card(), 'E'),
                LetterState.Right(LetterType.Card(), 'R'),
                LetterState.Right(LetterType.Card(), 'T'),
                LetterState.Right(LetterType.Card(), 'Q'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 5`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "QQWTT".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "QWERT")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Exact(LetterType.Card(), 'Q'),
                LetterState.Wrong(LetterType.Card(), 'Q'),
                LetterState.Right(LetterType.Card(), 'W'),
                LetterState.Wrong(LetterType.Card(), 'T'),
                LetterState.Exact(LetterType.Card(), 'T'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 6`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "QQWTQ".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "QWQWQ")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Exact(LetterType.Card(), 'Q'),
                LetterState.Right(LetterType.Card(), 'Q'),
                LetterState.Right(LetterType.Card(), 'W'),
                LetterState.Wrong(LetterType.Card(), 'T'),
                LetterState.Exact(LetterType.Card(), 'Q'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 7`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "QWDQC".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "QQQQQ")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Exact(LetterType.Card(), 'Q'),
                LetterState.Wrong(LetterType.Card(), 'W'),
                LetterState.Wrong(LetterType.Card(), 'D'),
                LetterState.Exact(LetterType.Card(), 'Q'),
                LetterState.Wrong(LetterType.Card(), 'C'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 8`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "ОЛОВО".toList()
        val actual = wordCheckable.checkWord(true,wordToCheck, "СЛОВО")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Wrong(LetterType.Card(), 'О'),
                LetterState.Exact(LetterType.Card(), 'Л'),
                LetterState.Exact(LetterType.Card(), 'О'),
                LetterState.Exact(LetterType.Card(), 'В'),
                LetterState.Exact(LetterType.Card(), 'О'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 9`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "ОПЛОТ".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "СЛОВО")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Right(LetterType.Card(), 'О'),
                LetterState.Wrong(LetterType.Card(), 'П'),
                LetterState.Right(LetterType.Card(), 'Л'),
                LetterState.Right(LetterType.Card(), 'О'),
                LetterState.Wrong(LetterType.Card(), 'Т'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 10`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "ОКОВА".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "СЛОВО")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Right(LetterType.Card(), 'О'),
                LetterState.Wrong(LetterType.Card(), 'К'),
                LetterState.Exact(LetterType.Card(), 'О'),
                LetterState.Exact(LetterType.Card(), 'В'),
                LetterState.Wrong(LetterType.Card(), 'А'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 11`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "АКОВА".toList()
        val actual = wordCheckable.checkWord(false, wordToCheck, "СЛОВО")
        val expected = RowState.InvalidWord(
            listOf(
                LetterState.InvalidWord('А'),
                LetterState.InvalidWord('К'),
                LetterState.InvalidWord('О'),
                LetterState.InvalidWord('В'),
                LetterState.InvalidWord('А'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 12`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "QQQQQ".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "QWERT")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Exact(LetterType.Card(), 'Q'),
                LetterState.Wrong(LetterType.Card(), 'Q'),
                LetterState.Wrong(LetterType.Card(), 'Q'),
                LetterState.Wrong(LetterType.Card(), 'Q'),
                LetterState.Wrong(LetterType.Card(), 'Q'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 13`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "СОПЛО".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "СЛОВО")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Exact(LetterType.Card(), 'С'),
                LetterState.Right(LetterType.Card(), 'О'),
                LetterState.Wrong(LetterType.Card(), 'П'),
                LetterState.Right(LetterType.Card(), 'Л'),
                LetterState.Exact(LetterType.Card(), 'О'),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test word checking 14`() = runBlocking {
        val wordCheckable = WordCheckable.Base()
        val wordToCheck = "СОСЛО".toList()
        val actual = wordCheckable.checkWord(true, wordToCheck, "СЛОВО")
        val expected = RowState.Wrong(
            listOf(
                LetterState.Exact(LetterType.Card(), 'С'),
                LetterState.Right(LetterType.Card(), 'О'),
                LetterState.Wrong(LetterType.Card(), 'С'),
                LetterState.Right(LetterType.Card(), 'Л'),
                LetterState.Exact(LetterType.Card(), 'О'),
            )
        )
        assertEquals(expected, actual)
    }
}