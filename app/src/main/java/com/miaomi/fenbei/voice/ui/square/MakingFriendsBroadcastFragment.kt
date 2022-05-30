//package com.miaomi.fenbei.voice.ui.square
//
//import android.text.TextUtils
//import android.util.Log
//import android.view.View
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.gson.Gson
//import com.miaomi.fenbei.base.bean.MakeFriendBean
//import com.miaomi.fenbei.voice.MsgBean
//import com.miaomi.fenbei.voice.MsgType
//import com.miaomi.fenbei.voice.BaseFragment
//import com.miaomi.fenbei.voice.JoinChatCallBack
//import com.miaomi.fenbei.voice.MsgListener
//import com.miaomi.fenbei.base.core.msg.MsgManager
//import com.miaomi.fenbei.voice.Callback
//import com.miaomi.fenbei.voice.NetService
//import com.miaomi.fenbei.voice.DataHelper
//import com.miaomi.fenbei.voice.ImgUtil
//import com.miaomi.fenbei.voice.ToastUtil
//import com.miaomi.fenbei.voice.ChatRoomManager
//import com.miaomi.fenbei.voice.R
//import kotlinx.android.synthetic.main.chatting_fragment_making_friends_broadcast.*
//
//
//class MakingFriendsBroadcastFragment: BaseFragment(), MsgListener {
//
//    private lateinit var adapter: MakingFriendsAdapter
//
//    override fun getLayoutId(): Int {
//        return R.layout.chatting_fragment_making_friends_broadcast
//    }
//
//    override fun initView(view: View) {
//        MsgManager.INSTANCE.addMsgListener(this)
//        adapter = MakingFriendsAdapter(activity!!)
//        common_msg_rl.layoutManager = LinearLayoutManager(context)
//        common_msg_rl.adapter = adapter
//        getData()
//    }
//
//    private fun getData(){
//        NetService.getInstance(context!!).getMakeFriengMsgList(object : Callback<List<MakeFriendBean>>(){
//            override fun onSuccess(nextPage: Int, bean: List<MakeFriendBean>, code: Int) {
//                adapter.setRealData(bean)
//                common_msg_rl.scrollToPosition(adapter.itemCount - 1)
//
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//
//        })
//    }
//
//
////    private fun initWordList() {
////        adapter = MakingFriendsAdapter(activity!!)
////        common_msg_rl.adapter = adapter
////        common_msg_rl.layoutManager = LinearLayoutManager(context!!)
////        var data = ArrayList<MsgBean>()
//////        var list = DataHelper.getBroadcastMsg()
//////        list.forEach {
//////            if (it.opt == MsgType.FULL_SERVICE_MSG) data.add(it)
//////        }
////        adapter.setData(data)
////        common_msg_rl.scrollToPosition(data.size - 1)
////    }
//
//    override fun onNewMsg(text: String): Boolean {
//        if (TextUtils.isEmpty(text)) {
//            return false
//        }
//        var msgBean: MsgBean? = null
//        try {
//            msgBean = Gson().fromJson(text, MsgBean::class.java) ?: return false
//        } catch (e: Exception) {
//            return false
//        }
//
//        if (msgBean.chatId != DataHelper.getIMDevelop().imBigGroupID) {
//            return false
//        }
//        if (msgBean.opt == null) {
//            return false
//        }
//        Log.i("lzq","收到消息"+msgBean.opt)
//        when (msgBean.opt) {
//            MsgType.FULL_SERVICE_MSG -> {
//                if (msgBean.type.toString() == MakingFriendsActivity.TYPE_TOP_MSG) {
//                    adapter.addData(arrayListOf(msgBean))
//                    common_msg_rl.scrollToPosition(adapter.itemCount - 1)
//                    updateTopMsg(msgBean)
//                } else {
//                    adapter.addData(arrayListOf(msgBean))
//                    common_msg_rl.scrollToPosition(adapter.itemCount - 1)
//                }
//            }
//            else -> {
//            }
//        }
//        return true
//    }
//
//
////    private fun initTopMsg() {
////        if (!TextUtils.isEmpty(SPUtil.getString(SPUtil.CONFIG_TOP_MSG,""))) {
////            var msgBean = Gson().fromJson(SPUtil.getString(SPUtil.CONFIG_TOP_MSG,""), MsgBean::class.java)
////            if (System.currentTimeMillis() / 1000 - msgBean.time!! < msgBean.intervalTime) {
////                updateTopMsg(msgBean)
////            }
////        }
//
//    private fun updateTopMsg(msgBean: MsgBean) {
//        top_msg_rl.visibility = View.VISIBLE
//        top_msg_rl.setOnClickListener {
//            if (msgBean.roomId == ChatRoomManager.getRoomId()) {
//                ToastUtil.i(context!!, "您已在该房间")
//            } else {
//                ChatRoomManager.joinChat(context!!, msgBean.roomId, object : JoinChatCallBack {
//                    override fun onSuc() {
//
//                    }
//
//                    override fun onFail(msg: String) {
//                        ToastUtil.error(context!!, msg)
//                    }
//
//                })
//            }
//        }
//        ImgUtil.loadCircleImg(context!!, msgBean.fromUserInfo.face, header_iv)
//        user_nick_tv.text = msgBean.fromUserInfo.nickname
//        msg_tv.text = msgBean.content
//        wealth_level_iv.setWealthLevel(msgBean.fromUserInfo.wealth_level.grade)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        MsgManager.INSTANCE.removeMsgListener(this)
//
//    }
//
//}