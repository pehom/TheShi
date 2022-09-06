package com.pehom.theshi.testdata

import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.domain.model.VocabularyItemScheme

fun getVocabularies(): MutableList<Vocabulary> {
    return mutableListOf(
        Vocabulary(
            "Animals", arrayOf(
                VocabularyItemScheme("animals1", "кот", "cat", "kat", "", ""),
                VocabularyItemScheme("animals2", "лев", "lion", "ˈlīən", "", ""),
                VocabularyItemScheme("animals3", "собака", "dog", "dôɡ", "", ""),
            )
        ),
        Vocabulary(
            "Art", arrayOf(
                VocabularyItemScheme("art1", "искусство", "art", "ärt", "", ""),
                VocabularyItemScheme("art2", "живопись", "painting", "ˈpān(t)iNG", "", ""),
                VocabularyItemScheme("art3", "фотография", "photography", "fəˈtäɡrəfē", "", ""),
                VocabularyItemScheme("art4", "балет", "ballet", "baˈlā", "", ""),
                VocabularyItemScheme(
                    "art5",
                    "выступление",
                    "performance",
                    "pərˈfôrməns",
                    "",
                    ""
                ),
                VocabularyItemScheme("art6", "шоу", "show", "SHō", "", ""),
                VocabularyItemScheme("art7", "автор", "author", "ˈôTHər", "", ""),
            )
        ),
        Vocabulary(
            "Geography", arrayOf(
                VocabularyItemScheme("geography1", "страна", "country", "ˈkəntrē", "", ""),
                VocabularyItemScheme("geography2", "глобус", "globe", "ɡlōb", "", ""),
                VocabularyItemScheme("geography3", "материк", "mainland", "ˈmānˌland", "", ""),
                VocabularyItemScheme("geography4", "остров", "island", "ˈīlənd", "", ""),
                VocabularyItemScheme(
                    "geography5",
                    "архипелаг",
                    "archipelago",
                    "ˌärkəˈpeləˌɡō",
                    "",
                    ""
                ),
                VocabularyItemScheme("geography6", "река", "river", "ˈrivər", "", ""),
                VocabularyItemScheme("geography7", "озеро", "lake", "lāk", "", ""),
                VocabularyItemScheme("geography8", "море", "sea", "sē", "", ""),
            )
        ),
        Vocabulary(
            "Jobs", arrayOf(
                VocabularyItemScheme("jobs1", "работать", "to work", "wərk", "", ""),
                VocabularyItemScheme("jobs2", "работник", "employee", "emˈploiē", "", ""),
                VocabularyItemScheme("jobs3", "работодатель", "employer", "əmˈploiər", "", ""),
                VocabularyItemScheme("jobs4", "зарплата", "salary", "ˈsal(ə)rē", "", ""),
                VocabularyItemScheme("jobs5", "контракт", "contract", "", "", ""),
                VocabularyItemScheme("jobs6", "офис", "office", "ˈôfis", "", "")
            )
        ),
        Vocabulary(
            "Meal", arrayOf(
                VocabularyItemScheme("meal1", "еда", "food", "fo͞od", "", ""),
                VocabularyItemScheme("meal2", "есть", "eat", "ēt", "", ""),
                VocabularyItemScheme("meal3", "готовить", "cook", "ko͝ok", "", ""),
                VocabularyItemScheme("meal4", "кухня", "kitchen", "ˈkiCH(ə)n", "", ""),
                VocabularyItemScheme("meal5", "повар", "cook", "ko͝ok", "", ""),
                VocabularyItemScheme("meal6", "суп", "soup", "so͞op", "", ""),
                VocabularyItemScheme("meal7", "пицца", "pizza", "ˈpētsə", "", "")
            )
        ),
        Vocabulary(
            "Music", arrayOf(
                VocabularyItemScheme("music1", "музыка", "music", "ˈmyo͞ozik", "", ""),
                VocabularyItemScheme("music2", "нота", "note", "nōt", "", ""),
                VocabularyItemScheme("music3", "трэк", "track", "trak", "", ""),
                VocabularyItemScheme("music4", "петь", "to sing", "siNG", "", ""),
                VocabularyItemScheme("music5", "песня", "song", "sôNG", "", ""),
                VocabularyItemScheme("music6", "играть", "play", "plā", "", ""),
                VocabularyItemScheme("music7", "жанр", "genre", "ˈZHänrə", "", ""),
            )
        ),
        Vocabulary(
            "Science", arrayOf(
                VocabularyItemScheme("science1", "изучать", "study", "ˈstədē", "", ""),
                VocabularyItemScheme("science2", "опыт", "experiment", "", "", ""),
                VocabularyItemScheme("science3", "данные", "data", "ˈdadə", "", ""),
                VocabularyItemScheme("science4", "ученый", "scientist", "ˈsīəntəst", "", ""),
                VocabularyItemScheme(
                    "science5",
                    "лаборатория",
                    "laboratory",
                    "ˈlabrəˌtôrē",
                    "",
                    ""
                ),
                VocabularyItemScheme(
                    "science6",
                    "микроскоп",
                    "microscope",
                    "ˈmīkrəˌskōp",
                    "",
                    ""
                ),
                VocabularyItemScheme("science7", "образец", "sample", "ˈsampəl", "", "")
            )
        ),
        Vocabulary(
            "Sports", arrayOf(
                VocabularyItemScheme("sports1", "спорт", "sport", "spôrt", "", ""),
                VocabularyItemScheme("sports2", "игра", "game", "ɡām", "", ""),
                VocabularyItemScheme("sports3", "футбол", "football", "ˈfo͝otˌbôl", "", ""),
                VocabularyItemScheme("sports4", "хоккей", "hockey", "ˈhäkē", "", ""),
                VocabularyItemScheme("sports5", "теннис", "tennis", "ˈtenəs", "", ""),
                VocabularyItemScheme("sports6", "тренироваться", "to train", "trān", "", ""),
                VocabularyItemScheme("sports7", "судья", "referee", "ˌrefəˈrē", "", "")
            )
        ),
        Vocabulary(
            "Technics", arrayOf(
                VocabularyItemScheme("technics1", "телевизор", "TV", "tivi", "", ""),
                VocabularyItemScheme("technics2", "тостер", "toaster", "ˈtōstər", "", ""),
                VocabularyItemScheme("technics3", "фен", "hairdryer", "ˈher drī(ə)r", "", ""),
                VocabularyItemScheme(
                    "technics4",
                    "микроволновка",
                    "microwave",
                    "ˈmīkrəˌwāv",
                    "",
                    ""
                ),
                VocabularyItemScheme("technics5", "миксер", "mixer", "ˈmiksər", "", ""),
                VocabularyItemScheme(
                    "technics6",
                    "компьютер",
                    "computer",
                    "kəmˈpyo͞odər",
                    "",
                    ""
                )
            )
        ),
        Vocabulary(
            "Transport", arrayOf(
                VocabularyItemScheme("transport1", "велосипед", "bicycle", "ˈbīsək(ə)l", "", ""
                ),
                VocabularyItemScheme("transport2", "автобус", "bus", "bəs", "", ""),
                VocabularyItemScheme("transport3", "мотоцикл", "motorcycle", "ˈmōdərˌsīk(ə)l", "", ""
                ),
                VocabularyItemScheme("transport4", "легковая машина", "car", "kär", "", "")
            )
        )
    )

}