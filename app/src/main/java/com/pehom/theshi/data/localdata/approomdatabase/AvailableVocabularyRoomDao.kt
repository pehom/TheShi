package com.pehom.theshi.data.localdata.approomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AvailableVocabularyRoomDao {

    @Insert
    suspend fun addVocabularyRoomItem(vocabularyRoomItem: AvailableVocabularyRoomItem)

    @Query("SELECT * FROM vocabularies_table")
    fun getAllVocabularyRoomItems(): LiveData<List<AvailableVocabularyRoomItem>>

    @Query("SELECT * FROM vocabularies_table WHERE VocabularyFsDocRefPath = :vcbDocRefPath AND userFsId = :userFsId")
    suspend fun getAvailableVocabularyRoomItemByVcbDocRefPath(vcbDocRefPath: String, userFsId: String): AvailableVocabularyRoomItem?


    @Query("SELECT * FROM vocabularies_table WHERE userFsId = :userFsId")
    fun readAvailableVocabularyItemsByUserFsId(userFsId: String): LiveData<List<AvailableVocabularyRoomItem>>
    @Update
    suspend fun updateVocabularyRoomItem(vocabularyRoomItem: AvailableVocabularyRoomItem)

    @Delete
    suspend fun deleteVocabularyRoomItem(vocabularyRoomItem: AvailableVocabularyRoomItem)
}