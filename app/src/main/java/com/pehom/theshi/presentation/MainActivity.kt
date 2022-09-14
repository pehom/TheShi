package com.pehom.theshi.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pehom.theshi.presentation.screens.authscreen.RegisterScreen
import com.pehom.theshi.presentation.screens.authscreen.SignInScreen
import com.pehom.theshi.presentation.screens.loginscreen.LoginScreen
import com.pehom.theshi.presentation.screens.mentorscreen.MentorScreen
import com.pehom.theshi.presentation.screens.studentscreen.StudentScreen
import com.pehom.theshi.presentation.screens.taskscreen.TaskScreen
import com.pehom.theshi.presentation.screens.testscreen.TestScreen
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.presentation.viewmodel.MainViewModelFactory
import com.pehom.theshi.ui.theme.TheShiTheme
import com.pehom.theshi.utils.Constants


class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    // private lateinit var fireDatabase: FirebaseDatabase
    private lateinit var vm: MainViewModel
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isDeviceProtectedStorage )
            auth = FirebaseAuth.getInstance()
        vm = ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]

        //  fireDatabase = FirebaseDatabase.getInstance()
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

    override fun onBackPressed() {
        when (vm.screenState.value) {
            vm.MODE_TASK_SCREEN -> {
                vm.saveCurrentTaskInfo()
                vm.screenState.value = vm.MODE_STUDENT_SCREEN }
            vm.MODE_TEST_SCREEN -> {
                vm.currentTask.value.isTestGoing.value = false
                vm.saveCurrentTaskInfo()
                vm.screenState.value = vm.MODE_TASK_SCREEN
            }
            vm.MODE_MENTOR_SCREEN -> {
                if (vm.isStudentProfileShown.value) vm.isStudentProfileShown.value =!vm.isStudentProfileShown.value
                else super.onBackPressed()}
            vm.MODE_STUDENT_SCREEN -> super.onBackPressed()
            vm.MODE_REGISTER_SCREEN -> vm.screenState.value = vm.MODE_LOGIN_SCREEN
            vm.MODE_LOGIN_SCREEN -> super.onBackPressed()
            vm.MODE_SIGN_IN_SCREEN -> vm.screenState.value = vm.MODE_LOGIN_SCREEN
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null){
            //TODO network status checking needed
            val shPref = getSharedPreferences(Constants.APP_SHARED_PREF, MODE_PRIVATE)
            if (shPref.contains(Constants.SHARED_PREF_USER_ID)) {
                val fsUserId = shPref.getString(Constants.SHARED_PREF_USER_ID, "")
                vm.user = fsUserId?.let { vm.useCases.readFirestoreUserInfoUseCase.execute(it, this){} }!!
                vm.screenState.value = vm.MODE_STUDENT_SCREEN
            }
        }
        else
            vm.screenState.value = vm.MODE_LOGIN_SCREEN
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = getSharedPreferences(Constants.APP_SHARED_PREF, MODE_PRIVATE)
        sharedPref.edit().putString(Constants.SHARED_PREF_USER_ID, vm.user.fsId).apply()
    }
}

@Composable
fun SetupScreen(
    viewModel: MainViewModel,
    auth: FirebaseAuth,
) {
    when (viewModel.screenState.value) {
        viewModel.MODE_STUDENT_SCREEN -> StudentScreen(viewModel, auth)
        viewModel.MODE_TASK_SCREEN -> TaskScreen(viewModel)
        viewModel.MODE_TEST_SCREEN -> TestScreen(viewModel)
        viewModel.MODE_MENTOR_SCREEN -> MentorScreen(viewModel, auth)
        viewModel.MODE_LOGIN_SCREEN -> LoginScreen(viewModel, auth)
        viewModel.MODE_REGISTER_SCREEN -> RegisterScreen(viewModel, auth)
        viewModel.MODE_SIGN_IN_SCREEN -> SignInScreen( viewModel,auth )
    }
}

