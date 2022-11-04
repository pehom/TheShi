package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.RequestAdd
import com.pehom.theshi.domain.model.Student
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class ReadRequestsAddFsUseCase {

    fun execute(
        viewModel: MainViewModel,
        onResponse: ()-> Unit
    ) {
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value).collection(Constants.PENDING_REQUESTS).get()
            .addOnSuccessListener { docs ->
                if (docs.size() > 0) {
                    viewModel.requestsAdd.clear()
                    for (doc in docs) {
                        val receiverFsId = FsId(doc[Constants.RECEIVER_FSID].toString())
                        val senderFsId = FsId(doc[Constants.SENDER_FSID].toString())
                        val request = RequestAdd(senderFsId, receiverFsId)
                        request.receiverName = doc[Constants.RECEIVER_NAME].toString()
                        request.receiverPhone = doc[Constants.RECEIVER_PHONE].toString()
                        request.senderName = doc[Constants.SENDER_NAME].toString()
                        request.senderPhone = doc[Constants.SENDER_PHONE].toString()
                        request.state = doc[Constants.STATE].toString()

                        when (request.state) {
                            Constants.ACCEPTED -> {
                                if (request.receiverFsId.value == viewModel.user.value.fsId.value) {
                                    doc.reference.delete()
                                } else {
                                    val newStudent = Student(request.receiverFsId, request.receiverName)
                                    doc.reference.delete()
                                    viewModel.useCases.addStudentFsUseCase.execute(viewModel, newStudent) {}
                                }
                            }
                            Constants.DECLINED, Constants.CANCELLED -> {
                                doc.reference.delete()
                            }

                            Constants.PENDING -> {
                                viewModel.requestsAdd.add(request)
                            }
                        }
                    }
                }
                Log.d("readRequestsAddFsUseCase", "viewmodel.requestsAdd = ${viewModel.requestsAdd}")
                onResponse()
            }
            .addOnFailureListener {
                Log.d("readRequestsAddFsUseCase", "reading requests failed, Error: ${it.message}")
            }
    }

}