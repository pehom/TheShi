package com.pehom.theshi.domain.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf

class Task(val id:String, val title: String, var vocabulary: Vocabulary) {
    private val DIVIDER = "*/*"
   // lateinit var vocabulary: Vocabulary
    val currentTaskWord =  mutableStateOf(VocabularyItemScheme("","",""))
    val currentTestWord =  mutableStateOf(VocabularyItemScheme("","",""))
    val currentLearningWord = mutableStateOf(VocabularyItemScheme("","",""))
    val taskWordsRemain = mutableStateOf(vocabulary.items.size-1)
    val testWordsRemain = mutableStateOf(vocabulary.items.size-1)
    val learningWordsRemain = mutableStateOf(vocabulary.items.size-1)
    val currentTaskWordDisplay = mutableStateOf("")
    val currentTestWordDisplay = mutableStateOf("")
    val currentLearningWordDisplay = mutableStateOf("")
    val currentTaskItem = mutableStateOf(0)
    val currentTestItem = mutableStateOf(0)
    val currentLearningItem = mutableStateOf(0)
   // val correctTestAnswers: MutableList<VocabularyItemScheme> = mutableListOf()
    val wrongTestAnswers: MutableMap<Int, String> = mutableMapOf()
   // val correctLearningAnswers: MutableList<VocabularyItemScheme> = mutableListOf()
   //val wrongLearningAnswers: MutableMap<Int, String> = mutableMapOf()
    var isTaskDone: Boolean = false
    var isTestDone: Boolean = false
    var progress = 0
    var isTestGoing = mutableStateOf(false)
    var perfectTestsNumber = 0
    var currentWordDisplayTextDone = "the end"

    constructor(
        _id:String,
        _title: String,
        _vocabulary: Vocabulary,
        _currentTestItem: Int,
        _currentTaskItem: Int,
        _currentLearningItem: Int,
        _progress: Int,
        _wrongTestAnswers: MutableMap<Int, String>
    ) : this(_id,_title,_vocabulary) {
        currentTaskItem.value = _currentTaskItem
        currentTestItem.value = _currentTestItem
        currentLearningItem.value = _currentLearningItem
        progress = _progress
        taskWordsRemain.value = _vocabulary.items.size-1 - _currentTaskItem
        testWordsRemain.value = _vocabulary.items.size-1 - _currentTestItem
        learningWordsRemain.value = _vocabulary.items.size-1 - _currentLearningItem
        wrongTestAnswers.putAll(_wrongTestAnswers)

    }

    constructor(_id:String, _title: String, _vocabulary: Vocabulary, _currentVocabularyItem: Int, _currentTestItem: Int, _isDone: Boolean) : this(_id,_title,_vocabulary) {
      //  this.title = _title
       // this.vocabulary = _vocabulary
        this.currentTaskItem.value = _currentVocabularyItem
        this.currentTestItem.value = _currentTestItem
        this.isTaskDone = _isDone
        this.currentTaskWord.value = this.vocabulary.items[currentTaskItem.value]
        this.currentTestWord.value = this.vocabulary.items[currentTestItem.value]
        if (vocabulary.items.size == 1 && vocabulary.items[0].orig == "") {
            this.taskWordsRemain.value = 0
            this.testWordsRemain.value = 0
        } else{
            this.taskWordsRemain.value = this.vocabulary.items.size-this.currentTaskItem.value
            this.testWordsRemain.value = this.vocabulary.items.size-this.currentTestItem.value
        }
       // this.currentTaskWordDisplay.value = currentTaskWord.value.orig
        this.currentTestWordDisplay.value = currentTestWord.value.orig
        if (!this.isTaskDone){
            this.currentTaskWordDisplay.value = currentTaskWord.value.orig
        } else
            this.currentTaskWordDisplay.value = currentWordDisplayTextDone
    }

    override fun toString(): String {
        var s =id+DIVIDER+title
        s+DIVIDER+currentTaskItem.value+DIVIDER+currentTestItem.value+DIVIDER+isTaskDone+DIVIDER+vocabulary.toString()
        return s
    }

    fun fromString(source: String): Task? {
        val values = source.trim().split(DIVIDER)
        return if (values.size == 6) {
            val id = values[0]
            val title = values[1]
            val progress = values[2].toInt()
            val currentTestItem = values[3].toInt()
            val isDone = values[4].toBoolean()
            val vocabulary = Vocabulary(VocabularyTitle(""), mutableListOf(VocabularyItemScheme("","",""))).fromString(values[5])
            Task(id,title,vocabulary,progress,currentTestItem,isDone)
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

    fun learningRefresh(){
        learningWordsRemain.value = vocabulary.items.size - currentLearningItem.value
        if (learningWordsRemain.value > 0){
            currentLearningWord.value = vocabulary.items[currentLearningItem.value]
            this.currentLearningWordDisplay.value = currentLearningWord.value.orig
        }else
            this.currentLearningWordDisplay.value = currentWordDisplayTextDone
    }

    fun setReadyForTask(/*_currentTaskItem: Int*/) {
       // currentTaskItem.value = _currentTaskItem
        taskWordsRemain.value = vocabulary.items.size - currentTaskItem.value
        if (currentTaskItem.value >= 0 && currentTaskItem.value < vocabulary.items.size) {
            currentTaskWord.value = vocabulary.items[currentTaskItem.value]
            currentTaskWordDisplay.value = currentTaskWord.value.orig
        } else {
            currentTaskWordDisplay.value = currentWordDisplayTextDone
        }
    }
    fun setReadyForTest(/*_currentTestItem: Int*/) {
       // currentTestItem.value = _currentTestItem
        Log.d("pppp", "currentTestItem = ${currentTestItem.value}")
        testWordsRemain.value = vocabulary.items.size - currentTestItem.value
        if (currentTestItem.value >= 0 && currentTestItem.value < vocabulary.items.size) {
            currentTestWord.value = vocabulary.items[currentTestItem.value]
            currentTestWordDisplay.value = currentTestWord.value.orig
        } else {
            currentTestWordDisplay.value = currentWordDisplayTextDone
        }
    }

    fun setReadyForLearning(/*_currentLearningItem: Int*/) {
       // currentLearningItem.value = _currentLearningItem
        learningWordsRemain.value = vocabulary.items.size - currentLearningItem.value
        if (currentLearningItem.value >= 0 && currentLearningItem.value < vocabulary.items.size) {
            currentLearningWord.value = vocabulary.items[currentLearningItem.value]
            currentLearningWordDisplay.value = currentLearningWord.value.orig
        } else {
            currentLearningWordDisplay.value = currentWordDisplayTextDone
        }

    }

}