package com.pehom.theshi.utils

import com.pehom.theshi.domain.model.TaskInfo

fun tasksToString(tasks: MutableList<TaskInfo>): String {
   val result = ""
   for (i in 0 until tasks.size-1) {
       result + tasks[i].toString() + Constants.TASKS_DIVIDER
   }
   result + tasks[tasks.size-1].toString()
   return result
}