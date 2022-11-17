package com.pehom.theshi.utils

import com.pehom.theshi.domain.model.TaskInfo
import com.pehom.theshi.domain.model.VocabularyTitle

fun taskInfoFromString(source: String): TaskInfo {
    val items = source.trim().split(Constants.TASK_INFO_DIVIDER)
    return if (items.size == 4)
         TaskInfo(items[0], items[1], VocabularyTitle( items[2]), items[3])
     else
         TaskInfo("taskId", "taskTitle", VocabularyTitle(), "taskStatus")
}