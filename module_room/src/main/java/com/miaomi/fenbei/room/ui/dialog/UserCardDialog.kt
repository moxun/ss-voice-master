package com.miaomi.fenbei.room.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.*
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.miaomi.fenbei.base.core.msg.MsgManager
import com.miaomi.fenbei.base.core.msg.SendMsgListener
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.DataHelper.getUserInfo
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.util.ToastUtil.i
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.ui.adapter.UserCardGiftAdapter
import com.tencent.imsdk.TIMMessage
import kotlinx.android.synthetic.main.room_dialog_user_card.view.*
import org.greenrobot.eventbus.EventBus


class UserCardDialog(chatId: String, userInfo: UserInfo, callback: ClickListener) : BaseBottomDialog() {

    interface ClickListener {
        fun sendGift(userInfo: UserInfo)
        fun sendRedEnvelope(userInfo: UserInfo)
        fun callSomebody(userInfo: UserInfo)
        fun micCtrl4Host(userInfo: UserInfo)
        fun banMic(userInfo: UserInfo)
        fun banUser(userInfo: UserInfo, opt: Int)
        fun kickOut(userInfo: UserInfo)
        fun report(userInfo: UserInfo)
        fun joinNewRoom(roomId: Int, hostId: Int)
    }

    private lateinit var userCardGiftAdapter: UserCardGiftAdapter
    private var mChatId: String = chatId
    private var mUserInfo: UserInfo = userInfo

    private var mUserCardInfo: UserCardBean = UserCardBean()
    private var mCallback: ClickListener = callback

    //    private var mystery = 0
    private var nickname = ""
    private var headerUrl = ""
    private lateinit var rootView: View
    private var isShow = false

    override fun onResume() {
        super.onResume()
        isShow = true
    }

    override fun onPause() {
        super.onPause()
        isShow = false
    }

