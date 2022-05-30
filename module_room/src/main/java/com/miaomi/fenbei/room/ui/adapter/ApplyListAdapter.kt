package com.miaomi.fenbei.room.ui.adapter


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.callback.ChatRoomBaseCallBack
import com.miaomi.fenbei.base.bean.UserInfo
import com.miaomi.fenbei.base.util.ImgUtil
import kotlinx.android.synthetic.main.room_item_apply.view.*


class ApplyListAdapter : RecyclerView.Adapter<ApplyListAdapter.ViewHolder>() {

    private var mList :ArrayList<UserInfo> = ArrayList()

    fun setData(list: ArrayList<UserInfo>) {
        this.mList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_item_apply, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position],position)
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: UserInfo, position:Int) {
            itemView.wealth_level_iv.setWealthLevel(bean.wealth_level.grade)
            itemView.user_coin.text = "账户余额：" + bean.recharge_residue.toString() + "钻石"
            ImgUtil.loadCircleImg(itemView.context.applicationContext, bean.face, itemView.user_icon)
            itemView.user_sex.setSeleted(bean.gender)
            itemView.user_nick.text = bean.nickname
//            when (bean.type) {
//                1 -> {
//                    itemView.flag_iv.visibility = View.VISIBLE
//                    itemView.flag_iv.setImageResource(R.drawable.chatting_apply_vip_icon)
//                }
//                2 -> {
//                    itemView.flag_iv.visibility = View.VISIBLE
//                    itemView.flag_iv.setImageResource(R.drawable.chatting_apply_guard_icon)
//                }
//                else -> {
//                    itemView.flag_iv.visibility = View.GONE
//                }
//            }
            itemView.agree_btn.setOnClickListener {
                ChatRoomManager.applyMicOpt(it.context,0,bean,object : ChatRoomBaseCallBack {
                    override fun onSuc() {
                        mList.removeAt(position)
                        notifyDataSetChanged()
                    }
                    override fun onFail(msg: String) {}
                })

            }

            itemView.deny_btn.setOnClickListener {
                ChatRoomManager.applyMicOpt(it.context,1,bean,object : ChatRoomBaseCallBack {
                    override fun onSuc() {
                        mList.removeAt(position)
                        notifyDataSetChanged()
                    }
                    override fun onFail(msg: String) {}
                })
            }
        }
    }
}

