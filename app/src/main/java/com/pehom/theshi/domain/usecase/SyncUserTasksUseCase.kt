package com.pehom.theshi.domain.usecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SyncUserTasksUseCase {
    private val TAG = "SyncUserTasksUseCase"

    fun execute(
        viewModel: MainViewModel,
        onSuccess: () -> Unit
        ){
            val user = viewModel.user.value
            val roomTaskIds = mutableSetOf<String>()
            val fsUserTaskIds = mutableSetOf<String>()
            val fsMentorTaskIds = mutableSetOf<String>()
            val tasksUserMap = mutableMapOf<String, DocumentReference>()
            val tasksMentorMap = mutableMapOf<String,DocumentReference>()
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                Constants.REPOSITORY.readTaskRoomItemIdsByUserFsId(user.fsId.value).forEach {
                    roomTaskIds.add(it)
                }
                Firebase.firestore.collection(Constants.USERS).document(user.fsId.value).collection(Constants.TASKS_BY_USER).get()
                    .addOnSuccessListener { docs ->
                        for (doc in docs){
                            val id = doc[Constants.TASK_ID].toString()
                            Log.d(TAG, "taskByUserId = $id")
                            fsUserTaskIds.add(id)
                            tasksUserMap[id] = doc.reference
                        }
                        val dif = fsUserTaskIds.subtract(roomTaskIds)
                        if (dif.isNotEmpty()){
                            tasksUserMap.minus(dif)
                            tasksUserMap.values.forEach(){documentReference ->
                                viewModel.useCases.getTaskByReferenceFsUseCase.execute(documentReference){
                                    if (it!=null){
                                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                                            Constants.REPOSITORY.createTaskRoomItem(it){}
                                        }
                                    }
                                }
                            }
                        }
                        Firebase.firestore.collection(Constants.USERS).document(user.fsId.value).collection(Constants.TASKS_BY_MENTOR).get()
                            .addOnSuccessListener {
                                for (doc in docs){
                                    val id = doc[Constants.TASK_ID].toString()
                                    Log.d(TAG, "taskByMentorId = $id")
                                    fsMentorTaskIds.add(id)
                                    tasksMentorMap[id] = doc.reference
                                }
                                val difference = fsMentorTaskIds.subtract(roomTaskIds)
                                if (difference.isNotEmpty()){
                                    tasksMentorMap.minus(difference)
                                    tasksMentorMap.values.forEachIndexed(){index,documentReference ->
                                        viewModel.useCases.getTaskByReferenceFsUseCase.execute(documentReference){
                                            if (it!=null){
                                                viewModel.viewModelScope.launch(Dispatchers.IO) {
                                                    Constants.REPOSITORY.createTaskRoomItem(it){
                                                        if (index == tasksMentorMap.size-1) {
                                                            onSuccess()
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    onSuccess()
                                }
                            }
                            .addOnFailureListener {
                                Log.d(TAG, "getting tasks by mentor failed, Error: ${it.message}")
                            }
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "getting tasks by user failed, Error: ${it.message}")
                    }
            }
    }
}