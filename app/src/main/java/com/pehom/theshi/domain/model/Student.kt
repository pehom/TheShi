package com.pehom.theshi.domain.model

import com.pehom.theshi.utils.Constants

class Student( val id: String, var name: String) {
    val tasks: MutableList<TaskInfo> = mutableListOf()
    private val mentors: MutableSet<String> = mutableSetOf()
    var learnedWords = 0
   // val wordBook: MutableSet<VocabularyItemScheme> = mutableSetOf()

    override fun toString(): String {
        val divider = Constants.STUDENT_DIVIDER
        return id + divider + name
    }
}