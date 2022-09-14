package com.pehom.theshi.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pehom.theshi.R
import com.pehom.theshi.domain.model.*
import com.pehom.theshi.domain.usecase.*
import com.pehom.theshi.testdata.getStudents
import com.pehom.theshi.testdata.getTasksForStudents
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.TaskIdFactory

/*@HiltViewModel
@InternalCoroutinesApi*/
class MainViewModel(private val context: Context) /*@Inject constructor(

)*/ : ViewModel() {
    val MODE_SIGN_IN_SCREEN = 8
    val MODE_REGISTER_SCREEN = 7
    val MODE_STUDENT_SCREEN = 1
    val MODE_MENTOR_SCREEN = 2
    val MODE_TASK_SCREEN = 4
    val MODE_TEST_SCREEN = 5
    val MODE_LOGIN_SCREEN = 6

    var currentTask = mutableStateOf(Task("","", Vocabulary("", mutableListOf(VocabularyItemScheme("","","")))))
    val tasksInfo = mutableStateListOf<TaskInfo>()
    val students: MutableList<Student> = mutableListOf()
    val vocabularies: MutableList<Vocabulary> = mutableListOf()
    val switchState = mutableStateOf(true)
    val screenState = mutableStateOf(MODE_STUDENT_SCREEN)
    val currentTaskNumber = mutableStateOf(0)
    val studentNumber =  mutableStateOf(0)
    val isStudentProfileShown = mutableStateOf(false)
    val loading = mutableStateOf(false)
    val allVocabularyTitles = mutableStateListOf<VocabularyTitle>()
    val drawerType = mutableStateOf(Constants.DRAWER_ADD_NEW_TASK)
    var user = User("","","","", mutableListOf(), mutableListOf(), mutableListOf(), Funds())
    var lastTaskInfo = mutableStateOf(TaskInfo("","",VocabularyTitle()))

    val useCases = UseCases(
        GetAllVocabularyTitles(allVocabularyTitles),
        SetTaskByTitle(),
        SignInUseCase(),
        CreateFirestoreAccountUseCase(),
        ReadFirestoreUserInfoUseCase(),
        UpdateUserTasksFsUseCase()
        )

    init {
        val title =  context.getString(R.string.wordbook)
        tasksInfo.add(TaskInfo(TaskIdFactory.createId(), title,VocabularyTitle( title )))
        students +=  getStudents()
        students.forEach { it.tasks += getTasksForStudents() } // test data
        readAllVocabularyTitles()
        Log.d("viewModel", "viewModel created")

    }

    override fun onCleared() {
        Log.d("viewModel", "MainViewModel.onCleared() has been invoked")
        super.onCleared()
    }

    fun toggleSwitch() {
        switchState.value = !switchState.value
        if (switchState.value) {
            drawerType.value = Constants.DRAWER_ADD_NEW_TASK
            screenState.value = MODE_STUDENT_SCREEN
        }
        else {
            drawerType.value = Constants.DRAWER_ADD_STUDENT
            screenState.value = MODE_MENTOR_SCREEN
        }
        // toggleSwitchUseCase.execute()
    }

    private fun readAllVocabularyTitles() {
        useCases.getAllVocabularyTitles.execute()
    }

    fun saveCurrentTaskInfo () {
        val id = currentTask.value.id
        val title = currentTask.value.title
        val vocabularyTitle = currentTask.value.vocabulary.title
        val currentTaskItem = currentTask.value.currentTaskItem.value
        val currentTestItem = currentTask.value.currentTestItem.value
        val progress = currentTask.value.progress

        lastTaskInfo.value = TaskInfo(id, title, VocabularyTitle(vocabularyTitle))
        lastTaskInfo.value.currentTaskItem.value = currentTaskItem
        lastTaskInfo.value.currentTestItem.value = currentTestItem
        lastTaskInfo.value.progress = progress
    }

}