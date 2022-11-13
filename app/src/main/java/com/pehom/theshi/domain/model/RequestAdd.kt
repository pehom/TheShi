package com.pehom.theshi.domain.model

import com.pehom.theshi.utils.Constants

class RequestAdd(val senderFsId: FsId, val receiverFsId: FsId ) {
    var state = Constants.PENDING
    var senderName = "senderName"
    var senderPhone = "senderPhone"
    var receiverName = "receiverName"
    var receiverPhone = "receiverPhone"

}