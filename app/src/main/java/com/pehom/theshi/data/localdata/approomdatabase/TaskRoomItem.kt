package com.pehom.theshi.data.localdata.approomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pehom.theshi.utils.Constants

@Entity(tableName = "tasks_table")
data class TaskRoomItem(

    @PrimaryKey(autoGenerate = false)
    var id: String = "taskId",

    @ColumnInfo(name = Constants.MENTOR_FS_ID)
    var mentorFsId: String = "mentorFsId",

    @ColumnInfo(name = Constants.STUDENT_FS_ID)
    var studentFsId: String = "studentFsId",

    @ColumnInfo(name = Constants.TASK_TITLE)
    var taskTitle: String = "taskTitle",

    @ColumnInfo(name= Constants.VOCABULARY_FS_DOC_REF_PATH)
    var vcbFsDocRefPath: String = "vcbDocRefPath",

    @ColumnInfo(name = Constants.VOCABULARY_TITLE)
    var vcbTitle: String = "vcbTitle",

    @ColumnInfo(name= Constants.IS_AVAILABLE)
    var isAvailable: Boolean = false,

    @ColumnInfo(name= Constants.PROGRESS)
    var progress: Int = 0,

    @ColumnInfo(name= Constants.CURRENT_TASK_ITEM)
    var currentTaskItem: Int = 0,

    @ColumnInfo(name= Constants.CURRENT_TEST_ITEM)
    var currentTestItem: Int = 0,

    @ColumnInfo(name= Constants.CURRENT_LEARNING_ITEM)
    var currentLearningItem: Int = 0,

    @ColumnInfo(name= Constants.WRONG_TEST_ANSWERS)
    var wrongTestAnswers: MutableMap<Int, String> = mutableMapOf(),

    @ColumnInfo(name = Constants.SYNC_COUNT)
    var syncCount: Int = 0
){
     fun incrementSyncCount(){
        if (syncCount < 1000000)
            syncCount++
        else syncCount = 2
    }
}