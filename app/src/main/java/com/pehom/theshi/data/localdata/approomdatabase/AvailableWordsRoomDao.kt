package com.pehom.theshi.data.localdata.approomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AvailableWordsRoomDao {

    @Insert
    suspend fun addAvailableWordsRoomItem(availableWordsRoomItem: AvailableWordsRoomItem)

    @Query("SELECT * FROM available_words_table")
    fun getAvailableWordsRoomItems(): LiveData<List<AvailableWordsRoomItem>>

    @Query("SELECT * FROM available_words_table WHERE VocabularyFsDocRefPath = :vcbDocRefPath AND userFsId = :userFsId")
    fun getAvailableWordsRoomItemsByVcbDocRefPath(vcbDocRefPath: String, userFsId: String): List<AvailableWordsRoomItem>

    @Query("SELECT * FROM available_words_table WHERE userFsId = :userFsId")
    fun getAvailableWordsRoomItemsByUserFsIdAsLiveData(userFsId: String): LiveData<List<AvailableWordsRoomItem>>

    @Query("SELECT * FROM available_words_table WHERE userFsId = :userFsId")
    suspend fun getAvailableWordsRoomItemsByUserFsIdAsList(userFsId: String): List<AvailableWordsRoomItem>

    @Query("SELECT COUNT(*) FROM available_words_table WHERE VocabularyFsDocRefPath = :vcbDocRefPath")
    suspend fun getAvailableVocabularyRoomItemSizeByVcbDocRefPath(vcbDocRefPath: String): Int

    @Update
    suspend fun updateAvailableWordsRoomItem(availableWordsRoomItem: AvailableWordsRoomItem)

    @Delete
    suspend fun deleteAvailableWordsRoomItem(availableWordsRoomItem: AvailableWordsRoomItem)
}