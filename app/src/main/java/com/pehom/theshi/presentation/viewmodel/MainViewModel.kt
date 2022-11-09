package com.pehom.theshi.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.pehom.theshi.data.localdata.approomdatabase.AppRoomDatabase
import com.pehom.theshi.data.localdata.approomdatabase.StudentRoomItem
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.data.localdata.repository.RoomRepository
import com.pehom.theshi.domain.model.*
import com.pehom.theshi.domain.usecase.*
import com.pehom.theshi.domain.usecase.firestoreusecase.*
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.domain.usecase.firestoreusecase.SetupMainViewModelFsUseCase
import com.pehom.theshi.domain.usecase.roomusecase.*
import com.pehom.theshi.utils.TaskIdFactory

class MainViewModel(
    val context: Context,
    val application: Application
    ) /*@Inject constructor(

)*/ : ViewModel() {
    val MODE_SIGN_IN_SCREEN = 8
    val MODE_REGISTER_SCREEN = 7
    val MODE_STUDENT_SCREEN = 1
    val MODE_MENTOR_SCREEN = 2
    val MODE_TASK_SCREEN = 4
    val MODE_TEST_SCREEN = 5
    val MODE_LOGIN_SCREEN = 6
    val MODE_STARTER_SCREEN =9
    val MODE_LEARNING_SCREEN = 10
    val MODE_DEVELOPER_SCREEN = 11
    val MODE_TASK_INFO = 12
    val MODE_WORDBOOK_SCREEN = 13
    val MODE_WORDBOOK_TASK_SCREEN = 14
    val MODE_ADMIN_SCREEN = 15

    var currentTask = mutableStateOf(Task("","", Vocabulary(VocabularyTitle("","","",0), mutableListOf(VocabularyItemScheme("","","")))))
    val wordbook = mutableSetOf<VocabularyItemScheme>()
    val students = mutableStateListOf<Student>()
    val switchState = mutableStateOf(true)
    val screenState = mutableStateOf(MODE_STARTER_SCREEN)
    val currentTaskNumber = mutableStateOf(0)
    val currentTaskRoomItem = mutableStateOf(TaskRoomItem("","","","", "",false,0,0,0,0, mutableMapOf()))
    val studentNumber =  mutableStateOf(0)
    val studentFsId = mutableStateOf("")
    val currentStudent = mutableStateOf(StudentRoomItem("","",""))
    val isStudentProfileShown = mutableStateOf(false)
    val requestsAdd = mutableStateListOf<RequestAdd>()
    val currentWordbookVocabulary = mutableStateOf(Vocabulary(VocabularyTitle(""), mutableListOf()))
    val allVocabularyTitles = mutableStateListOf<VocabularyTitle>()
    val drawerType = mutableStateOf(Constants.DRAWER_USER_PROFILE)
    val user = mutableStateOf(User(FsId(""),"","","",Funds()))
    var lastTaskInfo = mutableStateOf(TaskInfo("","",VocabularyTitle()))
    val isStarterScreenEnded = mutableStateOf(false)
    val isViewModelSet = mutableStateOf(false)
    val currentWordbookTaskRoomItem = mutableStateOf(TaskRoomItem(id = Constants.WORDBOOK_TASK_ROOM_ITEM))
    lateinit var taskIdFactory: TaskIdFactory
    lateinit var sharedPreferences: SharedPreferences


    val useCases = UseCases(
        SetTaskByVocabulary(),
        SignInUseCase(),
        CreateFirestoreAccountUseCase(),
        ReadFirestoreUserInfoUseCase(),
        AddUserTaskFsUseCase(),
        AddStudentTaskFsUseCase(),
        AddVocabularyToWordbookFsUseCase(),
        ReadWordbookFsUseCase(),
        CancelRequestAddUseCase(),
        DeclineRequestAddUseCase(),
        AcceptRequestAddUseCase(),
        ReadRequestsAddFsUseCase(),
        CreateRequestAddFsUseCase(),
        GetUserFsIdByPhoneFsUseCase(),
        SignOutUseCase(),
        AddStudentFsUseCase(),
        ReadStudentsFsUseCase(),
        ReadStudentTasksFsUseCase(),
        SetupMainViewModelFsUseCase(),
        SetTaskProgressFsUseCase(),
        SetTaskIdFactoryFsUseCase(),
        UpdateLastTaskIdSfxFsUseCase(),
        GetAllVocabularyTitlesFsUseCase(),
        UpdateTaskFsUseCase(),
        AddTaskRoomUseCase(),
        AddVocabularyToWordbookRoomUseCase(),
        AddStudentRoomUseCase(),
        ReadAllUserTasksFsUseCase(),
        SetCurrentTaskByTaskIdFsUseCase(),
        SetTaskByIdRoomUseCase(),
        BuyVocabularyWithLoadedItemsUseCase(),
        ReadVcbItemsByVcbDocRefFsUseCase(),
        ReadVcbTitleByFsDocRefPathFsUseCase(),
        CheckAvailableVocabularyByFsDocRefPathFsUseCase(),
        SaveAvailableVocabularyRoomUseCase(),
        GetWordbookSizeByUserFsId(),
        DeleteTaskByIdFsUseCase(),
        GetVocabularyByFsDocRefRoomUseCase(),
        SetAllWordsWordbookTaskUseCase(),
        UploadVocabularyToFsUseCase(),
        CheckExistedVocabularyByTitleAndLevelFsUseCase(),
        CheckIsAdminFsUseCase()
        )

    init {
        Log.d("viewModel", "viewModel created")
        initRoomDb(context) {
        }
    }

    override fun onCleared() {
        Log.d("onCleared", "MainViewModel.onCleared() has been invoked")
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
    }

    fun saveAppStateToSharedPreferences(){
        sharedPreferences.edit().putString(Constants.SHARED_PREF_USER_ID, user.value.fsId.value)
       // sharedPreferences.edit().putString(Constants.)
    }

    private fun initRoomDb(context: Context, onSuccess: ()-> Unit) {

        val taskRoomDao = AppRoomDatabase.getInstance(context).getTaskRoomDao()
        val wordbookDao = AppRoomDatabase.getInstance(context).getWordbookDao()
        val vocabularyRoomDao = AppRoomDatabase.getInstance(context).getVocabularyRoomDao()
        val studentDao = AppRoomDatabase.getInstance(context).getStudentDao()
        val availableWordsRoomDao = AppRoomDatabase.getInstance(context).getAvailableWordsRoomDao()
        val mentorRoomDao = AppRoomDatabase.getInstance(context).getMentorRoomDao()
        Constants.REPOSITORY = RoomRepository(taskRoomDao, wordbookDao, vocabularyRoomDao, studentDao, availableWordsRoomDao,mentorRoomDao)
        onSuccess()
    }
}