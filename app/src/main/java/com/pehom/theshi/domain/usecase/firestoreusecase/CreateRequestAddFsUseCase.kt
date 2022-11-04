package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.RequestAdd
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class CreateRequestAddFsUseCase {
    fun execute(
        viewModel: MainViewModel,
        request: RequestAdd,
        onResponse: () -> Unit
    ) {
      //  viewModel.requestsAdd.add(request)
        val dataRequestSent = hashMapOf(
            Constants.RECEIVER_FSID to request.receiverFsId.value,
            Constants.STATE to request.state,
            Constants.RECEIVER_NAME to request.receiverName,
            Constants.RECEIVER_PHONE to request.receiverPhone,
            Constants.SENDER_NAME to request.senderName,
            Constants.SENDER_PHONE to request.senderPhone,
            Constants.SENDER_FSID to request.senderFsId.value
        )
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value)
            .collection(Constants.PENDING_REQUESTS).add(dataRequestSent)
                .addOnSuccessListener {
                    val dataRequestReceived = hashMapOf(
                        Constants.SENDER_FSID to viewModel.user.value.fsId.value,
                        Constants.RECEIVER_FSID to request.receiverFsId.value,
                        Constants.STATE to Constants.PENDING,
                        Constants.RECEIVER_NAME to request.receiverName,
                        Constants.RECEIVER_PHONE to request.receiverPhone,
                        Constants.SENDER_NAME to request.senderName,
                        Constants.SENDER_PHONE to request.senderPhone
                    )
                    Firebase.firestore.collection(Constants.USERS).document(request.receiverFsId.value)
                        .collection(Constants.PENDING_REQUESTS).add(dataRequestReceived)
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