package com.pehom.theshi.domain.usecase.roomusecase

import androidx.lifecycle.viewModelScope
import com.pehom.theshi.data.localdata.approomdatabase.AvailableVocabularyRoomItem
import com.pehom.theshi.data.localdata.approomdatabase.AvailableWordsRoomItem
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaveAvailableVocabularyRoomUseCase {
    private val TAG = "SaveAvailableVocabularyRoomUseCase"
    fun execute(
        viewModel: MainViewModel,
        vocabulary: Vocabulary
    ){
        val vcbTitle = vocabulary.title
        viewModel.viewModelScope.launch(Dispatchers.IO) {

            Constants.REPOSITORY.createAvailableVocabularyRoomItem(
                AvailableVocabularyRoomItem(
                    null,
                    vcbTitle.fsDocRefPath,
                    vcbTitle.timestamp,
                    vcbTitle.value,
                    vcbTitle.price,
                    viewModel.user.value.fsId.value
                )
            ){}

            vocabulary.items.forEach(){vcbItemScheme ->
                val availableWordsRoomItem = AvailableWordsRoomItem(
                    null,
                    vcbTitle.fsDocRefPath,
                    vcbItemScheme.orig,
                    vcbItemScheme.trans,
                    vcbItemScheme.imgUrl
                )
                Constants.REPOSITORY.addAvailableWordsRoomItem(availableWordsRoomItem)
            }
            Constants.REPOSITORY.updateTaskRoomItem(viewModel.currentTaskRoomItem.value){}
        }
    }
}