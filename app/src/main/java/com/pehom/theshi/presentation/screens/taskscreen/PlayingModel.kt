package com.pehom.theshi.presentation.screens.taskscreen

import androidx.compose.runtime.mutableStateListOf
import com.pehom.theshi.domain.model.Task
import com.pehom.theshi.domain.model.VocabularyItemScheme
import com.pehom.theshi.testdata.getVocabularies
import kotlinx.coroutines.delay

class PlayingModel {
    val TASKISDONE = -3
    val NEXTTRY = -1
    val MATCH = 1
    //val cards: Array<PlayingCardModel> = Array(16) {PlayingCardModel(it)}
    val cards = mutableStateListOf(
        PlayingCardModel(0), PlayingCardModel(0), PlayingCardModel(0), PlayingCardModel(0),
        PlayingCardModel(0), PlayingCardModel(0), PlayingCardModel(0), PlayingCardModel(0),
        PlayingCardModel(0), PlayingCardModel(0), PlayingCardModel(0), PlayingCardModel(0),
        PlayingCardModel(0), PlayingCardModel(0), PlayingCardModel(0), PlayingCardModel(0),
    )
    var firstCard = PlayingCardModel(-1)
    var secondCard = PlayingCardModel(-1)
    var currentCard = PlayingCardModel(-5)
    var correctVariant = ""
    val variants = mutableStateListOf("","","","","","","","","","","","","","","","")

    lateinit var task: Task

    constructor( _task: Task) {
        task = _task
        correctVariant = task.currentTaskWord.value.translation
        val localVariants = getVariants(task.currentTaskWord.value)
        if (variants.size == localVariants.size){
            for (i in localVariants.indices) {
                variants[i] = localVariants[i]
            }
        }
        var result = ""
        if (variants.size == cards.size) {
            for (i in variants.indices){
                cards[i] = PlayingCardModel(i, variants[i])
                result+=cards[i].value + " "
            }
        }
    }

    suspend fun selectCard(currentCardNumber: Int): Int {
        var result = NEXTTRY
        if (task.currentTaskItem.value < task.vocabulary.items.size) {
            if (cards[currentCardNumber].isClickable.value) {
                if (!cards[currentCardNumber].isVisible.value) {
                    currentCard = cards[currentCardNumber]
                    if (firstCard.cardNumber == -1) {
                        firstCard = currentCard
                        cards[currentCardNumber].isVisible.value = true
                        cards[currentCardNumber].isClickable.value = false
                    } else if (secondCard.cardNumber == -1) {
                        secondCard = currentCard
                        cards[currentCardNumber].isVisible.value = true
                        cards[currentCardNumber].isClickable.value = false
                        delay(400)
                        if (firstCard.value != secondCard.value) {
                            cards[firstCard.cardNumber].isClickable.value = true
                            cards[firstCard.cardNumber].isVisible.value = false
                            cards[firstCard.cardNumber].correctCardBorder.value = false
                            cards[currentCardNumber].isClickable.value = true
                            cards[currentCardNumber].isVisible.value = false
                            cards[currentCardNumber].correctCardBorder.value = false
                            firstCard = PlayingCardModel(-1)
                            secondCard = PlayingCardModel(-1)
                        } else{
                            if(firstCard.value == correctVariant) {
                                firstCard.correctCardBorder.value = true
                                secondCard.correctCardBorder.value = true
                                delay(700)
                                firstCard = PlayingCardModel(-1)
                                secondCard = PlayingCardModel(-1)
                                for (card in cards){
                                    card.isVisible.value = false
                                    card.isClickable.value = true
                                    card.correctCardBorder.value = false
                                }
                                if (task.currentTaskItem.value+1 < task.vocabulary.items.size){
                                    task.currentTaskItem.value++
                                    task.taskRefresh()
                                    result = MATCH
                                    setCards()
                                }
                                else {
                                    task.currentTaskItem.value++
                                    task.isTaskDone = true
                                    task.taskRefresh()
                                    for (card in cards){
                                        card.isVisible.value = false
                                        card.isClickable.value = false
                                        card.correctCardBorder.value = false
                                    }
                                    result = TASKISDONE
                                }
                            }
                            firstCard = PlayingCardModel(-1)
                            secondCard = PlayingCardModel(-1)
                        }
                    }
                }
            }
        }
        return result
    }

    fun getVariants(theWord: VocabularyItemScheme): List<String> {
        val vocabularies = getVocabularies()
        val allWords = mutableListOf<String>()
        for (vocabulary in vocabularies) {
            for (item in vocabulary.items) {
                allWords.add(item.translation)
            }
        }
        val resultSet: MutableSet<String> = mutableSetOf(theWord.translation)
        while (resultSet.size<8)  resultSet.add(allWords.random())
        val resultList = (resultSet.toList()+resultSet.toList()).asSequence().shuffled().toList()
        println(resultList)
        return resultList
    }

    fun setCards() {
        correctVariant = task.currentTaskWord.value.translation
        val localVariants = getVariants(task.currentTaskWord.value)
        if (variants.size == localVariants.size){
            for (i in localVariants.indices) {
                variants[i] = localVariants[i]
            }
        }
        if (variants.size == cards.size) {
            for (i in variants.indices){
                cards[i].value = variants[i]
                cards[i].isClickable.value = true
                cards[i].isVisible.value = false
            }
        }
    }
}