package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.presentation.LetterItem
import com.zhigaras.fiveletters.presentation.LetterType
import org.junit.Assert.*
import org.junit.Test

class StringConverterTest {
    
    @Test
    fun `test convert letters to char list`() {
        val charList = listOf('q', 'w', 'e', 'r', 't')
        val letterList = charList.map { LetterItem.Default(LetterType.Card, it) }
        val actual = StringConverter.Base().convertLetterToCharList(letterList)
        assertEquals(charList, actual)
    }
}