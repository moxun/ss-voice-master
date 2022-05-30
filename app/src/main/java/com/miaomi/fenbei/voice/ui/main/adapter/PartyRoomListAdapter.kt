package com.miaomi.fenbei.voice.ui.main.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.base.bean.ChatListBean
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.item_home_all_room.view.*

class PartyRoomListAdapter(private val context: Activity) : RecyclerView.Adapter<PartyRoomListAdapter.ViewHolder>() {
    private var mList: MutableList<ChatListBean> = ArrayList()
    private var onRoomLongClickListner: OnRoomLongClickListner? = null

    fun setOnRoomLongClickListner(onRoomLongClickListner: OnRoomLongClickListner) {
        this.onRoomLongClickListner = onRoomLongClickListner
    }


    fun setData(list: MutableList<ChatListBean>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(list: MutableList<ChatListBean>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_all_room, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])   //有数据打开
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: ChatListBean) {
            itemView.chat_name.text = bean.chat_room_name
//            itemView.tv_host_name.text = bean.host_name
//            if(itemView.chat_name.text.length >= 8){
//                itemView.chat_name.startScroll()
//            }
            if (bean.locked == 1){
                itemView.chat_lock.visibility = View.VISIBLE
            }else{
                itemView.chat_lock.visibility = View.GONE
            }
            itemView.online_num.text = bean.hot_value
            itemView.iv_host_face.startAnimation(AnimationUtils.loadAnimation(itemView.context, R.anim.home_page_head))
            ImgUtil.loadGif(itemView.context, R.drawable.base_icon_room_online, itemView.iv_room_gif)
            ImgUtil.loadRoundImg(itemView.context, bean.chat_room_icon, itemView.chat_icon, 8f)
            ImgUtil.loadFaceIcon(itemView.context, bean.host_face, itemView.iv_host_face)
            itemView.tv_label.setLevel(bean.label)

            itemView.setOnClickListener {
                ChatRoomManager.joinChat(context, bean.chat_room_id, object : JoinChatCallBack {
                    override fun onSuc() {

                    }

                    override fun onFail(msg: String) {
                        ToastUtil.error(itemView.context, msg)
                    }
                })
            }
            itemView.setOnLongClickListener {
                if (onRoomLongClickListner != null) {
                    onRoomLongClickListner!!.onClick(bean.chat_room_name, bean.chat_room_id)
                }
                true
            }
        }
    }

    interface OnRoomLongClickListner {
        fun onClick(roomName: String, roomId: String)
    }
}