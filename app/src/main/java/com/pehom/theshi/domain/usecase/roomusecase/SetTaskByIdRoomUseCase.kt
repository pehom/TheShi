package com.pehom.theshi.domain.usecase.roomusecase

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetTaskByIdRoomUseCase {
    fun execute(
        viewModel: MainViewModel,
        taskRoomItem: MutableState<TaskRoomItem>,
        onSuccess: () -> Unit
    ){
       // val taskId = taskRoomItem.value.id
       // val taskTitle = taskRoomItem.value.taskTitle
        val vcbDocRefPath = taskRoomItem.value.vcbFsDocRefPath
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            Constants.REPOSITORY.readAvailableVocabularyRoomItemByVcbDocRefPath(vcbDocRefPath, viewModel.user.value.fsId.value){
                if (it != null) {
                    val vcbTitle = VocabularyTitle(
                        it.vcbTitle,
                        it.vcbDocRefPath,
                        it.vcbTimeStamp,
                        0 // TODO need another model e.g. VocabularyFsItem with price parameter
                    )
                    val resultVocabulary = Vocabulary(vcbTitle, mutableListOf())
                    viewModel.viewModelScope.launch(Dispatchers.IO){
                        Constants.REPOSITORY.readAvailableWordsRoomItemsByVcbDocRefPath(vcbDocRefPath, viewModel.user.value.fsId.value){availableWordsRoomItems ->
                            availableWordsRoomItems.forEach {it1 ->
                                val orig = it1.orig
                                val trans = it1.trans
                                val imgUrl = it1.imgUrl
                                resultVocabulary.items.add(VocabularyItemScheme(orig, trans, imgUrl))
                            }
                            viewModel.useCases.setTaskByVocabulary.execute(viewModel, resultVocabulary, taskRoomItem){
                                viewModel.viewModelScope.launch(Dispatchers.Main) {
                                    onSuccess()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}