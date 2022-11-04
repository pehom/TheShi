package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.Student
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class ReadStudentsFsUseCase {
    fun execute(
        viewModel: MainViewModel,
        onResponse: () -> Unit
    ) {
        viewModel.students.clear()
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.STUDENTS).get()
            .addOnSuccessListener { docs ->
                for (doc in docs) {
                    val student = Student(FsId(doc[Constants.FS_ID].toString()), doc[Constants.NAME].toString())
                    student.learnedWords = doc[Constants.LEARNED_WORDS].toString().toInt()
                    viewModel.students.add(student)
                }
                onResponse()
            }
            .addOnFailureListener {
                Log.d("readStudentsFsUseCase", "reading students failed, Error: ${it.message}")
            }
    }
}