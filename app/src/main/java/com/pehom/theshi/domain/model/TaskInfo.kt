package com.pehom.theshi.domain.model

import com.pehom.theshi.utils.Constants

data class TaskInfo(
    val id: String,
    val title: String,
    val vocabularyTitle: VocabularyTitle,
    var status: String
) {
    var progress = 0

    override fun toString(): String {
        val DIVIDER = Constants.TASK_INFO_DIVIDER
        return id + DIVIDER + title + DIVIDER + vocabularyTitle + DIVIDER + status
    }

}