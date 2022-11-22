package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.TaskIdFactory

class SetTaskIdFactoryFsUseCase {
  /*  private val TAG = "SetTaskIdFactoryFsUseCase"
    fun execute(
        viewModel: MainViewModel,
        onResponse: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        val fsId = viewModel.user.value.fsId
        Firebase.firestore.collection(Constants.USERS).document(fsId.value).get()
            .addOnSuccessListener { doc ->
                if (doc.get(Constants.LAST_TASK_ID_SFX)!= null) {
                    viewModel.user.value.lastIdSfx = doc.get(Constants.LAST_TASK_ID_SFX).toString().toInt()
                    viewModel.taskIdFactory = TaskIdFactory(viewModel)
                    onResponse()
                } else {
                    viewModel.user.value.lastIdSfx = 0
                    viewModel.taskIdFactory = TaskIdFactory(viewModel)
                    onResponse()
                }
            }
            .addOnFailureListener {
                Log.d("setTaskIdFactoryFsUseCase", "setting taskIdFactory failed, Error: ${it.message}")
            }
    }*/
}