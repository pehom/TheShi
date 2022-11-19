package com.pehom.theshi.domain.usecase.roomusecase

import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetupMainViewmodelRoomUseCase {
    private val TAG = "SetupMainViewmodelRoomUseCase"

    fun execute(
        viewModel: MainViewModel,
        onSuccess: () -> Unit
    ){
        viewModel.viewModelScope.launch(Dispatchers.IO) {

        }
    }
}