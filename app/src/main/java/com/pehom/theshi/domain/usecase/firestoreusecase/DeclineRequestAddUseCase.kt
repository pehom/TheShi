package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.RequestAdd
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class DeclineRequestAddUseCase {
    fun execute(
        request: RequestAdd,
        viewModel: MainViewModel,
        onResponse: () -> Unit
    ){
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.PENDING_REQUESTS).whereEqualTo(Constants.SENDER_FSID, request.senderFsId.value).get()
            .addOnSuccessListener { docs ->
                docs.forEach() {doc ->
                    doc.reference.delete()
                    viewModel.requestsAdd.remove(request)
                    Firebase.firestore.collection(Constants.USERS).document(request.senderFsId.value)
                        .collection(Constants.PENDING_REQUESTS).whereEqualTo(Constants.RECEIVER_FSID, request.receiverFsId.value).get()
                        .addOnSuccessListener {  docs ->
                            if (docs.documents.size == 1) {
                                for (doc in docs) {
                                    doc.reference.update(Constants.STATE, Constants.DECLINED)
                                        .addOnSuccessListener {
                                            onResponse()
                                        }
                                        .addOnFailureListener {
                                            Log.d("declineRequestAddUseCase", "updating failed, Error: ${it.message}")
                                        }
                                }
                            } else {
                                for (doc in docs) {
                                    doc.reference.update(Constants.STATE, Constants.DECLINED)
                                }
                                onResponse()
                            }
                        }
                        .addOnFailureListener {
                            Log.d("declineRequestAddUseCase", "getting requester failed, Error: ${it.message}")
                        }
                }
            }
            .addOnFailureListener {
                Log.d("declineRequestAddUseCase", "getting requests failed, Error: ${it.message}")
            }
    }
}