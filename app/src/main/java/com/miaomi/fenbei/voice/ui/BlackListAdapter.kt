package com.miaomi.fenbei.voice.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.base.bean.BaseBean
import com.miaomi.fenbei.base.bean.UserInfo
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.chatting_item_black_list.view.*


class BlackListAdapter(list: ArrayList<UserInfo>) : RecyclerView.Adapter<BlackListAdapter.ViewHolder>() {
    private var mList: ArrayList<UserInfo> = list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chatting_item_black_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: UserInfo) {
            itemView.user_id.text = "ID:" + bean.good_number.toString()
            itemView.user_sign.text = bean.signature
            itemView.user_sex.setSeleted(bean.gender)
            ImgUtil.loadCircleImg(itemView.context, bean.face, itemView.user_icon)
            itemView.user_nick.text = bean.nickname

            itemView.delete_btn.setOnClickListener {

                NetService.getInstance(it.context).blackRemove(bean.user_id.toString(),object : Callback<BaseBean>(){
                    override fun onSuccess(nextPage: Int, result: BaseBean, code: Int) {
                        mList.remove(bean)
                        notifyDataSetChanged()
                    }

                    override fun onError(msg: String, throwable: Throwable, code: Int) {
                        ToastUtil.error(it.context,msg)
                    }

                    override fun isAlive(): Boolean {
                        return true
                    }
                })
            }
        }
    }
}

