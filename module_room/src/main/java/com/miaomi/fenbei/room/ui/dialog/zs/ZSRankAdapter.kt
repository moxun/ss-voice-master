package com.miaomi.fenbei.room.ui.dialog.zs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.base.bean.ZSRankItemBean
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_item_zs_rank.view.*

class ZSRankAdapter : RecyclerView.Adapter<ZSRankAdapter.ViewHolder>() {

    private var data: List<ZSRankItemBean> = ArrayList()

    fun setData(list: List<ZSRankItemBean>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate( R.layout.room_item_zs_rank, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bean: ZSRankItemBean, position: Int) {
            if (bean.face!=null){
                ImgUtil.loadFaceIcon(itemView.context, bean.face, itemView.iv_face)

            }
            itemView.tv_position.text = ""+(position+1)
            if (position > 2){
                itemView.iv_position.visibility = View.INVISIBLE
            }else{
                if (position == 0){
                    itemView.iv_position.setImageResource(R.drawable.room_zs_rank_position_1)
                }
                if (position == 1){
                    itemView.iv_position.setImageResource(R.drawable.room_zs_rank_position_2)
                }
                if (position == 2){
                    itemView.iv_position.setImageResource(R.drawable.room_zs_rank_position_3)
                }
                itemView.iv_position.visibility = View.VISIBLE
            }
            itemView.tv_name.text = "${bean.nickname}"
            itemView.tv_acount.text = "${bean.amount}钻石"
            itemView.tv_id.text = "ID:${bean.user_id}"
        }
    }
} 