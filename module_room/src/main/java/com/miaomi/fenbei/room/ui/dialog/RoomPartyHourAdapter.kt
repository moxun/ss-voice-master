package com.miaomi.fenbei.room.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.base.bean.PartyRoomHourBean
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_item_room_party_hour.view.*

class RoomPartyHourAdapter  : RecyclerView.Adapter<RoomPartyHourAdapter.ViewHolder>() {

    private var mList :ArrayList<PartyRoomHourBean.ListBean> = ArrayList()
//    private var mContext: Context = context

    fun setData(list: List<PartyRoomHourBean.ListBean>) {
        this.mList.clear()
        this.mList.addAll(list)
        notifyDataSetChanged()
    }
    fun addData(list: List<PartyRoomHourBean.ListBean>) {
        this.mList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_item_room_party_hour, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: PartyRoomHourBean.ListBean) {
//            itemView.tv_num.text = bean.num.toString()
            itemView.name_tv.text = bean.name
//            itemView.tv_to_user.text = bean.get_nickname
//            itemView.tv_time.text = TimeUtil.getMSTime(bean.time)
//            ImgUtil.loadFaceIcon(mContext,bean.send_face,itemView.iv_from_user)
            ImgUtil.loadRoundImg(itemView.context,bean.icon,itemView.header_iv)
//            ImgUtil.loadGiftImg(mContext,bean.gift_icon,itemView.iv_gift)
        }
    }
}