package com.zhigaras.fiveletters.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersModule {
    
    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
    
    class Base : DispatchersModule {
        override fun io(): CoroutineDispatcher = Dispatchers.IO
        override fun ui(): CoroutineDispatcher = Dispatchers.Main
    }
}