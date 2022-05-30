package com.miaomi.fenbei.voice.ui.square

import android.app.Activity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.MakeFriendBean
import com.miaomi.fenbei.base.bean.MsgBean
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.chatting_item_full_service_msg.view.*
import kotlinx.android.synthetic.main.chatting_item_full_service_msg.view.iv_sex
import java.util.*

class MakingFriendsAdapter(private var context: Activity): RecyclerView.Adapter<MakingFriendsAdapter.ViewHolder>() {

    var list: ArrayList<MakeFriendBean> = ArrayList()


    fun setRealData(msgs: List<MakeFriendBean>) {
        list.clear()
        list.addAll(msgs)
        this.notifyDataSetChanged()
    }

    fun addRealData(msgs: ArrayList<MakeFriendBean>) {
        list.addAll(msgs)
        this.notifyDataSetChanged()
    }

    fun setData(msgs: ArrayList<MsgBean>) {
        list.clear()
        for (msg in msgs){
            list.add(MakeFriendBean(msg))
        }
        this.notifyDataSetChanged()
    }

    fun addData(msgs: ArrayList<MsgBean>) {
        for (msg in msgs){
            list.add(MakeFriendBean(msg))
        }
        this.notifyDataSetChanged()
    }

    fun getData(): ArrayList<MakeFriendBean> {
        return list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.chatting_item_full_service_msg, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {

            if (!TextUtils.isEmpty(list[position].medal)){
                itemView.medal_iv.visibility = View.VISIBLE
                ImgUtil.loadImg(itemView.context,list[position].medal, itemView.medal_iv)
            }else{
                itemView.medal_iv.visibility = View.GONE
            }
            ImgUtil.loadFaceIcon(context, list[position].face, itemView.user_header_iv)
//            ImgUtil.loadImg(context!!,list[position].,itemView.iv_medal)
            itemView.iv_sex.setSeleted(list[position].gender)
            itemView.user_nick_tv.text = list[position].nickname
            itemView.msg_content_tv.text = list[position].content
            itemView.wealth_level_iv.setWealthLevel(list[position].wealth_level)
            itemView.setOnClickListener {
                if (!TextUtils.isEmpty(list[position].room_id.toString())) {
                    if (list[position].room_id == 0){
                        ARouter.getInstance().build("/app/userhomepage")
                                .withString("user_id", list[position].user_id.toString())
                                .withString("user_name", list[position].nickname)
                                .withString("user_face", list[position].face)
                                .navigation()
                    }else{
                        if (list[position].room_id.toString() == ChatRoomManager.getRoomId()) {
                            ToastUtil.error(context, "您已在该房间")
                        } else {
                            ChatRoomManager.joinChat(context, list[position].room_id.toString(), object : JoinChatCallBack {
                                override fun onSuc() {
                                }

                                override fun onFail(msg: String) {
                                    ToastUtil.error(itemView.context, msg)
                                }
                            })

                        }
                    }
                }
            }
        }
    }
}