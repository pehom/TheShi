package com.pehom.theshi.utils

import com.pehom.theshi.domain.model.TaskInfo


fun tasksFromString(source: String): MutableList<TaskInfo> {
    val items = source.trim().split(Constants.TASKS_DIVIDER)
    val result = mutableListOf<TaskInfo>()
    items.forEach(){
        val taskInfo = taskInfoFromString(it)
        if (taskInfo.title != "") result.add(taskInfo)
    }
    return result
}