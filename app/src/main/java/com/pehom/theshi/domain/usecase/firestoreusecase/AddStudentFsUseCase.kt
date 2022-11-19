package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.Student
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class AddStudentFsUseCase {
    private val TAG ="AddStudentFsUseCase"
    fun execute(
        viewModel: MainViewModel,
        _newStudent: Student,
        onResponse: ()->Unit
    ) {
        Log.d(TAG, "$TAG invoked")
        val db = Firebase.firestore
        val newStudent = hashMapOf(
            Constants.FS_ID to _newStudent.fsId.value,
            Constants.NAME to _newStudent.name,
            Constants.PHONE_NUMBER to _newStudent.phone,
            Constants.STATUS to Constants.ACCEPTED
          //  Constants.LEARNED_WORDS to _newStudent.learnedWords
        )
        db.collection(Constants.USERS).document(viewModel.user.value.fsId.value).collection(Constants.STUDENTS)
            .document(_newStudent.fsId.value).set(newStudent)
            .addOnSuccessListener {
                onResponse()
            }
            .addOnFailureListener {
                Log.d("addStudentUseCase", "adding student failed, Error: ${it.message}")
            }
    }
}