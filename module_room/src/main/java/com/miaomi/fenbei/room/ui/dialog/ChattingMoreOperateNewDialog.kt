package com.miaomi.fenbei.room.ui.dialog

import android.content.DialogInterface
import android.os.Bundle

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.ChattingOperateBean
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.room.ui.adapter.ChattingOperateAdapter
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.gift.GiftManager
import kotlinx.android.synthetic.main.room_dialog_more_operate_new.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * Created by
 * on 2019-08-28.
 */
class ChattingMoreOperateNewDialog: BaseBottomDialog() {

    companion object {
        fun init(isShowApplyNotice: Boolean): ChattingMoreOperateNewDialog {
            val dialogFragment = ChattingMoreOperateNewDialog()
            val bundle = Bundle()
            bundle.putBoolean("isShow", isShowApplyNotice)
            dialogFragment.arguments = bundle
            return dialogFragment
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_more_operate_new
    }

    override fun bindView(v: View) {
        val data = ArrayList<ChattingOperateBean>()
        data.add(ChattingOperateBean(if (!ChatRoomManager.isCloseVoice) R.drawable.room_icon_opreate_sound_open else R.drawable.room_icon_opreate_sound_close,
                if (!ChatRoomManager.isCloseVoice) "关闭声音" else "打开声音", ChattingOperateAdapter.TYPE_VOICE))
//        data.add(ChattingOperateBean(R.drawable.room_icon_opreate_redpack, "红包", ChattingOperateAdapter.TYPE_ROOM_REDPACK))
        if (ChatRoomManager.getMicPosition() != 0 || ChatRoomManager.isRoomHost()) {
            data.add(ChattingOperateBean(R.drawable.room_icon_opreate_music, "音乐库", ChattingOperateAdapter.TYPE_MUSIC))
        }
        data.add(ChattingOperateBean(R.drawable.room_icon_opreate_volue, "调音台", ChattingOperateAdapter.TYPE_VOICE_SETTING))
        data.add(ChattingOperateBean(if (GiftManager.getInstance().isOpenAnim) R.drawable.room_icon_opreate_gift_special_n else R.drawable.room_icon_opreate_gift_special_p,
                if (GiftManager.getInstance().isOpenAnim) "关闭特效" else "开启特效", ChattingOperateAdapter.TYPE_SPECIAL))
        data.add(ChattingOperateBean(if (GiftManager.getInstance().isOpenFullService) R.drawable.room_icon_full_notify_open else R.drawable.room_icon_full_notify_close,
                if (GiftManager.getInstance().isOpenFullService) "关闭全服" else "开启全服", ChattingOperateAdapter.TYPE_FULL_SERVICE))
//        data.add(ChattingOperateBean(R.drawable.room_icon_opreate_share, "分享房间", ChattingOperateAdapter.TYPE_ROOM_SHARE))
          if (ChatRoomManager.getUserRole() > 0) {
            data.add(ChattingOperateBean(R.drawable.room_icon_opreate_clear, "清公屏", ChattingOperateAdapter.TYPE_CHAT_CLEAR))
            data.add(ChattingOperateBean(if (ChatRoomManager.mCloseChat == 0) R.drawable.room_icon_opreate_gift_special_n else R.drawable.room_icon_opreate_gift_special_p,
                    if (ChatRoomManager.mCloseChat == 0) "关公屏" else "开公屏", ChattingOperateAdapter.TYPE_CHAT_CLOSE))
            data.add(ChattingOperateBean(R.drawable.room_icon_opreate_heart, "清除魅力值", ChattingOperateAdapter.TYPE_ROOM_CLEAR_MIKE))
            data.add(ChattingOperateBean(R.drawable.room_icon_opreate_room_setting, "房间设置", ChattingOperateAdapter.TYPE_ROOM_SETTING))
            data.add(ChattingOperateBean(R.drawable.room_icon_opreate_room_locked, "房间上锁", ChattingOperateAdapter.TYPE_ROOM_LOCKED))
            if (ChatRoomManager.isRoomHost()){
                data.add(ChattingOperateBean(R.drawable.room_icon_opreate_room_manager, "管理员", ChattingOperateAdapter.TYPE_ROOM_MANAGER))
            }
//            if (ChatRoomManager.roomType == ChatRoomManager.ROOM_TYPE_LABOR_UNION) {
//                data.add(ChattingOperateBean(R.drawable.chatting_room_icon_close_live, "关闭房间", ChattingOperateAdapter.TYPE_ROOM_CLOSE_LIVE))
//            }
        } else {
//            data.add(ChattingOperateBean(R.drawable.room_icon_opreate_report, "举报房间", ChattingOperateAdapter.TYPE_ROOM_REPORT))
        }
        if (ChatRoomManager.isSuperManager()) {
            data.add(ChattingOperateBean(R.drawable.room_icon_opreate_close_room, "强制关房", ChattingOperateAdapter.TYPE_ROOM_CLOSE))
        }
//        data.add(ChattingOperateBean(if (DataHelper.isFullChatOpen()) R.drawable.chatting_room_icon_full_chat_p else R.drawable.chatting_room_icon_full_chat_n,
//                if (DataHelper.isFullChatOpen()) "关闭全服公聊" else "开启全服公聊", ChattingOperateAdapter.TYPE_ROOM_FULL_CHAT))
        var adapter = ChattingOperateAdapter()
        v.operate_rl.adapter = adapter
        adapter.setData(data)
        v.operate_rl.layoutManager = GridLayoutManager(context, 6, GridLayoutManager.VERTICAL, false)
        adapter.setOnItemClickListener(object : ChattingOperateAdapter.OnItemClickListener {
            override fun onItemClick(type: Int) {
                listener?.apply {
                    when (type) {
                        ChattingOperateAdapter.TYPE_VOICE -> {
                            onVoiceClick()
                        }
                        ChattingOperateAdapter.TYPE_MUSIC -> {
                            onMusicClick()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_REDPACK -> {
                            onSendRedPack()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_REPORT -> {
                            onRoomReport()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_SETTING -> {
                            onRoomSetting()
                        }
                        ChattingOperateAdapter.TYPE_CHAT_CLOSE -> {
                            onCloseOrOpenChat(if (ChatRoomManager.mCloseChat == 0) 1 else 0)
                        }
                        ChattingOperateAdapter.TYPE_CHAT_CLEAR -> {
                            onClearChat()
                        }
                        ChattingOperateAdapter.TYPE_SPECIAL -> {
                            onCloseOrOpenSpecial()
                        }
                        ChattingOperateAdapter.TYPE_FULL_SERVICE -> {
                            onCloseOrOpenFullService()
                        }
                        ChattingOperateAdapter.TYPE_VOICE_SETTING -> {
                            onVoiceSettingClick()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_CLOSE -> {
                            onRoomClose()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_CLEAR_MIKE -> {
                            onClearMike()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_CLOSE_LIVE -> {
                            onLiveClose()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_MIC -> {
                            onCloseOrOpenMic()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_APPLY_LIST -> {
                            onApplyList()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_EMOJI -> {
                            onEmoji()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_SHARE -> {
                            onShare()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_LOCKED -> {
                            onRoomLocked()
                        }
                        ChattingOperateAdapter.TYPE_ROOM_MANAGER -> {
                            onRoomManager()
                        }
                        else -> {}
                    }
                    this@ChattingMoreOperateNewDialog.dismiss()
                }
            }
        })
    }

    override fun onDismiss(dialog: DialogInterface?) {
        listener?.apply { dismiss() }
        super.onDismiss(dialog)
    }

    interface OnOperateClickListener {
        fun onVoiceClick()
        fun onMusicClick()
        fun onRoomReport()
        fun onRoomSetting()
        fun onVoiceSettingClick()
        fun onClearChat()
        fun onCloseOrOpenChat(type: Int)
        fun onCloseOrOpenSpecial()
        fun onCloseOrOpenFullService()
        fun onRoomClose()
        fun onClearMike()
        fun onLiveClose()
        fun onCloseOrOpenMic()
        fun onApplyList()
        fun onEmoji()
        fun onShare()
        fun dismiss()
        fun onRoomLocked()
        fun onRoomManager()
        fun onSendRedPack()
    }

    private var listener: OnOperateClickListener? = null

    fun setOnOperateClickListener (listener: OnOperateClickListener) {
        this.listener = listener
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }
}