package com.pehom.theshi.data.localdata.approomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordbookDao {
    @Insert
    suspend fun addWordbookItem(wordbookItem: WordbookRoomItem)

    @Query("SELECT * FROM wordbook_table")
    fun getAllWordbookItems(): LiveData<List<WordbookRoomItem>>

    @Query("SELECT * FROM wordbook_table WHERE userFsId =:userFsId")
    suspend fun getWordbookItemsByUserFsID(userFsId: String): List<WordbookRoomItem>


    @Query("SELECT * FROM wordbook_table WHERE VocabularyFsDocRefPath = :vcbDocRefPath")
    suspend fun getWordbookItemByVcbDocRefPath(vcbDocRefPath: String): WordbookRoomItem

    @Update
    suspend fun updateWordbookItem(wordbookItem: WordbookRoomItem)

    @Delete
    suspend fun deleteWordbookItem(wordbookItem: WordbookRoomItem)

}