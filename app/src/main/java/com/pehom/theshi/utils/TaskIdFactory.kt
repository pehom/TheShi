package com.pehom.theshi.utils

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object TaskIdFactory
  //  (val userFsId: FsId, var lastIdSfx: Int,  val sharedPreferences: SharedPreferences)
{
    var lastId = ""

    fun createId(viewModel: MainViewModel): String {

        viewModel.user.value.lastIdSfx++
        lastId = viewModel.user.value.fsId.value + viewModel.user.value.lastIdSfx
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val userRoomItem = Constants.REPOSITORY.readUserRoomItemByUserFsId(viewModel.user.value.fsId.value)
            if (userRoomItem != null){
                userRoomItem.lastTaskIdSfx = viewModel.user.value.lastIdSfx
                Constants.REPOSITORY.updateUserRoomItem(userRoomItem)
            }
        }
        Log.d("createTaskId", "lastId = $lastId")
        return lastId
    }
}