package com.pehom.theshi.domain.model

data class VocabularyItemScheme(
  //  val id: String,
    val orig: String,
    val trans: String,
   // val transcription: String,
   // val pronouncingUrl: String,
    val imgUrl: String
    ) {
    lateinit var vocabularyTitle: VocabularyTitle
}