package com.pehom.theshi.domain.usecase.roomusecase

import androidx.lifecycle.viewModelScope
import com.pehom.theshi.data.localdata.approomdatabase.WordbookRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddVocabularyToWordbookRoomUseCase {
    fun execute(
        viewModel: MainViewModel,
        onSuccess: () -> Unit
    ){
        val currentTask = viewModel.currentTask.value
        val currentVocabularyTitle = currentTask.vocabulary.title
        viewModel.viewModelScope.launch ( Dispatchers.IO ) {
            Constants.REPOSITORY.readWordbookRoomItemByVcbDocRefPath(currentVocabularyTitle.fsDocRefPath){
                if (it == null) {
                    val wordbookRoomItem = WordbookRoomItem(
                        null,
                        viewModel.user.value.fsId.value,
                        currentVocabularyTitle.fsDocRefPath,
                        currentVocabularyTitle.value
                    )
                    viewModel.viewModelScope.launch ( Dispatchers.IO ){
                        Constants.REPOSITORY.createWordbookRoomItem(wordbookRoomItem){
                            viewModel.viewModelScope.launch(Dispatchers.Main){
                                onSuccess()
                            }
                        }
                    }
                }
            }
        }
    }
}