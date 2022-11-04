package com.pehom.theshi.domain.model


class VocabularyTitle (var value: String = "") {
    var fsDocRefPath = "fsDocRefPath"
    var timestamp = "timeStamp"
    var price = 0

    constructor(value: String, _fsDocRefPath: String): this(value) {
        fsDocRefPath = _fsDocRefPath
    }

    constructor(value: String, _fsDocRefPath: String, _timeStamp: String, _price: Int): this(value){
        fsDocRefPath = _fsDocRefPath
        timestamp = _timeStamp
        price = _price

    }
}