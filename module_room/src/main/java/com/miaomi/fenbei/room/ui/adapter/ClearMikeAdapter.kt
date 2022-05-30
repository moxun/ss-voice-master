package com.miaomi.fenbei.room.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_item_clear_mike.view.*
import java.util.*

/**
 * Created by 
 * on 2019-08-28.
 */
class ClearMikeAdapter: RecyclerView.Adapter<ClearMikeAdapter.ViewHolder>() {

    private var mList : ArrayList<String> = ArrayList()

    fun setData(list: ArrayList<String>) {
        this.mList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.room_item_clear_mike, parent,false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bean: String) {
            itemView.mic_tv.text = bean
            itemView.mic_tv.setOnClickListener {
                onItemClickListener?.apply { onItemClick(adapterPosition) }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position : Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}