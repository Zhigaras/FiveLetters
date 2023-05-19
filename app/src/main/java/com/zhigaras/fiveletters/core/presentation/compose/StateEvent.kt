package com.zhigaras.fiveletters.core.presentation.compose

sealed interface StateEvent {
    
    object Triggered : StateEvent {
        override fun toString(): String = "triggered"
    }
    
    object Consumed : StateEvent {
        override fun toString(): String = "consumed"
    }
}

val triggered = StateEvent.Triggered

val consumed = StateEvent.Consumed