package com.miaomi.fenbei.voice.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.miaomi.fenbei.base.AudioPlayer
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.dialog_music_audition.*

class MusicAuditionDialog(musicName:String,musicPath:String,context: Context) : Dialog(context, R.style.common_dialog) {

    private var name = musicName
    private var path = musicPath
    private var isPlaying = true
    override fun dismiss() {
        super.dismiss()
        AudioPlayer.getInstance().stopPlay()
        isPlaying = false
        tv_name.stopScroll()
        iv_change_play_status.setImageResource(R.drawable.room_icon_music_operate_start)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_music_audition)
        tv_name.text = this.name
        tv_name.startScroll()
        AudioPlayer.getInstance().startPlay(path) { }
        iv_change_play_status.setImageResource(R.drawable.room_icon_music_operate_pause)
        iv_change_play_status.setOnClickListener{
            if (!isPlaying) {
                isPlaying = true
                tv_name.startScroll()
                AudioPlayer.getInstance().startPlay(path) { }
                iv_change_play_status.setImageResource(R.drawable.room_icon_music_operate_pause)
            } else {
                AudioPlayer.getInstance().stopPlay()
                isPlaying = false
                tv_name.stopScroll()
                iv_change_play_status.setImageResource(R.drawable.room_icon_music_operate_start)
            }
        }


    }

}