package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.base.bean.ApplyInfoList
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.Data
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.LoadHelper
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.ui.adapter.ApplyListAdapter
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ApplyListDialog : BaseBottomDialog() {

    private lateinit var loadHelper: LoadHelper

    override fun getLayoutRes(): Int {

        return R.layout.room_dialog_apply_list
    }


    override fun bindView(v: View) {

        val applyList = v.findViewById<RecyclerView>(R.id.apply_rv)
        applyList.layoutManager = LinearLayoutManager(context)
        val adapter = ApplyListAdapter()
        applyList.adapter = adapter
        loadHelper = LoadHelper()
        loadHelper.registerLoad(applyList)
        bindData(adapter)
    }

    private fun bindData(adapter: ApplyListAdapter) {
        NetService.getInstance(context!!).getApplyList(ChatRoomManager.getRoomId(),object : Callback<ApplyInfoList>(){
            override fun onSuccess(nextPage: Int, bean: ApplyInfoList, code: Int) {
                if (bean.list.isEmpty()) {
                    loadHelper.setEmptyCallback(0,"暂无申请记录~")
                } else {
                    loadHelper.bindView(Data.CODE_SUC)
                }
                adapter.setData(bean.list)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {

            }

            override fun isAlive(): Boolean {
                return true
            }

        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }

}