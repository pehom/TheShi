package com.pehom.theshi.domain.usecase

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.R
import com.pehom.theshi.data.localdata.approomdatabase.AvailableVocabularyRoomItem
import com.pehom.theshi.data.localdata.approomdatabase.AvailableWordsRoomItem
import com.pehom.theshi.domain.model.Vocabulary
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BuyVocabularyWithLoadedItemsUseCase {
    private val TAG = "BuyVocabularyUseCase"
    fun execute(
        viewModel: MainViewModel,
        price: Int,
        context: Context,
        vocabulary: Vocabulary,
        onSuccess: () -> Unit
    ){
        if (price <= viewModel.user.value.funds.amount.value) {
            viewModel.user.value.funds.spend(price)
            Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
                .update(Constants.FUNDS, viewModel.user.value.funds.amount.value)
            viewModel.currentTaskRoomItem.value.isAvailable = true
            Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
                .collection(Constants.TASKS).document(viewModel.currentTaskRoomItem.value.id)
                .update("${Constants.DETAILS}.${Constants.IS_AVAILABLE}", true)

            Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
                .collection(Constants.AVAILABLE_VOCABULARIES)
                .add(hashMapOf(Constants.VOCABULARY_FS_DOC_REF_PATH to vocabulary.title.fsDocRefPath))
            val availableVocabularyRoomItem = AvailableVocabularyRoomItem(
                null,
                vocabulary.title.fsDocRefPath,
                vocabulary.title.timestamp,
                vocabulary.title.value,
                vocabulary.title.price,
                viewModel.user.value.fsId.value
                )
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                Constants.REPOSITORY.readWordbookRoomItemByVcbDocRefPath(vocabulary.title.fsDocRefPath){
                    if (it == null) {
                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            Constants.REPOSITORY.createAvailableVocabularyRoomItem(availableVocabularyRoomItem){}
                            vocabulary.items.forEachIndexed {index, it ->
                                val availableWordsRoomItem = AvailableWordsRoomItem(
                                    null,
                                    vocabulary.title.fsDocRefPath,
                                    it.orig,
                                    it.trans,
                                    it.imgUrl,
                                    viewModel.user.value.fsId.value
                                )
                                Constants.REPOSITORY.addAvailableWordsRoomItem(availableWordsRoomItem)
                                if (index == vocabulary.items.lastIndex) {
                                    onSuccess()
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            Toast.makeText(context, context.getString(R.string.not_enough_funds) ,Toast.LENGTH_SHORT).show()
        }
    }
}