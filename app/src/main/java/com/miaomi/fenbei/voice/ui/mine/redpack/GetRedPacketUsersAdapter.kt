package com.miaomi.fenbei.voice.ui.mine.redpack

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.miaomi.fenbei.base.bean.GetRedPacketUsersBean
import com.miaomi.fenbei.base.util.TimeUtil
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.room_item_red_packet_get_user.view.*

class GetRedPacketUsersAdapter (data: List<GetRedPacketUsersBean>): BaseQuickAdapter<GetRedPacketUsersBean, BaseViewHolder>(R.layout.room_item_red_packet_get_user, data) {

    override fun convert(helper: BaseViewHolder, item: GetRedPacketUsersBean) {
        helper.itemView.nick_tv.text = item.nickname + "(ID:"+item.user_id+")"
        helper.itemView.diamond_tv.text = item.diamonds.toString()+"钻石"
        helper.itemView.tv_time.setText(TimeUtil.getDayTime(item.create_time))

    }
}