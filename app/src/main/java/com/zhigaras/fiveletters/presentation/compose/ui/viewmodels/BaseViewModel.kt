package com.zhigaras.fiveletters.presentation.compose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<T : Any>(initialState: T) : ViewModel() {
    
    private val _state = MutableStateFlow(initialState)
    
    protected var state: T
        get() = _state.value
        set(value) {
            _state.value = value
        }
    
    fun getState() = _state.asStateFlow()
    
    protected inline fun scopeLaunch(
        context: CoroutineContext = EmptyCoroutineContext,
        crossinline onLoading: () -> Unit = {},
        crossinline onSuccess: () -> Unit = {},
        crossinline onError: (e: Exception) -> Unit = {},
        crossinline onFinally: () -> Unit = {},
        crossinline job: suspend () -> Unit,
    ): Job {
        return viewModelScope.launch(context) {
            try {
                onLoading()
                job()
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            } finally {
                onFinally()
            }
        }
    }
}