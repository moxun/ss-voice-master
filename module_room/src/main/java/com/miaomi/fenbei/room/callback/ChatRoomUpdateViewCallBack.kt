package com.miaomi.fenbei.room.callback

import com.miaomi.fenbei.base.bean.LightUpBean
import com.miaomi.fenbei.base.bean.MsgBean
import com.miaomi.fenbei.base.bean.MsgType
import com.miaomi.fenbei.base.bean.UserInfo
import java.util.ArrayList

public interface ChatRoomUpdateViewCallBack {
    companion object {
        const val TYPE_HOST = 0
        const val TYPE_MIC = 1
        const val TYPE_WORD = 2
//        const val TYPE_WORD_ALL_REFRESH = 15
        const val TYPE_BOTTOM = 3
        const val TYPE_ALL = 4
//        const val TYPE_USER_JOIN = 5
//        const val TYPE_USER_LEAVE = 6
        const val TYPE_INPUT = 10
        const val TYPE_GIFT_CHEST = 12
        const val TYPE_RED_PACKET = 13
        const val QUIT_XIANGQING_QUEUE = 14
        const val XIANGQING_NEXT_STEP = 15
        const val XIANGQING_POWER_FANZHUAN = 16
        const val XIANGQING_BRIGHT = 17
        const val XIANGQING_FAIL = 18
        const val XIANGQING_SUCCESS = 19
//            const val TYPE_JOIN_GUARD = 14
    }

    fun onUpdate(type: Int, msgBeans: ArrayList<MsgBean>)

    fun onUpdateWord(type: Int, msgBean: MsgBean)

    fun onUpdateTopMsg( msgBean: MsgBean)

    fun onLigHtUp(type:Int,lightupbean: LightUpBean)

    fun onRefreshMic(users:List<UserInfo>)

    fun onRefreshBottomBt(msgType: MsgType)
}