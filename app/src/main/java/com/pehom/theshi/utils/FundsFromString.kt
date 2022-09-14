package com.pehom.theshi.utils

import com.pehom.theshi.domain.model.Funds

fun fundsFromString(source: String): Funds {
    val result = Funds()
    if (source.toInt() >= 0) result.setAmount(source.toInt())
    return result
}