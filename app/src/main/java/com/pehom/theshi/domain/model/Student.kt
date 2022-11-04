package com.pehom.theshi.domain.model

import androidx.compose.runtime.mutableStateListOf
import com.pehom.theshi.utils.Constants

class Student(val fsId: FsId, var name: String) {
    val tasks = mutableStateListOf<TaskInfo>()
    //val mentors: MutableSet<String> = mutableSetOf()
    var learnedWords = 0
   // val wordBook: MutableSet<VocabularyItemScheme> = mutableSetOf()

    override fun toString(): String {
        val divider = Constants.STUDENT_DIVIDER
        return fsId.value + divider + name
    }
}