package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteNewStudentsToRoomFsUseCase {
    private val TAG = "WriteNewStudentsToRoomFsUseCase"

    fun execute(
        viewModel: MainViewModel,
        onSuccess: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        viewModel.useCases.readNewStudentsFsUseCase.execute(viewModel){
            if (it.isNotEmpty()){
                viewModel.viewModelScope.launch(Dispatchers.IO) {
                    it.forEachIndexed() {index, student ->
                        Constants.REPOSITORY.createStudentRoomItem(student){
                            if (index == it.size-1){
                                onSuccess()
                            }
                        }
                    }
                }
            } else {
                onSuccess()
            }
        }
    }
}