package com.pehom.theshi.utils

import android.content.SharedPreferences
import android.util.Log
import androidx.activity.ComponentActivity
import com.pehom.theshi.domain.model.FsId

class TaskIdFactory(val userFsId: FsId, var lastIdSfx: Int,  val sharedPreferences: SharedPreferences) {
    var lastId = ""

    fun createId(): String {
        if (sharedPreferences.contains(Constants.SHARED_PREF_LAST_TASK_ID_SFX)) {
            val shPrefSfx = sharedPreferences.getInt(Constants.SHARED_PREF_LAST_TASK_ID_SFX, 0)
            if (shPrefSfx > lastIdSfx) lastIdSfx = shPrefSfx
        }

        lastIdSfx++
        lastId = userFsId.value + lastIdSfx

        sharedPreferences.edit().putInt(Constants.SHARED_PREF_LAST_TASK_ID_SFX, lastIdSfx).apply()
        Log.d("createTaskId", "lastId = $lastId")
        return lastId
    }
}