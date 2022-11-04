package com.pehom.theshi.utils

import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.Student

fun studentFromString(source: String): Student {
    val items = source.trim().split(Constants.STUDENT_DIVIDER)
    return if (items.size == 2) {
        Student(FsId(items[0]), items[1])
    } else
        Student(FsId(""), "")

}