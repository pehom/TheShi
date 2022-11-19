package com.pehom.theshi.domain.usecase.roomusecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetUserByUserFsIdRoomUseCase {
    private val TAG = "SetUserByUserFsIdRoomUseCase"

    fun execute(
        viewModel: MainViewModel,
        userFsId: String,
        onSuccess: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val userRoomItem = Constants.REPOSITORY.readUserRoomItemByUserFsId(userFsId)
            if (userRoomItem != null){
                viewModel.user.value = userRoomItem.mapToUser()
                onSuccess()
            }
        }
    }
}