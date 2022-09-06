package com.pehom.theshi.testdata

import androidx.compose.runtime.mutableStateListOf
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme

fun setTasksForStudents(): MutableList<Task> {
    return mutableStateListOf(
        Task(
            "first task", Vocabulary(
                "Animals", mutableListOf(
                    VocabularyItemScheme( "кот", "cat",  ""),
                    VocabularyItemScheme( "лев", "lion",  ""),
                    VocabularyItemScheme( "собака", "dog",  "")
                )
            )
        ), Task(
            "second task", Vocabulary(
                "Jobs", mutableListOf(
                    VocabularyItemScheme( "работать", "to work",  ""),
                    VocabularyItemScheme( "работник", "employee",  ""),
                    VocabularyItemScheme( "работодатель", "employer", " "),
                    VocabularyItemScheme( "зарплата", "salary",  ""),
                    VocabularyItemScheme( "контракт", "contract", ""),
                    VocabularyItemScheme( "офис", "office",  "")
                )
            )
        ), Task(
            "third task", Vocabulary(
                "Meal", mutableListOf(
                    VocabularyItemScheme( "еда", "food",  ""),
                    VocabularyItemScheme("есть", "eat",  ""),
                    VocabularyItemScheme( "готовить", "cook",  ""),
                    VocabularyItemScheme( "кухня", "kitchen",  ""),
                    VocabularyItemScheme( "повар", "cook",  ""),
                    VocabularyItemScheme( "суп", "soup",  ""),
                    VocabularyItemScheme( "пицца", "pizza",  "")
                )
            )
        )
    )
}