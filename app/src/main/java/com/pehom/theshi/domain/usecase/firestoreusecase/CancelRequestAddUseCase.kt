package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.RequestAdd
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class CancelRequestAddUseCase {
    private val TAG = "CancelRequestAddUseCase"
    fun execute(
        request: RequestAdd,
        viewModel: MainViewModel,
        onResponse: () -> Unit
    ){
        Log.d(TAG, "request.sender = ${request.senderFsId}")
        Log.d(TAG, "request.receiver = ${request.receiverFsId}")

        viewModel.requestsAdd.remove(request)
        if (viewModel.user.value.fsId.value == request.senderFsId.value) {
            Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
                .collection(Constants.PENDING_REQUESTS).whereEqualTo(Constants.RECEIVER_FSID, request.receiverFsId.value).get()
                .addOnSuccessListener { docs ->
                    docs.forEach() { docSender ->
                        //  doc.reference.update(Constants.STATE, Constants.CANCELLED)
                        if (docSender[Constants.STATE].toString() == Constants.PENDING) {
                            Firebase.firestore.collection(Constants.USERS).document(request.receiverFsId.value)
                                .collection(Constants.PENDING_REQUESTS).whereEqualTo(Constants.SENDER_FSID, request.senderFsId.value).get()
                                .addOnSuccessListener { docs ->
                                    if (docs.size() == 1) {
                                        docs.forEach() { docReceiver ->
                                            //   doc.reference.update(Constants.STATE, Constants.CANCELLED)
                                            if (docReceiver[Constants.STATE].toString() == Constants.PENDING) {
                                                docReceiver.reference.delete()
                                                    .addOnSuccessListener {
                                                        docSender.reference.delete()
                                                        onResponse()
                                                    }
                                                    .addOnFailureListener {
                                                        Log.d("cancelRequestAddFsUseCase", "cancellation failed, Error: ${it.message}")
                                                    }
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
                }
                .addOnFailureListener {
                    Log.d("cancelRequestAddUseCase", "cancellation failed, Error: ${it.message}")
                }
        } else if (viewModel.user.value.fsId.value == request.receiverFsId.value){
            Firebase.firestore.collection(Constants.USERS).document(request.senderFsId.value)
                .collection(Constants.PENDING_REQUESTS).whereEqualTo(Constants.RECEIVER_FSID, request.receiverFsId.value).get()
                .addOnSuccessListener { docs ->
                    docs.forEach() { docSender ->
                        //  doc.reference.update(Constants.STATE, Constants.CANCELLED)
                        if (docSender[Constants.STATE].toString() == Constants.PENDING) {
                            Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
                                .collection(Constants.PENDING_REQUESTS).whereEqualTo(Constants.SENDER_FSID, request.senderFsId.value).get()
                                .addOnSuccessListener { docs ->
                                    if (docs.size() == 1) {
                                        docs.forEach() { docReceiver ->
                                            //   doc.reference.update(Constants.STATE, Constants.CANCELLED)
                                            if (docReceiver[Constants.STATE].toString() == Constants.PENDING) {
                                                docReceiver.reference.delete()
                                                    .addOnSuccessListener {
                                                        docSender.reference.delete()
                                                        onResponse()
                                                    }
                                                    .addOnFailureListener {
                                                        Log.d("cancelRequestAddFsUseCase", "cancellation failed, Error: ${it.message}")
                                                    }
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
                }
                .addOnFailureListener {
                    Log.d("cancelRequestAddUseCase", "cancellation failed, Error: ${it.message}")
                }
        }
    }
}