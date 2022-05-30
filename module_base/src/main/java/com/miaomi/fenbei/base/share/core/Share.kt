package com.miaomi.fenbei.base.share.core

import android.app.Activity
import com.miaomi.fenbei.base.R
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb


object Share {

    private fun share(context:Activity,url:String,type:SHARE_MEDIA) {
        val web = UMWeb(url)
        val thumb = UMImage(context, R.drawable.ic_app_logo)
        web.title = "来山水语音，聆听让你心动的声音" //标题
        web.setThumb(thumb)
        web.description = "邀请你来派对房间一起嗨" //描述

        ShareAction(context)
                .setPlatform(type)//传入平台
                .withMedia(web)//分享内容
                .setCallback(object : UMShareListener {
                    override fun onResult(p0: SHARE_MEDIA?) {
                    }

                    override fun onCancel(p0: SHARE_MEDIA?) {
                    }

                    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                    }

                    override fun onStart(p0: SHARE_MEDIA?) {
                    }

                })//回调监听器
                .share()
    }

    fun qqShare(context:Activity,url:String){
        share(context,url,SHARE_MEDIA.QQ)
    }
    fun QZONEPyqShare(context:Activity,url:String){
        share(context,url,SHARE_MEDIA.QZONE)
    }
    fun wxShare(context:Activity,url:String){
        share(context,url,SHARE_MEDIA.WEIXIN)
    }
    fun wxPyqShare(context:Activity,url:String){
        share(context,url,SHARE_MEDIA.WEIXIN_CIRCLE)
    }
}