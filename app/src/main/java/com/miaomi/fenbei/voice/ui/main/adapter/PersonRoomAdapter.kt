package com.miaomi.fenbei.voice.ui.main.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.miaomi.fenbei.base.bean.PersonRoomItemBean
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.item_room_person.view.*

class PersonRoomAdapter : BaseQuickAdapter<PersonRoomItemBean, BaseViewHolder>(R.layout.item_room_person) {
    override fun convert(helper: BaseViewHolder, item: PersonRoomItemBean) {
        ImgUtil.loadFaceIcon(helper.itemView.context,item.face,helper.itemView.chat_icon)
        helper.itemView.chat_name.text = item.name
        helper.itemView.nick_name.text = item.nickname
        helper.itemView.tv_label.text = item.label
        helper.itemView.setOnClickListener {
            ChatRoomManager.joinChat(helper.itemView.context,item.room_id.toString(),object : JoinChatCallBack {
                override fun onSuc() {
                }
                override fun onFail(msg: String) {
                    ToastUtil.error(helper.itemView.context,msg)
                }
            })
        }
        if ("交友".equals(item.label)){
            helper.itemView.tv_label.setTextColor(Color.parseColor("#ED9378"))
            helper.itemView.cl_content.setBackgroundResource(R.drawable.bg_item_room_person_jy)
        }
        if ("娱乐".equals(item.label)){
//            helper.itemView.tv_label.text = "娱乐"
            helper.itemView.tv_label.setTextColor(Color.parseColor("#5DCED2"))
            helper.itemView.cl_content.setBackgroundResource(R.drawable.bg_item_room_person_yl)
        }
        if ("相亲".equals(item.label)){
//            helper.itemView.tv_label.text = "相亲"
            helper.itemView.tv_label.setTextColor(Color.parseColor("#ED7E7F"))
            helper.itemView.cl_content.setBackgroundResource(R.drawable.bg_item_room_person_xq)
        }
        if ("电台".equals(item.label)){
//            helper.itemView.tv_label.text = "电台"
            helper.itemView.tv_label.setTextColor(Color.parseColor("#5099F0"))
            helper.itemView.cl_content.setBackgroundResource(R.drawable.bg_item_room_person_dt)
        }
        if ("Pia戏".equals(item.label)){
//            helper.itemView.tv_label.text = "Pia戏"
            helper.itemView.tv_label.setTextColor(Color.parseColor("#D37FF3"))
            helper.itemView.cl_content.setBackgroundResource(R.drawable.bg_item_room_person_px)
        }
        if ("游戏".equals(item.label)){
//            helper.itemView.tv_label.text = "游戏"
            helper.itemView.tv_label.setTextColor(Color.parseColor("#756FF2"))
            helper.itemView.cl_content.setBackgroundResource(R.drawable.bg_item_room_person_yx)
        }
    }

}