package com.pehom.theshi.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pehom.theshi.data.localdata.approomdatabase.AppRoomDatabase
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.data.localdata.repository.RoomRepository
import com.pehom.theshi.domain.model.*
import com.pehom.theshi.domain.usecase.*
import com.pehom.theshi.domain.usecase.firestoreusecase.*
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.domain.usecase.firestoreusecase.SetupMainViewModelFsUseCase
import com.pehom.theshi.domain.usecase.roomusecase.*
import com.pehom.theshi.utils.TaskIdFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
   // val MODE_LEARNING_SCREEN = 10
    val MODE_DEVELOPER_SCREEN = 11
  //  val MODE_TASK_INFO = 12
    val MODE_WORDBOOK_SCREEN = 13
    val MODE_WORDBOOK_TASK_SCREEN = 14
    val MODE_ADMIN_SCREEN = 15
    val MODE_REQUESTS_SCREEN = 16
    val MODE_USER_MENTORS_SCREEN = 17
    val MODE_USER_INFO_SCREEN = 18
    val MODE_AVAILABLE_VOCABULARIES_SCREEN = 19
    val MODE_SETTINGS_SCREEN = 20

    var currentTask = mutableStateOf(Task("","", Vocabulary(VocabularyTitle("","","",0), mutableListOf(VocabularyItemScheme("","","")))))
    val wordbook = mutableSetOf<VocabularyItemScheme>()
    val students = mutableStateListOf<Student>()
    val switchState = mutableStateOf(true)
    val screenState = mutableStateOf(MODE_STARTER_SCREEN)
    var lastScreen = -1
    var starterCount = 2
    val currentTaskRoomItem = mutableStateOf(TaskRoomItem("","","","", "","",false,0,0,0,0, mutableMapOf()))
    val studentNumber =  mutableStateOf(0)
    val studentTasks = mutableStateListOf<TaskInfo>()
    val studentWordbook = mutableStateListOf<String>()
    val currentStudent = mutableStateOf(Student(FsId(""),"",""))
    val lastStudent = mutableStateOf(Student(FsId(""),"",""))
   // val mentors = mutableStateListOf<Mentor>()
    val tasksFilterState = mutableStateOf(Constants.FILTER_ALL)
    val isStudentProfileShown = mutableStateOf(false)
    val requestsAdd = mutableStateListOf<RequestAdd>()
    val currentWordbookVocabulary = mutableStateOf(Vocabulary(VocabularyTitle(""), mutableListOf()))
    val allVocabularyTitles = mutableStateListOf<VocabularyTitle>()
    val drawerType = mutableStateOf(Constants.DRAWER_USER_PROFILE)
    val user = mutableStateOf(User(FsId(""),"","","",Funds()))
    var lastTaskInfo = mutableStateOf(TaskInfo("taskId","taskTitle",VocabularyTitle(), "taskStatus"))
    val isStarterScreenEnded = mutableStateOf(false)
    val isViewModelSet = mutableStateOf(false)
    var isAdmin = mutableStateOf(false)
    val currentWordbookTaskRoomItem = mutableStateOf(TaskRoomItem(id = Constants.WORDBOOK_TASK_ROOM_ITEM))
    val loadedVocabularies = mutableSetOf<Vocabulary>()
    val vocabularyTitlesListItemOrigItems = mutableMapOf<String, MutableList<String>>()
    private val vocabularyTitlesIdsList = MutableStateFlow(listOf<Int>())
    val vocabularyTitlesIds: StateFlow<List<Int>> get() = vocabularyTitlesIdsList
  //  lateinit var taskIdFactory: TaskIdFactory
    lateinit var sharedPreferences: SharedPreferences


    val useCases = UseCases(
        SetTaskByVocabulary(),
        SignInFsUseCase(),
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
        DeleteUserTaskByIdFsUseCase(),
        GetVocabularyByFsDocRefRoomUseCase(),
        SetAllWordsWordbookTaskUseCase(),
        UploadVocabularyToFsUseCase(),
        CheckExistedVocabularyByTitleAndLevelFsUseCase(),
        CheckIsAdminFsUseCase(),
        GetTaskByReferenceFsUseCase(),
        SyncUserTasksUseCase(),
        WriteNewTasksByMentorToRoomFsUseCase(),
        ReadStudentWordbookFsUseCase(),
        AddMentorFsUseCase(),
        DeleteStudentTaskByIdFsUseCase(),
        ReadAllUserMentorsFsUseCase(),
        ReadNewUserTasksByMentorFsUseCase(),
        CancelStudentTaskByIdFsUseCase(),
        ReadNewUserMentorsFsUseCase(),
        ReadNewStudentsFsUseCase(),
        WriteNewStudentsToRoomFsUseCase(),
        ReadNewAvailableVocabulariesFsUseCase(),
        WriteNewAvailableVocabulariesToRoomUseCase(),
        WriteNewAvailableWordsToRoomByFsDocRefPathUseCase(),
        SetUserByUserFsIdRoomUseCase(),
        SignInRoomUseCase(),
        UpdateUsernameFsUseCase(),
        UpdateMentorNameFsUseCase(),
        LoadWholeDataFsUseCase(),
        ReadUserinfoByAuthIdFsUseCase(),
        SetViewmodelNetworkItemsFsUseCase(),
        UpdateMentorFsUseCase()
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
            lastStudent.value = currentStudent.value
        }
        else {
            if (!isStudentProfileShown.value) {
                drawerType.value = Constants.DRAWER_ADD_STUDENT
                screenState.value = MODE_MENTOR_SCREEN
            } else {
                drawerType.value = Constants.DRAWER_ADD_NEW_TASK
                screenState.value = MODE_MENTOR_SCREEN
            }
        }
    }

    fun onVcbTitleItemClicked(itemId: Int, item: VocabularyTitle) {
        Log.d("qqq", "OrigItems.containsKey(${item.fsDocRefPath}) = ${vocabularyTitlesListItemOrigItems.containsKey(item.fsDocRefPath)}")
        vocabularyTitlesIdsList.value = vocabularyTitlesIdsList.value.toMutableList().also {
            if (it.contains(itemId)) {
                it.remove(itemId)
            } else {
                if (vocabularyTitlesListItemOrigItems[item.fsDocRefPath].isNullOrEmpty()) {
                    useCases.readVcbItemsByVcbDocRefFsUseCase.execute(item.fsDocRefPath) {
                        val origs = mutableListOf<String>()
                        it.forEach { vcbItemScheme ->
                            origs.add(vcbItemScheme.orig)
                            Log.d("qqq", "origFs = ${vcbItemScheme.orig}")
                        }
                        // vocabularyTitlesListItemOrigItems[item.fsDocRefPath] = mutableStateListOf()
                        vocabularyTitlesListItemOrigItems[item.fsDocRefPath]?.addAll(origs)
                        Log.d("qqq", "OrigItems.containsKey(vcbTitle.fsDocRefPath) = ${vocabularyTitlesListItemOrigItems.containsKey(item.fsDocRefPath)}")

                    }
                }
                it.add(itemId)
            }
        }
    }

    fun saveAppStateToSharedPreferences(){
        sharedPreferences.edit().putString(Constants.SHARED_PREF_LAST_USER_ID, user.value.fsId.value).apply()
       // sharedPreferences.edit().putString(Constants.)
    }

    private fun initRoomDb(context: Context, onSuccess: ()-> Unit) {

        val taskRoomDao = AppRoomDatabase.getInstance(context).getTaskRoomDao()
        val wordbookDao = AppRoomDatabase.getInstance(context).getWordbookDao()
        val vocabularyRoomDao = AppRoomDatabase.getInstance(context).getVocabularyRoomDao()
        val studentDao = AppRoomDatabase.getInstance(context).getStudentDao()
        val availableWordsRoomDao = AppRoomDatabase.getInstance(context).getAvailableWordsRoomDao()
        val mentorRoomDao = AppRoomDatabase.getInstance(context).getMentorRoomDao()
        val userRoomDao = AppRoomDatabase.getInstance(context).getUserRoomDao()
        Constants.REPOSITORY = RoomRepository(taskRoomDao, wordbookDao, vocabularyRoomDao, studentDao, availableWordsRoomDao,mentorRoomDao, userRoomDao)
        onSuccess()
    }
}