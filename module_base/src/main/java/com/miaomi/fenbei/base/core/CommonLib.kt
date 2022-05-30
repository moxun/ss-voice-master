package com.miaomi.fenbei.base.core

import android.annotation.SuppressLint
import android.app.Application
import com.kingja.loadsir.core.LoadSir
import com.miaomi.fenbei.base.AudioPlayer
import com.miaomi.fenbei.base.bean.event.LoginEvent
import com.miaomi.fenbei.base.bean.event.NetWorkBean
import com.miaomi.fenbei.base.bean.event.RefreshUnReadMsgBean
import com.miaomi.fenbei.base.widget.*
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.LogUtil
import com.miaomi.fenbei.base.util.PhotoUtils
import com.miaomi.fenbei.base.util.ToastUtil
import com.tencent.imsdk.*
import com.umeng.analytics.MobclickAgent
import org.greenrobot.eventbus.EventBus
import java.io.File


object CommonLib {

    lateinit var mContext: Application
    var isInRoom:Boolean = false
    var roomId = ""

    @SuppressLint("WrongConstant")
    fun init(context: Application){
        mContext = context
        initSoundFile()
        LogUtil.initLog()   //log初始化
        LoadSir.beginBuilder()
                .addCallback(ErrorView())//添加各种状态页
                .addCallback(EmptyView())
                .addCallback(RoomEmptyView())
                .addCallback(LoadingView())
                .addCallback(TokenInvalidView())
                .addCallback(FindEmptyView())
                .commit()
        initIM(context)
        PhotoUtils.initImgUri(context)


    }

    private fun initSoundFile(){
        val f = File(AudioPlayer.RECORD_DIR)
        if (!f.exists()) {
            f.mkdirs()
        }
        val f1 = File(AudioPlayer.RECORD_DOWNLOAD_DIR)
        if (!f1.exists()) {
            f1.mkdirs()
        }
    }

    fun onPageStart(name:String){
        MobclickAgent.onPageStart(name)
    }

    fun onPageEnd(name:String){
        MobclickAgent.onPageEnd(name)
    }

    fun onProfileSignIn(way:String,id:String){
        MobclickAgent.onProfileSignIn(way,id)
    }

    @SuppressLint("WrongConstant")
    fun initIM(context: Application){
        val userConfig = TIMUserConfig()
                .setUserStatusListener(object : TIMUserStatusListener {
                    override fun onUserSigExpired() {
                        ToastUtil.error(context,"登陆信息过期")
//                        DataHelper.clearTimSign()
                        EventBus.getDefault().post(LoginEvent(false))

                    }

                    override fun onForceOffline() {
                        ToastUtil.error(context,"账号在其他设备登陆")
//                        DataHelper.clearTimSign()
                        EventBus.getDefault().post(LoginEvent(false))

                    }

                })
                .setConnectionListener(object :TIMConnListener{
                    override fun onConnected() {
                        EventBus.getDefault().post(NetWorkBean(true))

                    }
                    override fun onWifiNeedAuth(p0: String?) {}
                    override fun onDisconnected(p0: Int, p1: String?) {
//                        ToastUtil.error(context,"聊天室连接断开～")
                    }
                })
                .setRefreshListener(object :TIMRefreshListener{
                    override fun onRefreshConversation(p0: List<TIMConversation>) {
                    }

                    override fun onRefresh() {
                    }

                })
                .enableReadReceipt(true)
                .setMessageReceiptListener {
                    if (it.isNotEmpty()){
                        EventBus.getDefault().post(RefreshUnReadMsgBean(it[0].conversation.peer))
                    }
                }
        val config = TIMSdkConfig(DataHelper.getIMDevelop().imSdkAppId)
                .enableLogPrint(true)
        TIMManager.getInstance().init(context, config)
        TIMManager.getInstance().userConfig =userConfig
    }

}