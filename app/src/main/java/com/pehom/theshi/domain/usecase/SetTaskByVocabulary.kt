package com.pehom.theshi.domain.usecase

import androidx.compose.runtime.MutableState
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.presentation.viewmodel.MainViewModel

class SetTaskByVocabulary  {

    fun execute(
        viewModel: MainViewModel,
        vocabulary: Vocabulary,
        taskRoomItem: MutableState<TaskRoomItem>,
        onSuccess: ()-> Unit
    ) {
            val task = Task(
                taskRoomItem.value.id,
                taskRoomItem.value.taskTitle,
                vocabulary,
                taskRoomItem.value.currentTestItem,
                taskRoomItem.value.currentTaskItem,
                taskRoomItem.value.currentLearningItem,
                taskRoomItem.value.progress,
                taskRoomItem.value.wrongTestAnswers
            )
            viewModel.currentTask.value = task
            viewModel.currentTask.value.setReadyForLearning()
            viewModel.currentTask.value.setReadyForTask()
            viewModel.currentTask.value.setReadyForTest()
            onSuccess()
    }
}