package com.pehom.theshi.domain.model

import androidx.compose.runtime.mutableStateOf
import com.pehom.theshi.utils.Constants

data class TaskInfo(
    val id: String,
    val title: String,
    val vocabularyTitle: VocabularyTitle,

) {
    /*var currentTestItem = mutableStateOf(0)
    var currentTaskItem = mutableStateOf(0)*/
    var currentTestItem = 0
    var currentTaskItem = 0
    var currentLearningItem = 0
    var progress = 0

    override fun toString(): String {
        val DIVIDER = Constants.TASK_INFO_DIVIDER
        return id + DIVIDER + title + DIVIDER + vocabularyTitle
    }

}