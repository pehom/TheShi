package com.pehom.theshi.data.localdata.approomdatabase

import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.LiveData
import androidx.room.*
import com.pehom.theshi.utils.Constants

@Dao
interface TaskRoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskRoomItem(taskRoomItem: TaskRoomItem)

    @Query("SELECT * FROM tasks_table")
    fun getAllTaskRoomItems(): LiveData<List<TaskRoomItem>>

    @Query("SELECT * FROM tasks_table WHERE id =:taskId")
    suspend fun getTaskRoomItemById(taskId: String): TaskRoomItem?

    @Query("SELECT * FROM tasks_table WHERE studentFsId =:fsId AND id <> 'wbTaskRoomItem' AND id <> 'allWordsWordbookTask'")
    fun getTaskRoomItemsByFsId(fsId: String): LiveData<List<TaskRoomItem>>

    @Query("SELECT id FROM tasks_table WHERE studentFsId =:userFsId AND id <> 'wbTaskRoomItem' AND id <> 'allWordsWordbookTask'")
    suspend fun getTaskRoomItemIdsByUserFsId(userFsId: String): List<String>

    @Query("SELECT COUNT(*) FROM tasks_table WHERE studentFsId =:userFsId AND id <> 'wbTaskRoomItem' AND id <> 'allWordsWordbookTask'")
    suspend fun getTaskRoomItemsCountByUserFsId(userFsId: String): Int

    @Update
    suspend fun updateTaskRoomItem(taskRoomItem: TaskRoomItem)

    @Delete
    suspend fun deleteTaskRoomItem(taskRoomItem: TaskRoomItem)
}
