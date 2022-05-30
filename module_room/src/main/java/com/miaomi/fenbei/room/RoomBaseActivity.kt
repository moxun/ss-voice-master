package com.miaomi.fenbei.room

import com.miaomi.fenbei.base.core.BaseActivity

abstract class RoomBaseActivity :BaseActivity(){
    protected fun start(){
        RoomLib.getChatHelper(application, true)?.apply {
            startForeground(110, getNotification(ChatRoomManager.getRoomId())) }
    }

    protected fun stop(){
        RoomLib.getChatHelper(application, true)?.apply {
            startForeground(111, keepAliveNotification()) }
    }
}