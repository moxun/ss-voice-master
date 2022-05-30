package com.miaomi.fenbei.room.ui.callback

import android.view.View
import com.miaomi.fenbei.base.bean.MsgType
import com.miaomi.fenbei.base.bean.UserInfo

interface WordClickListener {

    fun onUserItemClick(view: View, userInfo: UserInfo)

    fun onWordItemClick(type: MsgType, roomId: String) {}

    fun onEnterRoomClick(userInfo: UserInfo)
}