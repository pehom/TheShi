package com.pehom.theshi.data.localdata.approomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pehom.theshi.utils.Constants

@Entity (tableName = "wordbook_table")
data class WordbookRoomItem (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name= Constants.USER_FS_ID)
    var userFsId: String = "userFsId",

    @ColumnInfo(name = Constants.VOCABULARY_FS_DOC_REF_PATH)
    var vcbDocRefPath: String = "vcbDocRefPath",

    @ColumnInfo (name = Constants.VOCABULARY_TITLE)
    var vcbTitle: String = "vcbTitle"

   /* @ColumnInfo(name = Constants.VOCABULARY_TITLE)
    var vocabularyTitle: String,
    @ColumnInfo(name= Constants.VOCABULARY_FS_DOC_REF_PATH)
    var vcbDocRef: String,
    @ColumnInfo(name = Constants.TIMESTAMP)
    var vcbTimeStamp : String,*/
    /*@ColumnInfo(name = Constants.ORIG)
    var orig: String,
    @ColumnInfo(name = Constants.TRANS)
    var trans: String,
    @ColumnInfo(name = Constants.IMG_URL)
    var imgUrl: String*/

    )