package com.pehom.theshi.testdata

import androidx.compose.runtime.mutableStateListOf
import com.pehom.theshi.domain.model.Student

fun getStudents(): MutableList<Student> {
    return mutableStateListOf(Student("+79201234567", "Gena"), Student("+79202345678", "Jane"))
}