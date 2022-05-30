//package com.miaomi.fenbei.voice.ui.main.adapter
//
//import android.app.Activity
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.miaomi.fenbei.base.bean.ChatListBean
//import com.miaomi.fenbei.voice.JoinChatCallBack
//import com.miaomi.fenbei.voice.ImgUtil
//import com.miaomi.fenbei.voice.ToastUtil
//import com.miaomi.fenbei.voice.ChatRoomManager
//import com.miaomi.fenbei.voice.R
//import kotlinx.android.synthetic.main.item_home_other_room.view.*
//
//class HomeRoomAdapter(private val context: Activity) : RecyclerView.Adapter<HomeRoomAdapter.ViewHolder>() {
//    private var mList: MutableList<ChatListBean> = ArrayList()
//
//
//
//    fun setData(list: MutableList<ChatListBean>){
//        mList = list
//        notifyDataSetChanged()
//    }
//
//    fun addData(list: MutableList<ChatListBean>){
//        mList.addAll(list)
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_other_room, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(mList[position])
//    }
//
//    override fun getItemCount(): Int = mList.size
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(bean: ChatListBean){
//            itemView.tv_name.text = bean.chat_room_name
////            if(itemView.chat_name.text.length >= 8){
////                itemView.chat_name.startScroll()
////            }
////            if(!TextUtils.isEmpty(bean.contribute)){
////                itemView.ll_hot.visibility = View.VISIBLE
////                itemView.online_num.text = bean.contribute
////                ImgUtil.loadGif(itemView.context, R.drawable.gif_icon_room_hot,itemView.iv_hot_lable)
////            }else{
////                itemView.ll_hot.visibility = View.GONE
////            }
//            itemView.tv_topic.text = bean.room_topic
//            itemView.tv_contribute.setContribute(bean.label,bean.contribute)
//            ImgUtil.loadLeftRoundImg(itemView.context,bean.chat_room_icon,itemView.iv_avter)
//            itemView.tv_label.setLevel(bean.label)
//            itemView.setOnClickListener {
//                ChatRoomManager.joinChat(context,bean.chat_room_id,object : JoinChatCallBack {
//                    override fun onSuc() {
//                    }
//                    override fun onFail(msg: String) {
//                        ToastUtil.error(itemView.context,msg)
//                    }
//                })
//            }
//        }
//    }
//
//}
