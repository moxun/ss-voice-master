package com.miaomi.fenbei.room.ui.adapter

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.VoiceTypeBean
import com.miaomi.fenbei.base.util.DensityUtil
import com.noober.background.drawable.DrawableCreator
import kotlinx.android.synthetic.main.room_item_voice_type.view.*
import java.util.*

/**
 * Created by 
 * on 2019-07-16.
 */
class VoiceTypeAdapter: RecyclerView.Adapter<VoiceTypeAdapter.ViewHolder>() {

    private var list: ArrayList<VoiceTypeBean> = ArrayList()

    fun setData(data: ArrayList<VoiceTypeBean>) {
        this.list = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(parent.context, R.layout.room_item_voice_type, null))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bean: VoiceTypeBean) {
            itemView.type_tv.text = bean.type
            itemView.type_tv.background = if (bean.isSelect) DrawableCreator.Builder().setCornersRadius(5f)
                    .setSolidColor(Color.parseColor("#FD7F8F")).build() else DrawableCreator.Builder()
                    .setSolidColor(Color.parseColor("#ffffff")).setStrokeColor(Color.parseColor("#CCCCCC"))
                    .setStrokeWidth(DensityUtil.dp2px(itemView.context, 1f).toFloat()).setCornersRadius(5f).build()
            itemView.type_tv.setTextColor(if (bean.isSelect) Color.parseColor("#ffffff") else Color.parseColor("#666666"))
            itemView.setOnClickListener {
                list.forEach { it.isSelect = false }
                list[adapterPosition].isSelect = true
                onItemClickListener?.apply {
                    onItemClick(adapterPosition)
                }
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

}