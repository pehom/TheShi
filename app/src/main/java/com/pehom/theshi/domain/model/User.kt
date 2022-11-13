package com.pehom.theshi.domain.model

data class User(
    var fsId: FsId,
    var authId: String,
    var email: String,
    var phoneNumber: String,
    val funds: Funds
) {
    var name = ""
}