package com.pehom.theshi.domain.model

import androidx.compose.runtime.mutableStateOf

class Funds {
    val amount = mutableStateOf(0)

    fun setAmount(_amount: Int) {
        amount.value = _amount
    }

    fun spend(_amount: Int): Boolean {
        if (amount.value >= _amount) {
            amount.value -= _amount
            return true
        } else return false
    }

    fun deposit(_amount: Int) {
        amount.value += _amount
    }

   /* fun amount(): Int {
        return amount.value
    }*/

    override fun toString(): String {
        return "" + amount.value
    }
}