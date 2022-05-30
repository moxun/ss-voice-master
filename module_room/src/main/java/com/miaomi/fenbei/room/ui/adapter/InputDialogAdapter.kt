package com.miaomi.fenbei.room.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.item_input_dialog.view.*

class InputDialogAdapter(val onItemClickListener: OnItemClickListener) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_input_dialog, ArrayList<String>()) {


    override fun convert(helper: BaseViewHolder, item: String) {
        helper.itemView.tv_word.text = item
        helper.itemView.setOnClickListener{
            onItemClickListener.onItemClick(item)
        }
    }
    public interface OnItemClickListener{
        fun onItemClick(item:String)
    }

}