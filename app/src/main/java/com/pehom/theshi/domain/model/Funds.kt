package com.pehom.theshi.domain.model

import androidx.compose.runtime.mutableStateOf

class Funds {
    private val _amount = mutableStateOf(0)

    fun setAmount(amount: Int) {
        _amount.value = amount
    }

    fun spend(amount: Int): Boolean {
        if (_amount.value >= amount) {
            _amount.value -= amount
            return true
        } else return false
    }

    fun deposit(amount: Int) {
        _amount.value += amount
    }

    fun amount(): Int {
        return _amount.value
    }

    override fun toString(): String {
        return "" + _amount.value
    }
}