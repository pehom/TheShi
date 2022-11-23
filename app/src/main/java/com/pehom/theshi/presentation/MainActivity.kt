package com.pehom.theshi.presentation

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.pehom.theshi.presentation.screens.StarterScreen
import com.pehom.theshi.presentation.screens.adminscreen.AdminScreen
import com.pehom.theshi.presentation.screens.authscreen.RegisterScreen
import com.pehom.theshi.presentation.screens.authscreen.SignInScreen
import com.pehom.theshi.presentation.screens.availablevocabulariesscreen.AvailableVocabulariesScreen
import com.pehom.theshi.presentation.screens.developerscreen.DeveloperScreen
import com.pehom.theshi.presentation.screens.loginscreen.LoginScreen
import com.pehom.theshi.presentation.screens.mentorscreen.MentorScreen
import com.pehom.theshi.presentation.screens.requestsScreen.RequestsScreen
import com.pehom.theshi.presentation.screens.studentscreen.StudentScreen
import com.pehom.theshi.presentation.screens.taskscreen.TaskScreen
import com.pehom.theshi.presentation.screens.editprofilescreen.EditProfileScreen
import com.pehom.theshi.presentation.screens.usermentorsscreen.UserMentorsScreen
import com.pehom.theshi.presentation.screens.wordbookscreen.WordbookScreen
import com.pehom.theshi.presentation.screens.wordbookscreen.WordbookTaskScreen
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.presentation.viewmodel.MainViewModelFactory
import com.pehom.theshi.ui.theme.TheShiTheme
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var vm: MainViewModel
    private var tts: TextToSpeech? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // if (!isDeviceProtectedStorage )
        auth = FirebaseAuth.getInstance()
        vm = ViewModelProvider(this, MainViewModelFactory(this, this.application))[MainViewModel::class.java]
        vm.sharedPreferences = getSharedPreferences(Constants.APP_SHARED_PREF, MODE_PRIVATE)
        if (vm.sharedPreferences.contains(Constants.SHARED_PREF_TASKS_FILTER)){
            val filterValue = vm.sharedPreferences.getString((Constants.SHARED_PREF_TASKS_FILTER), Constants.FILTER_ALL)
            vm.tasksFilterState.value =filterValue!!
        }
        tts = TextToSpeech(this, this)

        if (vm.sharedPreferences.contains(Constants.SHARED_PREF_LAST_USER_ID) ){
            val sharedUserFsId = vm.sharedPreferences.getString(Constants.SHARED_PREF_LAST_USER_ID, null)
         //   if ( sharedUserFsId != ""){
            if (sharedUserFsId != null) {
                    vm.useCases.setUserByUserFsIdRoomUseCase.execute(vm, sharedUserFsId){
                        if (isNetworkAvailable()){

                            vm.useCases.setViewmodelNetworkItemsFsUseCase.execute(vm){
                                vm.isViewModelSet.value = true
                                if (vm.isStarterScreenEnded.value && vm.user.value.fsId.value != ""){
                                    vm.screenState.value = vm.MODE_STUDENT_SCREEN
                                } else {
                                    vm.screenState.value = vm.MODE_LOGIN_SCREEN
                                }
                            }
                        }
                    }
                } else {
                    vm.isViewModelSet.value = true
                    if (vm.isStarterScreenEnded.value){
                        vm.screenState.value = vm.MODE_LOGIN_SCREEN
                    }
                }
        } else {
            vm.isViewModelSet.value = true
            if (vm.isStarterScreenEnded.value){
                vm.screenState.value = vm.MODE_LOGIN_SCREEN
            }
        }
        setContent {
            TheShiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SetupScreen(vm, auth)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        when (vm.screenState.value) {
            vm.MODE_TASK_SCREEN -> {
                if (vm.currentTask.value.id != "") {
                    vm.currentTask.value.isTestGoing.value = false
                  //  vm.currentTaskRoomItem.value.incrementSyncCount()
                    vm.useCases.updateTaskFsUseCase.execute(vm, vm.currentTaskRoomItem.value){}
                    vm.viewModelScope.launch(Dispatchers.IO) {
                        Constants.REPOSITORY.updateTaskRoomItem(vm.currentTaskRoomItem.value){}
                    }
                }
                vm.screenState.value = vm.MODE_STUDENT_SCREEN
            }
            vm.MODE_MENTOR_SCREEN -> {
                if (vm.isStudentProfileShown.value){
                    vm.lastStudent.value = vm.currentStudent.value
                    vm.isStudentProfileShown.value =!vm.isStudentProfileShown.value
                }
                else super.onBackPressed()
            }
            vm.MODE_STUDENT_SCREEN -> super.onBackPressed()
            vm.MODE_REGISTER_SCREEN -> vm.screenState.value = vm.MODE_LOGIN_SCREEN
            vm.MODE_LOGIN_SCREEN -> super.onBackPressed()
            vm.MODE_SIGN_IN_SCREEN -> vm.screenState.value = vm.MODE_LOGIN_SCREEN
            vm.MODE_DEVELOPER_SCREEN -> {
                vm.screenState.value = vm.MODE_STUDENT_SCREEN
            }
            vm.MODE_WORDBOOK_SCREEN -> {
                vm.screenState.value = vm.MODE_STUDENT_SCREEN
            }
            vm.MODE_WORDBOOK_TASK_SCREEN -> {
                vm.viewModelScope.launch(Dispatchers.IO) {
                    Constants.REPOSITORY.updateTaskRoomItem(vm.currentWordbookTaskRoomItem.value){
                        vm.screenState.value = vm.MODE_WORDBOOK_SCREEN
                    }
                }
            }
            vm.MODE_ADMIN_SCREEN -> {
                vm.screenState.value = vm.MODE_STUDENT_SCREEN
            }
            vm.MODE_REQUESTS_SCREEN -> {
                vm.screenState.value = vm.lastScreen
            }
            vm.MODE_USER_MENTORS_SCREEN -> {
                vm.screenState.value = vm.lastScreen
                vm.viewModelScope.launch(Dispatchers.IO) {
                    val mentors = Constants.REPOSITORY.readMentorRoomItemsByUserFsIdAsList(vm.user.value.fsId.value)
                    if (mentors.isNotEmpty()){
                        mentors.forEach {
                            if (it.hasChanges){
                                vm.useCases.updateMentorFsUseCase.execute(it, vm.user.value.fsId.value){result ->
                                    if (result){
                                        vm.viewModelScope.launch(Dispatchers.IO) {
                                            it.hasChanges = false
                                            Constants.REPOSITORY.updateMentorRoomItem(it)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            vm.MODE_USER_INFO_SCREEN -> {
                vm.screenState.value = vm.lastScreen
            }
            vm.MODE_AVAILABLE_VOCABULARIES_SCREEN -> {
                vm.screenState.value = vm.lastScreen
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "onStart() invoked")

    }

    override fun onPause() {
        super.onPause()
        Log.d("lifecycle", "onPause() invoked")
    }

    override fun onStop() {
        Log.d("lifecycle", "onStop() invoked")
        super.onStop()
    }

    override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if (vm.currentTaskRoomItem.value.id != "" && vm.currentTaskRoomItem.value.id != "taskId") {
            //  vm.currentTaskRoomItem.value.incrementSyncCount()
            vm.useCases.updateTaskFsUseCase.execute(vm, vm.currentTaskRoomItem.value){}
            //  vm.currentWordbookTaskRoomItem.value.incrementSyncCount()
            vm.useCases.updateTaskFsUseCase.execute(vm, vm.currentWordbookTaskRoomItem.value){}
            vm.viewModelScope.launch(Dispatchers.IO) {
                Constants.REPOSITORY.updateTaskRoomItem(vm.currentTaskRoomItem.value){}
                Constants.REPOSITORY.updateTaskRoomItem(vm.currentWordbookTaskRoomItem.value){}
            }
        }
        val sharedPref = getSharedPreferences(Constants.APP_SHARED_PREF, MODE_PRIVATE)
        sharedPref.edit().putString(Constants.SHARED_PREF_LAST_USER_ID, vm.user.value.fsId.value).apply()
        sharedPref.edit().putInt(Constants.SHARED_PREF_SCREEN_STATE, vm.screenState.value).apply()
        sharedPref.edit().putString(Constants.SHARED_PREF_TASKS_FILTER, vm.tasksFilterState.value).apply()
        Log.d("lifecycle", "onDestroy() invoked")
        super.onDestroy()
    }

    override fun onInit(status: Int) {
        Log.d("TTS", "onInit invoked")
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            }
        }
        else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    @Composable
    private fun SetupScreen(
        viewModel: MainViewModel,
        auth: FirebaseAuth,
    ) {
        when (viewModel.screenState.value) {
            viewModel.MODE_STUDENT_SCREEN -> {
                StudentScreen(viewModel, auth)
                viewModel.drawerType.value = Constants.DRAWER_USER_PROFILE
                viewModel.lastScreen = viewModel.MODE_STUDENT_SCREEN
            }
            viewModel.MODE_TASK_SCREEN ->{ TaskScreen( viewModel, tts) }
            viewModel.MODE_MENTOR_SCREEN -> {
                MentorScreen(viewModel, auth)
                viewModel.lastScreen = viewModel.MODE_MENTOR_SCREEN
            }
            viewModel.MODE_LOGIN_SCREEN -> { LoginScreen(viewModel, auth) }
            viewModel.MODE_REGISTER_SCREEN -> { RegisterScreen(viewModel, auth) }
            viewModel.MODE_SIGN_IN_SCREEN -> { SignInScreen( viewModel,auth ) }
            viewModel.MODE_STARTER_SCREEN -> { StarterScreen(viewModel, auth) }
            viewModel.MODE_DEVELOPER_SCREEN -> { DeveloperScreen(viewModel, auth) }
            viewModel.MODE_WORDBOOK_SCREEN -> { WordbookScreen(viewModel) }
            viewModel.MODE_WORDBOOK_TASK_SCREEN -> { WordbookTaskScreen(viewModel, tts) }
            viewModel.MODE_ADMIN_SCREEN -> { AdminScreen(viewModel) }
            viewModel.MODE_REQUESTS_SCREEN -> { RequestsScreen(viewModel) }
            viewModel.MODE_USER_MENTORS_SCREEN -> UserMentorsScreen(viewModel)
            viewModel.MODE_USER_INFO_SCREEN -> EditProfileScreen(viewModel)
            viewModel.MODE_AVAILABLE_VOCABULARIES_SCREEN -> AvailableVocabulariesScreen(viewModel)
            viewModel.MODE_SETTINGS_SCREEN -> DeveloperScreen(viewModel, auth)
        }
    }
}




