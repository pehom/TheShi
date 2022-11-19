package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteNewTasksByMentorToRoomFsUseCase {
    private val TAG = "WriteNewTasksByMentorFsUseCase"

    fun execute(
        viewModel: MainViewModel,
        onSuccess: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        if (isNetworkAvailable()){
            viewModel.useCases.readNewUserTasksByMentorFsUseCase.execute(viewModel.user.value){
                if (it.isNotEmpty()) {
                    Log.d(TAG, "newTaskRoomList.size  = ${it.size}")
                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        it.forEachIndexed() {index, taskRoomItem ->
                            Constants.REPOSITORY.createTaskRoomItem(taskRoomItem){
                                //  docRef.update(Constants.IS_CHECKED, true)
                                if (index == it.size-1) {
                                    onSuccess()
                                }
                            }
                        }
                    }
                }else {
                    Log.d(TAG, "newTaskRoomList.size  = 0")
                    onSuccess()
                }
            }
        } else {
            onSuccess()
        }
    }
}