package com.pehom.theshi.domain.usecase.roomusecase


import androidx.lifecycle.viewModelScope
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskRoomUseCase {
    fun execute(
        viewModel: MainViewModel,
        newTaskInfo: TaskRoomItem,
        onSuccess: (TaskRoomItem) -> Unit
    ) {
        viewModel.viewModelScope.launch ( Dispatchers.IO ) {
            Constants.REPOSITORY.createTaskRoomItem(newTaskInfo) {
                viewModel.viewModelScope.launch (Dispatchers.Main) {
                    onSuccess(newTaskInfo)
                }
            }
        }
    }
}