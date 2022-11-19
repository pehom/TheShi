package com.pehom.theshi.data.localdata.approomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.Student
import com.pehom.theshi.utils.Constants

@Entity(tableName = "students_table")
data class StudentRoomItem(

    @PrimaryKey(autoGenerate = false)
    var fsId: String,

    @ColumnInfo(name = Constants.PHONE_NUMBER)
    var phoneNumber: String = "phoneNumber",

    @ColumnInfo(name = Constants.NAME)
    var name: String = "name",

    @ColumnInfo(name = Constants.MENTOR_FS_ID)
    var mentorFsId : String = "mentorFsId",

    @ColumnInfo(name = Constants.STATUS)
    var studentStatus: String = "studentStatus"

) {
    fun mapToStudent(): Student{
        return Student(FsId(fsId), name, phoneNumber )
    }
}