package com.miaomi.fenbei.room.ui.dialog.xy

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.miaomi.fenbei.base.bean.RecordBean
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_word_item_xy_record.view.*

/**
 * Created by
 * on 2019-06-03.
 */
class WinningRecordAdapter: RecyclerView.Adapter<WinningRecordAdapter.ViewHolder>() {

    private var data: ArrayList<RecordBean> = ArrayList()

    fun setData(list: List<RecordBean>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(list: List<RecordBean>){
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = View.inflate(parent.context, R.layout.room_word_item_xy_record, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bean: RecordBean, position: Int) {
            ImgUtil.loadImg(itemView.context.applicationContext, bean.icon, itemView.pic_iv)
            itemView.name_tv.text = "${bean.name} x${bean.number}"
            itemView.price_tv.text = "${bean.price}钻石"
            itemView.time_tv.text = bean.create_time
        }
    }

}