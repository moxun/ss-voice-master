package com.miaomi.fenbei.base.share.callback

interface ThirdLoginCallback {
    fun onSuc(openId:String,unionId:String,gender:String,nickName:String,face:String)
    fun onFail(msg:String)
    fun onCancel()
}