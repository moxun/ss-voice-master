package com.miaomi.fenbei.room.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.RedPacketRankBean
import com.miaomi.fenbei.base.util.ImgUtil
import kotlinx.android.synthetic.main.room_item_red_packet_luck_rank.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by 
 * on 2020-01-02.
 */
class RedPacketLuckRankAdapter(data: List<RedPacketRankBean.ItemsBean>): BaseQuickAdapter<RedPacketRankBean.ItemsBean, BaseViewHolder>(R.layout.room_item_red_packet_luck_rank, data) {

    override fun convert(helper: BaseViewHolder, item: RedPacketRankBean.ItemsBean) {
        ImgUtil.loadCircleImg(helper.itemView.context.applicationContext, item.face, helper.itemView.header_iv, R.drawable.common_avter_placeholder)
        helper.itemView.nick_tv.text = item.nickname
        helper.itemView.time_tv.text = SimpleDateFormat("HH:mm").format(Date(item.grab_time * 1000L))
        helper.itemView.diamond_tv.text = item.diamonds.toString()
        helper.itemView.first_luck_iv.visibility = if (item.best == 1) View.VISIBLE else View.GONE
    }
}