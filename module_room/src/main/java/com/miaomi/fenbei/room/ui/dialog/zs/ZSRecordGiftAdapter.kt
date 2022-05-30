package com.miaomi.fenbei.room.ui.dialog.zs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.base.bean.ZSPrizeRecordBean
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_item_zs_record.view.*

class ZSRecordGiftAdapter: RecyclerView.Adapter<ZSRecordGiftAdapter.ViewHolder>() {

    private var data: ArrayList<ZSPrizeRecordBean> = ArrayList()

    fun setData(list: ArrayList<ZSPrizeRecordBean>) {
        data = list
        notifyDataSetChanged()
    }

    fun addData(list: ArrayList<ZSPrizeRecordBean>) {
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.room_item_zs_record, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bean: ZSPrizeRecordBean, position: Int) {
            ImgUtil.loadImg(itemView.context, bean.icon, itemView.iv_icon)
            itemView.tv_name.text = "${bean.name}x${bean.count}"
            itemView.tv_price.text = "${bean.price}钻石"
            itemView.tv_time.text = "${bean.create_time}"
        }
    }
} 