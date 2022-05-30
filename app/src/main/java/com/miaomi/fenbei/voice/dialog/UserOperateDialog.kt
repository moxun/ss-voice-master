package com.miaomi.fenbei.voice.dialog

import android.view.View
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.dialog_user_operate.view.*

class UserOperateDialog: BaseBottomDialog() {


    companion object {

        const val USER_OPERATE_GZ = 1
        const val USER_OPERATE_JB = 2

//        fun init(bean: UserInfo): MicOperateDialog {
//            var dialog = MicOperateDialog()
//            var bundle = Bundle()
//            bundle.putParcelable("data", bean)
//            dialog.arguments = bundle
//            return dialog
//        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.dialog_user_operate
    }

    override fun bindView(v: View) {
        v.btn_1.setOnClickListener {
            onUserOperateClickListener?.apply { onItemClick(USER_OPERATE_GZ) }
            dismiss()
        }
        v.btn_2.setOnClickListener {
            onUserOperateClickListener?.apply { onItemClick(USER_OPERATE_JB) }
            dismiss()
        }
        v.cancel_btn.setOnClickListener { dismiss() }
    }

    interface OnUserOperateClickListener {
        fun onItemClick(operateType: Int)
    }

    private var onUserOperateClickListener: OnUserOperateClickListener? = null

    fun setOnUserOperateClickListener(onUserOperateClickListener: OnUserOperateClickListener){
        this.onUserOperateClickListener = onUserOperateClickListener
    }
}