package com.pehom.theshi.domain.usecase

import androidx.lifecycle.viewModelScope
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.domain.model.VocabularyTitle
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetAllWordsWordbookTaskUseCase {
    private val TAG = "SetAllWordsWordbookTaskUseCase"

    fun execute(
        viewModel: MainViewModel,
        onSuccess: () -> Unit
    ) {
        val context = viewModel.context
        val userFsId = viewModel.user.value.fsId.value
        val availableWords = mutableListOf<VocabularyItemScheme>()
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            Constants.REPOSITORY.readAvailableWordsRoomItemsByUserFsIdAsList(userFsId) { aWordsRoomItems ->
                if (aWordsRoomItems.isNotEmpty()) {
                    aWordsRoomItems.forEach {
                        availableWords.add(
                            VocabularyItemScheme(
                                it.orig,
                                it.trans,
                                it.imgUrl
                            )
                        )
                    }
                    val vcb = Vocabulary(
                        VocabularyTitle(Constants.ALL_WORDS_WORDBOOK_TASK, "", "", 0),
                        availableWords
                    )
                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        Constants.REPOSITORY.readTaskRoomItemById(Constants.ALL_WORDS_WORDBOOK_TASK){wordbookTaskRoomItem ->
                            if (wordbookTaskRoomItem != null) {
                                if(wordbookTaskRoomItem.taskTitle == context.getString(R.string.all_words)) {
                                    viewModel.currentWordbookTaskRoomItem.value = wordbookTaskRoomItem
                                    viewModel.useCases.setTaskByVocabulary.execute(viewModel, vcb, viewModel.currentWordbookTaskRoomItem){
                                        viewModel.currentWordbookVocabulary.value = vcb
                                        onSuccess()
                                    }
                                } else {
                                    viewModel.currentWordbookTaskRoomItem.value = TaskRoomItem(
                                        id = Constants.ALL_WORDS_WORDBOOK_TASK,
                                        mentorFsId = viewModel.user.value.fsId.value,
                                        studentFsId = viewModel.user.value.fsId.value,
                                        taskTitle = context.getString(R.string.all_words),
                                        isAvailable = true
                                    )
                                    viewModel.useCases.setTaskByVocabulary.execute(viewModel, vcb, viewModel.currentWordbookTaskRoomItem){
                                        viewModel.currentWordbookVocabulary.value = vcb
                                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                                            viewModel.useCases.updateTaskFsUseCase.execute(viewModel, viewModel.currentWordbookTaskRoomItem){}
                                            Constants.REPOSITORY.updateTaskRoomItem(viewModel.currentWordbookTaskRoomItem.value){
                                                onSuccess()
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                viewModel.currentWordbookTaskRoomItem.value = TaskRoomItem(
                                    id = Constants.ALL_WORDS_WORDBOOK_TASK,
                                    mentorFsId = viewModel.user.value.fsId.value,
                                    studentFsId = viewModel.user.value.fsId.value,
                                    taskTitle = context.getString(R.string.all_words),
                                    isAvailable = true
                                )
                                viewModel.useCases.setTaskByVocabulary.execute(viewModel, vcb, viewModel.currentWordbookTaskRoomItem){
                                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                                        viewModel.useCases.addUserTaskFsUseCase.execute(viewModel, viewModel.currentWordbookTaskRoomItem.value){}
                                        Constants.REPOSITORY.createTaskRoomItem(viewModel.currentWordbookTaskRoomItem.value){
                                            viewModel.currentWordbookVocabulary.value = vcb
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
    }
}