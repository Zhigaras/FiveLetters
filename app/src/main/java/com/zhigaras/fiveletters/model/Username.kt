package com.zhigaras.fiveletters.model

abstract class Username {
    
    class NotLoadedYet(): Username()
    
    abstract class Loaded(): Username() {
        
        abstract val name: String
        
        class Specified(override val name: String) : Loaded()
        
        class Unspecified() : Loaded() {
            override val name = "unspecified"
        }
        
        class NeedNameRequest() : Loaded() {
            override val name = ""
        }
    }
}