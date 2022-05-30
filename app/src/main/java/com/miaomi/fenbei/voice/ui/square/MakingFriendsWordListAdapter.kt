//package com.miaomi.fenbei.voice.ui.square
//
//import android.app.Activity
//import androidx.recyclerview.widget.RecyclerView
//import android.text.TextUtils
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.miaomi.fenbei.voice.MsgBean
//import com.miaomi.fenbei.voice.MsgType
//import com.miaomi.fenbei.voice.JoinChatCallBack
//import com.miaomi.fenbei.voice.ImgUtil
//import com.miaomi.fenbei.voice.ToastUtil
//import com.miaomi.fenbei.voice.ChatRoomManager
//import com.miaomi.fenbei.voice.R
//import kotlinx.android.synthetic.main.chatting_item_full_service_gift.view.*
//import kotlinx.android.synthetic.main.chatting_item_full_service_msg.view.*
//import java.util.*
//
//
//class MakingFriendsWordListAdapter(private var context: Activity): RecyclerView.Adapter<MakingFriendsWordListAdapter.ViewHolder>() {
//
//    var list: ArrayList<MsgBean> = ArrayList()
//
//    private var FULL_SERVICE_MSG: Int = 0
//    private var FULL_SERVICE_GIFT: Int = 1
//
//    fun setData(msgBean: ArrayList<MsgBean>) {
//        list = msgBean
//        this.notifyDataSetChanged()
//    }
//
//    fun addData(msgBean: ArrayList<MsgBean>) {
//        list.addAll(msgBean)
//        this.notifyDataSetChanged()
//    }
//
//    fun getData(): ArrayList<MsgBean> {
//        return list
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when (getData()[position].opt) {
//            MsgType.FULL_SERVICE_GIFT -> {
//                FULL_SERVICE_GIFT
//            }
//            else -> {
//                FULL_SERVICE_MSG
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        var view: View
//        when (viewType) {
//            FULL_SERVICE_GIFT -> {
//                view = LayoutInflater.from(context).inflate(R.layout.chatting_item_full_service_gift, parent, false)
//            }
//            else -> {
//                view = LayoutInflater.from(context).inflate(R.layout.chatting_item_full_service_msg, parent, false)
//            }
//        }
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(position)
//    }
//
//    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        fun bind(position: Int) {
//            when (itemViewType) {
//                FULL_SERVICE_GIFT -> {
//                    ImgUtil.loadImg(context!!, list[position].giftBean!!.giftIcon, itemView.gift_iv)
//                    ImgUtil.loadCircleImg(context!!, list[position].fromUserInfo.face, itemView.from_user_header_iv)
//                    itemView.from_user_name_tv.text = list[position].fromUserInfo.nickname
//                    ImgUtil.loadCircleImg(context!!, list[position].toUserInfo.face, itemView.to_user_header_iv)
//                    itemView.to_user_name_tv.text = list[position].toUserInfo.nickname
//                    itemView.gift_num_tv.text = "x${list[position].giftBean!!.giftNum}"
//                    itemView.room_id_tv.text = "ID:${list[position].roomId}"
//                    itemView.setOnClickListener {
//                        if (!TextUtils.isEmpty(list[position].roomId)) {
//                            if (list[position].roomId == ChatRoomManager.getRoomId()) {
//                                ToastUtil.i(context, "您已在该房间")
//                            } else {
//                                ChatRoomManager.joinChat(context, list[position].roomId ,object : JoinChatCallBack{
//                                    override fun onSuc() {
//                                    }
//                                    override fun onFail(msg: String) {
//                                        ToastUtil.error(itemView.context,msg)
//                                    }
//                                })
//                            }
//                        }
//                    }
//                }
//                else -> {
//                    ImgUtil.loadFaceIcon(context!!, list[position].fromUserInfo.face, itemView.user_header_iv)
//                    ImgUtil.loadImg(context!!,list[position].fromUserInfo.medal,itemView.iv_medal)
//                    itemView.user_nick_tv.text = list[position].fromUserInfo.nickname
//                    itemView.msg_content_tv.text = list[position].content
//                    itemView.wealth_level_iv.setWealthLevel(list[position].fromUserInfo.wealth_level.grade)
//                    itemView.setOnClickListener {
//                        if (TextUtils.isEmpty(list[position].roomId)) {
//                            ChatRoomManager.joinChat(context ,list[position].roomId, object : JoinChatCallBack {
//                                override fun onSuc() {
//
//                                }
//                                override fun onFail(msg: String) {
//                                    ToastUtil.error(itemView.context, msg)
//                                }
//
//                            })
//                        } else {
//                            if (list[position].roomId == ChatRoomManager.getRoomId()) {
//                                ToastUtil.i(context, "您已在该房间")
//                            } else {
//                                ChatRoomManager.joinChat(context, list[position].roomId ,object : JoinChatCallBack{
//                                    override fun onSuc() {
//                                    }
//                                    override fun onFail(msg: String) {
//                                        ToastUtil.error(itemView.context,msg)
//                                    }
//                                })
//
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}