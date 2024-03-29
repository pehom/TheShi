package com.pehom.theshi.domain.usecase.firestoreusecase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pehom.theshi.domain.model.FsId
import com.pehom.theshi.domain.model.RequestAdd
import com.pehom.theshi.presentation.viewmodel.MainViewModel
import com.pehom.theshi.utils.Constants

class ReadRequestsAddFsUseCase {
    private val TAG = "ReadRequestsAddFsUseCase"
    fun execute(
        viewModel: MainViewModel,
        onSuccess: (List<RequestAdd>)-> Unit
    ) {
        Log.d(TAG, "$TAG invoked")
        val resultList = mutableListOf<RequestAdd>()
        Firebase.firestore.collection(Constants.USERS).document(viewModel.user.value.fsId.value).collection(Constants.PENDING_REQUESTS).get()
            .addOnSuccessListener { docs ->
                if (docs.size() > 0) {
                  //  viewModel.requestsAdd.clear()
                    for (doc in docs) {
                        val details = doc[Constants.DETAILS] as Map<*,*>
                        val receiverFsId = FsId(doc[Constants.RECEIVER_FSID].toString())
                        val senderFsId = FsId(doc[Constants.SENDER_FSID].toString())
                        val request = RequestAdd(senderFsId, receiverFsId)
                        request.receiverName = details[Constants.RECEIVER_NAME].toString()
                        request.receiverPhone = details[Constants.RECEIVER_PHONE].toString()
                        request.senderName = details[Constants.SENDER_NAME].toString()
                        request.senderPhone = details[Constants.SENDER_PHONE].toString()
                        request.state = doc[Constants.STATE].toString()

                        when (request.state) {
                           /* Constants.ACCEPTED -> {
                                if (request.receiverFsId.value == viewModel.user.value.fsId.value) {
                                    val mentor = Mentor(request.senderFsId.value, request.senderPhone, request.senderName)
                                    viewModel.useCases.addMentorFsUseCase.execute(viewModel.user.value, mentor){
                                        doc.reference.delete()
                                    }
                                } else {
                                    val newStudent = Student(request.receiverFsId, request.receiverName, request.receiverPhone)
                                    viewModel.useCases.addStudentFsUseCase.execute(viewModel, newStudent) {
                                        doc.reference.delete()
                                    }
                                }
                            }*/
                           /* Constants.DECLINED, Constants.CANCELLED -> {
                                doc.reference.delete()
                            }*/

                            Constants.PENDING -> {
                                resultList.add((request))
                              //  viewModel.requestsAdd.add(request)
                            }
                        }
                    }
                }
                Log.d("readRequestsAddFsUseCase", "viewmodel.requestsAdd = ${viewModel.requestsAdd}")
                onSuccess(resultList)
            }
            .addOnFailureListener {
                Log.d("readRequestsAddFsUseCase", "reading requests failed, Error: ${it.message}")
            }
    }

}