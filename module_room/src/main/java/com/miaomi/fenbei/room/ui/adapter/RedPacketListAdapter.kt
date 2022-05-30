package com.miaomi.fenbei.room.ui.adapter

import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.RedPacketBean
import kotlinx.android.synthetic.main.room_item_red_packet_list.view.*

/**
 * Created by 
 * on 2020-01-03.
 */
class RedPacketListAdapter(redPacketBeans : ArrayList<RedPacketBean>): BaseQuickAdapter<RedPacketBean, RedPacketListAdapter.CountDownHolder>(R.layout.room_item_red_packet_list, redPacketBeans) {

    private var countDownTimers = ArrayList<CountDownTimer>()

    override fun convert(helper: CountDownHolder, item: RedPacketBean) {
        helper.countDownTimer?.let { it.cancel() }
        helper.countDownTimer = CustomerCountDownTimer(item.expired_at * 1000 - System.currentTimeMillis(), 1000, helper)
        helper.countDownTimer?.start()
        countDownTimers.add(helper.countDownTimer!!)
    }

    inner class CustomerCountDownTimer : CountDownTimer {

        private var view: TextView? = null
        private var helper: CountDownHolder? = null

        constructor(millisInFuture: Long, countDownInterval: Long, helper: CountDownHolder) : super(millisInFuture, countDownInterval) {
            this.view = helper.itemView.red_packet_time_tv
            this.helper = helper
        }

        override fun onFinish() {
            if (data.isEmpty()) return
            data?.removeAt(0)
            countDownTimers.removeAt(0)
            if (recyclerView.isComputingLayout) {
                recyclerView.post { notifyDataSetChanged() }
            } else {
                notifyDataSetChanged()
            }
        }

        override fun onTick(millisUntilFinished: Long) {
            view?.text = "${millisUntilFinished/1000}s"
        }
    }

    inner class CountDownHolder(view: View) : BaseViewHolder(view) {
        var countDownTimer: CountDownTimer? = null
    }

    fun cancelAllTimer() {
        countDownTimers.forEach { it?.cancel() }
        countDownTimers.clear()
    }
}