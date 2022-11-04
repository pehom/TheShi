package com.pehom.theshi.domain.usecase.roomusecase

import androidx.lifecycle.viewModelScope
import com.pehom.theshi.data.localdata.approomdatabase.StudentRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddStudentRoomUseCase {
    fun execute(
        viewModel: MainViewModel,
        newStudent: StudentRoomItem,
        onSuccess: () -> Unit
    ) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            Constants.REPOSITORY.createStudentRoomItem(newStudent) {
                viewModel.viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            }
        }
    }
}