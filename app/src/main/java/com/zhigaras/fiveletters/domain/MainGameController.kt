package com.zhigaras.fiveletters.domain

interface MainGameController {
    
    
    
    class Base(
        private val keyboardStateController: KeyboardStateController,
        private val letterFieldController: LetterFieldController
    ): MainGameController {
    
    
    
    }
    
}