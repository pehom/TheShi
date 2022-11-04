package com.pehom.theshi.presentation.screens.gamescreen

import androidx.compose.runtime.mutableStateOf

class PlayingCardModel(_cardNumber: Int) {
    var cardNumber: Int = _cardNumber
    val isClickable = mutableStateOf(true)
    val isVisible = mutableStateOf(false)
    var value: String = ""
    val correctCardBorder = mutableStateOf(false)

    constructor(_cardNumber: Int, _value: String) : this(_cardNumber) {
        this.value = _value
    }
}