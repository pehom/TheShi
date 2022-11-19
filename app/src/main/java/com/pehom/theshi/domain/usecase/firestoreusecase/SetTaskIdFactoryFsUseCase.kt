package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.TaskIdFactory

class SetTaskIdFactoryFsUseCase {
    private val TAG = "SetTaskIdFactoryFsUseCase"
    fun execute(
        fsId: FsId,
        viewModel: MainViewModel,
        onResponse: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        Firebase.firestore.collection(Constants.USERS).document(fsId.value).get()
            .addOnSuccessListener { doc ->
                if (doc.get(Constants.LAST_TASK_ID_SFX)!= null) {
                    val lastIdSfx = doc.get(Constants.LAST_TASK_ID_SFX).toString().toInt()
                    viewModel.taskIdFactory = TaskIdFactory(fsId, lastIdSfx, viewModel.sharedPreferences)
                    onResponse()
                } else {
                    val lastIdSfx = 0
                    viewModel.taskIdFactory = TaskIdFactory(fsId, lastIdSfx, viewModel.sharedPreferences)
                    onResponse()
                }
            }
            .addOnFailureListener {
                Log.d("setTaskIdFactoryFsUseCase", "setting taskIdFactory failed, Error: ${it.message}")
            }
    }
}