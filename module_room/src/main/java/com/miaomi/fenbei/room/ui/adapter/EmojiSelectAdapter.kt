package com.miaomi.fenbei.room.ui.adapter


import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.EmojiGroupBean
import com.miaomi.fenbei.room.ui.dialog.EmojiDialog
import com.miaomi.fenbei.base.util.ImgUtil
import kotlinx.android.synthetic.main.room_item_select_emoji.view.*


class EmojiSelectAdapter(emojiGroup: List<EmojiGroupBean>, callback: EmojiDialog.EmojiCallback) : RecyclerView.Adapter<EmojiSelectAdapter.ViewHolder>() {

    val mList = emojiGroup
    val mCallback = callback

    fun update(position: Int) {
        mList.forEachIndexed { index, emojiGroupBean ->
            emojiGroupBean.isSelect = index == position
        }
        notifyDataSetChanged()
    }

    init {
        if (!mList.isEmpty()){
            mList[0].isSelect = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_item_select_emoji, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position],position)
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: EmojiGroupBean, position:Int) {
            ImgUtil.loadImg(itemView.context.applicationContext,bean.emoji_group_icon,itemView.emoji_mini_icon)
            if (bean.isSelect){
                itemView.item_content.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }else{
                itemView.item_content.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            itemView.setOnClickListener {
                mCallback.onGroupClick(bean,position)
            }
        }
    }
}

