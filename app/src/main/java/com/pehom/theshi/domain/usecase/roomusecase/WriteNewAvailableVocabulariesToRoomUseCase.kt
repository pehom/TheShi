package com.pehom.theshi.domain.usecase.roomusecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteNewAvailableVocabulariesToRoomUseCase {
    private val TAG = "WriteNewAvailableVocabulariesToRoomUseCase"

    fun execute(
        viewModel: MainViewModel,
        onSuccess: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        viewModel.useCases.readNewAvailableVocabulariesFsUseCase.execute(viewModel){
            if (it.isNotEmpty()){
                viewModel.viewModelScope.launch(Dispatchers.IO) {
                    it.forEachIndexed() {index, availableVcbRoomItem ->
                        Constants.REPOSITORY.createAvailableVocabularyRoomItem(availableVcbRoomItem){
                            if (index == it.size){
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