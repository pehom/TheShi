package com.pehom.theshi.domain.model

class Vocabulary(val title: String, val items: MutableList<VocabularyItemScheme>) {
    private val DIVIDER = "|||"
    private val SCHEMEDIVIDER ="\\"
    private val LOCALDIVIDER = "+++"

    override fun toString(): String {
        val s = title
        val schemeString=""

        for (item in items) {
            schemeString+
                  //  item.id+SCHEMEDIVIDER+
                    item.imgUrl+SCHEMEDIVIDER+
                    item.orig+SCHEMEDIVIDER+
                  //  item.transcription+SCHEMEDIVIDER+
                  // item.pronouncingUrl+SCHEMEDIVIDER+
                    item.translation+LOCALDIVIDER
        }
        s+DIVIDER+schemeString
        println(s)
        return s
    }

    fun fromString(source: String): Vocabulary {
        val values = source.trim().split(DIVIDER)
        val items: MutableList<VocabularyItemScheme> = mutableListOf()
        if (values.size>1) {
            val title = values[0]
            val localItems = values[1].trim().split(LOCALDIVIDER)
            localItems.forEachIndexed() {_, item ->
                println("Vocabulary.fromString() item= $item ")
                val schemeResource = item.trim().split(SCHEMEDIVIDER)
              //  val id = schemeResource[0]
                val imgUrl = schemeResource[0]
                val orig = schemeResource[1]
               // val transcription = schemeResource[3]
               // val pronouncingUrl = schemeResource[4]
                val translation = schemeResource[2]
                items + listOf(VocabularyItemScheme(orig,translation,imgUrl))
            }

        } else println("Vocabulary.fromString() gone wrong")

        return Vocabulary(title, items)
    }

}