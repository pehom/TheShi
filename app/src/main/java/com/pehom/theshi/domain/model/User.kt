package com.pehom.theshi.domain.model

data class User(
    var fsId: String,
    var authId: String,
    var email: String,
    var phoneNumber: String,
    val tasks: MutableList<TaskInfo>,
    val students: MutableList<Student>,
    val availableVocabularies: MutableList<VocabularyTitle>,
    val funds: Funds
) {
    val savedVocabularies = mutableListOf<VocabularyTitle>()
}