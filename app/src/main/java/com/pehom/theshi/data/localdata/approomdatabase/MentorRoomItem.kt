package com.pehom.theshi.data.localdata.approomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pehom.theshi.utils.Constants

@Entity(tableName = "mentors_table")
data class MentorRoomItem(

    @PrimaryKey(autoGenerate = false)
    var mentorFsID: String ,

    @ColumnInfo(name = Constants.NAME)
    var name: String = "mentorName",

    @ColumnInfo(name = Constants.PHONE_NUMBER)
    var phone: String = "phoneNumber",

    @ColumnInfo(name = Constants.STUDENT_FS_ID)
    var studentFsId: String = "studentFsId",

    @ColumnInfo(name = Constants.STATUS)
    var mentorStatus: String = "mentorStatus"
)