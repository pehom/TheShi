package com.pehom.theshi.utils

import com.pehom.theshi.domain.model.Student

fun studentsFromString(source: String): MutableList<Student> {
    val items = source.trim().split(Constants.STUDENTS_DIVIDER)
    val result = mutableListOf<Student>()
    if (items.isNotEmpty()){
        items.forEach() {
            result.add(studentFromString(it))
        }
    }
    return result
}