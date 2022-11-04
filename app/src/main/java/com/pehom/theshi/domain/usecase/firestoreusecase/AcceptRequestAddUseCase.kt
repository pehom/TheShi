package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.RequestAdd
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class AcceptRequestAddUseCase {
    fun execute(
        request: RequestAdd,
        viewModel: MainViewModel,
        onResponse: () -> Unit
    ){
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.PENDING_REQUESTS).whereEqualTo(Constants.SENDER_FSID, request.senderFsId.value).get()
            .addOnSuccessListener { docs ->
                docs.forEach() {doc ->
                    doc.reference.update(Constants.STATE, Constants.ACCEPTED)
                    Firebase.firestore.collection(Constants.USERS).document(request.senderFsId.value)
                        .collection(Constants.PENDING_REQUESTS).whereEqualTo(Constants.RECEIVER_FSID, request.receiverFsId.value).get()
                        .addOnSuccessListener {  docs ->
                            if (docs.documents.size == 1) {
                                for (doc in docs) {
                                    doc.reference.update(Constants.STATE, Constants.ACCEPTED)
                                        .addOnSuccessListener {
                                            onResponse()
                                        }
                                        .addOnFailureListener {
                                            Log.d("acceptRequestAddUseCase", "updating request failed, Error: ${it.message}")
                                        }
                                }
                            } else {
                                for (doc in docs) {
                                    doc.reference.update(Constants.STATE, Constants.ACCEPTED)
                                }
                                onResponse()
                            }
                        }
                        .addOnFailureListener {
                            Log.d("acceptRequestAddUseCase", "getting requester failed, Error: ${it.message}")
                        }
                }
            }
            .addOnFailureListener {
                Log.d("acceptRequestAddUseCase", "getting requests failed, Error: ${it.message}")
            }
    }
}