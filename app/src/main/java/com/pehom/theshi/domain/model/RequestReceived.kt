package com.pehom.theshi.domain.model

import com.pehom.theshi.utils.Constants

class RequestReceived(val mentorFsId: FsId) {
    var state = Constants.CANCELLED
    var mentorName = ""
    var mentorPhone = ""
}