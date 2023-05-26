package com.zhigaras.fiveletters.feature.menu.domain.model

import org.junit.Assert.*
import org.junit.Test

class UserStatTest {
    
    @Test
    fun averageAttempts1() {
        val userStat = UserStat.Test(listOf(0, 0, 0, 0, 0, 0))
        assertEquals(0f, userStat.averageAttempts)
    }
    
    @Test
    fun averageAttempts2() {
        val userStat = UserStat.Test(listOf(1, 0, 0, 0, 0, 0))
        assertEquals(1f, userStat.averageAttempts)
    }
    
    @Test
    fun averageAttempts3() {
        val userStat = UserStat.Test(listOf(0, 0, 0, 0, 0, 1))
        assertEquals(6f, userStat.averageAttempts)
    }
    
    @Test
    fun averageAttempts4() {
        val userStat = UserStat.Test(listOf(0, 0, 0, 0, 0, 6))
        assertEquals(6f, userStat.averageAttempts)
    }
    
    @Test
    fun averageAttempts5() {
        val userStat = UserStat.Test(listOf(10, 0, 0, 0, 0, 0))
        assertEquals(1f, userStat.averageAttempts)
    }
    
    @Test
    fun averageAttempts6() {
        val userStat = UserStat.Test(listOf(0, 0, 4, 0, 0, 0))
        assertEquals(3f, userStat.averageAttempts)
    }
    
    @Test
    fun averageAttempts7() {
        val userStat = UserStat.Test(listOf(0, 10, 0, 0, 0, 10))
        assertEquals(4f, userStat.averageAttempts)
    }
    
    @Test
    fun averageAttempts8() {
        val userStat = UserStat.Test(listOf(10, 10, 10, 10, 10, 10))
        assertEquals(3.5f, userStat.averageAttempts)
    }
    
    @Test
    fun averageAttempts10() {
        val userStat = UserStat.Test(listOf(0, 0, 5, 0, 5, 0))
        assertEquals(4f, userStat.averageAttempts)
    }
}