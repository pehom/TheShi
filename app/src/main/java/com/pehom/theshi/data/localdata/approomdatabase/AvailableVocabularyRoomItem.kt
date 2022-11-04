package com.pehom.theshi.data.localdata.approomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pehom.theshi.utils.Constants

@Entity(tableName = "vocabularies_table")
data class AvailableVocabularyRoomItem(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = Constants.VOCABULARY_FS_DOC_REF_PATH)
    var vcbDocRefPath: String = "vcbDocRefPath",

    @ColumnInfo(name = Constants.TIMESTAMP)
    var vcbTimeStamp : String = "vcbTimeStamp",

    @ColumnInfo(name = Constants.VOCABULARY_TITLE)
    var vcbTitle: String = "vcbTitle",

    @ColumnInfo(name = Constants.PRICE)
    var price: Int = 0,

    @ColumnInfo(name  = Constants.USER_FS_ID)
    var userFsId: String = "userFsId"
)