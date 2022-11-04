package com.pehom.theshi.data.localdata.approomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDao {
    @Insert
    suspend fun addStudentRoomItem(studentRoomItem: StudentRoomItem)

    @Query("SELECT * FROM students_table")
    fun getAllStudentRoomItems(): LiveData<List<StudentRoomItem>>

    @Query("SELECT * FROM students_table WHERE mentorFsId =:mentorId")
    fun getStudentRoomItemsByMentorId(mentorId: String): LiveData<List<StudentRoomItem>>

    @Query("SELECT * FROM students_table WHERE fsId =:studentFsId")
    fun getStudentRoomItemByFsId(studentFsId: String): LiveData<StudentRoomItem>

    @Update
    suspend fun updateStudentRoomItem(studentRoomItem: StudentRoomItem)

    @Delete
    suspend fun deleteStudentRoomItem(studentRoomItem: StudentRoomItem)
}