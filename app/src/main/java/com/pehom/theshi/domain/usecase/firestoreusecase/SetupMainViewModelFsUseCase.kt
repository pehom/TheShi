package com.pehom.theshi.domain.usecase.firestoreusecase

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetupMainViewModelFsUseCase() {
    private val TAG = "SetupMainViewModelFsUseCase"
    fun execute(
        context: Context,
        viewModel: MainViewModel,
        currentUser: FirebaseUser,
        onResponse: () -> Unit
    ){
        Log.d(TAG, "$TAG invoked")
        Firebase.firestore.collection(Constants.USERS).whereEqualTo(Constants.AUTH_ID,  currentUser.uid).get()
            .addOnSuccessListener {documents ->
                Log.d("setupViewModelFs", "setupViewModelFs I'm here. documents.size = ${documents.size()} ")
                if (documents.size() == 1) {
                    Log.d("setupViewModelFs", "setupViewModelFs I'm here also")
                    for (doc in documents) {
                        val fsId = FsId(doc.get(Constants.FS_ID).toString())
                        viewModel.useCases.readFirestoreUserInfoUseCase.execute(fsId.value, context){user->
                            viewModel.user.value = user

                            viewModel.currentWordbookTaskRoomItem.value.studentFsId = user.fsId.value
                            viewModel.useCases.readRequestsAddFsUseCase.execute(viewModel){
                                    viewModel.useCases.readNewUserMentorsFsUseCase.execute(user.fsId.value){newMentors ->
                                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                                            newMentors.forEach(){mentorRoomItem ->
                                                Constants.REPOSITORY.addMentorRoomItem(mentorRoomItem)
                                            }
                                            val tasksCount = Constants.REPOSITORY.getTaskRoomItemsCountByUserFsId(viewModel.user.value.fsId.value)
                                            if (tasksCount == 0){
                                                viewModel.useCases.readAllUserTasksFsUseCase.execute(viewModel){
                                                    if (it.isNotEmpty()){
                                                        it.forEach {taskRoomItem ->
                                                            viewModel.useCases.addTaskRoomUseCase.execute(viewModel, taskRoomItem){}
                                                        }
                                                    }
                                                }
                                            }
                                            viewModel.useCases.writeNewTasksByMentorToRoomFsUseCase.execute(viewModel){
                                                viewModel.useCases.writeNewStudentsToRoomFsUseCase.execute(viewModel){
                                                    viewModel.isViewModelSet.value = true
                                                    Log.d("viewModel", "setup viewmodel.isViewModelSet = ${viewModel.isViewModelSet.value}")
                                                    Log.d("viewModel", "setup viewmodel.isStarterScreenEnded = ${viewModel.isStarterScreenEnded.value}")
                                                    onResponse()
                                                    //  may be starter screen is not ended yet
                                                }
                                            }
                                        }
                                    }
                                 //TODO   sync room with fs needed
                            }
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d("onStart", "onStart readFsUserInfoUseCase failed, Error: ${it.message}")
            }
    }
}

