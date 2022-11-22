package com.pehom.theshi.domain.usecase.firestoreusecase

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
        val fsId = viewModel.user.value.fsId.value
        viewModel.useCases.readRequestsAddFsUseCase.execute(viewModel){
            viewModel.requestsAdd.addAll(it)
            viewModel.useCases.readNewUserTasksByMentorFsUseCase.execute(viewModel.user.value){newTasks ->
                viewModel.viewModelScope.launch(Dispatchers.IO) {
                    newTasks.forEachIndexed() {index, item ->
                        Constants.REPOSITORY.createTaskRoomItem(item){
                            if (index == newTasks.size -1) {
                                viewModel.useCases.readNewUserMentorsFsUseCase.execute(fsId){newMentors ->
                                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                                        newMentors.forEachIndexed { _, mentorRoomItem ->
                                            Constants.REPOSITORY.addMentorRoomItem(mentorRoomItem)
                                        }
                                        viewModel.useCases.readNewStudentsFsUseCase.execute(viewModel){newStudents ->
                                            viewModel.viewModelScope.launch(Dispatchers.IO){
                                                newStudents.forEachIndexed { index, studentRoomItem ->
                                                    Constants.REPOSITORY.createStudentRoomItem(studentRoomItem){
                                                        if (index == newStudents.size -1) {
                                                            onSuccess()
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}