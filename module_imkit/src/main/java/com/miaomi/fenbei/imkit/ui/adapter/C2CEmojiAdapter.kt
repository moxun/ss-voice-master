package com.miaomi.fenbei.imkit.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miaomi.fenbei.base.bean.EmojiItemBean
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.imkit.listener.OnSeletedEmojiListener
import com.miaomi.fenbei.imkit.R
import kotlinx.android.synthetic.main.chatting_item_c2c_emoji.view.*

class C2CEmojiAdapter(emojiBeanList: List<EmojiItemBean>) : RecyclerView.Adapter<C2CEmojiAdapter.ViewHolder>() {

    var mList = emojiBeanList

    private var statusListen: OnSeletedEmojiListener? = null

    public fun setOnSeletedEmoji(statusListen: OnSeletedEmojiListener) {
        this.statusListen = statusListen
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chatting_item_c2c_emoji, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: EmojiItemBean) {
            ImgUtil.loadImg(itemView.context.applicationContext,bean.emoji_icon,itemView.emoji_icon)
            itemView.emoji_text.text = bean.emoji_text

            itemView.setOnClickListener {
//                if (bean.emoji_group_id == 1 && bean.emoji_id == 1) {
//                    bean.emoji_result = Random().nextInt(6)
//                } else if (bean.emoji_group_id == 1 && bean.emoji_id == 35) {
//                    bean.emoji_result = Random().nextInt(10)
//                } else if (bean.emoji_group_id == 4 && bean.emoji_id == 75) {
//                    bean.emoji_result = Random().nextInt(2)
//                } else if (bean.emoji_group_id == 4 && bean.emoji_id == 77) {
//                    bean.emoji_result = Random().nextInt(8)
//                } else if (bean.emoji_group_id == 4 && bean.emoji_id == 89) {
//                    bean.emoji_result = StringBuilder().append(1 + Random().nextInt(6))
//                            .append(1 + Random().nextInt(6)).append(1 + Random().nextInt(6)).toString().toInt()
//                }
                if (statusListen != null){
                    statusListen!!.onSeletedEmoji(bean.emoji_gif)
                }
//                MsgManager.INSTANCE.sendEmoji("http://im-file-oss.bingteng666.com/im_test/img/gift_icon/1559551774_976.gif")
            }
        }
    }
}