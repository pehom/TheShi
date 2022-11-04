package com.pehom.theshi.domain.model

import com.pehom.theshi.utils.Constants

class RequestSend(val studentFsId: FsId) {
    var state = Constants.CANCELLED
    var studentPhone = ""
    var studentName = ""

}