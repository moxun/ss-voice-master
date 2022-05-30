package com.miaomi.fenbei.room.ui.adapter


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.EmojiItemBean
import com.miaomi.fenbei.base.config.EmojiConfig
import com.miaomi.fenbei.base.util.ImgUtil
import kotlinx.android.synthetic.main.room_item_emoji.view.*
import java.util.*


class EmojiAdapter(emojiBeanList: List<EmojiItemBean>) : RecyclerView.Adapter<EmojiAdapter.ViewHolder>() {

    var mList = emojiBeanList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_item_emoji, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: EmojiItemBean) {
            ImgUtil.loadImg(itemView.context.applicationContext, bean.emoji_icon, itemView.emoji_icon)
            itemView.emoji_text.text = bean.emoji_text

            itemView.setOnClickListener {
                if (ChatRoomManager.isEmojiClickEnable) {
                    if (bean.emoji_group_id == 1 && bean.emoji_id == EmojiConfig.EMOJI_SZ) {
                        bean.emoji_result = Random().nextInt(6)
                    } else if (bean.emoji_group_id == 1 && bean.emoji_id == EmojiConfig.EMOJI_MXJ) {
                        bean.emoji_result = Random().nextInt(9) + 1
                    }
//                    else if (bean.emoji_group_id == 1 && bean.emoji_id == 35) {
//                        bean.emoji_result = Random().nextInt(10)
//                    } else if (bean.emoji_group_id == 4 && bean.emoji_id == 75) {
//                        bean.emoji_result = Random().nextInt(2)
//                    } else if (bean.emoji_group_id == 4 && bean.emoji_id == 77) {
//                        bean.emoji_result = Random().nextInt(8)
//                    } else if (bean.emoji_group_id == 4 && bean.emoji_id == 89) {
//                        bean.emoji_result = StringBuilder().append(1 + Random().nextInt(6))
//                                .append(1 + Random().nextInt(6)).append(1 + Random().nextInt(6)).toString().toInt()
//                    }
                    ChatRoomManager.sendEmoji(bean)
//                    ToastUtil.suc(itemView.context.applicationContext, "点太快了哦")
                }
            }
        }
    }
}

