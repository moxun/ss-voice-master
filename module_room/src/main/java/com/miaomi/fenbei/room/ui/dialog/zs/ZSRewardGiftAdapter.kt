package com.miaomi.fenbei.room.ui.dialog.zs

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.ZSRewardBean
import com.miaomi.fenbei.base.util.ImgUtil
import kotlinx.android.synthetic.main.room_item_zs_gift_reward.view.*


class ZSRewardGiftAdapter: RecyclerView.Adapter<ZSRewardGiftAdapter.ViewHolder>() {

    private var data: List<ZSRewardBean> = ArrayList()
    private var watertype:Int = 1
    fun setData(list: List<ZSRewardBean>,waterType:Int) {
        data = list
        watertype=waterType
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = View.inflate(parent.context, R.layout.room_item_zs_gift_reward, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bean: ZSRewardBean, position: Int) {
            ImgUtil.loadImg(itemView.context, bean.icon, itemView.iv_gift)
            itemView.tv_name.text = "${bean.name}"
            itemView.tv_price.text = "${bean.amount}钻石"
            itemView.ll_gift_number.addNumber(bean.stock)
            if(watertype==2){
                itemView.fl_reward_gift.setBackgroundResource(R.drawable.room_bg_item_xy_reward_gift)
            }else{
                itemView.fl_reward_gift.setBackgroundResource(R.drawable.room_bg_item_reward_gift)
            }

        }
    }
}