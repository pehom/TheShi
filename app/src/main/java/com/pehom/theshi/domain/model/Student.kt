package com.pehom.theshi.domain.model

import com.pehom.theshi.utils.Constants

class Student(val fsId: FsId, var name: String, val phone: String) {
  //  val wordbook = mutableListOf<String>()

    override fun toString(): String {
        val divider = Constants.STUDENT_DIVIDER
        return fsId.value + divider + name + divider + phone
    }
}