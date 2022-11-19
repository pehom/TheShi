package com.pehom.theshi.domain.model


data class User(
    var fsId: FsId,
    var authId: String,
    var email: String,
    var phoneNumber: String,
    val funds: Funds
) {
    var name = ""

   /* fun mapToUserRoomItem(): UserRoomItem{
        return UserRoomItem(
            fsId.value,
            authId,
            name,
            phoneNumber,
            email,

        )
    }*/
}
