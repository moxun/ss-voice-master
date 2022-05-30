package com.miaomi.fenbei.voice.ui.main.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.base.bean.ChatListBean
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.item_home_all_room.view.*

class RoomListAdapter(private val context: Activity) : RecyclerView.Adapter<RoomListAdapter.ViewHolder>() {
    private var mList: MutableList<ChatListBean> = ArrayList()

    fun setData(list: MutableList<ChatListBean>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hot_room_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])   //有数据打开
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: ChatListBean) {
            itemView.chat_name.text = bean.chat_room_name
            ImgUtil.loadRoundImg(itemView.context, bean.chat_room_icon, itemView.chat_icon, 8f)
            itemView.setOnClickListener {
                ChatRoomManager.joinChat(context, bean.chat_room_id, object : JoinChatCallBack {
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