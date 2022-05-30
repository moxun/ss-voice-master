package com.miaomi.fenbei.base.bean


data class EggIndexBean (
        var hammer: Int = 0,
        var price: Int = 0,
        var balance: Int = 0,
        var record: ArrayList<MsgRecordBean>,
        var dress:String = "",
        var fontana:Int = 0,
        var fontana_rule:String = ""
)

data class MsgRecordBean (
    var nickname: String = "",
    var gift_name: String = "",
    var gift_number: Int = 0
)