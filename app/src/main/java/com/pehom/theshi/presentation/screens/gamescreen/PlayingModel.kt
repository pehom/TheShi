package com.pehom.theshi.presentation.screens.gamescreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import com.pehom.theshi.data.localdata.approomdatabase.TaskRoomItem
import com.pehom.theshi.domain.model.Task
import kotlinx.coroutines.delay

class PlayingModel {
    val TASKISDONE = -3
    val NEXTTRY = -1
    val MATCH = 1
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

    var task: Task
    var taskRoomItem: MutableState<TaskRoomItem>

    constructor( _task: Task, _taskRoomItem: MutableState<TaskRoomItem>) {
        taskRoomItem = _taskRoomItem
        task = _task
        correctVariant = task.currentTaskWord.value.trans
        val localVariants = getVariants()
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
                                    taskRoomItem.value.currentTaskItem = task.currentTaskItem.value
                                    task.taskRefresh()
                                    result = MATCH
                                    setCards()
                                }
                                else {
                                    task.currentTaskItem.value++
                                    taskRoomItem.value.currentTaskItem = task.currentTaskItem.value
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

    private fun getVariants(): List<String> {
        val theWord = task.currentTaskWord.value
        var i = 1
        val resultSet: MutableSet<String> = mutableSetOf(theWord.trans)
        if (task.vocabulary.items.size > 7) {
            while (resultSet.size < 8) resultSet.add(task.vocabulary.items.random().trans)
            return (resultSet.toList() + resultSet.toList()).asSequence().shuffled().toList()
        }
        else {
            task.vocabulary.items.forEach {
                resultSet.add(it.trans)
            }
            while (resultSet.size < 8) {
                resultSet.add("variant $i")
                i++
            }
            return (resultSet.toList() + resultSet.toList()).asSequence().shuffled().toList()
        }
    }

    fun setCards() {
        correctVariant = task.currentTaskWord.value.trans
        val localVariants = getVariants()
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