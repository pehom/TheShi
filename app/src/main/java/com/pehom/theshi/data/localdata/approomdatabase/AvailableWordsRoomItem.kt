package com.pehom.theshi.data.localdata.approomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pehom.theshi.utils.Constants

@Entity(tableName = "available_words_table")
data class AvailableWordsRoomItem(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = Constants.VOCABULARY_FS_DOC_REF_PATH)
    var vcbDocRefPath: String = "vcbDocRefPath",

    @ColumnInfo(name = Constants.ORIG)
    var orig: String = "orig",

    @ColumnInfo(name = Constants.TRANS)
    var trans: String = "trans",

    @ColumnInfo(name = Constants.IMG_URL)
    var imgUrl: String = "imgUrl",

    @ColumnInfo(name = Constants.USER_FS_ID)
    var userFsID: String = "userFsId"
)
