package com.pehom.theshi.domain.usecase.firestoreusecase

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class SetupMainViewModelFsUseCase() {
    fun execute(
        context: Context,
        viewModel: MainViewModel,
        currentUser: FirebaseUser,
        onResponse: () -> Unit
    ){
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
                                viewModel.useCases.setTaskIdFactoryFsUseCase.execute(fsId,viewModel){
                                    viewModel.useCases.getAllVocabularyTitlesFsUseCase.execute(viewModel){
                                        viewModel.isViewModelSet.value = true
                                        if (viewModel.isStarterScreenEnded.value){
                                            viewModel.screenState.value = viewModel.MODE_STUDENT_SCREEN
                                            onResponse()
                                        }
                                    }
                                }
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

