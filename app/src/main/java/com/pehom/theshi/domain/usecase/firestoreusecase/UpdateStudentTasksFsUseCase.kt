package com.pehom.theshi.domain.usecase.firestoreusecase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.presentation.viewmodel.MainViewModel

class UpdateStudentTasksFsUseCase {
    fun execute(viewmodel: MainViewModel) {
        Firebase.firestore.collection("")
    }
}