package com.zhigaras.fiveletters.core.presentation

import android.content.Context
import androidx.annotation.StringRes

interface UiText {
    
    fun asString(context: Context): String
    
    class Dynamic(private val value: String) : UiText {
        override fun asString(context: Context) = value
    }
    
    class Resource(
        @StringRes private val resId: Int,
        private vararg val args: Any
    ) : UiText {
        override fun asString(context: Context): String {
            return context.getString(resId, *args)
        }
    }
    
    object Empty: UiText {
        override fun asString(context: Context): String {
            return ""
        }
    }
}