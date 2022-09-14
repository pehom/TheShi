package com.pehom.theshi.testdata

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.pehom.theshi.domain.model.*

fun getTasksForStudents(): MutableList<TaskInfo> {
    return mutableStateListOf(
        TaskInfo("-2","first task", VocabularyTitle("Animals")),
        TaskInfo("-1","second task", VocabularyTitle("Jobs"))
    )
}