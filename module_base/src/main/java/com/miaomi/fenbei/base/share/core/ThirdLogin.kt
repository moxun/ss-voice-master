package com.miaomi.fenbei.base.share.core

import android.app.Activity
import com.miaomi.fenbei.base.share.callback.ThirdLoginCallback
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareConfig
import com.umeng.socialize.bean.SHARE_MEDIA


object ThirdLogin {
//    val LOGIN_BY_WX = 2
//    val LOGIN_BY_QQ = 3


    //三方登陆
    fun oauthLogin(acyivity:Activity, platform: SHARE_MEDIA, callback: ThirdLoginCallback) {
//        UMShareAPI.get(acyivity).deleteOauth(acyivity, platform,object : UMAuthListener {
//            override fun onComplete(p0: SHARE_MEDIA?, p1: Int, p2: MutableMap<String, String>) {
//            }
//
//            override fun onCancel(p0: SHARE_MEDIA?, p1: Int) {
//            }
//
//            override fun onError(p0: SHARE_MEDIA?, p1: Int, p2: Throwable?) {
//            }
//
//            override fun onStart(p0: SHARE_MEDIA?) {
//            }
//        } )
        val config = UMShareConfig()
        config.isNeedAuthOnGetUserInfo(true)
        UMShareAPI.get(acyivity).setShareConfig(config)
        UMShareAPI.get(acyivity).getPlatformInfo(acyivity,platform, object : UMAuthListener{
            override fun onComplete(p0: SHARE_MEDIA, p1: Int, p2: MutableMap<String, String>) {
                var uid = p2["uid"]
                var name = p2["name"]
                var iconurl = p2["iconurl"]
                var gender = p2["gender"]!!.stringGenter2Int()
                if (uid != null && name != null && iconurl != null){
                    callback.onSuc(uid,uid,gender.toString(),name,iconurl)
                }
            }

            override fun onCancel(p0: SHARE_MEDIA?, p1: Int) {
                callback.onCancel()
            }

            override fun onError(p0: SHARE_MEDIA?, p1: Int, p2: Throwable?) {
                callback.onFail(p2.toString())
            }

            override fun onStart(p0: SHARE_MEDIA?) {
            }

        })
    }

    fun String.stringGenter2Int(): Int {
        var gender = 1
        when (this) {
            "男" -> gender = 1
            "女" -> gender = 2
        }
        return gender
    }
    fun Int.intGenter2String(): String {
        var gender = "男"
        when (this) {
            1 -> gender = "男"
            2 -> gender = "男"
        }
        return gender
    }
}