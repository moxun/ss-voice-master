package com.miaomi.fenbei.room.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.RedPacketRankBean
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.room.ui.adapter.RedPacketLuckRankAdapter
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.util.ToastUtil
import kotlinx.android.synthetic.main.room_dialog_red_packet.close_iv
import kotlinx.android.synthetic.main.room_dialog_red_packet_luck_rank.*

/**
 * Created by 
 * on 2020/1/2.
 */
class RedPacketLuckRankDialog(context: Context, private var collection_id: String): Dialog(context, R.style.common_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_dialog_red_packet_luck_rank)

        close_iv.setOnClickListener { dismiss() }

        back_btn.setOnClickListener { dismiss() }

        getGrabList()
    }

    private fun getGrabList() {
        NetService.getInstance(context).getGrabList(collection_id, object : Callback<RedPacketRankBean>() {
            override fun onSuccess(nextPage: Int, bean: RedPacketRankBean, code: Int) {
                rank_rl.layoutManager = LinearLayoutManager(context)
                rank_rl.adapter = RedPacketLuckRankAdapter(bean.items)
                got_num_tv.text = "领取${bean.grab_count}/${bean.split_count}个"
                total_diamond_tv.text = "总金额${bean.diamonds}钻石"
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