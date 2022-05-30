package com.miaomi.fenbei.voice.ui.mine.redpack

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.miaomi.fenbei.base.bean.GetRedPacketUsersBean
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.room_dialog_red_packet_get_uses.*

class GetRedPacketUsersDialog(context: Context, private var order_no: String): Dialog(context, R.style.common_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_dialog_red_packet_get_uses)

        close_iv.setOnClickListener { dismiss() }


        getGrabList()
        rank_rl.layoutManager = LinearLayoutManager(context)
    }

    private fun getGrabList() {
        NetService.getInstance(context).getRedRecordDetail(order_no, object : Callback<List<GetRedPacketUsersBean>>() {
            override fun onSuccess(nextPage: Int, bean: List<GetRedPacketUsersBean>, code: Int) {
                rank_rl.adapter = GetRedPacketUsersAdapter(bean)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.suc(context, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }
}