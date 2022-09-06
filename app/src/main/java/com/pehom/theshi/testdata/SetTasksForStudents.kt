package com.pehom.theshi.testdata

import androidx.compose.runtime.mutableStateListOf
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme

fun setTasksForStudents(): MutableList<Task> {
    return mutableStateListOf(
        Task(
            "first task", Vocabulary(
                "Animals", arrayOf(
                    VocabularyItemScheme("animals1", "кот", "cat", "kat", "", ""),
                    VocabularyItemScheme("animals2", "лев", "lion", "ˈlīən", "", ""),
                    VocabularyItemScheme("animals3", "собака", "dog", "dôɡ", "", "")
                )
            )
        ), Task(
            "second task", Vocabulary(
                "Jobs", arrayOf(
                    VocabularyItemScheme("jobs1", "работать", "to work", "wərk", "", ""),
                    VocabularyItemScheme("jobs2", "работник", "employee", "emˈploiē", "", ""),
                    VocabularyItemScheme("jobs3", "работодатель", "employer", "əmˈploiər", "", ""
                    ),
                    VocabularyItemScheme("jobs4", "зарплата", "salary", "ˈsal(ə)rē", "", ""),
                    VocabularyItemScheme("jobs5", "контракт", "contract", "", "", ""),
                    VocabularyItemScheme("jobs6", "офис", "office", "ˈôfis", "", "")
                )
            )
        ), Task(
            "third task", Vocabulary(
                "Meal", arrayOf(
                    VocabularyItemScheme("meal1", "еда", "food", "fo͞od", "", ""),
                    VocabularyItemScheme("meal2", "есть", "eat", "ēt", "", ""),
                    VocabularyItemScheme("meal3", "готовить", "cook", "ko͝ok", "", ""),
                    VocabularyItemScheme("meal4", "кухня", "kitchen", "ˈkiCH(ə)n", "", ""),
                    VocabularyItemScheme("meal5", "повар", "cook", "ko͝ok", "", ""),
                    VocabularyItemScheme("meal6", "суп", "soup", "so͞op", "", ""),
                    VocabularyItemScheme("meal7", "пицца", "pizza", "ˈpētsə", "", "")
                )
            )
        )
    )
}