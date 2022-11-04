package com.pehom.theshi.domain.model

import androidx.compose.runtime.mutableStateListOf

data class User(
    var fsId: FsId,
    var authId: String,
    var email: String,
    var phoneNumber: String,
    val funds: Funds
) {
    val savedVocabularies = mutableListOf<VocabularyTitle>()
    val tasks = mutableStateListOf<TaskInfo>()
    val students = mutableListOf<Student>()
    val mentors = mutableListOf<Mentor>()
    val availableVocabularies = mutableListOf<VocabularyTitle>()
    val sentRequests = mutableStateListOf<RequestSend>()
    val receivedRequests = mutableStateListOf<RequestReceived>()
}