    //    private var nobleId = 0
    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_user_card
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mystery = mUserInfo.mystery
        nickname = mUserInfo.nickname
        headerUrl = mUserInfo.face
    }

    override fun bindView(v: View) {
        rootView = v
        loadUserData(context!!)
    }

    private fun bindData() {
        rootView.tv_des_gift_wall.text = "共收获" + mUserCardInfo.gift_cat_count + "款礼物，点亮" + mUserCardInfo.star_count + "颗星星"
        userCardGiftAdapter = UserCardGiftAdapter(mUserCardInfo.three_gift, context)
        var linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rootView.rv_gift.layoutManager = linearLayoutManager
        rootView.rv_gift.adapter = userCardGiftAdapter
        rootView.bg_content.setBackgroundResource(getContentBg(mUserCardInfo.rank_id))
        if (mUserCardInfo.user_id == DataHelper.getUID()) {
//            rootView.btn_layout.visibility = View.GONE

//            rootView.btn_layout_1.visibility = View.GONE
            rootView.btn_layout_2.visibility = View.GONE
            rootView.report_user.visibility = View.GONE
            rootView.addblack_user.visibility = View.GONE
            rootView.invite_to_mic.visibility = View.GONE
            if (mUserCardInfo.type != 0) {
                rootView.tv_down_mic.visibility = View.VISIBLE
            } else {
                rootView.tv_down_mic.visibility = View.GONE
            }
            rootView.send_msg.visibility = View.GONE
            rootView.follow_someone.visibility = View.GONE
            rootView.send_red_envelope.visibility = View.GONE
        } else {
            rootView.tv_down_mic.visibility = View.GONE
            if (mUserCardInfo.is_follow == 1) {
                rootView.follow_someone.visibility = View.GONE
            } else {
                rootView.follow_someone.visibility = View.VISIBLE
            }
            rootView.btn_layout.visibility = View.VISIBLE
            when {
                ChatRoomManager.isSuperManager() -> {
//                    rootView.btn_layout_1.visibility = View.VISIBLE
                    rootView.btn_layout_2.visibility = View.VISIBLE
                    rootView.invite_to_mic.visibility = View.VISIBLE
                    rootView.send_gift.visibility = View.VISIBLE
                }
                ChatRoomManager.isRoomHost() -> {
                    //房主操作
//                    rootView.btn_layout_1.visibility = View.VISIBLE
                    rootView.btn_layout_2.visibility = View.VISIBLE
                    rootView.invite_to_mic.visibility = View.VISIBLE
                    rootView.send_gift.visibility = View.VISIBLE
                }
                ChatRoomManager.isRoomManager() -> {//管理员操作
                    when (mUserCardInfo.user_role) {
                        2 -> {
                            //管理员点开房主
//                            rootView.btn_layout_1.visibility = View.VISIBLE
                            rootView.btn_layout_2.visibility = View.GONE
                            rootView.invite_to_mic.visibility = View.GONE
                            rootView.send_gift.visibility = View.VISIBLE
                        }
                        else -> {
//                            rootView.btn_layout_1.visibility = View.VISIBLE
                            rootView.btn_layout_2.visibility = View.VISIBLE
                            rootView.invite_to_mic.visibility = View.VISIBLE
                            rootView.send_gift.visibility = View.VISIBLE
                        }
                    }
                }
                else -> {
                    //普通用户操作
//                    rootView.btn_layout_1.visibility = View.VISIBLE
                    rootView.btn_layout_2.visibility = View.GONE
                    rootView.invite_to_mic.visibility = View.GONE
                    rootView.send_gift.visibility = View.VISIBLE
                }
            }
        }

        renderInviteBtn(ChatRoomManager.getUserRole(), mUserCardInfo.user_role, mUserCardInfo.type != 0)
        renderMicBtn(ChatRoomManager.getUserRole(), mUserCardInfo.user_role, mUserCardInfo.type != 0, mUserCardInfo.status)
        renderWordBtn(ChatRoomManager.getUserRole(), mUserCardInfo.user_role, mUserCardInfo.speak == 0)
        renderKickOutBtn(ChatRoomManager.getUserRole(), mUserCardInfo.user_role)

//        like_num_tv.text = mUserCardInfo.love.toString()
//        user_fans.text = "粉丝：${mUserCardInfo.fans_number}"
//        rootView.user_info.text = "ID:" + if (mUserCardInfo.good_number_state == 1) mUserCardInfo.good_number.toString() else mUserCardInfo.user_id.toString() + " " + mUserCardInfo.city + " " + mUserCardInfo.fans_number + "粉丝"
//        rootView.user_info.setCompoundDrawablesWithIntrinsicBounds(if (mUserCardInfo.good_number_state == 1) R.drawable.common_user_icon_liang else 0, 0, 0, 0)
//        if (mUserCardInfo.city.isEmpty()) {
//            user_local.visibility = View.GONE
//            line.visibility = View.GONE
//        } else {
//            user_local.visibility = View.VISIBLE
//            line.visibility = View.VISIBLE
//            user_local.text = mUserCardInfo.city
//        }
//        rootView.user_sign.visibility = if (TextUtils.isEmpty(mUserCardInfo.signature)) View.INVISIBLE else View.VISIBLE
//        rootView.user_sign.text = mUserCardInfo.signature
        if (mUserCardInfo.good_number_state == 1){
            rootView.user_info.show(mUserCardInfo.good_number.toString(),true)
        }else{
            rootView.user_info.show(mUserCardInfo.user_id.toString(),false)
        }
        if (mUserCardInfo.medal.isNotEmpty()) {
            rootView.medal_iv.visibility = View.VISIBLE
            ImgUtil.loadImg(context!!, mUserCardInfo.medal, rootView.medal_iv)
        } else {
            rootView.medal_iv.visibility = View.GONE
        }
        rootView.user_sex.setSeleted(mUserCardInfo.gender)
        if (mUserCardInfo.wealth_level.grade > 0) {
            rootView.cl_wealth.visibility = View.VISIBLE
            rootView.wealth_level_iv.setWealthLevel(mUserCardInfo.wealth_level.grade)
            rootView.tv_wealth_des.text = rootView.wealth_level_iv.wealthDes
        } else {
            rootView.cl_wealth.visibility = View.GONE
        }
        if (mUserCardInfo.charm_level.grade > 0) {
            rootView.cl_charm.visibility = View.VISIBLE
            rootView.charm_level_iv.setCharmLevel(mUserCardInfo.charm_level.grade)
            rootView.tv_charm_des.text = rootView.charm_level_iv.charmDes
        } else {
            rootView.cl_charm.visibility = View.GONE
        }
//            user_local_room.visibility = View.GONE
//        } else {
//            user_local_room.visibility = View.VISIBLE
//        }
//        if (mUserCardInfo.user_room_id == 0) {
//            user_room.visibility = View.GONE
//        } else {
//            user_room.visibility = View.VISIBLE
//        }
//        if (mystery == 1) {
//            rootView.report_layout.visibility = View.GONE
////            user_local_room.visibility = View.GONE
////            user_room.visibility = View.GONE
//        }
        ImgUtil.loadFaceIcon(context!!, mUserCardInfo.face, rootView.user_icon)
        ImgUtil.loadImg(context!!, mUserCardInfo.lecturer, rootView.iv_lecturer)
        rootView.iv_mic_seat.loadSeat(mUserCardInfo.seat_frame,mUserCardInfo.replaceArr)
        rootView.user_nick.text = nickname


        rootView.send_gift.setOnClickListener {
            mCallback.sendGift(mUserCardInfo)
            dismiss()
        }
        rootView.send_red_envelope.setOnClickListener {
            mCallback.sendRedEnvelope(mUserCardInfo)
            dismiss()
        }
        rootView.follow_someone.setOnClickListener {
            followAction(rootView.follow_someone, mUserCardInfo.user_id)
        }
        rootView.tv_down_mic.setOnClickListener {
            ChatRoomManager.micCtrl(context!!, 0, false)
            dismiss()
        }
        rootView.send_msg.setOnClickListener {
            if (getUserInfo()!!.wealth_level.grade == 0 && getUserInfo()!!.charm_level.grade == 0) {
                i(context!!, "财富或魅力等级大于0级才能私聊")
                return@setOnClickListener
            }
            ARouter.getInstance().build("/imkit/privatechat")
                    .withString("CHAT_ID", mUserCardInfo.user_id.toString())
                    .withString("FROM_USER_NICKNAME", mUserCardInfo.nickname)
                    .withString("FROM_USER_AVTER", mUserCardInfo.face)
                    .navigation()
            dismiss()
        }


        rootView.user_icon.setOnClickListener {
            ARouter.getInstance().build("/app/userhomepage")
                    .withString("user_id", mUserCardInfo.user_id.toString())
                    .withString("user_name", mUserCardInfo.nickname)
                    .withString("user_face", mUserCardInfo.face)
                    .navigation()
            dismiss()
        }

        rootView.tv_homepage.setOnClickListener {
            ARouter.getInstance().build("/app/userhomepage")
                    .withString("user_id", mUserCardInfo.user_id.toString())
                    .withString("user_name", mUserCardInfo.nickname)
                    .withString("user_face", mUserCardInfo.face)
                    .navigation()
            dismiss()
        }

//        showBgSvgaGiftAnim()
//        showBgSvgaGiftAnimBg()

//        user_room.setOnClickListener {
//            if (mUserCardInfo.user_room_id.toString() == mChatId) {
//                ToastUtil.suc(context, "您已在该房间")
//            } else {
//                mCallback.joinNewRoom(mUserCardInfo.user_room_id, mUserCardInfo.user_id)
//                dismiss()
//            }
//        }
//
//        user_local_room.setOnClickListener {
//            if (mUserCardInfo.user_local_room_id.toString() == mChatId) {
//                ToastUtil.suc(context, "您已在该房间")
//            } else {
//                mCallback.joinNewRoom(mUserCardInfo.user_local_room_id, mUserCardInfo.user_id)
//                dismiss()
//            }
//        }

        rootView.report_user.setOnClickListener {
            mCallback.report(mUserCardInfo)
            dismiss()
        }
        rootView.addblack_user.setOnClickListener {
            addBlack(mUserCardInfo.user_id)
        }
        rootView.invite_to_mic.setOnClickListener {
            mCallback.micCtrl4Host(mUserCardInfo)
            dismiss()
        }
        rootView.ban_user_mic.setOnClickListener {
            //0为麦操作1为言操作
//            mCallback.banUser(mUserCardInfo, 0)
            mCallback.banMic(mUserCardInfo)
            dismiss()
        }
        rootView.ban_user_word.setOnClickListener {
            //0为麦操作1为言操作
            mCallback.banUser(mUserCardInfo, 1)
            dismiss()
        }
        rootView.kick_out.setOnClickListener {
            mCallback.kickOut(mUserCardInfo)
            dismiss()
        }
    }

    private fun loadUserData(context: Context) {
        NetService.getInstance(context).getUserInfo(mChatId, mUserInfo.user_id, object : Callback<UserCardBean>() {
            override fun onSuccess(nextPage: Int, bean: UserCardBean, code: Int) {
                if (isShow) {
                    mUserCardInfo = bean
                    bindData()
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return isShow
            }
        })
    }

//    private fun bindView(context: Context) {
////        if (mUserInfo.nickname.isEmpty()) {
////            return
////        }
//    }

    private fun renderInviteBtn(fromRole: Int, toRole: Int, isOnMic: Boolean) {
        when (fromRole) {
            0 -> {
                //GONE do nothing
            }
            else -> {
                if (isOnMic) {
                    rootView.invite_to_mic.text = "抱下"
//                    var backgroundRes = DrawableCreator.Builder().setCornersRadius(DensityUtil.dp2px(context,20f).toFloat())
//                            .setGradientColor(Color.parseColor("#E865FF"), Color.parseColor("#6B00C6"))
//                            .setGradientAngle(-180).build()
//                    invite_to_mic.background = backgroundRes
                } else {
//                    if (ChatRoomManager.roomType == ChatRoomManager.ROOM_TYPE_BROADCASTING_STATION) {
//                        invite_to_mic.visibility = View.GONE
//                    }
                    rootView.invite_to_mic.text = "抱上"
//                    var backgroundRes = DrawableCreator.Builder().setCornersRadius(DensityUtil.dp2px(context,20f).toFloat())
//                            .setGradientColor(Color.parseColor("#8149FF"), Color.parseColor("#342EFF"))
//                            .setGradientAngle(-180).build()
//                    invite_to_mic.background = backgroundRes
                }
            }
        }
    }

    private fun renderMicBtn(fromRole: Int, toRole: Int, isOnMic: Boolean, status: Int) {
        when (fromRole) {
            1 -> {
                when (toRole) {
                    1 -> {    //管理员之间
//                        ban_user_mic.setTextColor(Color.parseColor("#999999"))
                        rootView.ban_user_mic.isEnabled = false
                    }
                    0 -> {
                        if (!isOnMic) {   //没在麦上
                            rootView.ban_user_mic.isEnabled = false
//                            ban_user_mic.setTextColor(Color.parseColor("#999999"))
                        } else {
                            rootView.ban_user_mic.isEnabled = true
//                            ban_user_mic.setTextColor(Color.parseColor("#222222"))
                            renderMicBtn2(status)
                        }
                    }
                }
            }
            2 -> {      //对0，1操作，2本身GONE不需处理
                if (!isOnMic) {   //没在麦上
                    rootView.ban_user_mic.isEnabled = false
//                    ban_user_mic.setTextColor(Color.parseColor("#999999"))
                } else {
                    rootView.ban_user_mic.isEnabled = true
//                    ban_user_mic.setTextColor(Color.parseColor("#222222"))
                    renderMicBtn2(status)
                }
            }
        }
    }

    private fun renderMicBtn2(status: Int) {
        when (status) {
            2 -> {
                rootView.ban_user_mic.text = "解除禁麦"
            }
            1 -> {
                rootView.ban_user_mic.text = "禁麦"
            }
            0 -> {
                rootView.ban_user_mic.text = "禁麦"
            }
        }
    }

    private fun renderWordBtn(fromRole: Int, toRole: Int, canSpeak: Boolean) {

        when (fromRole) {
            1 -> {
                when (toRole) {
                    1 -> {
//                        ban_user_word.setTextColor(Color.parseColor("#999999"))
                        rootView.ban_user_word.isEnabled = false
                    }
                }
            }
        }
        if (canSpeak) {
            rootView.ban_user_word.text = "禁言"
        } else {
            rootView.ban_user_word.text = "解除禁言"
        }
    }

    private fun renderKickOutBtn(fromRole: Int, toRole: Int) {
        when (fromRole) {
            1 -> {
                when (toRole) {
                    1 -> {
//                        kick_out.setTextColor(Color.parseColor("#999999"))
                        rootView.kick_out.isEnabled = false
                    }
                }
            }
        }
    }

    private fun followAction(follow_someone: TextView, uid: Int) {
        NetService.getInstance(context!!).followSomeBodyAction(ChatRoomManager.getRoomId(),uid, object : Callback<FollowUserBean>() {
            override fun onSuccess(nextPage: Int, bean: FollowUserBean, code: Int) {
                if (bean.is_follow == 0) {
                    rootView.follow_someone.visibility = View.VISIBLE
                    ToastUtil.suc(context!!, "取消关注")
                    EventBus.getDefault().post(FollowStatusBean(false, uid))
                } else {
                    rootView.follow_someone.visibility = View.GONE
                    ToastUtil.suc(context!!, "关注成功")
                    EventBus.getDefault().post(FollowStatusBean(true, uid))
                    MsgManager.INSTANCE.sendFollowMsg(uid.toString(), object : SendMsgListener {


                        override fun onSendSuc(timMessage: TIMMessage) {

                        }

                        override fun onSendFail(i: Int, s: String) {

                        }
                    })
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    lateinit var addBlackDialog: CommonDialog
    private fun addBlack(uid: Int) {
        addBlackDialog = CommonDialog(context!!)
                .setContent("拉黑后，你将不再收到对方信息，同时对方无法加入你的房间哦")
                .setTitle("友情提示")
                .setLeftBt("取消") { addBlackDialog.dismiss() }
                .setRightBt("确定") {
                    NetService.getInstance(context!!).addBlack(uid.toString(), mChatId, object : Callback<BaseBean>() {
                        override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                            addBlackDialog.dismiss()
                            ToastUtil.suc(context!!, "拉黑成功")
                        }

                        override fun onError(msg: String, throwable: Throwable, code: Int) {
                            addBlackDialog.dismiss()
                            ToastUtil.suc(context!!, msg)
                        }

                        override fun isAlive(): Boolean {
                            return true
                        }

                    })
                }
        addBlackDialog.show()
    }

//    private fun showBgSvgaGiftAnim() {
//        val parser = SVGAParser(context)
//        rootView.iv_svga_noble.callback = object : SVGACallback {
//            override fun onPause() {}
//            override fun onFinished() {}
//            override fun onRepeat() {}
//            override fun onStep(i: Int, v: Double) {}
//        }
//        parser.decodeFromAssets("icon_medal_dihuang.svga", object : ParseCompletion {
//            override fun onComplete(videoItem: SVGAVideoEntity) {
//                val drawable = SVGADrawable(videoItem)
//                rootView.iv_svga_noble.setImageDrawable(drawable)
//                rootView.iv_svga_noble.startAnimation()
//            }
//
//            override fun onError() {}
//        })
//    }

//    private fun showBgSvgaGiftAnimBg() {
//        val parser = SVGAParser(context)
//        rootView.iv_svga_noble_bg.callback = object : SVGACallback {
//            override fun onPause() {}
//            override fun onFinished() {}
//            override fun onRepeat() {}
//            override fun onStep(i: Int, v: Double) {}
//        }
//        parser.decodeFromAssets("bg_user_card_nobleman_huanshen.svga", object : ParseCompletion {
//            override fun onComplete(videoItem: SVGAVideoEntity) {
//                val drawable = SVGADrawable(videoItem)
//                rootView.iv_svga_noble_bg.setImageDrawable(drawable)
//                rootView.iv_svga_noble_bg.startAnimation()
//            }
//
//            override fun onError() {}
//        })
//    }

    private fun getContentBg(nobleId: Int): Int {
//        if (nobleId == BaseConfig.NOBLE_LEVEL_ZJ){
//            return R.drawable.bg_user_card_zi
//        }
//        if (nobleId == BaseConfig.NOBLE_LEVEL_BJ){
//            return R.drawable.bg_user_card_bo
//        }
//        if (nobleId == BaseConfig.NOBLE_LEVEL_HJ){
//            return R.drawable.bg_user_card_hou
//        }
//        if (nobleId == BaseConfig.NOBLE_LEVEL_GJ){
//            return R.drawable.bg_user_card_gong
//        }
//        if (nobleId == BaseConfig.NOBLE_LEVEL_GW){
//            return R.drawable.bg_user_card_wang
//        }
//        if (nobleId == BaseConfig.NOBLE_LEVEL_DH){
//            return R.drawable.bg_user_card_huang
//        }
//        if (nobleId == BaseConfig.NOBLE_LEVEL_HS){
//            return R.drawable.bg_user_card_god
//        }
        return R.drawable.bg_user_card_normal
    }
}
