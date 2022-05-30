package com.miaomi.fenbei.room.ui.callback

import android.view.View
import com.miaomi.fenbei.base.bean.UserInfo

interface MicClickListener {
    fun onMicItemClick(view: View, userInfo: UserInfo, position:Int)
}