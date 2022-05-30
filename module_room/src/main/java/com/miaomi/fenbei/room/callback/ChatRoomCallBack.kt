package com.miaomi.fenbei.room.callback

import java.util.HashMap

abstract class ChatRoomCallBack {
    open fun speaking(uid: HashMap<Int, Int>) {}
    open fun kickOut() {}
    open fun levelChat() {}
    open fun sendEmoji() {}
    open fun showMusicIcon(isRun:Boolean){}
    open fun closeMusicIcon(){}
}