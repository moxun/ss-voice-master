package com.miaomi.fenbei.room.callback

import com.miaomi.fenbei.base.bean.InviteBean
import com.miaomi.fenbei.base.bean.XqResultBean


public interface ChatRoomLightCallBack {
    fun onUpdateHand(status: Int,xqResultBean: XqResultBean)
    fun onInVite(inviteBean: InviteBean)
}