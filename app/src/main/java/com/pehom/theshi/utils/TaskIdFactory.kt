package com.pehom.theshi.utils

object TaskIdFactory {
    private var id = 0
    fun createId(): String {
        return ""+id++
    }
}