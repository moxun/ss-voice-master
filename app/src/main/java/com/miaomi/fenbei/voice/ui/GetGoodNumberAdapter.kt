package com.miaomi.fenbei.voice.ui

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.miaomi.fenbei.base.bean.LeopardGoodNumberBean
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.item_get_good_number.view.*

class GetGoodNumberAdapter (val onItemClickLiseter:OnItemClickLiseter): BaseQuickAdapter<LeopardGoodNumberBean, BaseViewHolder>(R.layout.item_get_good_number,ArrayList<LeopardGoodNumberBean>()) {

    override fun convert(helper: BaseViewHolder, item: LeopardGoodNumberBean) {
        helper.itemView.tv_number.isSelected = true
        helper.itemView.tv_number.text =item.good_number
        helper.itemView.tv_price.text = item.price+"钻石"
        helper.itemView.setOnClickListener{
            onItemClickLiseter.onItemCick(item)
        }
    }

    public interface OnItemClickLiseter{
        fun onItemCick(bean: LeopardGoodNumberBean)
    }

}