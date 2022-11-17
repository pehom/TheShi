package com.pehom.theshi.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.domain.model.*
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class SignOutUseCase {

    fun execute (
        viewModel: MainViewModel,
        auth: FirebaseAuth
    ) {
        viewModel.screenState.value = viewModel.MODE_LOGIN_SCREEN
        auth.signOut()
        viewModel.user.value = User(FsId(""), "", "", "", Funds())
        viewModel.requestsAdd.clear()
        viewModel.wordbook.clear()
        viewModel.currentTask.value = Task("","", Vocabulary(VocabularyTitle(""), mutableListOf()))
        viewModel.lastTaskInfo.value =  TaskInfo("", "", VocabularyTitle(""), "")
        viewModel.students.clear()
        viewModel.studentNumber.value = 0
        viewModel.switchState.value = true
        viewModel.isStudentProfileShown.value = false
        viewModel.allVocabularyTitles.clear()

    }
}