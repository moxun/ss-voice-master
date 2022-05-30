package com.miaomi.fenbei.base.core.msg


interface MsgListener {

    fun onNewMsg(text:String):Boolean
}