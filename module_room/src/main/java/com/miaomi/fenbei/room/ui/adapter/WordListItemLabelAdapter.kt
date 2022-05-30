package com.miaomi.fenbei.room.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.util.ImgUtil
import kotlinx.android.synthetic.main.room_item_word_label.view.*

/**
 * Created by 
 * on 2019-12-21.
 */
class WordListItemLabelAdapter(data: List<String>): BaseQuickAdapter<String, BaseViewHolder>(R.layout.room_item_word_label, data) {

    override fun convert(helper: BaseViewHolder, item: String) {
        ImgUtil.loadImg(helper.itemView.context, item, helper.itemView.label_iv)
    }

}