package com.miaomi.fenbei.voice.ui.square

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.miaomi.fenbei.base.bean.*
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.miaomi.fenbei.base.core.msg.MsgListener
import com.miaomi.fenbei.base.core.msg.MsgManager
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.net.NetService.Companion.getInstance
import com.miaomi.fenbei.base.util.CKeyboardUtil
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.util.ToastUtil.error
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.ChatRoomManager.joinChat
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.voice.ui.main.adapter.HomeMsgAdapter
import kotlinx.android.synthetic.main.chatting_activity_making_friends.*


class MakingFriendsActivity: BaseActivity() , MsgListener {
    private lateinit var adapter: HomeMsgAdapter
    private var isOpenSendTopMsg = false

    companion object {
        const val TYPE_COMMON_MSG = "0"
        const val TYPE_TOP_MSG = "1"
        fun getIntent(context: Context): Intent {
            return Intent(context, MakingFriendsActivity::class.java)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.chatting_activity_making_friends
    }

    override fun initView() {
        setBaseStatusBar(useThemestatusBarColor = false, useStatusBarColor = false)
        initContent()
        adapter.onItemClickListner = object :HomeMsgAdapter.OnItemClickListner{
            override fun onItemClick(item: MakeFriendBean) {
                enterRoom(item.user_id)
            }

        }

        send_btn.setOnClickListener {
            if (isOpenSendTopMsg){
                val upDialog = CommonDialog(this)
                upDialog.setTitle("友情提示")
                upDialog.setContent("本条交友消息需花费50钻石，是否发送？")
                upDialog.setLeftBt("取消") {
                    upDialog.dismiss()
                }
                upDialog.setRightBt("确定") {
                    sendMsg(TYPE_TOP_MSG)
                    upDialog.dismiss()
                }
                upDialog.show()
            }else{
                sendMsg(TYPE_COMMON_MSG)
            }
        }
        rule_btn.setOnClickListener { ARouter.getInstance().build("/app/agreement").withInt("AGREE_TYPE", 2).navigation() }
        open_iv.setOnClickListener {
            if (isOpenSendTopMsg){
                isOpenSendTopMsg = false
                open_iv.isSelected = false
                tv_open.isSelected = false
            }else{
                isOpenSendTopMsg = true
                tv_open.isSelected = true
                open_iv.isSelected = true
            }
        }
        tv_open.setOnClickListener {
            if (isOpenSendTopMsg){
                isOpenSendTopMsg = false
                open_iv.isSelected = false
                tv_open.isSelected = false
            }else{
                isOpenSendTopMsg = true
                tv_open.isSelected = true
                open_iv.isSelected = true
            }
        }
        back_btn.setOnClickListener {
            finish()
        }
//        word_chat_btn.setOnEditorActionListener { _, actionId, _ ->
//
//            if (isOpenSendTopMsg){
//                val upDialog = CommonDialog(this)
//                upDialog.setTitle("友情提示")
//                upDialog.setContent("本条交友消息需花费100钻石，是否发送？")
//                upDialog.setLeftBt("取消") {
//                    upDialog.dismiss()
//                }
//                upDialog.setRightBt("确定") {
//                    sendMsg(TYPE_TOP_MSG)
//                    upDialog.dismiss()
//                }
//                upDialog.show()
//            }else{
//                sendMsg(TYPE_COMMON_MSG)
//            }
//            false
//        }

        getData()
    }

    fun enterRoom(userId: Int) {
        getInstance(this@MakingFriendsActivity).getCurrentRoom(userId, object : Callback<CurrentRoomBean>() {
            override fun onSuccess(nextPage: Int, bean: CurrentRoomBean, code: Int) {
                if (!TextUtils.isEmpty(bean.room_id)) {
                    joinChat(this@MakingFriendsActivity, bean.room_id, object : JoinChatCallBack {
                        override fun onSuc() {}
                        override fun onFail(msg: String) {
                            error(this@MakingFriendsActivity, msg)
                        }
                    })
                } else {
                    error(this@MakingFriendsActivity, "当前不在房间")
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                error(this@MakingFriendsActivity, msg)
            }
            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    private fun initContent() {
//        var fragments = ArrayList<Fragment>()
//        fragments.add(MakingFriendsBroadcastFragment())
//        fragments.add(MakingFriendsBroadcastFragment())
//        content_vp.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
//            override fun getItem(position: Int): Fragment {
//                return fragments[position]
//            }
//
//            override fun getCount(): Int {
//                return fragments.size
//            }
//        }
////        tab_layout.bindViewPager(listOf("交友广播", "礼物广播"), content_vp)
//        tab_layout.setViewPager(content_vp)
//        tab_layout.setTitles("交友广播")
//        tab_layout.setStripColor(Color.WHITE)
//        tab_layout.setActiveColor(Color.WHITE)
//        tab_layout.setInactiveColor(Color.WHITE)
//        tab_layout.setStripType(TabLayout.StripType.POINT)
//        tab_layout.setStripGravity(TabLayout.StripGravity.BOTTOM)
//        tab_layout.setAnimationDuration(300)

        MsgManager.INSTANCE.addMsgListener(this)
        adapter = HomeMsgAdapter(this@MakingFriendsActivity)
        common_msg_rl.layoutManager = LinearLayoutManager(this@MakingFriendsActivity)
        common_msg_rl.adapter = adapter
    }


    private fun sendMsg(type: String) {

        if (word_chat_btn.text!!.isEmpty()) {
            ToastUtil.i(this, "交友内容不能为空哦~")
            return
        }
        if (type != TYPE_TOP_MSG){
            var currentTime = System.currentTimeMillis()
            if (currentTime - DataHelper.getSendSquaeTime() < 5000){
                ToastUtil.i(this, "连续发送时间不能超过5S")
                return
            }
            DataHelper.saveSendSquaeTime(currentTime)
        }
        NetService.getInstance(this).chatSquare(type, word_chat_btn.text.toString(), object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                word_chat_btn.setText("")
                word_chat_btn.clearFocus()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.i(this@MakingFriendsActivity, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
        CKeyboardUtil.hideKeyboard(word_chat_btn)
    }

    private fun getData(){
        NetService.getInstance(this@MakingFriendsActivity).getMakeFriengMsgList(object : Callback<List<MakeFriendBean>>() {
            override fun onSuccess(nextPage: Int, bean: List<MakeFriendBean>, code: Int) {
                adapter.setRealData(bean)
                common_msg_rl.scrollToPosition(adapter.itemCount - 1)

            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
        NetService.getInstance(this@MakingFriendsActivity).getSquareHeadInfo(object : Callback<MakeFriendBean>() {
            override fun onSuccess(nextPage: Int, bean: MakeFriendBean, code: Int) {
                if (!TextUtils.isEmpty(bean.face)) {
                    updateTopMsg (bean)
                }

            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }


//    private fun initWordList() {
//        adapter = MakingFriendsAdapter(activity!!)
//        common_msg_rl.adapter = adapter
//        common_msg_rl.layoutManager = LinearLayoutManager(context!!)
//        var data = ArrayList<MsgBean>()
////        var list = DataHelper.getBroadcastMsg()
////        list.forEach {
////            if (it.opt == MsgType.FULL_SERVICE_MSG) data.add(it)
////        }
//        adapter.setData(data)
//        common_msg_rl.scrollToPosition(data.size - 1)
//    }

    override fun onNewMsg(text: String): Boolean {
        if (TextUtils.isEmpty(text)) {
            return false
        }
        var msgBean: MsgBean? = null
        try {
            msgBean = Gson().fromJson(text, MsgBean::class.java) ?: return false
        } catch (e: Exception) {
            return false
        }

        if (msgBean.chatId != DataHelper.getIMDevelop().imBigGroupID) {
            return false
        }
        if (msgBean.opt == null) {
            return false
        }
        when (msgBean.opt) {
            MsgType.FULL_SERVICE_MSG -> {
                if (msgBean.type.toString() == TYPE_TOP_MSG) {
                    adapter.addData(arrayListOf(msgBean))
                    common_msg_rl.scrollToPosition(adapter.itemCount - 1)
                    updateTopMsg(msgBean)
                } else {
                    adapter.addData(arrayListOf(msgBean))
                    common_msg_rl.scrollToPosition(adapter.itemCount - 1)
                }
            }
            else -> {
            }
        }
        return true
    }


//    private fun initTopMsg() {
//        if (!TextUtils.isEmpty(SPUtil.getString(SPUtil.CONFIG_TOP_MSG,""))) {
//            var msgBean = Gson().fromJson(SPUtil.getString(SPUtil.CONFIG_TOP_MSG,""), MsgBean::class.java)
//            if (System.currentTimeMillis() / 1000 - msgBean.time!! < msgBean.intervalTime) {
//                updateTopMsg(msgBean)
//            }
//        }

    private fun updateTopMsg(msgBean: MsgBean) {
        top_msg_rl.visibility = View.VISIBLE
        top_msg_rl.setOnClickListener {
            if(!TextUtils.isEmpty(msgBean.roomId)){
                if (msgBean.roomId == ChatRoomManager.getRoomId()) {
                    ToastUtil.i(this@MakingFriendsActivity, "您已在该房间")
                } else {
                    ChatRoomManager.joinChat(this@MakingFriendsActivity, msgBean.roomId, object : JoinChatCallBack {
                        override fun onSuc() {

                        }

                        override fun onFail(msg: String) {
                            ToastUtil.error(this@MakingFriendsActivity, msg)
                        }

                    })
                }
            }else{
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id", msgBean.fromUserInfo.user_id.toString())
                        .withString("user_name", msgBean.fromUserInfo.nickname)
                        .withString("user_face", msgBean.fromUserInfo.face)
                        .navigation()
            }
        }
        ImgUtil.loadCircleImg(this@MakingFriendsActivity, msgBean.fromUserInfo.face, iv_top_face)
        tv_top_name.text = msgBean.fromUserInfo.nickname
        tv_top_content.text = msgBean.content
//        ImgUtil.loadFaceIcon(this@MakingFriendsActivity, msgBean.fromUserInfo.face, header_iv)
//        header_iv_seat.isSelected = true
//        user_nick_tv.text = msgBean.fromUserInfo.nickname
//        msg_tv.text = msgBean.content
//        wealth_level_iv.setWealthLevel(msgBean.fromUserInfo.wealth_level.grade)
    }


    private fun updateTopMsg(msgBean: MakeFriendBean) {
        top_msg_rl.visibility = View.VISIBLE
        top_msg_rl.setOnClickListener {
            if (!TextUtils.isEmpty(msgBean.room_id.toString())) {
                if (msgBean.room_id == 0){
                    ARouter.getInstance().build("/app/userhomepage")
                            .withString("user_id", msgBean.user_id.toString())
                            .withString("user_name", msgBean.nickname)
                            .withString("user_face", msgBean.face)
                            .navigation()
                }else{
                    if (msgBean.room_id.toString() == ChatRoomManager.getRoomId()) {
                        ToastUtil.i(this@MakingFriendsActivity, "您已在该房间")
                    } else {
                        ChatRoomManager.joinChat(this@MakingFriendsActivity, msgBean.room_id.toString(), object : JoinChatCallBack {
                            override fun onSuc() {

                            }

                            override fun onFail(msg: String) {
                                ToastUtil.error(this@MakingFriendsActivity, msg)
                            }

                        })
                    }
                }
            }
        }


        ImgUtil.loadCircleImg(this@MakingFriendsActivity, msgBean.face, iv_top_face)
        tv_top_name.text = msgBean.nickname
        tv_top_content.text = msgBean.content
    }



}