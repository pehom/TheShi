package com.pehom.theshi.domain.usecase.roomusecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetWordbookSizeByUserFsId {
    private val TAG = "GetWordbookSizeByUserFsId"
    fun execute(
        viewModel: MainViewModel,
        onSuccess: (Int) -> Unit
    ){
        var count = 0
        viewModel.viewModelScope.launch(Dispatchers.IO) {
           val wordbookRoomItems = Constants.REPOSITORY.readWordbookRoomItemsByUserFsId(viewModel.user.value.fsId.value)
           wordbookRoomItems.forEach {
              Log.d(TAG, "wordbookRoomItem = $it")
              count+= Constants.REPOSITORY.getAvailableVocabularyRoomItemSizeByVcbDocRefPath(it.vcbDocRefPath)
           }
           viewModel.viewModelScope.launch(Dispatchers.Main) {
               onSuccess(count)
           }
        }
    }
}