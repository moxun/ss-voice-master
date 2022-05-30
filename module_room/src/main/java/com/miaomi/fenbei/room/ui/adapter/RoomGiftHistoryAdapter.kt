package com.miaomi.fenbei.room.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.RoomGiftHistoryBean
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.TimeUtil
import kotlinx.android.synthetic.main.room_item_room_gift_history.view.*

class RoomGiftHistoryAdapter(context:Context) : RecyclerView.Adapter<RoomGiftHistoryAdapter.ViewHolder>() {

    private var mList :ArrayList<RoomGiftHistoryBean> = ArrayList()
    private var mContext:Context = context

    fun setData(list: List<RoomGiftHistoryBean>) {
        this.mList.clear()
        this.mList.addAll(list)
        notifyDataSetChanged()
    }
    fun addData(list: List<RoomGiftHistoryBean>) {
        this.mList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_item_room_gift_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: RoomGiftHistoryBean) {
            itemView.tv_num.text = bean.num.toString()
            itemView.tv_from_user.text = bean.send_nickname
            itemView.tv_to_user.text = bean.get_nickname
            itemView.tv_time.text = TimeUtil.getMSTime(bean.time)
            if (bean.d_fan_type == 0){
                itemView.tv_status.text = "送给"
            }else{
                itemView.tv_status.text = "加入"
            }
//            ImgUtil.loadFaceIcon(mContext,bean.send_face,itemView.iv_from_user)
//            ImgUtil.loadFaceIcon(mContext,bean.get_face,itemView.iv_to_user)
            if (bean.gift_icon != null){
                ImgUtil.loadGiftImg(mContext,bean.gift_icon,itemView.iv_gift)
            }
        }
    }
}