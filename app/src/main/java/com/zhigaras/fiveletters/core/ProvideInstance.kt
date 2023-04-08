package com.zhigaras.fiveletters.core

import android.content.Context

interface ProvideInstance {
    
    fun provideDatabaseModule(): DatabaseModule
    
    class Release(private val context: Context) : ProvideInstance {
        
        override fun provideDatabaseModule(): DatabaseModule = DatabaseModule.Base(context)
        
    }
    
    class Mock(private val context: Context) : ProvideInstance {
        
        override fun provideDatabaseModule(): DatabaseModule = DatabaseModule.Mock(context)
        
    }
}