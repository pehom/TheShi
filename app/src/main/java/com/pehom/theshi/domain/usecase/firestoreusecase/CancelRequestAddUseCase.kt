package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.RequestAdd
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class CancelRequestAddUseCase {
    fun execute(
        request: RequestAdd,
        viewModel: MainViewModel,
        onResponse: () -> Unit
    ){
        viewModel.requestsAdd.remove(request)
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.PENDING_REQUESTS).whereEqualTo(Constants.RECEIVER_FSID, request.receiverFsId.value).get()
            .addOnSuccessListener { docs ->
                docs.forEach() { doc ->
                    doc.reference.update(Constants.STATE, Constants.CANCELLED)
                    Firebase.firestore.collection(Constants.USERS).document(request.receiverFsId.value)
                        .collection(Constants.PENDING_REQUESTS).whereEqualTo(Constants.SENDER_FSID, request.senderFsId.value).get()
                        .addOnSuccessListener { docs ->
                            if (docs.size() == 1) {
                                docs.forEach() { doc ->
                                    doc.reference.update(Constants.STATE, Constants.CANCELLED)
                                        .addOnSuccessListener {
                                            onResponse()
                                        }
                                        .addOnFailureListener {
                                            Log.d("cancelRequestAddFsUseCase", "cancellation failed, Error: ${it.message}")
                                        }
                                }
                            } else {
                                docs.forEach() { doc ->
                                    doc.reference.update(Constants.STATE, Constants.CANCELLED)
                                }
                                onResponse()
                            }
                        }
                }
            }
            .addOnFailureListener {
                Log.d("cancelRequestAddUseCase", "cancellation failed, Error: ${it.message}")
            }
    }
}