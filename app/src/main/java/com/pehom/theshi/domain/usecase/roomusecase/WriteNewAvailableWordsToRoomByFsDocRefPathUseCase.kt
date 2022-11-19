package com.pehom.theshi.domain.usecase.roomusecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.data.localdata.approomdatabase.AvailableWordsRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteNewAvailableWordsToRoomByFsDocRefPathUseCase {
    private val TAG = "WriteNewAvailableWordsToRoomUseCase"

    fun execute(
        fsDocRefPath: String,
        viewModel: MainViewModel,
        onSuccess: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        viewModel.viewModelScope.launch(Dispatchers.IO) {
           val existedWordsCount = Constants.REPOSITORY.getAvailableWordsRoomItemsCountByUserFsIdAndVcbDocRefPath(
                viewModel.user.value.fsId.value,
                fsDocRefPath
            )
            if (existedWordsCount == 0) {
                Firebase.firestore.document(fsDocRefPath).collection(Constants.ITEMS).get()
                    .addOnSuccessListener {docs ->
                        docs.forEachIndexed { index, doc ->
                            val newWord = AvailableWordsRoomItem(
                                null,
                                fsDocRefPath,
                                doc[Constants.ORIG].toString(),
                                doc[Constants.TRANS].toString(),
                                doc[Constants.IMG_URL].toString(),
                                viewModel.user.value.fsId.value
                            )
                            viewModel.viewModelScope.launch(Dispatchers.IO) {
                                Constants.REPOSITORY.addAvailableWordsRoomItem(newWord)
                            }
                        }
                        onSuccess()
                    }
            } else {
                onSuccess()
            }
        }
    }
}