package com.miaomi.fenbei.room.callback

import com.miaomi.fenbei.base.bean.db.MusicBean

public interface ChatRoomOnMusicCallBack {
    fun onPlay(musicBean: MusicBean?)
    fun onPauseMusic()
    fun onResumeMusic()
    fun progress(progress: Int)
    fun onRemove(musicBean: MusicBean)
}