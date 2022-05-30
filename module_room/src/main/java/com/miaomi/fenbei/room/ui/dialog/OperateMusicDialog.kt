package com.miaomi.fenbei.room.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.SeekBar
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.RoomMusicManager
import com.miaomi.fenbei.room.callback.ChatRoomOnMusicCallBack
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.base.bean.db.MusicBean
import kotlinx.android.synthetic.main.room_dialog_music_operate.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class OperateMusicDialog(context: Context) : Dialog(context, R.style.common_dialog), ChatRoomOnMusicCallBack {
    override fun progress(progress: Int) {
    }

    override fun onRemove(musicBean: MusicBean) {
    }

    override fun onPlay(musicBean: MusicBean?) {
        tv_name.text = musicBean?.name
    }

    override fun onPauseMusic() {
        iv_change_play_status.setImageResource(R.drawable.room_icon_music_operate_start)
    }

    override fun onResumeMusic() {
        iv_change_play_status.setImageResource(R.drawable.room_icon_music_operate_pause)

    }

    override fun dismiss() {
        super.dismiss()
        tv_name.stopScroll()
        EventBus.getDefault().unregister(this)
        RoomMusicManager.unregisterMusic()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.room_dialog_music_operate)
        EventBus.getDefault().register(this)
        RoomMusicManager.registerMusic(this)
        tv_name.text = RoomMusicManager.getCurMusic()?.name
        bgm_volume_seekbar.progress = ChatRoomManager.getBGMVolume()
        tv_name.startScroll()
        iv_close.setOnClickListener{
            RoomMusicManager.pausePlayMusic()
            dismiss()
            mOnOperateMusic?.onClose()
        }

        iv_next.setOnClickListener{
            RoomMusicManager.playNextMusic()
        }
        iv_change_mode.setOnClickListener{
            dismiss()
            mOnOperateMusic?.onShowList()
        }
        if (RoomMusicManager.isPlaying()) {
            iv_change_play_status.setImageResource(R.drawable.room_icon_music_operate_pause)
        } else {
            iv_change_play_status.setImageResource(R.drawable.room_icon_music_operate_start)
        }
        iv_change_play_status.setOnClickListener{
            if (RoomMusicManager.isPlaying()) {
                RoomMusicManager.pausePlayMusic()
            } else {
                RoomMusicManager.resumePlayMusic()
            }
        }

        bgm_volume_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                ChatRoomManager.setBGMVolume(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        if (isShowing) dismiss()
    }

    var mOnOperateMusic: OnOperateMusic? = null
    public fun setOnOperateMusic(onOperateMusic: OnOperateMusic){
        mOnOperateMusic = onOperateMusic
    }
    public interface OnOperateMusic{
        fun onClose(){}
        fun onShowList(){}
    }
}