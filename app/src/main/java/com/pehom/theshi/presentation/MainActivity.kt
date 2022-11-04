package com.pehom.theshi.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pehom.theshi.presentation.screens.StarterScreen
import com.pehom.theshi.presentation.screens.adminscreen.AdminScreen
import com.pehom.theshi.presentation.screens.authscreen.RegisterScreen
import com.pehom.theshi.presentation.screens.authscreen.SignInScreen
import com.pehom.theshi.presentation.screens.developerscreen.DeveloperScreen
import com.pehom.theshi.presentation.screens.learningscreen.LearningScreen
import com.pehom.theshi.presentation.screens.loginscreen.LoginScreen
import com.pehom.theshi.presentation.screens.mentorscreen.MentorScreen
import com.pehom.theshi.presentation.screens.studentscreen.StudentScreen
import com.pehom.theshi.presentation.screens.gamescreen.GameScreen
import com.pehom.theshi.presentation.screens.taskscreen.TaskScreen
import com.pehom.theshi.presentation.screens.testscreen.TestScreen
import com.pehom.theshi.presentation.screens.wordbookscreen.WordbookScreen
import com.pehom.theshi.presentation.screens.wordbookscreen.WordbookTaskScreen
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.presentation.viewmodel.MainViewModelFactory
import com.pehom.theshi.ui.theme.TheShiTheme
import com.pehom.theshi.utils.Constants
import com.pehom.theshi.utils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var vm: MainViewModel
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lifecycle", "onCreate() invoked")

        // if (!isDeviceProtectedStorage )
        auth = FirebaseAuth.getInstance()
        Log.d("viewModel", "on create auth.currentUser = ${auth.currentUser}")

        vm = ViewModelProvider(this, MainViewModelFactory(this, this.application))[MainViewModel::class.java]
        vm.sharedPreferences = getSharedPreferences(Constants.APP_SHARED_PREF, MODE_PRIVATE)

        /*val shPref = getSharedPreferences(Constants.APP_SHARED_PREF, MODE_PRIVATE)
        if (isNetworkAvailable()) {
            Log.d("onStart", "onStart currentUserUid = ${auth.currentUser?.uid}")
            if (auth.currentUser != null) {
                vm.useCases.setupMainViewModelFsUseCase.execute(this, vm, auth.currentUser!!){}
            }
        } else if (shPref.contains(Constants.SHARED_PREF_USER_ID) ) {
            // TODO readUserInfoRoom()
        }
        //TODO network status checking needed
        else
            vm.screenState.value = vm.MODE_LOGIN_SCREEN*/

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
        if (isNetworkAvailable()) {
            if (auth.currentUser != null) {
                Log.d("ppp", "auth.currentUser = ${auth.currentUser}")

                vm.useCases.setupMainViewModelFsUseCase.execute(this, vm, auth.currentUser!!){}
            }
        } else if (vm.sharedPreferences.contains(Constants.SHARED_PREF_USER_ID) ) {
            // TODO readUserInfoRoom()
        }
        //TODO network status checking needed
        else
            vm.screenState.value = vm.MODE_LOGIN_SCREEN


    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        when (vm.screenState.value) {
            vm.MODE_TASK_SCREEN -> {
                if (vm.currentTask.value.id != "") {
                    vm.currentTask.value.isTestGoing.value = false
                    vm.useCases.updateTaskFsUseCase.execute(vm, vm.currentTaskRoomItem){}
                    vm.viewModelScope.launch(Dispatchers.IO) {
                        Constants.REPOSITORY.updateTaskRoomItem(vm.currentTaskRoomItem.value){}
                    }
                }
                vm.screenState.value = vm.MODE_STUDENT_SCREEN
            }
            vm.MODE_MENTOR_SCREEN -> {
                if (vm.isStudentProfileShown.value) vm.isStudentProfileShown.value =!vm.isStudentProfileShown.value
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
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "onStart() invoked")

    }

    override fun onPause() {
        super.onPause()
        Log.d("lifecycle", "onPause() invoked")
        if (vm.currentTaskRoomItem.value.id != "" && vm.currentTaskRoomItem.value.id != "taskId") {
            vm.useCases.updateTaskFsUseCase.execute(vm, vm.currentTaskRoomItem){}
            vm.useCases.updateTaskFsUseCase.execute(vm, vm.currentWordbookTaskRoomItem){}
            vm.viewModelScope.launch(Dispatchers.IO) {
                Constants.REPOSITORY.updateTaskRoomItem(vm.currentTaskRoomItem.value){}
            }
        }
        val sharedPref = getSharedPreferences(Constants.APP_SHARED_PREF, MODE_PRIVATE)
        sharedPref.edit().putString(Constants.SHARED_PREF_USER_ID, vm.user.value.fsId.value).apply()
        sharedPref.edit().putInt(Constants.SHARED_PREF_SCREEN_STATE, vm.screenState.value).apply()

    }

    override fun onStop() {
        super.onStop()
        Log.d("lifecycle", "onStop() invoked")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycle", "onDestroy() invoked")

    }
}

@Composable
fun SetupScreen(
    viewModel: MainViewModel,
    auth: FirebaseAuth,
) {
    when (viewModel.screenState.value) {
        viewModel.MODE_STUDENT_SCREEN -> StudentScreen(viewModel, auth)
        viewModel.MODE_TASK_SCREEN -> TaskScreen( viewModel)
        viewModel.MODE_MENTOR_SCREEN -> MentorScreen(viewModel, auth)
        viewModel.MODE_LOGIN_SCREEN -> LoginScreen(viewModel, auth)
        viewModel.MODE_REGISTER_SCREEN -> RegisterScreen(viewModel, auth)
        viewModel.MODE_SIGN_IN_SCREEN -> SignInScreen( viewModel,auth )
        viewModel.MODE_STARTER_SCREEN -> StarterScreen(viewModel, auth)
        viewModel.MODE_DEVELOPER_SCREEN -> DeveloperScreen(viewModel)
        viewModel.MODE_WORDBOOK_SCREEN -> WordbookScreen(viewModel)
        viewModel.MODE_WORDBOOK_TASK_SCREEN -> WordbookTaskScreen(viewModel)
        viewModel.MODE_ADMIN_SCREEN -> AdminScreen(viewModel)
    }
}


