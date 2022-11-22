package com.pehom.theshi.data.localdata.approomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.Funds
import com.pehom.theshi.domain.model.User
import com.pehom.theshi.utils.Constants

@Entity(tableName = "users_table")
data class UserRoomItem(
    @PrimaryKey(autoGenerate = false)
    var userFsId: String,

    @ColumnInfo(name = Constants.AUTH_ID)
    var authId: String,

    @ColumnInfo(name = Constants.NAME)
    var userName: String,

    @ColumnInfo(name = Constants.PHONE_NUMBER)
    var phoneNumber: String,

    @ColumnInfo(name = Constants.EMAIL)
    var email: String,

    @ColumnInfo(name = Constants.PASSWORD)
    var password: String,

    @ColumnInfo(name = Constants.FUNDS)
    var funds: Int,

    @ColumnInfo(name = Constants.LAST_TASK_ID_SFX)
    var lastTaskIdSfx : Int

) {
    fun mapToUser(): User{
        val user = User(
            FsId(userFsId),
            authId,
            email,
            phoneNumber,
            Funds(funds))
        user.lastIdSfx = lastTaskIdSfx
        user.name = userName

        return  user
    }
}