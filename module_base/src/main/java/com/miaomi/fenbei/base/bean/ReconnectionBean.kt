package com.miaomi.fenbei.base.bean

data class ReconnectionBean (
        val type:Int, //0不做操作，1需要做重连操作
        val unique_id:String,
        val password: String
)
