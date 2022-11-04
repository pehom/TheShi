package com.pehom.theshi.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class MainViewModelFactory(
    val context: Context,
    val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return MainViewModel( context, application) as T
    }

    /*override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context = context) as T
    }*/
}

