package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetViewmodelNetworkItemsFsUseCase {
    private val TAG = "SetViewmodelNetworkItemsFsUseCase"

    fun execute(
        viewModel: MainViewModel,
        onSuccess: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        val fsId = viewModel.user.value.fsId.value
        var count = 4
        viewModel.useCases.readRequestsAddFsUseCase.execute(viewModel){
            viewModel.requestsAdd.addAll(it)
            count--
            if (count == 0){
                onSuccess()
            }
        }
        viewModel.useCases.readNewUserTasksByMentorFsUseCase.execute(viewModel.user.value) { newTasks ->
            if (newTasks.isNotEmpty()) {
                viewModel.viewModelScope.launch(Dispatchers.IO) {
                    newTasks.forEach {
                        Constants.REPOSITORY.createTaskRoomItem(it) {}
                    }
                    count--
                    if (count == 0) {
                        onSuccess()
                    }
                }
            } else {
                count--
                if (count == 0) {
                    onSuccess()
                }
            }
        }
        viewModel.useCases.readNewStudentsFsUseCase.execute(viewModel){newStudents ->
            if (newStudents.isNotEmpty()){
                viewModel.viewModelScope.launch(Dispatchers.IO){
                    newStudents.forEachIndexed { _, studentRoomItem ->
                        Constants.REPOSITORY.createStudentRoomItem(studentRoomItem){}
                    }
                    count--
                    if (count == 0) {
                        onSuccess()
                    }
                }
            } else {
                count--
                if (count == 0) {
                    onSuccess()
                }
            }
        }
        viewModel.useCases.readNewUserMentorsFsUseCase.execute(fsId){newMentors ->
            if (newMentors.isNotEmpty()){
                viewModel.viewModelScope.launch(Dispatchers.IO) {
                    newMentors.forEachIndexed { _, mentorRoomItem ->
                        Constants.REPOSITORY.addMentorRoomItem(mentorRoomItem)
                    }
                    count--
                    if (count == 0) {
                        onSuccess()
                    }
                }
            } else {
                count--
                if (count == 0) {
                    onSuccess()
                }
            }
        }
    }
}