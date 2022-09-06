package com.pehom.theshi.domain.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class Task() {
    private val DIVIDER = "*/*"
    lateinit var title: String
    lateinit var vocabulary: Vocabulary
    lateinit var currentTaskWord: MutableState<VocabularyItemScheme>
    lateinit var currentTestWord: MutableState<VocabularyItemScheme>
    lateinit var taskWordsRemain: MutableState<Int>
    lateinit var testWordsRemain: MutableState<Int>
    val currentTaskWordDisplay = mutableStateOf("")
    val currentTestWordDisplay = mutableStateOf("")
    val currentTaskItem = mutableStateOf(0)
    val currentTestItem = mutableStateOf(0)
    val correctTestAnswers: MutableList<VocabularyItemScheme> = mutableListOf()
    val wrongTestAnswers: MutableMap<Int, String> = mutableMapOf()
    var isTaskDone: Boolean = false
    var isTestDone: Boolean = false
    var progress = 0
    var isTestGoing = mutableStateOf(false)
    var perfectTestsNumber = 0
    var currentWordDisplayTextDone = "the end"

    constructor(title: String, vocabulary: Vocabulary) : this() {
        this.title = title
        this.vocabulary = vocabulary
        this.currentTaskWord = mutableStateOf(this.vocabulary.items[currentTaskItem.value])
        this.currentTestWord = mutableStateOf(this.vocabulary.items[currentTestItem.value])
        this.taskWordsRemain = mutableStateOf(this.vocabulary.items.size-this.currentTaskItem.value)
        this.testWordsRemain = mutableStateOf(this.vocabulary.items.size-this.currentTestItem.value)
        this.currentTaskWordDisplay.value = currentTaskWord.value.orig
        this.currentTestWordDisplay.value = currentTestWord.value.orig

/*        if (!this.isDone){
            this.currentTaskWordDisplay.value = currentTaskWord.value.orig
        } else
            this.currentTaskWordDisplay.value = currentWordDisplayTextDone*/
    }

    constructor(_title: String, _vocabulary: Vocabulary, _currentVocabularyItem: Int, _currentTestItem: Int, _isDone: Boolean) : this() {
        this.title = _title
        this.vocabulary = _vocabulary
        this.currentTaskItem.value = _currentVocabularyItem
        this.currentTestItem.value = _currentTestItem
        this.isTaskDone = _isDone
        this.currentTaskWord = mutableStateOf(this.vocabulary.items[currentTaskItem.value])
        this.currentTestWord = mutableStateOf(this.vocabulary.items[currentTestItem.value])
        this.taskWordsRemain = mutableStateOf(this.vocabulary.items.size-this.currentTaskItem.value)
        this.testWordsRemain = mutableStateOf(this.vocabulary.items.size-this.currentTestItem.value)
        this.currentTaskWordDisplay.value = currentTaskWord.value.orig
        this.currentTestWordDisplay.value = currentTestWord.value.orig
        /*  if (!this.isDone){
              this.currentTaskWordDisplay.value = currentTaskWord.value.orig
          } else
              this.currentTaskWordDisplay.value = currentWordDisplayTextDone*/
    }

    override fun toString(): String {
        var s = title
        s+DIVIDER+currentTaskItem.value+DIVIDER+currentTestItem.value+DIVIDER+isTaskDone+DIVIDER+vocabulary.toString()
        return s
    }

    fun fromString(source: String): Task? {
        val values = source.trim().split(DIVIDER)
        return if (values.size == 6) {
            val title = values[0]
            val progress = values[1].toInt()
            val currentTestItem = values[2].toInt()
            val isDone = values[3].toBoolean()
            val vocabulary = Vocabulary("", mutableListOf(VocabularyItemScheme("","",""))).fromString(values[4])
            Task(title,vocabulary,progress,currentTestItem,isDone)
        } else null
    }

    fun taskRefresh() {
        taskWordsRemain.value = vocabulary.items.size - currentTaskItem.value
        if (taskWordsRemain.value > 0){
            currentTaskWord.value = vocabulary.items[currentTaskItem.value]
            this.currentTaskWordDisplay.value = currentTaskWord.value.orig
        }else
            this.currentTaskWordDisplay.value = currentWordDisplayTextDone
    }

    fun testRefresh() {
        testWordsRemain.value = vocabulary.items.size - currentTestItem.value
        if (testWordsRemain.value > 0){
            currentTestWord.value = vocabulary.items[currentTestItem.value]
            this.currentTestWordDisplay.value = currentTestWord.value.orig
        }else
            this.currentTestWordDisplay.value = currentWordDisplayTextDone
    }

    fun setReadyForTask() {
        currentTaskItem.value = 0
        taskWordsRemain.value = vocabulary.items.size
        currentTaskWord.value = vocabulary.items[0]
        currentTaskWordDisplay.value = currentTaskWord.value.orig
    }

}