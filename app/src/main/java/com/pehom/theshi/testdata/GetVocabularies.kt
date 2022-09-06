package com.pehom.theshi.testdata

import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme

fun getVocabularies(): MutableList<Vocabulary> {
    return mutableListOf(

        Vocabulary(
            "Art",mutableListOf(
                VocabularyItemScheme( "искусство", "art",  ""),
                VocabularyItemScheme( "живопись", "painting",  ""),
                VocabularyItemScheme( "фотография", "photography",  ""),
                VocabularyItemScheme( "балет", "ballet",  ""),
                VocabularyItemScheme( "выступление", "performance",  ""),
                VocabularyItemScheme("шоу", "show", ""),
                VocabularyItemScheme("автор", "author",  ""),
            )
        ),
        Vocabulary(
            "Geography", mutableListOf(
                VocabularyItemScheme( "страна", "country",  ""),
                VocabularyItemScheme( "глобус", "globe",  ""),
                VocabularyItemScheme( "материк", "mainland",  ""),
                VocabularyItemScheme( "остров", "island", ""),
                VocabularyItemScheme( "архипелаг", "archipelago",  ""),
                VocabularyItemScheme("река", "river",  ""),
                VocabularyItemScheme( "озеро", "lake",  ""),
                VocabularyItemScheme( "море", "sea",  ""),
            )
        ),
        Vocabulary(
            "Jobs", mutableListOf(
                VocabularyItemScheme("работать", "to work",  ""),
                VocabularyItemScheme( "работник", "employee",  ""),
                VocabularyItemScheme( "работодатель", "employer",  ""),
                VocabularyItemScheme( "зарплата", "salary",  ""),
                VocabularyItemScheme( "контракт", "contract",  ""),
                VocabularyItemScheme( "офис", "office",  "")
            )
        ),
        Vocabulary(
            "Meal", mutableListOf(
                VocabularyItemScheme( "еда", "food",  ""),
                VocabularyItemScheme( "есть", "eat", ""),
                VocabularyItemScheme( "готовить", "cook",  ""),
                VocabularyItemScheme( "кухня", "kitchen",  ""),
                VocabularyItemScheme( "повар", "cook",  ""),
                VocabularyItemScheme( "суп", "soup",  ""),
                VocabularyItemScheme( "пицца", "pizza",  "")
            )
        ),
        Vocabulary(
            "Music", mutableListOf(
                VocabularyItemScheme( "музыка", "music",  ""),
                VocabularyItemScheme( "нота", "note",  ""),
                VocabularyItemScheme( "трэк", "track",  ""),
                VocabularyItemScheme( "петь", "to sing", ""),
                VocabularyItemScheme( "песня", "song",  ""),
                VocabularyItemScheme( "играть", "play",  ""),
                VocabularyItemScheme( "жанр", "genre",  ""),
            )
        ),
        Vocabulary(
            "Science", mutableListOf(
                VocabularyItemScheme( "изучать", "study",  ""),
                VocabularyItemScheme( "опыт", "experiment",""),
                VocabularyItemScheme( "данные", "data",  ""),
                VocabularyItemScheme( "ученый", "scientist",  ""),
                VocabularyItemScheme( "лаборатория", "laboratory", ""),
                VocabularyItemScheme( "микроскоп", "microscope",  ""),
                VocabularyItemScheme( "образец", "sample",  "")
            )
        ),
        Vocabulary(
            "Sports", mutableListOf(
                VocabularyItemScheme("спорт", "sport", ""),
                VocabularyItemScheme( "игра", "game",  ""),
                VocabularyItemScheme( "футбол", "football",  ""),
                VocabularyItemScheme( "хоккей", "hockey",  ""),
                VocabularyItemScheme( "теннис", "tennis",  ""),
                VocabularyItemScheme( "тренироваться", "to train",  ""),
                VocabularyItemScheme( "судья", "referee",  "")
            )
        ),
        Vocabulary(
            "Technics", mutableListOf(
                VocabularyItemScheme( "телевизор", "TV",  ""),
                VocabularyItemScheme( "тостер", "toaster",  ""),
                VocabularyItemScheme( "фен", "hairdryer",  ""),
                VocabularyItemScheme("микроволновка", "microwave",  ""),
                VocabularyItemScheme( "миксер", "mixer",  ""),
                VocabularyItemScheme("компьютер", "computer", "")
            )
        ),
        Vocabulary(
            "Transport", mutableListOf(
                VocabularyItemScheme( "велосипед", "bicycle",  ""
                ),
                VocabularyItemScheme( "автобус", "bus",  ""),
                VocabularyItemScheme( "мотоцикл", "motorcycle",  ""
                ),
                VocabularyItemScheme("легковая машина", "car",  "")
            )
        )
    )

}
