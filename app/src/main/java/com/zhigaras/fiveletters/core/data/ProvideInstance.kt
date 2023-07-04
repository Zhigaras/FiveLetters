package com.zhigaras.fiveletters.core.data

import android.content.Context
import com.zhigaras.fiveletters.database.DatabaseModule

interface ProvideInstance {
    
    fun provideDatabaseModule(): DatabaseModule
    
    class Release(private val context: Context) : ProvideInstance {
        
        override fun provideDatabaseModule(): DatabaseModule = DatabaseModule.Base(context)
        
    }
    
    class Mock(private val context: Context) : ProvideInstance {
        
        override fun provideDatabaseModule(): DatabaseModule = DatabaseModule.Mock(context)
        
    }
}