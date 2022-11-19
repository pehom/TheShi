package com.pehom.theshi.domain.usecase.roomusecase

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.LoginModel
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInRoomUseCase {
    private val TAG = "SignInRoomUseCase"

    fun execute(
        context: android.content.Context,
        viewModel: MainViewModel,
        loginModel: LoginModel,
        onSuccess: (Boolean) -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val userRoomItem = Constants.REPOSITORY.readUserRoomItemByEmailAndPassword(loginModel.email, loginModel.password)
            if (userRoomItem != null && userRoomItem.userFsId != ""){
                viewModel.user.value = userRoomItem.mapToUser()
                onSuccess(true)
            } else {
                Toast.makeText(context, context.getString(R.string.wrong_email_or_password), Toast.LENGTH_SHORT).show()
                onSuccess(false)
            }
        }
    }
}