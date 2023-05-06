package com.zhigaras.fiveletters.domain

import com.zhigaras.fiveletters.model.Alphabet
import com.zhigaras.fiveletters.model.LetterState
import com.zhigaras.fiveletters.model.LetterType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class KeyboardStateControllerTest {
    
    private lateinit var keyboardStateController: KeyboardStateController
    
    @Before
    fun before() {
        keyboardStateController = KeyboardStateController.Base(Alphabet.En())
    }
    
    @Test
    fun `needToUpdateTest different chars`() {
        val currentLetter = LetterState.Default(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Exact(LetterType.Card(), 'w')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), false)
    }
    
    @Test
    fun `needToUpdateTest default-wrong`() {
        val currentLetter = LetterState.Default(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Wrong(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), true)
    }
    
    @Test
    fun `needToUpdateTest default-right`() {
        val currentLetter = LetterState.Default(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Right(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), true)
    }
    
    @Test
    fun `needToUpdateTest default-exact`() {
        val currentLetter = LetterState.Default(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Exact(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), true)
    }
    
    @Test
    fun `needToUpdateTest wrong-wrong`() {
        val currentLetter = LetterState.Wrong(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Wrong(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), false)
    }
    
    @Test
    fun `needToUpdateTest wrong-right`() {
        val currentLetter = LetterState.Wrong(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Right(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), true)
    }
    
    @Test
    fun `needToUpdateTest wrong-exact`() {
        val currentLetter = LetterState.Wrong(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Exact(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), true)
    }
    
    @Test
    fun `needToUpdateTest right-wrong`() {
        val currentLetter = LetterState.Right(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Wrong(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), false)
    }
    
    @Test
    fun `needToUpdateTest right-right`() {
        val currentLetter = LetterState.Right(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Right(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), false)
    }
    
    @Test
    fun `needToUpdateTest right-exact`() {
        val currentLetter = LetterState.Right(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Exact(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), true)
    }
    
    @Test
    fun `needToUpdateTest exact-wrong`() {
        val currentLetter = LetterState.Exact(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Wrong(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), false)
    }
    
    
    @Test
    fun `needToUpdateTest exact-right`() {
        val currentLetter = LetterState.Exact(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Right(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), false)
    }
    
    @Test
    fun `needToUpdateTest exact-exact`() {
        val currentLetter = LetterState.Exact(LetterType.Key(), 'q')
        val incomingLetter = LetterState.Exact(LetterType.Card(), 'q')
        assertEquals(keyboardStateController.needToUpdateKey(currentLetter, incomingLetter), false)
    }
}