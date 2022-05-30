package com.miaomi.fenbei.voice.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.base.bean.RoleSetBean
import com.miaomi.fenbei.base.bean.UserInfo
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.chatting_item_chat_manager.view.*
import kotlinx.coroutines.Job


class ChatManagerAdapter(val chatId: String, managerList: List<UserInfo>, val refreshListen: RefreshListen) : RecyclerView.Adapter<ChatManagerAdapter.ViewContentHolder>() {


    interface RefreshListen{
        fun onRefresh()
    }

    private var mList: List<UserInfo> = managerList



//    init {
//        var index: Int = 0
//        mList.put(index, "管理员" + managerList.size + "/10")
//        index += 1
//        for (userInfo in managerList) {
//            mList.put(index, userInfo)
//            index++
//        }
//        mList.put(index, "房间用户")
//        index += 1
//        for (userInfo in userList) {
//            mList.put(index, userInfo)
//            index++
//        }
//
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewContentHolder {

        return ViewContentHolder(LayoutInflater.from(parent.context).inflate(R.layout.chatting_item_chat_manager, parent, false))
    }

    override fun onBindViewHolder(holder: ViewContentHolder, position: Int) {

        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size


    inner class ViewContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: UserInfo) {
            itemView.user_id.text = "ID:" + bean.good_number.toString()
            itemView.user_sign.text = bean.signature
            itemView.user_sex.setSeleted(bean.gender)
            ImgUtil.loadCircleImg(itemView.context, bean.face, itemView.user_icon)
            itemView.user_nick.text = bean.nickname
            itemView.iv_gongxian.setWealthLevel(bean.wealth_level.grade)
            itemView.iv_meili.setCharmLevel(bean.charm_level.grade)
            if (bean.user_role == 0) {
                itemView.set_role_btn.text = "设置"
            } else {
                itemView.set_role_btn.text = "解除"
            }


            itemView.set_role_btn.setOnClickListener {
                NetService.getInstance(it.context).setUserRole(chatId, bean.user_id.toString(), object : Callback<RoleSetBean>() {
                    override fun onSuccess(nextPage: Int, roleSetBean: RoleSetBean, code: Int) {
                        itemView.set_role_btn?.text = if (roleSetBean.user_role == 0) {
                            "设置"
                        } else {
                            "解除"
                        }
                        refreshListen.onRefresh()
                        ChatRoomManager.setUserRole(roleSetBean.user_role,bean)
                    }

                    override fun onError(msg: String, throwable: Throwable, code: Int) {
                        ToastUtil.error(it.context, msg)
                    }

                    override fun isAlive(): Boolean {
                        return true
                    }
                })
            }
        }
    }
}

