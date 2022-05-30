package com.miaomi.fenbei.room.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.ui.callback.WordClickListener
import com.miaomi.fenbei.base.bean.UserInfo
import com.miaomi.fenbei.base.util.ImgUtil
import kotlinx.android.synthetic.main.room_dialog_user_card.view.*
import kotlinx.android.synthetic.main.room_item_user_list.view.*
import kotlinx.android.synthetic.main.room_item_user_list.view.user_icon
import kotlinx.android.synthetic.main.room_item_user_list.view.user_id
import kotlinx.android.synthetic.main.room_item_user_list.view.user_nick
import kotlinx.android.synthetic.main.room_item_user_list.view.user_sex
import kotlinx.android.synthetic.main.room_item_user_list.view.wealth_level_iv


class UserListAdapter(list: ArrayList<UserInfo>, listener: WordClickListener, context: Context) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private var mList: ArrayList<UserInfo> = list
    private var mListener: WordClickListener = listener
    private var mContext: Context = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_item_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: UserInfo) {
            itemView.user_id.visibility = View.VISIBLE
            itemView.user_sex.visibility = View.VISIBLE
            itemView.wealth_level_iv.setWealthLevel(bean.wealth_level.grade)
            if (bean.good_number_state == 1){
                itemView.user_id.show(bean.good_number.toString(),true)
            }else{
                itemView.user_id.show(bean.user_id.toString(),false)
            }
//            itemView.user_id.text = "ID:" + bean.good_number.toString()
//            itemView.user_id.setCompoundDrawablesWithIntrinsicBounds(if (bean.good_number_state == 0) 0 else R.drawable.common_user_icon_liang, 0, 0 ,0)
            if (bean.auth != 0) {
                itemView.auth_tv.visibility = View.VISIBLE
                if (bean.auth == 2){
                    itemView.auth_tv.setImageResource(R.drawable.room_icon_user_flag_owner)
                }else{
                    itemView.auth_tv.setImageResource(R.drawable.room_icon_user_flag_manager)
                }
            } else {
                itemView.auth_tv.visibility = View.GONE
            }

            itemView.user_sex.setSeleted(bean.gender)
            itemView.mic_status.visibility = if (bean.status == 0) View.GONE else View.VISIBLE
            itemView.user_status.visibility = if (bean.speak == 0) View.GONE else View.VISIBLE
            itemView.mic_online.visibility = if (bean.type == 0) View.GONE else View.VISIBLE
            if (bean.mike < 9){
                itemView.mic_online.text = "${bean.mike}麦"
            }else{
                itemView.mic_online.text = "房主"
            }
            ImgUtil.loadFaceIcon(mContext, bean.face, itemView.user_icon)
            itemView.user_nick.text = bean.nickname
            itemView.setOnClickListener {
                mListener.onUserItemClick(it,bean)
            }
        }
    }
}

