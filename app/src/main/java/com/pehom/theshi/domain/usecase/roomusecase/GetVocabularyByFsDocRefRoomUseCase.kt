package com.pehom.theshi.domain.usecase.roomusecase

import androidx.lifecycle.viewModelScope
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetVocabularyByFsDocRefRoomUseCase {
    private val TAG = "GetVocabularyByFsDocRefRoomUseCase"

    fun execute(
        viewModel: MainViewModel,
        vcbDocRefPath: String,
        onSuccess: (Vocabulary?) -> Unit
    ){
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            Constants.REPOSITORY.readAvailableVocabularyRoomItemByVcbDocRefPath(
                vcbDocRefPath,
                viewModel.user.value.fsId.value
            ){ if (it != null) {
                val vcbTitle = VocabularyTitle(it.vcbTitle, it.vcbDocRefPath, it.vcbTimeStamp, it.price)
                val vocabulary = Vocabulary(vcbTitle, mutableListOf())
                viewModel.viewModelScope.launch(Dispatchers.IO){
                    Constants.REPOSITORY.readAvailableWordsRoomItemsByVcbDocRefPath(
                        vcbDocRefPath,
                        viewModel.user.value.fsId.value
                    ){availableWordsRoomItems ->
                        if (availableWordsRoomItems.isNotEmpty()) {
                            availableWordsRoomItems.forEach { item ->
                                val vcbItemScheme = VocabularyItemScheme(item.orig, item.trans, item.imgUrl)
                                vocabulary.items.add(vcbItemScheme)
                            }
                            onSuccess(vocabulary)
                        }
                    }
                }
            } else {
                onSuccess(null)
            }
            }
        }
    }
}