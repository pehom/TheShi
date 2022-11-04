package com.pehom.theshi.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapConverter {

    @TypeConverter
    fun fromMap(map: Map<Int, String>): String =
        Gson().toJson(map)

    @TypeConverter
    fun toMap (string: String): Map<Int, String> =
        Gson().fromJson(string, object : TypeToken<Map<Int, String>>(){}.type)
}