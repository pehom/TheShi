package com.pehom.theshi.domain.model

import com.pehom.theshi.utils.Constants

class RequestAdd(val senderFsId: FsId, val receiverFsId: FsId ) {
    var state = Constants.PENDING
    var senderName = "mentorName"
    var senderPhone = "mentorPhone"
    var receiverName = "studentName"
    var receiverPhone = "studentPhone"

}