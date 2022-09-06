package com.pehom.theshi.domain.model

class Student( val id: String, var name: String) {
    var tasks: MutableList<Task> = mutableListOf()
    val mentors: MutableSet<String> = mutableSetOf()
    var learnedWords = 0
    val wordBook: MutableSet<VocabularyItemScheme> = mutableSetOf()
}