package com.pehom.theshi.utils

import com.pehom.theshi.domain.model.VocabularyTitle

fun vocabularyTitlesFromString(source: String): MutableList<VocabularyTitle> {
    val items = source.trim().split(Constants.VOCABULARY_TITLE_DIVIDER)
    val result = mutableListOf<VocabularyTitle>()
    items.forEach(){
        result.add(VocabularyTitle(it))
    }
    return result
}