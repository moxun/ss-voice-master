package com.miaomi.fenbei.voice.ui.main.adapter

import android.app.Activity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.MakeFriendBean
import com.miaomi.fenbei.base.bean.MsgBean
import com.miaomi.fenbei.base.config.BaseConfig
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.item_home_msg.view.*
import java.util.ArrayList

class HomeMsgAdapter(private var context: Activity): RecyclerView.Adapter<HomeMsgAdapter.ViewHolder>() {

    var list: ArrayList<MakeFriendBean> = ArrayList()
    lateinit var onItemClickListner:OnItemClickListner


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
        var view = LayoutInflater.from(context).inflate(R.layout.item_home_msg, parent, false)
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
            itemView.iv_face.isSelected = list[position].gender == BaseConfig.USER_INFO_GENDER_MAN
            itemView.isSelected = list[position].gender == BaseConfig.USER_INFO_GENDER_MAN
            ImgUtil.loadFaceIcon(context, list[position].face, itemView.iv_face)
//            ImgUtil.loadImg(context!!,list[position].,itemView.iv_medal)
            itemView.tv_name.text = list[position].nickname
            itemView.tv_content.text = list[position].content
            itemView.iv_face.setOnClickListener{
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id", list[position].user_id.toString())
                        .withString("user_name", list[position].nickname)
                        .withString("user_face", list[position].face)
                        .navigation()
            }
            itemView.setOnClickListener {
                if(onItemClickListner != null){
                    onItemClickListner.onItemClick(list[position])
                }
//                if (!TextUtils.isEmpty(list[position].room_id.toString())) {
//                    if (list[position].room_id == 0){
//                        ARouter.getInstance().build("/app/userhomepage")
//                                .withString("user_id", list[position].user_id.toString())
//                                .withString("user_name", list[position].nickname)
//                                .withString("user_face", list[position].face)
//                                .navigation()
//                    }else{
//                        if (list[position].room_id.toString() == ChatRoomManager.getRoomId()) {
//                            ToastUtil.error(context, "您已在该房间")
//                        } else {
//                            ChatRoomManager.joinChat(context, list[position].room_id.toString(), object : JoinChatCallBack {
//                                override fun onSuc() {
//                                }
//
//                                override fun onFail(msg: String) {
//                                    ToastUtil.error(itemView.context, msg)
//                                }
//                            })
//
//                        }
//                    }
//                }
            }
        }
    }
    interface OnItemClickListner{
        fun onItemClick(item:MakeFriendBean)
    }
}