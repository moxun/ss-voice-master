package com.miaomi.fenbei.base.bean

data class GotRedPacketBean (
        var got: Boolean = false,
        var diamonds: Int = 0,
        var total_diamonds: Int = 0,
        var nickname: String = "",
        var face: String = "",
        var say_text: String
)