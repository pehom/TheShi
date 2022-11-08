package com.pehom.theshi.data.localdata.approomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pehom.theshi.utils.Constants

@Entity(tableName = "mentors_table")
data class MentorRoomItem(

    @PrimaryKey(autoGenerate = false)
    var mentorFsID: String = "mentorFsID",

    @ColumnInfo(name = Constants.NAME)
    var name: String = "mentorName"
)