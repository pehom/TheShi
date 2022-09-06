package com.pehom.theshi.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pehom.theshi.domain.model.*
import com.pehom.theshi.domain.usecase.CreateAccountUseCase
import com.pehom.theshi.domain.usecase.GetAllVocabularyTitles
import com.pehom.theshi.domain.usecase.UseCases
import com.pehom.theshi.testdata.getStudents
import com.pehom.theshi.testdata.getTasks
import com.pehom.theshi.testdata.setTasksForStudents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

/*@HiltViewModel
@InternalCoroutinesApi*/
class MainViewModel /*@Inject constructor(

)*/ : ViewModel() {
    val MODE_SIGN_IN_SCREEN = 8
    val MODE_REGISTER_SCREEN = 7
    val MODE_STUDENT_SCREEN = 1
    val MODE_MENTOR_SCREEN = 2
    val MODE_TASK_SCREEN = 4
    val MODE_TEST_SCREEN = 5
    val MODE_LOGIN_SCREEN = 6

    val tasks = mutableStateListOf<Task>()
    val students: MutableList<Student> = mutableListOf()
    val vocabularies: MutableList<Vocabulary> = mutableListOf()
    val switchState = mutableStateOf(true)
    val screenState = mutableStateOf(MODE_STUDENT_SCREEN)
    val currentTaskNumber = mutableStateOf(0)
    val studentNumber =  mutableStateOf(0)
    val isStudentProfileShown = mutableStateOf(false)
    val loading = mutableStateOf(false)
    val allVocabularyTitles = mutableStateListOf<VocabularyTitle>()

    val useCases = UseCases(GetAllVocabularyTitles(allVocabularyTitles))

    lateinit var createAccountUseCase: CreateAccountUseCase

    init {
        tasks += getTasks()
        students +=  getStudents()
        students.forEach { it.tasks = setTasksForStudents() } // test data
     //   vocabularies += getVocabularies()
        readAllVocabularyTitles()
        // allVocabularyTitles.addAll(readAllVocabularyTitles())
        Log.d("viewModel", "viewModel created")

    }

    override fun onCleared() {
        Log.d("viewModel", "MainViewModel.onCleared() has been invoked")
        super.onCleared()
    }

    fun toggleSwitch() {
        switchState.value = !switchState.value
        if (switchState.value) screenState.value = MODE_STUDENT_SCREEN
        else screenState.value = MODE_MENTOR_SCREEN
        // toggleSwitchUseCase.execute()
    }

    private fun readAllVocabularyTitles() {
        useCases.getAllVocabularyTitles.execute()
    }
}