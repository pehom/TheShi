package com.pehom.theshi.data.localdata.approomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pehom.theshi.utils.Constants

@Entity(tableName = "students_table")
data class StudentRoomItem(

    @PrimaryKey(autoGenerate = false)
    var fsId: String,

    @ColumnInfo(name = Constants.NAME)
    var name: String,

    @ColumnInfo(name = Constants.MENTOR_FS_ID)
    var mentorFsID : String

)