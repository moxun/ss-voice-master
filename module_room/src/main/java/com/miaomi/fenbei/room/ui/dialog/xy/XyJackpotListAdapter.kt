package com.miaomi.fenbei.room.ui.dialog.xy

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.miaomi.fenbei.base.bean.JackpotBean
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_item_xy_jackpot.view.*

/**
 * Created by
 * on 2019-06-03.
 */
class XyJackpotListAdapter: RecyclerView.Adapter<XyJackpotListAdapter.ViewHolder>() {

    private var data: ArrayList<JackpotBean> = ArrayList()

    fun setData(list: ArrayList<JackpotBean>) {
        data = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = View.inflate(parent.context, R.layout.room_item_xy_jackpot, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bean: JackpotBean, position: Int) {
            ImgUtil.loadImg(itemView.context.applicationContext, bean.icon, itemView.pic_iv)
            itemView.name_tv.text = bean.name
            itemView.price_tv.text = "${bean.price}钻石"
        }
    }

}