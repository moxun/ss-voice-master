package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import android.widget.SeekBar
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.RoomMusicManager
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.base.bean.VoiceTypeBean
import com.miaomi.fenbei.room.ui.adapter.VoiceTypeAdapter
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import kotlinx.android.synthetic.main.room_dialog_voice_setting.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by 
 * on 2019-07-16.
 */
class VoiceSettingDialog: BaseBottomDialog() {

    private lateinit var v: View
    
    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_voice_setting
    }

    override fun bindView(v: View) {
        this.v = v
        var adapter = VoiceTypeAdapter()
        adapter.setData(getVoiceTypeList())
//        v.voice_rl.adapter = adapter
        adapter.setOnItemClickListener(object : VoiceTypeAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                ChatRoomManager.setVoiceChange(position)
            }
        })
//        v.voice_rl.layoutManager = GridLayoutManager(v.context, 4, GridLayoutManager.VERTICAL, false)

        v.record_volume_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                ChatRoomManager.setRecordingVolume(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        v.bgm_volume_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                ChatRoomManager.setBGMVolume(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        v.voice_st.setOnCheckedChangeListener { _, isChecked ->
            ChatRoomManager.remoteVoiceCtrl(isChecked)
        }
        v.ear_monitoring_st.setOnCheckedChangeListener{ _, isChecked ->
            ChatRoomManager.setEnableInEarMonitoring(isChecked)
        }

        v.bgm_volume_seekbar.isEnabled = RoomMusicManager.isPlaying()
        v.bgm_volume_seekbar.progress = ChatRoomManager.getBGMVolume()
        v.record_volume_seekbar.progress = ChatRoomManager.getRecordingVolume()
        v.voice_st.isChecked = ChatRoomManager.isCloseVoice
        v.ear_monitoring_st.isChecked = ChatRoomManager.isEarMonitoring
    }

    private fun getVoiceTypeList(): ArrayList<VoiceTypeBean> {
        var data = ArrayList<VoiceTypeBean>()
        data.add(VoiceTypeBean(false,"原声"))
        data.add(VoiceTypeBean(false,"老男孩"))
        data.add(VoiceTypeBean(false,"小男孩"))
        data.add(VoiceTypeBean(false,"小女孩"))
        data.add(VoiceTypeBean(false,"猪八戒"))
        data.add(VoiceTypeBean(false,"空灵"))
        data.add(VoiceTypeBean(false,"绿巨人"))
        data[ChatRoomManager.voiceType].isSelect = true
        return data
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }

}