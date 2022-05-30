package com.miaomi.fenbei.room.callback

import com.miaomi.fenbei.base.bean.ColumnInfoBean
import com.miaomi.fenbei.base.bean.InviteBean
import com.miaomi.fenbei.base.bean.XqResultBean


 interface ChatRoomRadioCallBack {
    fun onUpdateColumnInfo(columnInfoBean:ColumnInfoBean)
}