//package com.miaomi.fenbei.voice.ui.square
//
//import androidx.recyclerview.widget.LinearLayoutManager
//import android.text.TextUtils
//import android.view.View
//import com.google.gson.Gson
//import com.miaomi.fenbei.voice.MsgBean
//import com.miaomi.fenbei.voice.MsgType
//import com.miaomi.fenbei.voice.BaseFragment
//import com.miaomi.fenbei.voice.MsgListener
//import com.miaomi.fenbei.base.core.msg.MsgManager
//import com.miaomi.fenbei.voice.DataHelper
//import com.miaomi.fenbei.voice.R
//import kotlinx.android.synthetic.main.chatting_fragment_gift_broadcast.*
//import java.util.*
//
//
//class GiftBroadcastFragment: BaseFragment(), MsgListener {
//
//    private var adapter: MakingFriendsWordListAdapter? = null
//
//    override fun getLayoutId(): Int {
//        return R.layout.chatting_fragment_gift_broadcast
//    }
//
//    override fun initView(view: View) {
//        initWordList()
//        MsgManager.INSTANCE.addMsgListener(this)
//    }
//
//    private fun initWordList() {
//        adapter = MakingFriendsWordListAdapter(activity!!)
//        common_msg_rl.adapter = adapter
//        common_msg_rl.layoutManager = LinearLayoutManager(context!!)
//        var data = ArrayList<MsgBean>()
////        var list = DataHelper.getBroadcastMsg()
////        list.forEach {
////            if (it.opt == MsgType.FULL_SERVICE_GIFT) data.add(it)
////        }
//        adapter!!.setData(data)
//        common_msg_rl.scrollToPosition(data.size - 1)
//    }
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
//        when (msgBean.opt) {
//            MsgType.FULL_SERVICE_GIFT -> {
//                adapter!!.addData(arrayListOf(msgBean))
//                common_msg_rl.scrollToPosition(adapter!!.itemCount - 1)
//            }
//            else -> {
//            }
//        }
//        return true
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        MsgManager.INSTANCE.removeMsgListener(this)
//    }
//}