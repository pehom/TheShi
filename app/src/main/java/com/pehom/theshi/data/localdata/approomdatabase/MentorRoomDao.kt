package com.pehom.theshi.data.localdata.approomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MentorRoomDao {

    @Query("SELECT * FROM mentors_table")
    fun readAllMentorRoomItems(): LiveData<List<MentorRoomItem>>

    @Insert
    suspend fun addMentorRoomItem(mentorRoomItem: MentorRoomItem)

    @Query("SELECT * FROM mentors_table WHERE mentorFsID =:mentorFsId")
    suspend fun readMentorRoomItemByMentorFsId(mentorFsId: String): MentorRoomItem?

    @Query("SELECT * FROM mentors_table WHERE studentFsId =:userFsId")
    fun readMentorRoomItemsByUserFsId (userFsId: String ): LiveData<List<MentorRoomItem>>

    @Query("SELECT COUNT(*) FROM mentors_table WHERE studentFsId =:userFsId")
    fun getMentorRoomItemsCountByUserFsId (userFsId: String): Int

    @Update
    suspend fun updateMentorRoomItem(mentorRoomItem: MentorRoomItem)

    @Delete
    suspend fun deleteMentorRoomItem(mentorRoomItem: MentorRoomItem)
}