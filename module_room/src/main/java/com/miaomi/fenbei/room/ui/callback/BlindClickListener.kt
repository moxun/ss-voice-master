package com.miaomi.fenbei.room.ui.callback

import android.view.View
import com.miaomi.fenbei.base.bean.UserInfo

interface BlindClickListener {
    fun onMicItemClick(view: View, userInfo: UserInfo, position:Int)
    fun onLightUpItemClick()
    fun onLightHeartItemClick(position: Int)
}