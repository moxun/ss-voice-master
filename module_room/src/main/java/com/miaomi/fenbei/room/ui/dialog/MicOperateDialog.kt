package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.UserInfo
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import kotlinx.android.synthetic.main.room_dialog_mic_operate.view.*

/**
 * Created by 
 * on 2019-08-22.
 */
class MicOperateDialog (bean: UserInfo): BaseBottomDialog() {

    private var mUserInfo: UserInfo = bean

    companion object {
        const val MIC_LOCKED = -1
        const val MIC_BANNED = 2

        const val MIC_OPERATE_LOCK = 1
        const val MIC_OPERATE_BAN = 2
        const val MIC_OPERATE_UP = 3
        const val MIC_OPERATE_CLEAR = 4

//        fun init(bean: UserInfo): MicOperateDialog {
//            var dialog = MicOperateDialog()
//            var bundle = Bundle()
//            bundle.putParcelable("data", bean)
//            dialog.arguments = bundle
//            return dialog
//        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_mic_operate
    }

    override fun bindView(v: View) {
        v.clear_layout.visibility = View.VISIBLE
        if (ChatRoomManager.isRoomHost()) {
            v.up_layout.visibility = View.GONE
        } else {
            v.up_layout.visibility = View.VISIBLE
        }
//        var bean = arguments!!.getParcelable("data") as UserInfo
        if (mUserInfo.user_id == MIC_LOCKED) {
            v.lock_btn.text = "解除锁麦"
        } else {
            v.lock_btn.text = "锁麦"
        }
        if (mUserInfo.status == MIC_BANNED) {
            v.ban_btn.text = "解除禁麦"
        } else {
            v.ban_btn.text = "禁麦"
        }
        v.lock_btn.setOnClickListener {
            onMicOperateClickListener?.apply { onMicOperateClick(MIC_OPERATE_LOCK) }
            dismiss()
        }
        v.ban_btn.setOnClickListener {
            onMicOperateClickListener?.apply { onMicOperateClick(MIC_OPERATE_BAN) }
            dismiss()
        }
        v.up_btn.setOnClickListener {
            onMicOperateClickListener?.apply { onMicOperateClick(MIC_OPERATE_UP) }
            dismiss()
        }
        v.clear_btn.setOnClickListener {
            onMicOperateClickListener?.apply { onMicOperateClick(MIC_OPERATE_CLEAR) }
            dismiss()
        }
        v.cancel_btn.setOnClickListener { dismiss() }
    }

    interface OnMicOperateClickListener {
        fun onMicOperateClick(operateType: Int)
    }

    private var onMicOperateClickListener: OnMicOperateClickListener? = null

    fun setOnMicOperateClickListener(onMicOperateClickListener: OnMicOperateClickListener): MicOperateDialog {
        this.onMicOperateClickListener = onMicOperateClickListener
        return this
    }
}