package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.data.localdata.approomdatabase.AvailableWordsRoomItem
import com.pehom.theshi.data.localdata.approomdatabase.WordbookRoomItem
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadWholeDataFsUseCase {
    private val TAG = "LoadWholeDataFsUseCase"

    fun execute(
        viewModel: MainViewModel,
        onSuccess: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        var count = 6
        val userFsId = viewModel.user.value.fsId.value
        viewModel.useCases.readNewStudentsFsUseCase.execute(viewModel){
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                it.forEach { student ->
                    Constants.REPOSITORY.createStudentRoomItem(student){}
                }
                count--
                Log.d(Constants.INSPECTING_TAG, "readNewStudentsFsUseCase count = $count")
                if (count == 0){
                    onSuccess()
                }
            }
        }
        viewModel.useCases.readNewUserMentorsFsUseCase.execute(userFsId){
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                it.forEach { mentor ->
                    Constants.REPOSITORY.addMentorRoomItem(mentor)
                }
                count--
                Log.d(Constants.INSPECTING_TAG, "readNewUserMentorsFsUseCase count = $count")
                if (count == 0){
                    onSuccess()
                }
            }
        }
        viewModel.useCases.readAllUserTasksFsUseCase.execute(viewModel){
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                it.forEach { task ->
                    Constants.REPOSITORY.createTaskRoomItem(task){}
                }
                count--
                Log.d(Constants.INSPECTING_TAG, "readAllUserTasksFsUseCase count = $count")
                if (count == 0){
                    onSuccess()
                }
            }
        }
        viewModel.useCases.readNewAvailableVocabulariesFsUseCase.execute(viewModel) { availableVocabularies ->
            Log.d(Constants.INSPECTING_TAG, "readNewAvailableVocabulariesFsUseCase vcbs = $availableVocabularies")
            if (availableVocabularies.isEmpty()){
                count--
                Log.d(Constants.INSPECTING_TAG, "readNewAvailableVocabulariesFsUseCase count = $count")
                if (count == 0) {
                    onSuccess()
                }
            } else {
                viewModel.viewModelScope.launch(Dispatchers.IO) {
                    availableVocabularies.forEachIndexed { index, vcb ->
                        Constants.REPOSITORY.createAvailableVocabularyRoomItem(vcb) {}
                        viewModel.useCases.readVcbItemsByVcbDocRefFsUseCase.execute(vcb.vcbDocRefPath) { words ->
                            words.forEach { word ->
                                viewModel.viewModelScope.launch(Dispatchers.IO) {
                                    val wordsRoomItem = AvailableWordsRoomItem(
                                        null,
                                        vcb.vcbDocRefPath,
                                        word.orig,
                                        word.trans,
                                        word.imgUrl,
                                        userFsId
                                    )
                                    Constants.REPOSITORY.addAvailableWordsRoomItem(wordsRoomItem)
                                    if (index == availableVocabularies.size - 1) {
                                        count--
                                        Log.d(Constants.INSPECTING_TAG, "readVcbItemsByVcbDocRefFsUseCase count = $count")
                                        if (count == 0) {
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
        viewModel.useCases.readWordbookFsUseCase.execute(viewModel){map ->
            Log.d(Constants.INSPECTING_TAG, "$TAG wordbookMap = $map")
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                map.keys.forEach {key ->
                    val wordsRoomItem = WordbookRoomItem(
                        null,
                        userFsId,
                        map[key]!!,
                        key
                    )
                    Constants.REPOSITORY.createWordbookRoomItem(wordsRoomItem){}
                }
                count--
                Log.d(Constants.INSPECTING_TAG, "readWordbookFsUseCase count = $count")
                if (count == 0){
                    onSuccess()
                }
            }
        }
        viewModel.useCases.readRequestsAddFsUseCase.execute(viewModel){
            Log.d(Constants.INSPECTING_TAG, "$TAG requests = $it")
            if (it.isEmpty()){
                count--
                if (count == 0){
                    onSuccess()
                }
            } else {
                viewModel.requestsAdd.addAll(it)
                count--
                if (count == 0){
                    onSuccess()
                }
            }
        }
    }
}