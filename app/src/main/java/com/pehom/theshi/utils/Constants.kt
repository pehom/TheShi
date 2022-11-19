package com.pehom.theshi.utils

import com.pehom.theshi.data.localdata.DatabaseRepository

object Constants {
    const val STUDENT_PHONE = "studentPhone"
    const val DRAWER_ADD_NEW_TASK = 0
    const val DRAWER_ADD_STUDENT = 1
    const val DRAWER_USER_PROFILE = 2
    const val DRAWER_PENDING_REQUESTS = 3
    const val AUTH_ID = "authId"
    const val FS_ID = "fsId"
    const val USERS = "Users"
    const val PHONE_NUMBER = "phoneNumber"
    const val EMAIL = "email"
    const val TASKS_BY_USER = "tasksByUser"
    const val STUDENTS = "students"
    const val AVAILABLE_VOCABULARIES = "availableVocabularies"
    const val FUNDS = "funds"
    const val NAME = "name"
    const val TASK_INFO_DIVIDER = "'!'"
    const val TASKS_DIVIDER = "'|'"
    const val STUDENT_DIVIDER = "||"
    const val STUDENTS_DIVIDER = "^^"
    const val VOCABULARY_TITLE_DIVIDER = "{{"
    const val APP_SHARED_PREF = "appSP"
    const val SHARED_PREF_LAST_USER_ID = "lastUserId"
    const val WORDBOOK = "wordbook"
    const val TASK_ID = "taskId"
    const val TASK_TITLE = "title"
    const val VOCABULARY_TITLE = "vocabularyTitle"
    const val CURRENT_TEST_ITEM = "currentTestItem"
    const val CURRENT_TASK_ITEM = "currentTaskItem"
    const val PROGRESS = "progress"
    const val LEARNED_WORDS = "learnedWords"
    const val PENDING = "pending"
    const val DECLINED = "declined"
    const val ACCEPTED = "accepted"
    const val CANCELLED = "cancelled"
    const val DISMISSED = "dismissed"
    const val BLOCKED= "blocked"
    const val SENDER_FSID = "senderFsId"
    const val RECEIVER_FSID = "receiverFsID"
    const val STATE = "state"

    const val TASK = "task"
    const val ORIG = "orig"
    const val TRANS = "trans"
    const val IMG_URL = "imgUrl"

    const val PENDING_REQUESTS = "pendingRequests"
    const val SENDER_NAME = "senderName"
    const val SENDER_PHONE = "senderPhone"
    const val RECEIVER_NAME = "receiverName"
    const val RECEIVER_PHONE = "receiverPhone"
    const val LAST_TASK_ID_SFX = "lastTaskIdSfx"
    const val VOCABULARIES_MAIN_REF = "VocabulariesMk2"
    const val VOCABULARIES = "Vocabularies"
    const val VOCABULARY_FS_DOC_REF_PATH = "VocabularyFsDocRefPath"
    const val CURRENT_LEARNING_ITEM = "currentLearningItem"
    const val APP_STATE_DB_NAME = "appStateDb"
    const val THE_SHI_ROOM_DATABASE = "theShiRoomDatabase"
    const val SHARED_PREF_LAST_TASK_ID_SFX = "lastTaskIdSfx"
    const val ITEMS = "Items"
    const val TIMESTAMP = "timeStamp"
    const val VCB_TIMESTAMP = "vcbTimeStamp"
    const val STUDENT_FS_ID = "studentFsId"
    const val MENTOR_FS_ID = "mentorFsId"
    const val WRONG_TEST_ANSWERS = "wrongTestAnswers"
    const val USER_FS_ID = "userFsId"
    const val DETAILS = "details"
    const val VOCABULARY_ID = "vocabularyId"
    const val ALL_WORDS_WORDBOOK_TASK = "allWordsWordbookTask"
    const val IS_AVAILABLE = "isAvailable"
    const val PRICE = "price"
    const val WORDBOOK_TASK_ROOM_ITEM = "wbTaskRoomItem"
    const val MODE_GAME_SCREEN = 4
    const val MODE_TEST_SCREEN = 5
    const val MODE_LEARNING_SCREEN = 10
    const val MODE_TASK_INFO = 12
    const val NEW_VOCABULARY_XL = "newVocabularyXl"
    const val OPEN_DOWNLOADS_REQUEST_CODE = 69
    const val SHARED_PREF_SCREEN_STATE = "sharedPrefScreenState"
    const val ADMINS = "Admins"
    const val SYNC_COUNT = "syncCount"
    const val TASKS_BY_MENTOR = "tasksByMentor"
    const val TASKS_BY_MENTOR_GOT_CHANGES = "tasksByMentorGotChanges"
    const val REFERENCE = "reference"
    const val IS_CHECKED = "isChecked"
    const val MENTORS = "mentors"
    const val STATUS = "status"
    const val STATUS_IN_PROGRESS = "inProgress"
    const val STATUS_CANCELLED = "cancelled"
    const val STATUS_FINISHED = "finished"
    const val PASSWORD = "password"
    lateinit var REPOSITORY: DatabaseRepository



}