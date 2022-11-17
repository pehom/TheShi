package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.Mentor
import com.pehom.theshi.domain.model.RequestAdd
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class AcceptRequestAddUseCase {
    private val TAG = "AcceptRequestAddUseCase"
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
                        .addOnSuccessListener {
                            val mentor = Mentor(request.senderFsId.value, request.senderPhone, request.senderName)
                            viewModel.useCases.addMentorFsUseCase.execute(viewModel.user.value, mentor){}
                            Firebase.firestore.collection(Constants.USERS).document(request.senderFsId.value)
                                .collection(Constants.PENDING_REQUESTS).whereEqualTo(Constants.RECEIVER_FSID, request.receiverFsId.value).get()
                                .addOnSuccessListener {  docs ->
                                    if (docs.documents.size == 1) {
                                        for (doc in docs) {
                                            doc.reference.delete()
                                                .addOnSuccessListener {
                                                    Firebase.firestore.collection(Constants.USERS).document(request.senderFsId.value)
                                                        .collection(Constants.STUDENTS).document(request.receiverPhone)
                                                        .set(hashMapOf(
                                                            Constants.FS_ID to request.receiverFsId.value,
                                                            Constants.PHONE_NUMBER to request.receiverPhone,
                                                            Constants.NAME to request.receiverName,
                                                            Constants.STATUS to Constants.ACCEPTED
                                                        ))
                                                        .addOnSuccessListener {
                                                            onResponse()
                                                        }
                                                        .addOnFailureListener {
                                                            Log.d(TAG, "creating new student doc failed, Error: ${it.message}")
                                                        }
                                                }
                                                .addOnFailureListener {
                                                    Log.d("acceptRequestAddUseCase", "deleting sender request failed, Error: ${it.message}")
                                                }
                                        }
                                    } else {
                                        for (doc in docs) {
                                            doc.reference.delete()
                                        }
                                        Firebase.firestore.collection(Constants.USERS).document(request.senderFsId.value)
                                            .collection(Constants.STUDENTS).document(request.receiverPhone)
                                            .set(hashMapOf(
                                                Constants.FS_ID to request.receiverFsId.value,
                                                Constants.PHONE_NUMBER to request.receiverPhone,
                                                Constants.NAME to request.receiverName,
                                                Constants.STATUS to Constants.ACCEPTED
                                            ))
                                            .addOnSuccessListener {
                                                onResponse()
                                            }
                                            .addOnFailureListener {
                                                Log.d(TAG, "creating new student doc failed, Error: ${it.message}")
                                            }
                                    }
                                }
                                .addOnFailureListener {
                                    Log.d("acceptRequestAddUseCase", "getting requester failed, Error: ${it.message}")
                                }
                        }
                }
            }
            .addOnFailureListener {
                Log.d("acceptRequestAddUseCase", "getting requests failed, Error: ${it.message}")
            }
    }
}