package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.StudentRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class ReadNewStudentsFsUseCase {
    private val TAG = "ReadNewStudentsFsUseCase"

    fun execute (
        viewModel: MainViewModel,
        onSuccess: (List<StudentRoomItem>) -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        val resultList = mutableListOf<StudentRoomItem>()
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.STUDENTS).get()
            .addOnSuccessListener {
                val changedDocs = it.documentChanges
                changedDocs.forEach(){doc ->
                    val studentRoomItem = StudentRoomItem(
                        doc.document[Constants.FS_ID].toString(),
                        doc.document[Constants.NAME].toString(),
                        doc.document[Constants.PHONE_NUMBER].toString(),
                        doc.document[Constants.STATUS].toString()
                    )
                    resultList.add(studentRoomItem)
                }
                onSuccess(resultList)
            }
            .addOnFailureListener {
                Log.d(TAG, "getting new students failed, Error: ${it.message}")
            }
    }
}