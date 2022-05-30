package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import android.widget.TextView
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.base.share.core.Share
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ShareDialog : BaseBottomDialog() {
    var share_title:String =""
    var share_desc:String = ""
    var share_icon:String = ""
    var share_url:String = ""




    override fun getLayoutRes(): Int {

        return R.layout.room_dialog_share
    }


    override fun bindView(v: View) {

        v.findViewById<TextView>(R.id.wx_share).setOnClickListener {
           Share.wxShare(activity!!,"http://test-decibel-web.cnciyin.com/download")
            dismiss()
        }
        v.findViewById<TextView>(R.id.circle_share).setOnClickListener {
           Share.wxPyqShare(activity!!,"http://test-decibel-web.cnciyin.com/download")
            dismiss()

        }
        v.findViewById<TextView>(R.id.qq_share).setOnClickListener {
            Share.qqShare(activity!!,"http://test-decibel-web.cnciyin.com/download")
            dismiss()

        }
        v.findViewById<TextView>(R.id.qzone_share).setOnClickListener {
            Share.QZONEPyqShare(activity!!,"http://test-decibel-web.cnciyin.com/download")
            dismiss()

        }
        v.findViewById<TextView>(R.id.weibo_share).setOnClickListener {
            dismiss()
        }

        v.findViewById<TextView>(R.id.cancel_share_btn).setOnClickListener {
            dismiss()
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }

}