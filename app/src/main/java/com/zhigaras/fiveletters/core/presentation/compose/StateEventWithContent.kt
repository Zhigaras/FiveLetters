package com.zhigaras.fiveletters.core.presentation.compose

sealed interface StateEventWithContent<T>

class StateEventWithContentTriggered<T>(val content: T) : StateEventWithContent<T> {
    override fun toString(): String = "triggered($content)"
}

class StateEventWithContentConsumed<T> : StateEventWithContent<T> {
    override fun toString(): String = "consumed"
}

fun <T> triggered(content: T) = StateEventWithContentTriggered(content)

fun <T> consumed() = StateEventWithContentConsumed<T>()