package com.miaomi.fenbei.room.ui.dialog.zs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.base.bean.ZSGiftPrizePoolBean
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_item_zs_gift.view.*

class ZSGiftAdapter(val type: Int): RecyclerView.Adapter<ZSGiftAdapter.ViewHolder>() {

    private var data: List<ZSGiftPrizePoolBean> = ArrayList()

    fun setData(list: List<ZSGiftPrizePoolBean>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate( R.layout.room_item_zs_gift, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bean: ZSGiftPrizePoolBean, position: Int) {
            if (type == 0){
                itemView.iv_luck.visibility = View.INVISIBLE
            }else{
                itemView.iv_luck.visibility = View.VISIBLE
            }
            ImgUtil.loadImg(itemView.context, bean.icon, itemView.iv_icon)
            itemView.tv_name.text = "${bean.name}"
            itemView.tv_price.text = "${bean.price}钻石"
        }
    }
} 