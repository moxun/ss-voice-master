package com.miaomi.fenbei.room

import android.app.Application
import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder


object RoomLib {

    lateinit var mChatHelper: RoomService

    lateinit var mContext: Application

    var isBind: Boolean = false

    fun init (context: Application){
        mContext = context
        val intent = Intent(context.applicationContext, RoomService::class.java)
        isBind = context.bindService(intent, object :ServiceConnection{
            override fun onServiceDisconnected(name: ComponentName?) {
                isBind = false
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mChatHelper = (service as RoomService.ChatBinder).service
                mChatHelper.startForeground(22222, mChatHelper.keepAliveNotification())
//                EventBus.getDefault().post(ServiceOpenBean())
            }
        }, BIND_AUTO_CREATE)
    }

    fun getChatHelper(context: Application, isShowHint: Boolean = false, isRestart: Boolean = true): RoomService? {
        if (!isBind || !RoomLib::mChatHelper.isInitialized) {
            if (isRestart) {
                val intent = Intent(context.applicationContext, RoomService::class.java)
                isBind = context.bindService(intent, object :ServiceConnection{
                    override fun onServiceDisconnected(name: ComponentName?) {
                        isBind = false
                    }

                    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                        mChatHelper = (service as RoomService.ChatBinder).service
                    }
                }, BIND_AUTO_CREATE)
            }
            return null
        } else {
            return mChatHelper
        }
    }

}