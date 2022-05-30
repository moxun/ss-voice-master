package com.miaomi.fenbei.room

import com.miaomi.fenbei.room.callback.ChatRoomOnMusicCallBack
import com.miaomi.fenbei.base.bean.db.MusicBean
import com.miaomi.fenbei.base.core.CommonLib
import com.miaomi.fenbei.base.util.SPUtil
import com.miaomi.fenbei.base.util.ToastUtil
import java.util.*

object RoomMusicManager {
    private var mMusicList: ArrayList<MusicBean> = ArrayList()
    private var mMusicCallBack: ChatRoomOnMusicCallBack? = null
    private var curMusic: MusicBean? = null
    private var isPlaying: Boolean = false

    fun setMusicList(musicList: ArrayList<MusicBean>) {
        mMusicList = musicList
    }

    fun getMusicList():ArrayList<MusicBean> {
        return mMusicList
    }
    fun registerMusic(musicCallBack: ChatRoomOnMusicCallBack) {
        mMusicCallBack = musicCallBack
    }

    fun unregisterMusic() {
        mMusicCallBack = null
    }

    fun getCurMusic(): MusicBean? {
        return curMusic
    }

    fun stopPlayMusic() {
        isPlaying = false
        ChatRoomManager.mRtcEngine.stopAudioMixing()
        if (ChatRoomManager.mCallback != null) {
            ChatRoomManager.mCallback!!.closeMusicIcon()
        }
    }

    fun removeMusic(position: Int) {
        if (mMusicCallBack != null) {
            mMusicCallBack!!.onRemove(mMusicList[position])
        }
    }

    fun isPlaying(): Boolean {
        return isPlaying
    }

    fun isShowIcon():Boolean {
        return  curMusic != null
    }

    fun pausePlayMusic() {
        if (ChatRoomManager.mCallback != null) {
            ChatRoomManager.mCallback!!.showMusicIcon(false)
        }
        isPlaying = false
        ChatRoomManager.mRtcEngine.pauseAudioMixing()
        if (mMusicCallBack != null) {
            mMusicCallBack!!.onPauseMusic()
        }
    }

    fun resumePlayMusic() {
        if (ChatRoomManager.mStatusBean.status == 2) {
            ToastUtil.suc(CommonLib.mContext, "您已被禁麦")
            return
        }
        isPlaying = true
        if (ChatRoomManager.mCallback != null) {
            ChatRoomManager.mCallback!!.showMusicIcon(true)
        }
        ChatRoomManager.mRtcEngine.resumeAudioMixing()
        if (mMusicCallBack != null) {
            mMusicCallBack!!.onResumeMusic()
        }
    }

    fun playMusic(musicBean: MusicBean) {
        if (ChatRoomManager.mStatusBean.status == 2) {
            ToastUtil.suc(CommonLib.mContext, "您已被禁麦")
            return
        }
        if (ChatRoomManager.mCallback != null) {
            ChatRoomManager.mCallback!!.showMusicIcon(true)
        }
        curMusic = musicBean
        isPlaying = true
        ChatRoomManager.mRtcEngine.startAudioMixing(curMusic!!.url, false, false, 1)
        ChatRoomManager.setBGMVolume(ChatRoomManager.getBGMVolume())
        if (mMusicCallBack != null) {
            mMusicCallBack!!.onPlay(musicBean)
        }
        SPUtil.putLong(SPUtil.CONFIG_MUSIC_ID, musicBean.id)
    }

    fun playNextMusic() {
        if (curMusic == null) {
            return
        }
        mMusicList.forEachIndexed { index, musicBean ->
            if (musicBean.id == curMusic!!.id) {
                when (SPUtil.getInt(SPUtil.CONFIG_MUSIC_REPEAT_MODE, 0)) {
                    0 -> {  //列表
                        if ((index + 1) >= (mMusicList.size)) {
                            playMusic(mMusicList[0])
                        } else {
                            playMusic(mMusicList[index + 1])
                        }
                    }
                    1 -> {  //随机
                        playMusic(mMusicList[Random().nextInt(mMusicList.size)])
                    }
                    2 -> {  //单曲
                        playMusic(musicBean)
                    }
                }
                return
            }
        }
    }

    fun quitMusic(){
        isPlaying = false
        curMusic = null
    }
}