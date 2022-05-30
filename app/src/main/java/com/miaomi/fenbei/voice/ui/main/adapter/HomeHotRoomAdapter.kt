package com.miaomi.fenbei.voice.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.base.bean.ChatListBean
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.item_home_hot_room.view.*

class HomeHotRoomAdapter(private val context: Context) : RecyclerView.Adapter<HomeHotRoomAdapter.ViewHolder>() {
    private var mList: MutableList<ChatListBean> = ArrayList()


    fun setData(list: MutableList<ChatListBean>){
        mList = list
        notifyDataSetChanged()
    }

    fun addData(list: MutableList<ChatListBean>){
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_hot_room, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: ChatListBean){
            itemView.chat_name.text = bean.chat_room_name
            itemView.online_num.text = bean.hot_value
            ImgUtil.loadHotRoomRoundImg(itemView.context,bean.chat_room_icon,itemView.chat_icon,4f)
            itemView.tv_label.setLevel(bean.label)
            itemView.setOnClickListener {
                ChatRoomManager.joinChat(context,bean.chat_room_id,object : JoinChatCallBack {
                    override fun onSuc() {

                    }
                    override fun onFail(msg: String) {
                        ToastUtil.error(itemView.context,msg)
                    }
                })

            }
        }
    }
}