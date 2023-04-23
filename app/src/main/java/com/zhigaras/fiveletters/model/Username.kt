package com.zhigaras.fiveletters.model

abstract class Username {
    
    abstract val name: String
    
    class NotLoadedYet : Username() {
        override val name = ""
    }
    
    abstract class Loaded : Username() {
        
        class Specified(override val name: String) : Loaded()
        
        class Unspecified : Loaded() {
            override val name = "unspecified"
        }
        
        class NeedNameRequest : Loaded() {
            override val name = ""
        }
    }
}