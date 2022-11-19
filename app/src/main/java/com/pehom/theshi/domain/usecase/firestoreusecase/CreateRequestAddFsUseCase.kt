package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.RequestAdd
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class CreateRequestAddFsUseCase {
    private val TAG ="CreateRequestAddFsUseCase"
    fun execute(
        viewModel: MainViewModel,
        request: RequestAdd,
        onResponse: () -> Unit
    ) {
        Log.d(TAG, "$TAG invoked")
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.PENDING_REQUESTS).document(request.receiverPhone).get()
            .addOnSuccessListener {
                if (!it.exists()) {
                    val details = hashMapOf(
                        Constants.RECEIVER_NAME to request.receiverName,
                        Constants.RECEIVER_PHONE to request.receiverPhone,
                        Constants.SENDER_PHONE to request.senderPhone,
                        Constants.SENDER_FSID to request.senderFsId.value
                    )

                    val dataRequestSent = hashMapOf(
                        Constants.SENDER_NAME to request.senderName,
                        Constants.RECEIVER_FSID to request.receiverFsId.value,
                        Constants.STATE to request.state,
                        Constants.DETAILS to details
                    )
                    Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
                        .collection(Constants.PENDING_REQUESTS).document(request.receiverPhone).set(dataRequestSent)
                        .addOnSuccessListener {
                            val detailsReceived = hashMapOf(
                                Constants.RECEIVER_NAME to request.receiverName,
                                Constants.RECEIVER_PHONE to request.receiverPhone,
                                Constants.SENDER_NAME to request.senderName,
                                Constants.SENDER_PHONE to request.senderPhone
                            )
                            val dataRequestReceived = hashMapOf(
                                Constants.RECEIVER_FSID to request.receiverFsId.value,
                                Constants.SENDER_FSID to viewModel.user.value.fsId.value,
                                Constants.STATE to Constants.PENDING,
                                Constants.DETAILS to detailsReceived
                            )
                            Firebase.firestore.collection(Constants.USERS).document(request.receiverFsId.value)
                                .collection(Constants.PENDING_REQUESTS).document(request.senderPhone).set(dataRequestReceived)
                                .addOnSuccessListener {
                                    onResponse()
                                }
                                .addOnFailureListener {
                                    Log.d("createRequestAddFsUseCase", "adding requestReceived failed, Error: ${it.message}")
                                }
                        }
                        .addOnFailureListener {
                            Log.d("createRequestAddFsUseCase", "adding requestSent failed, Error: ${it.message}")
                        }
                }
            }
    }
}