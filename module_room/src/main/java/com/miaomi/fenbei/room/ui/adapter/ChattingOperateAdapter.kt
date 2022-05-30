package com.miaomi.fenbei.room.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.ChattingOperateBean
import kotlinx.android.synthetic.main.room_item_operate.view.*
import java.util.*

/**
 * Created by
 * on 2019-08-28.
 */
class ChattingOperateAdapter: RecyclerView.Adapter<ChattingOperateAdapter.ViewHolder>() {

    companion object {
        const val TYPE_VOICE = 1
        const val TYPE_MUSIC = 2
        const val TYPE_VOICE_SETTING = 3
        const val TYPE_SPECIAL = 4
        const val TYPE_CHAT_CLEAR = 5
        const val TYPE_CHAT_CLOSE = 6
        const val TYPE_ROOM_REPORT = 7
        const val TYPE_ROOM_SETTING = 8
        const val TYPE_ROOM_CLOSE = 9
        const val TYPE_ROOM_CLEAR_MIKE = 10
        const val TYPE_ROOM_CLOSE_LIVE = 11
        const val TYPE_ROOM_EMOJI = 12
        const val TYPE_ROOM_APPLY_LIST = 13
        const val TYPE_ROOM_MIC = 14
        const val TYPE_ROOM_SHARE = 15
        const val TYPE_ROOM_LOCKED = 16
        const val TYPE_ROOM_MANAGER = 17
        const val TYPE_ROOM_REDPACK = 118
        const val TYPE_FULL_SERVICE = 119
    }

    private var mList :ArrayList<ChattingOperateBean> = ArrayList()

    fun setData(list: ArrayList<ChattingOperateBean>) {
        this.mList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.room_item_operate, parent,false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bean: ChattingOperateBean) {
            itemView.operate_tv.text = bean.title
            itemView.operate_tv.setCompoundDrawablesWithIntrinsicBounds(0, bean.icon, 0, 0)
            itemView.apply_notice_tv.visibility = if (bean.isShowApplyNotice) View.VISIBLE else View.GONE
            itemView.setOnClickListener {
                onItemClickListener?.apply {
                    onItemClick(bean.type)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(type: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}