package com.miaomi.fenbei.room

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.view.SimpleDraweeView
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.miaomi.fenbei.base.RoomSetPwdDialog
import com.miaomi.fenbei.base.bean.*
import com.miaomi.fenbei.base.bean.event.NetWorkBean
import com.miaomi.fenbei.base.bean.event.UnReadNumBean
import com.miaomi.fenbei.base.bean.event.UserCardEvent
import com.miaomi.fenbei.base.config.BaseConfig
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.miaomi.fenbei.base.dialog.FirstChargeDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.Data
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.*
import com.miaomi.fenbei.base.util.DataHelper.getUID
import com.miaomi.fenbei.base.web.WebActivity
import com.miaomi.fenbei.base.widget.ChatLineRecyclerView
import com.miaomi.fenbei.base.widget.WordListSelectedView
import com.miaomi.fenbei.gift.GiftManager

import com.miaomi.fenbei.room.callback.*
import com.miaomi.fenbei.room.ui.adapter.WordListAdapter
import com.miaomi.fenbei.room.ui.callback.BlindClickListener
import com.miaomi.fenbei.room.ui.callback.RecordVolumeObservable
import com.miaomi.fenbei.room.ui.callback.WordClickListener
import com.miaomi.fenbei.room.ui.dialog.*
import com.miaomi.fenbei.room.ui.dialog.xb.GameXBDialog
import com.miaomi.fenbei.room.ui.dialog.xy.XYDialog
import com.miaomi.fenbei.room.ui.dialog.zs.ZSHomeDialog
import com.miaomi.fenbei.room.ui.fragment.ChatMode
import kotlinx.android.synthetic.main.activity_room_bottom_layout.*
import kotlinx.android.synthetic.main.activity_room_content.*
import kotlinx.android.synthetic.main.fragment_room_blind.*
import kotlinx.android.synthetic.main.fragment_room_blind_content.*
import kotlinx.android.synthetic.main.fragment_room_blind_content.banner
import kotlinx.android.synthetic.main.fragment_room_blind_content.mic_rv
import kotlinx.android.synthetic.main.fragment_room_blind_content.music_iv
import kotlinx.android.synthetic.main.fragment_room_blind_content.new_msg_tv
import kotlinx.android.synthetic.main.fragment_room_blind_content.tmv_top_msg
import kotlinx.android.synthetic.main.fragment_room_blind_content.word_list
import kotlinx.android.synthetic.main.fragment_room_blind_content.word_selet_view
import kotlinx.android.synthetic.main.room_fragment_blind_room_toolbar.*
import kotlinx.android.synthetic.main.room_fragment_blind_room_toolbar.host_icon_iv
import kotlinx.android.synthetic.main.room_fragment_blind_room_toolbar.host_seat_iv
import kotlinx.android.synthetic.main.room_fragment_blind_room_toolbar.mic_water
import kotlinx.android.synthetic.main.room_layout_blind_mic_view.*
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.collections.ArrayList

class BlindRoomFragment : BaseRoomFragment(), BlindClickListener, WordClickListener, ChatRoomUpdateViewCallBack, RecordVolumeObservable.RecordVolumeObserver, ChatRoomLightCallBack, ChatRoomOnNetDelayCallBack, ChatRoomOnLiveFinishCallBack {

    private var isClearChatHistory = true
    private var chatMode: Int = 0
    private var unreadMsgPos: Int = 0
    private var unreadMsgCount: Int = 0
    //    private var redPacketCountDownTimer: CountDownTimer? = null
    private var chatHistoryJob: Job? = null
    private var step: Int = 0
    private var bannerData = ArrayList<BannerBean>()
    private var mHostInfo: HostInfoBean? = null
    private var mUserHostInfo: UserInfo? = null
    private var mUserStatus: UserStatusBean? = null
    private var mRedPacketBaseData: RedPacketBaseBean? = null
    //    private var redPacketsData: ArrayList<RedPacketBean> = ArrayList()
    private var loveType: ArrayList<String> = ArrayList()
//    private var isRedPacketDeadline = true
    private lateinit var wordListAdapter: WordListAdapter
    //    private lateinit var inputDialog: InputDialog
    private lateinit var musicDialog: MusicDialog
    private lateinit var giftDialog: GiftDialog
    private lateinit var rowDialog: RowWheatDialog
    private lateinit var emojiDialog: EmojiDialog
    private lateinit var applyListDialog: ApplyListDialog
    private lateinit var firstChargeDialog: FirstChargeDialog
//    private lateinit var msgListDialog: MsgListDialog
    private var roomRankDialog: RoomRankDialog? = null
    private var voiceSettingDialog: VoiceSettingDialog? = null
    private var chattingMoreOperateNewDialog: ChattingMoreOperateNewDialog? = null
    private var clearMikeDialog: ClearMikeDialog? = null
    private var operateMusicDialog: OperateMusicDialog? = null
    private var redPacketDialog: RedPacketDialog? = null
    //    private var redPacketListDialog: RedPacketListDialog? = null
    private var wordSeletedType = WordListSelectedView.WSV_SELECTED_ALL

    private var statustype: Int = 2
    private var nexttype: Int = 1




    override fun getLayoutId(): Int {
        return R.layout.fragment_room_blind
    }

    override fun initView(view: View) {
        ChatRoomManager.register(ChatCallback(), this, this)
//        requestRecordPermission()
        EventBus.getDefault().register(this);
        initHostMicWaterRipple()
        initWordList()
        initDialog()
        initRoomBanner()
        initListener()
        initGiftPoint()
        ChatRoomManager.registerLight(this)
        ChatRoomManager.registerNetDelay(this)
//        ChatRoomManager.addRecordVolumeObserver(this)
        initPreStatus()
        initFirstchargeDialog()
    }

    /***
     * 20秒弹窗
     */
    private fun DownTime(){
            countDownTimer.start()
    }
    val countDownTimer: CountDownTimer = object : CountDownTimer(20000, 1000) {
        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            val upDialog = CommonDialog(context!!)

                        upDialog.setTitle("温馨提示")
                            upDialog.setContent("房主邀你排麦相亲,立即排麦吧")
                            upDialog.setLeftBt("取消") {
                                upDialog.dismiss()
                            }
                            upDialog.setRightBt("立即排麦") {
                                upDialog.dismiss()
                                rowDialog.show(childFragmentManager)
                            }
                            upDialog.show()
        }
    }

    override fun onUpdateTopMsg(msgBean: MsgBean) {
        tmv_top_msg.load(msgBean)
    }
    /**
     * 首充礼包
     */
    private fun initFirstchargeDialog(){

                if(mUserHostInfo!=null){
                    val today = DateUtil.ms2DateOnlyDay(System.currentTimeMillis())
                    var oldtime=SPUtil.getString("id:"+DataHelper.getUID().toString(),"0")
                    if(oldtime.equals("0")){
                        SPUtil.putString("id:"+DataHelper.getUID().toString(), today)
                        firstChargeDialog.setCanceledOnTouchOutside(false)
                        firstChargeDialog.show()
                    }else{
                        if(today!=oldtime){
                            SPUtil.putString("id:"+DataHelper.getUID().toString(), today)
                            firstChargeDialog.setCanceledOnTouchOutside(false)
                            firstChargeDialog.show()
                        }
                    }
                 }




    }
    override fun initPreStatus() {
//        ChatRoomManager.disPatchInRoom()
//        FloatingView.get().remove()
        if (ChatRoomManager.isReJoin) {    //重新进入房间
            loadData()
            if (RoomMusicManager.isShowIcon()) {
                music_iv.visibility = View.VISIBLE
                music_iv.clearAnimation()
                if (RoomMusicManager.isPlaying()) {
                    val anim = AnimationUtils.loadAnimation(context!!, R.anim.anim_music_play)
                    anim.interpolator = LinearInterpolator()
                    music_iv.startAnimation(anim)
                }
            } else {
                music_iv.visibility = View.INVISIBLE
            }
        } else {
            music_iv.visibility = View.INVISIBLE
            if (ChatRoomManager.mChatRoomInfo != null) {
                renderView(ChatRoomManager.mChatRoomInfo!!)
            }
        }
    }

    /**
     * 房主麦克风水波纹效果
     */
    private fun initHostMicWaterRipple() {
        mic_water.setDuration(1500)
        mic_water.setSpeed(500)
        mic_water.setColor(Color.rgb(255, 255, 255))
        mic_water.setStyle(Paint.Style.FILL)
        mic_water.setInterpolator(LinearOutSlowInInterpolator())
        mic_water.setInitialRadius(5f)
    }

    /**
     * 公屏聊天列表
     */
    private fun initWordList() {
        wordListAdapter = WordListAdapter(context!!, this)
        val layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        layoutManager.stackFromEnd = true
        word_list.layoutManager = layoutManager
        word_list.adapter = wordListAdapter
        word_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (unreadMsgPos != 0 && (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() >= unreadMsgPos) {
                    unreadMsgPos = 0
                    unreadMsgCount = 0
                    new_msg_tv.visibility = View.GONE
                }
            }
        })

        word_list.setOnTransListener(object : ChatLineRecyclerView.OnTransListener {

            override fun toTop() {
                word_selet_view.hide()
            }

            override fun toBottom() {
                word_selet_view.show()
            }

        })
    }

    /**
     * dialog初始化
     */
    private fun initDialog() {
//        inputDialog = InputDialog()
        musicDialog = MusicDialog()
        giftDialog = GiftDialog()
//        shareDialog = ShareDialog()
        emojiDialog = EmojiDialog()
        applyListDialog = ApplyListDialog()
//        msgListDialog = MsgListDialog()
        rowDialog = RowWheatDialog()
        firstChargeDialog= FirstChargeDialog(context!!)
    }

    private fun initRoomBanner() {
        banner.setOnBannerListener {
            when (it.mold) {
                BaseConfig.BANNER_MOLD_GAME_XX -> {
                    val dialog = GameXXDialog(context!!)
                    dialog.show()
                }
                BaseConfig.BANNER_MOLD_SMASH_EGG -> {

                    XYDialog().show(childFragmentManager)

                }
                BaseConfig.BANNER_MOLD_GAME_CB -> {
                    val dialog = GameXBDialog()
                    dialog.show(childFragmentManager)
                }
                BaseConfig.BANNER_MOLD_GAME_ZS -> {
                    ZSHomeDialog().show(childFragmentManager)
                }
                BaseConfig.BANNER_MOLD_WEB, BaseConfig.BANNER_MOLD_WEEK_STAR -> {
//                    GameDialog(it.url).show(childFragmentManager,"")
                    WebActivity.start(activity!!, it.url, it.title)
                }
                else -> {
                    ToastUtil.suc(context!!, "请升级版本查看")
                }
            }
        }
    }

//    private fun initGuardHeaderList() {
//        guardHeaders = ArrayList()
//        guardHeaderAdapter = GuardHeaderAdapter(guardHeaders)
//        rv_contribute_users.adapter = guardHeaderAdapter
//        guardHeaderAdapter.setOnItemClickListener { _, _, _ ->
//            roomRankDialog = RoomRankDialog.getInstance(TAB_TITLE_TYPE_BROADCASTING_STATION_CONTRIBUTION,RoomRankFragment.RANK_TYPE_RADIO_GUARD)
//            roomRankDialog?.show(childFragmentManager)
//        }
//        rv_contribute_users.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
//    }

    /**
     * 控件监听
     */
    private fun initListener() {
        game_btn.setOnClickListener {
            var dialog = GameListDialog()
            dialog.show(fragmentManager)
        }
        iv_emoji.setOnClickListener {
            emojiDialog.show(childFragmentManager)
        }
        iv_up_mic_list.setOnClickListener {
            rowDialog.show(childFragmentManager)
        }
        tv_blind_rule.setOnClickListener {
            WebActivity.start(context!!, BaseConfig.URL_BLIND_RULE, "相亲规则")
        }
        iv_blind_start.setOnClickListener {
            getNextStep(statustype)
        }

        iv_blind_reset.setOnClickListener {
            getNextStep(nexttype)
        }

        word_selet_view.setOnSeletedListener {
            wordSeletedType = it
            wordListAdapter.setData(ChatRoomManager.getWordList(), wordSeletedType)
            word_list.scrollToPosition(wordListAdapter.itemCount - 1)
        }

        tv_reload.setOnClickListener {
            loadData()
        }

//        tv_room_gift_history.setOnClickListener {
//            val dialog = RoomGiftHistoryDialog()
//            dialog.show(childFragmentManager)
//        }
//        red_packet_layout.setOnClickListener {
//            openRedPack()
//        }


//        red_packet_btn.setOnClickListener {
//            val dialog = SendRedPacketDialog(context!!,mRedPacketBaseData)
//            dialog.show()
//        }
//            ARouter.getInstance().build(RouterUrl.sendRedPacket)
//                    .withParcelable("data", mRedPacketBaseData).navigation() }


//        user_layout.setOnClickListener {
//            if (DoubleClickUtil.isFastDoubleClick()){
//                val dialog = RoomUserFragment()
//                dialog.setItemClickListener(object : RoomUserFragment.OnItemClickListener{
//                    override fun onItemClick(userInfo: UserInfo) {
//                        showUserCard(userInfo)
//                    }
//
//                })
//                dialog.show(childFragmentManager)
//            }
//        }

//        tv_open_mortal.setOnClickListener {
//        }


        mic_rv.addItemClickListener(this)

//        remote_voice_ctrl_btn.setOnCheckedChangeListener { _, isChecked ->
//            ChatRoomManager.remoteVoiceCtrl(!isChecked)
//        }
        follow_btn.setOnClickListener { followChat() }


        iv_finish.setOnClickListener {
            roomCommonOprateCallback.onFinish()
        }


        notice_tv.setOnClickListener {

            createRulePopupWindow(mHostInfo?.note).showAsDropDown(notice_tv)
        }


        more_btn.setOnClickListener { showChattingMoreOperateDialog() }



        word_chat_btn.setOnClickListener {
            NetService.getInstance(context!!).sendText(ChatRoomManager.getRoomId(), "", object : Callback<TextStatusBean>() {
                override fun onSuccess(nextPage: Int, bean: TextStatusBean, code: Int) {
                    if (!isAlive()) {
                        return
                    }
                    if (bean.speak == 0) {
                        roomCommonOprateCallback.onShowInputDialog(true,wordSeletedType)
//                        inputDialog.show(childFragmentManager)
                    } else {
                        ToastUtil.error(context!!, "禁言剩余时间" + bean.time)
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

//        emoji_btn.setOnClickListener { emojiDialog.show(childFragmentManager) }


        msg_btn.setOnClickListener {
//            msgListDialog.show(childFragmentManager)
            roomCommonOprateCallback.onShowMsgDialog()
            msg_notice.visibility = View.GONE
        }


//        mic_go_btn.setOnClickListener {
//            if (ChatRoomManager.getMicPosition() == 0) {
//                when (chatMode) {
//                    ChatMode.OPEN -> {        //上麦模式-自由上麦
//                        if (mic_rv.hasEmpty()) {
//                            ChatRoomManager.micCtrl(context!!, -1, false)
//                        } else {
//                            val upDialog = CommonDialog(context!!)
//                            upDialog.setTitle("友情提示")
//                            upDialog.setContent("当前麦位已满，确定申请上麦？")
//                            upDialog.setLeftBt("取消") {
//                                upDialog.dismiss()
//                            }
//                            upDialog.setRightBt("上麦") {
//                                ChatRoomManager.applyMic(context!!)
//                                upDialog.dismiss()
//                            }
//                            upDialog.show()
//                        }
//                    }
//                    ChatMode.ONLY_INVITE -> {    //仅邀请上麦
//                        if (ChatRoomManager.getUserRole() > 0) {
//                            if (mic_rv.hasEmpty()) {
//                                ChatRoomManager.micCtrl(context!!, -1, false)
//                            } else {
//                                val upDialog = CommonDialog(context!!)
//                                upDialog.setTitle("友情提示")
//                                upDialog.setContent("当前麦位已满，确定申请上麦？")
//                                upDialog.setLeftBt("取消") {
//                                    upDialog.dismiss()
//                                }
//                                upDialog.setRightBt("上麦") {
//                                    ChatRoomManager.applyMic(context!!)
//                                    upDialog.dismiss()
//                                }
//                                upDialog.show()
//                            }
//                        } else {
//                            val dialog = CommonDialog(context!!)
//                            dialog.setTitle("友情提示")
//                            dialog.setContent("当前房间仅邀请上麦，是否申请上麦")
//                            dialog.setLeftBt("取消") {
//                                dialog.dismiss()
//                            }
//                            dialog.setRightBt("上麦") {
//                                ChatRoomManager.applyMic(context!!)
//                                dialog.dismiss()
//                            }
//                            dialog.show()
//                        }
//                    }
//                }
//            } else {
//                val downDialog = CommonDialog(context!!)
//                downDialog.setTitle("友情提示")
//                downDialog.setContent("是否下麦")
//                downDialog.setLeftBt("取消") {
//                    downDialog.dismiss()
//                }
//                downDialog.setRightBt("下麦") {
//                    ChatRoomManager.micCtrl(context!!, 0, false)
//                    downDialog.dismiss()
//                }
//                downDialog.show()
//            }
//        }


//        playSvgaGiftAnim(send_gift_btn,"room_gift.svga")


        send_gift_btn.setOnClickListener {
            saveMicUser()
            GiftManager.getInstance().isInitGift(context!!) {
                if (mUserHostInfo != null) {
                    giftDialog.show(mUserHostInfo!!.nickname,
                            mUserHostInfo!!.user_id, mUserHostInfo!!.face, false, mUserHostInfo!!.mystery, childFragmentManager)
                }
            }
        }


        mic_status.setOnClickListener { ChatRoomManager.openMic(context!!) }

        new_msg_tv.setOnClickListener { word_list.scrollToPosition(wordListAdapter.itemCount - 1) }

        chat_popularity_fl.setOnClickListener {
            roomRankDialog = RoomRankDialog()
            roomRankDialog?.show(childFragmentManager)
        }

        music_iv.setOnClickListener {
            operateMusicDialog = OperateMusicDialog(context!!)
            operateMusicDialog?.setOnOperateMusic(object : OperateMusicDialog.OnOperateMusic {
                override fun onClose() {
                    music_iv.visibility = View.GONE
                }

                override fun onShowList() {
                    musicDialog.show(childFragmentManager)
                }
            })
            operateMusicDialog?.show()
        }

    }

//    private fun openRedPack(){
//        if (redPacketsData.size > 1) {
//            redPacketListDialog = RedPacketListDialog(context!!)
//            redPacketListDialog?.setOnGrabRedPacketItemClickListener(object : RedPacketListDialog.OnGrabRedPacketItemClickListener {
//                override fun onGrabRedPacketItemClick(position: Int) {
//                    getRedPacketBaseData()
//                }
//            })
//            redPacketListDialog?.show()
//        } else {
//            if (redPacketsData.isNotEmpty()) {
//                redPacketDialog = RedPacketDialog(context!!, redPacketsData[0], false,
//                        object : RedPacketDialog.OnDismissListener {
//                            override fun dismiss() {
//                                getRedPacketBaseData()
//                            }
//                        })
//                redPacketDialog?.setOnSendRedPacketClickListener(object : RedPacketDialog.OnSendRedPacketClickListener {
//                    override fun onSendRedPacketClick() {
////                            ARouter.getInstance().build(RouterUrl.sendRedPacket)
////                                    .withParcelable("data", mRedPacketBaseData).navigation()
//                        val dialog = SendRedPacketDialog(context!!,mRedPacketBaseData)
//                        dialog.show()
//                    }
//                })
//                redPacketDialog?.show()
//            }
//        }
//    }
    /**
     * 房间操作弹窗
     */
    private fun showChattingMoreOperateDialog() {
        chattingMoreOperateNewDialog = ChattingMoreOperateNewDialog.init(false)
        chattingMoreOperateNewDialog?.setOnOperateClickListener(object : ChattingMoreOperateNewDialog.OnOperateClickListener {
            override fun onVoiceClick() {
                ChatRoomManager.remoteVoiceCtrl(!ChatRoomManager.isCloseVoice)
            }

            override fun onRoomReport() {
//                report(ChatRoomManager.getRoomId(), 0)
            }

            override fun onRoomSetting() {
                ARouter.getInstance().build(RouterUrl.roomSetting)
                        .withString("mChatId", ChatRoomManager.getRoomId())
                        .withInt("type", ChatRoomManager.mRoomType).navigation(activity!!, 101)
            }

            override fun onMusicClick() {
                requestWritePermission()
            }

            override fun onVoiceSettingClick() {
                voiceSettingDialog = VoiceSettingDialog()
                voiceSettingDialog?.show(childFragmentManager)
            }

            override fun onClearChat() {
                clearChatList()
            }

            override fun onCloseOrOpenChat(type: Int) {
                closeChat(type)
            }

            override fun onCloseOrOpenSpecial() {
                GiftManager.getInstance().changeAnimstatus()
            }

            override fun onCloseOrOpenFullService() {
                GiftManager.getInstance().changeFullServiceStatus()
            }

            override fun onShare() {
//                shareDialog.show(childFragmentManager)
//                DataHelper.saveFullChatOpen(!DataHelper.isFullChatOpen())
            }

            override fun dismiss() {
            }

            override fun onRoomLocked() {
                val verifyPwdDialog = RoomSetPwdDialog(context!!, ChatRoomManager.getRoomId())
                verifyPwdDialog.setOnClickListener(object : RoomSetPwdDialog.OnClickListener {
                    override fun onRefuseClick() {
                        verifyPwdDialog.dismiss()
                    }

                    override fun onAgreeClick(str: String) {
                        setRoomPwd(str)
                        verifyPwdDialog.dismiss()
                    }

                })
                verifyPwdDialog.show()
                verifyPwdDialog.setContent("设置房间密码（4位数）")
            }

            override fun onRoomManager() {
                ARouter.getInstance().build(RouterUrl.roomManager)
                        .withString("chat_id", ChatRoomManager.getRoomId())
                        .navigation(context!!)
            }

            override fun onSendRedPack() {
                val dialog = SendRedPacketDialog(context!!, mRedPacketBaseData)
                dialog.show()
            }

            override fun onRoomClose() {
                superCloseRoom()
            }

            override fun onClearMike() {
                clearMikeDialog = ClearMikeDialog.newInstance(mic_rv.data.size)
                clearMikeDialog?.setOnOperateClickListener(object : ClearMikeDialog.OnClearMikeClickListener {
                    override fun clearMikeClick(position: Int) {
                        val upDialog = CommonDialog(context!!)
                        upDialog.setTitle("友情提示")
                        upDialog.setContent("是否清除麦序魅力值？")
                        upDialog.setLeftBt("取消") {
                            upDialog.dismiss()
                        }
                        upDialog.setRightBt("清除") {
                            ChatRoomManager.clearMike(context!!, position)
                            upDialog.dismiss()
                        }
                        upDialog.show()
                    }
                })
                clearMikeDialog?.show(childFragmentManager)
            }

            override fun onLiveClose() {
                val commonDialog = CommonDialog(context!!)
                commonDialog.setTitle("友情提示")
                commonDialog.setContent("是否关闭房间？")
                commonDialog.setLeftBt("取消") {
                    commonDialog.dismiss()
                }
                commonDialog.setRightBt("确定") {
//                    ChatRoomManager.closeLiveForManager(context!!, mHostInfo!!.icon!!)
                    commonDialog.dismiss()
                }
                commonDialog.show()
            }

            override fun onApplyList() {
                applyListDialog.show(childFragmentManager)
            }

            override fun onCloseOrOpenMic() {
                ChatRoomManager.openMic(context!!)
            }

            override fun onEmoji() {
                emojiDialog.show(childFragmentManager)
            }
        })
        chattingMoreOperateNewDialog?.show(childFragmentManager)
    }


    /**
     * 保存麦序
     */
    fun saveMicUser() {
        if (mUserHostInfo == null) {
            return
        }
        val micList = ArrayList<GiftInfoBean.ListBean>()
        val hostInfo = GiftInfoBean.ListBean()
        hostInfo.nickname = mUserHostInfo?.nickname
        hostInfo.face = mUserHostInfo?.face
        hostInfo.type = 0
        hostInfo.user_id = mUserHostInfo!!.user_id
        hostInfo.rank_id = mUserHostInfo!!.rank_id
        micList.add(hostInfo)
        for (info in mic_rv.data) {
            if (info.user_id > 0) {
                val bean = GiftInfoBean.ListBean()
                bean.nickname = info.nickname
                bean.user_id = info.user_id
                bean.face = info.face
                bean.type = info.type
                bean.mystery = info.mystery
                bean.rank_id = info.rank_id
                micList.add(bean)
            }
        }
        GiftManager.getInstance().saveMicUserList(micList)
    }


    fun loadData() {
        NetService.getInstance(context!!).getChatRoomInfo(ChatRoomManager.getRoomId(), object : Callback<ChatRoomInfo>() {
            override fun onSuccess(nextPage: Int, bean: ChatRoomInfo, code: Int) {
                ll_load_error.visibility = View.GONE
                renderView(bean)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                if (code == Data.CODE_ILLEGAL_REQUEST) {
                    return
                }
                ll_load_error.visibility = View.VISIBLE
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    @SuppressLint("NewApi")
    private fun getNextStep(nttype: Int) {

        NetService.getInstance(context!!).getNextStep(ChatRoomManager.getRoomId(), nttype, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {

            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {

                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    private fun getReversal(position: Int) {

        NetService.getInstance(context!!).getReversal(ChatRoomManager.getRoomId(), position, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {

            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    private fun getFinal_choice(nttype: Int) {

        NetService.getInstance(context!!).getFinal_choice(ChatRoomManager.getRoomId(), nttype, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {

            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    private fun getMsgHistoryList() {
        NetService.getInstance(context!!).getMsgHisroyList(ChatRoomManager.getRoomId(), object : Callback<List<MsgHistoryBean>>() {
            override fun onSuccess(nextPage: Int, bean: List<MsgHistoryBean>, code: Int) {
                chatHistoryJob = GlobalScope.launch {
                    if (!ChatRoomManager.isRoomHost() && isClearChatHistory) {
                        withContext(Dispatchers.Main) {
                            ChatRoomManager.addWordList(ChatRoomManager.getNoticeMsgBean(mHostInfo?.note!!))
                            wordListAdapter.addItem(ChatRoomManager.getNoticeMsgBean(mHostInfo?.note!!))
//                            wordListAdapter.notifyItemInserted(ChatRoomManager.getWordList().size - 1)
                            word_list.scrollToPosition(wordListAdapter.itemCount - 1)
                        }
                        delay(100)
                    }
                    bean.reversed().forEach {
                        withContext(Dispatchers.Main) {
                            val msgBean = MsgBean(MsgType.TEXT_MSG, it.content,
                                    it.room_id.toString(), "", "", 0, 0, 0, null, null, null, toUserInfo = UserInfo(),
                                    fromUserInfo = UserInfo(nickname = it.nickname, user_id = it.user_id,
                                            wealth_level = it.wealth_level, charm_level = it.charm_level,
                                            seat_frame = it.seat_frame, effects = 0, face = it.face, user_role = it.user_role, is_guard = it.is_guard,
                                            first_sign = mUserStatus!!.first_sign, good_number = it.good_number, good_number_state = it.good_number_state
                                            , activity_pic = it.activity_pic, mystery = it.mystery, medal = it.medal, rank_id = it.rank_id))
                            ChatRoomManager.addWordList(msgBean)
                            wordListAdapter.addItem(msgBean)
                            word_list.scrollToPosition(wordListAdapter.itemCount - 1)
                        }
                        delay(200)
                    }
                    if (isClearChatHistory && mUserStatus?.super_manager != 1) {
                        withContext(Dispatchers.Main) {
                            ChatRoomManager.sendMsg(MsgType.JOIN_CHAT, "进入房间", ChatRoomManager.getRoomId())
                        }

                    }
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    private fun getRoomBanner() {
        NetService.getInstance(context!!).getRadioBanner(getString(R.string.banner_type), "2",ChatRoomManager.getRoomId(), object : Callback<List<BannerBean>>() {
            override fun onSuccess(nextPage: Int, bean: List<BannerBean>, code: Int) {
                bannerData.clear()
                if (bean.isNotEmpty()) {
                    bannerData.addAll(bean)
//                    var data = BannerBean()
//                    bannerData.forEach {
//                        if (it.mold == BaseConfig.BANNER_MOLD_FIRST_RECHARGE && mUserStatus!!.is_first == 0) {
//                            data = it
//                        }
//                    }
//                    bannerData.remove(data)
//                    var isShowXXGame = false
//                    bannerData.forEach {
//                        if (it.mold == BaseConfig.BANNER_MOLD_GAME_XX ) {
//                            isShowXXGame = true
//                        }
//                    }
//                    if (isShowXXGame){
//                        tv_open_mortal.visibility = View.VISIBLE
//                    }else{
//                        tv_open_mortal.visibility = View.GONE
//                    }
                }
                banner?.update(bannerData)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }
    private fun getBrightStatus() {
        NetService.getInstance(context!!).getBrightStatus(ChatRoomManager.getRoomId(), object : Callback<BrightStatusBean>() {
            override fun onSuccess(nextPage: Int, bean:BrightStatusBean, code: Int) {
                mic_rv.BrightStatus(step,bean)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }
    private fun followChat() {
        if (follow_btn.isSelected) {
            return
        }
        NetService.getInstance(context!!).followChat(ChatRoomManager.getRoomId(), object : Callback<FollowResultBean>() {
            override fun onSuccess(nextPage: Int, bean: FollowResultBean, code: Int) {
                follow_btn.isSelected = false
                ChatRoomManager.sendMsg(MsgType.ROOM_COLLECT, "", ChatRoomManager.getRoomId())
            }
            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }


//    /**
//     * 获取红包配置数据
//     */
//    private fun getRedPacketBaseData() {
//        NetService.getInstance(context!!).getRedPacketBaseData(ChatRoomManager.getRoomId(), object: Callback<RedPacketBaseBean>() {
//            override fun onSuccess(nextPage: Int, bean: RedPacketBaseBean, code: Int) {
//                mRedPacketBaseData = bean
//                redPacketsData.clear()
//                redPacketsData.addAll(bean.collections)
//                if (redPacketsData.isNotEmpty()) {
//                    red_packet_layout.visibility = View.VISIBLE
//                    red_packet_num_tv.text = "${redPacketsData.size}个"
//                    setRedPacketCountDownTimer(redPacketsData[0].expired_at * 1000 - System.currentTimeMillis())
//                } else {
//                    red_packet_layout.visibility = View.GONE
//                    isRedPacketDeadline = true
//                }
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//        })
//    }


    private fun clearChatList() {
        if (ChatRoomManager.getUserRole() > 0) {
            ChatRoomManager.sendMsg(MsgType.CLEAR_CHAT, "${DataHelper.getUserInfo()!!.nickname}清理了公屏聊天", ChatRoomManager.getRoomId())
        } else {
            ToastUtil.suc(context!!, "您没有该权限哦")
        }
    }

    private fun closeChat(type: Int) {
        ChatRoomManager.closeChat(type)
    }

    private fun superCloseRoom() {
        NetService.getInstance(context!!).superRoomClose(ChatRoomManager.getRoomId(), object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {

            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {

            }

            override fun isAlive(): Boolean {
                return isAlive()
            }
        })
    }

    //渲染页面
    private fun renderView(bean: ChatRoomInfo) {
//        shareDialog.share_desc = bean.share_desc
//        shareDialog.share_title = bean.share_title
//        shareDialog.share_icon = bean.share_icon
//        shareDialog.share_url = bean.share_url
        mHostInfo = bean.host_info
        mUserHostInfo = bean.user_host
        ChatRoomManager.mUserHostInfo = bean.user_host
        mUserStatus = bean.user_status
        ChatRoomManager.mRoomType = bean.host_info.room_type
//        ImgUtil.loadWebpGif(context!!, bean.host_info.backdrop, chat_bg, R.drawable.common_bg_room_default)
        GiftManager.getInstance().accountBalance = bean.user_status.balance
        ChatRoomManager.mCloseChat = bean.user_status.screen
        ChatRoomManager.setRoomHostId(bean.user_host.user_id)
        ChatRoomManager.setStatusBean(bean.user_status)
        ChatRoomManager.isFollow = bean.host_info.is_follow
        if (!ChatRoomManager.isReJoin) {    //第一次进入房间
            new_msg_tv.visibility = View.GONE
            if(ChatRoomManager.getUserRole()<=1){
                DownTime()
            }

            getMsgHistoryList()
            if (isClearChatHistory) ChatRoomManager.setWordList(ArrayList())
        }
        getRoomBanner()
        getBrightStatus()
//        getRedPacketBaseData()
        loadCommonRoomView(bean)
        updateBottomView()
        wordListAdapter.setData(ChatRoomManager.getWordList(),WordListSelectedView.WSV_SELECTED_ALL)
        ChatRoomManager.isReJoin = true
        isClearChatHistory = true
        refreshRankTop()
    }


    /**
     * 加载普通房间view（个人/工会）
     */
    private fun loadCommonRoomView(bean: ChatRoomInfo) {
        input_layout.visibility = View.VISIBLE
        chatting_title_layout.visibility = View.VISIBLE
//        common_top_layout.visibility = View.VISIBLE
        mic_rv.data = bean.mic_list  //更新mic区域
//        EventBus.getDefault().post(MicNumBean(mic_rv.data.size))
        chatMode = bean.host_info.way!!
        if (ChatRoomManager.getUserRole() > 1) {
            iv_blind_start.visibility = View.VISIBLE
            iv_blind_reset.visibility = View.VISIBLE
        }else if(ChatRoomManager.getUserRole() > 0 && bean.user_status.type != 0 &&DataHelper.getUID() == bean.mic_list!![bean.user_status.type - 1].user_id){
            iv_blind_start.visibility = View.VISIBLE
            iv_blind_reset.visibility = View.VISIBLE
        }else{
            iv_blind_start.visibility = View.GONE
            iv_blind_reset.visibility = View.GONE
        }
        if (bean.host_info.step == 0) {
            iv_blind_start.text = "开始"
        } else if (bean.host_info.step == 5) {
            iv_blind_start.text = "结束"
        } else {
            iv_blind_start.text = "下一步"
        }

        step = bean.host_info.step
        checkBilidStatus(bean.host_info.step)
        ChatRoomManager.mRoomGoodNumber = bean.host_info.room_good_number!!
        if (bean.user_status.type != 0 && DataHelper.getUID() == bean.mic_list!![bean.user_status.type - 1].user_id) {
            ChatRoomManager.isVoiceLive = true
        } else {
            ChatRoomManager.setMicPosition(0)
        }
        delay_tv.visibility = View.GONE
        bean.mic_list!!.forEach {
            if (it.user_id > 0 && it.user_id != DataHelper.getUID()) {
                delay_tv.visibility = View.VISIBLE
            }
        }
//        if (bean.user_host.mike == 0){
//            host_charm_tv.visibility = View.INVISIBLE
//        }else{
//            host_charm_tv.visibility = View.VISIBLE
//        }
//        host_charm_tv.text = bean.user_host.mike.toString()
        if (ChatRoomManager.isRoomHost()) {
            follow_btn.isSelected = true
//            mic_go_btn.visibility = View.GONE
        } else {
//            mic_go_btn.visibility = View.GONE
            follow_btn.isSelected = bean.host_info.is_follow == 1
        }
        tv_hand_num.text = "牵手" + bean.host_info.hold_hands_count + "对"
//        iv_liang.text = ChatRoomManager.getRoomId()
        if (bean.host_info.room_good_number == 0){
            iv_liang.show(ChatRoomManager.getRoomId(),false)
        }else{
            iv_liang.show(bean.host_info.room_good_number.toString(),true)
        }
//        room_id.text = "ID:" + if (bean.host_info.room_good_number == 0) ChatRoomManager.getRoomId() else bean.host_info.room_good_number
//        room_id.setCompoundDrawablesWithIntrinsicBounds(if (bean.host_info.room_good_number == 0) 0 else R.drawable.common_user_icon_liang, 0, 0, 0)
        main_tv.text = bean.host_info.name
        ImgUtil.loadCircleImg(context!!, bean.user_host.face, host_icon_iv)
        if (TextUtils.isEmpty(bean.user_host.seat_frame)) {
            host_seat_iv.setBackgroundResource(0)
        } else {
//            host_seat_iv.visibility = View.VISIBLE
            host_seat_iv.setBackgroundResource(0)
            ImgUtil.loadGifOrWebp(context!!, bean.user_host.seat_frame, host_seat_iv, Int.MAX_VALUE)
        }
//        host_name_tv.text = bean.user_host.nickname

        chat_online_num.text = "" + bean.host_info.hot_value.toString()
        host_icon_iv.setOnClickListener {
            bean.user_host.user_role = 2
            showUserCard(bean.user_host)
        }
        mic_water.visibility = if (!ChatRoomManager.isReJoin) View.INVISIBLE else View.VISIBLE
    }


    private fun updateWord(msgBean: MsgBean) {
        wordListAdapter.addItem(msgBean, wordSeletedType)
        if (isVisBottom()) {
            new_msg_tv.visibility = View.GONE
            unreadMsgCount = 0
            word_list.scrollToPosition(wordListAdapter.itemCount - 1)
        } else {
            unreadMsgCount++
            new_msg_tv.visibility = View.VISIBLE
            new_msg_tv.text = if (unreadMsgCount >= 99) "99+条新消息" else "${unreadMsgCount}条新消息"
        }
    }

    // 消息列表是否在底部
    private fun isVisBottom(): Boolean {
        var layoutManager = word_list.layoutManager as LinearLayoutManager
        var visibleItemCount = layoutManager.childCount
        var totalItemCount = layoutManager.itemCount
        var lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        var state = word_list.scrollState
        if (visibleItemCount >= totalItemCount) {
            unreadMsgPos = 0
            return true
        }
        if (lastVisibleItemPosition >= totalItemCount - 4 && state == RecyclerView.SCROLL_STATE_IDLE) {
            unreadMsgPos = 0
            return true
        }
        if (unreadMsgPos == 0) unreadMsgPos = totalItemCount - 1
        return false
    }

    override fun onLiveFinish() {
        if (!ChatRoomManager.isSelfCloseLive) {
            ToastUtil.suc(activity!!, "房间已关闭")
        }
//            RoomClosedDialog(context!!, mHostInfo!!.icon!!).show()
        ChatRoomManager.isSelfCloseLive = false
    }

    override fun onUserItemClick(view: View, userInfo: UserInfo) {
        showUserCard(userInfo)
    }

    override fun onWordItemClick(type: MsgType, roomId: String) {
        if (type == MsgType.WINNING_MSG) {

            XYDialog().show(childFragmentManager)
        }
    }

    //灭灯
    override fun onLightUpItemClick() {
        val upDialog = CommonDialog(context!!)
        upDialog.setTitle("友情提示")
        upDialog.setContent("灭灯后将无法牵手,是否确定灭灯？")
        upDialog.setLeftBt("取消") {
            upDialog.dismiss()
        }
        upDialog.setRightBt("确定") {
            ChatRoomManager.turnoffLight(context!!)
            upDialog.dismiss()
        }
        upDialog.show()

    }

    // 选择心动女生
    override fun onLightHeartItemClick(position: Int) {
        val upDialog = CommonDialog(context!!)
        if (step == 5) {
            upDialog.setTitle("是否选择该嘉宾牵手成功?")
            upDialog.setContent("(选择后无法取消，只能选择一位)")
        } else {
            upDialog.setTitle("是否选择该嘉宾进入牵手环节?")
            upDialog.setContent("(选择后无法取消，最多选择两位）")
        }

        upDialog.setLeftBt("取消") {
            upDialog.dismiss()
        }
        upDialog.setRightBt("确定") {
            if (step == 5) {
                getFinal_choice(position)
            } else {
                if (loveType.size < 2) {
                    loveType.add("" + position)
                    getReversal(position)
                    mic_rv.lineShowView(position-1,1)
                } else {
                    ToastUtil.error(context!!, "最多选择两位嘉宾")
                }
            }
            upDialog.dismiss()
        }
        upDialog.show()
    }

    override fun onMicItemClick(view: View, userInfo: UserInfo, position: Int) {

        if (userInfo.user_id <= 0) {//当前麦无人

            when {
                ChatRoomManager.isRoomHost() -> {

                    XqMicOperateDialog(userInfo).setOnMicOperateClickListener(object : XqMicOperateDialog.OnMicOperateClickListener {
                        override fun onMicOperateClick(operateType: Int) {
                            when (operateType) {
                                MicOperateDialog.MIC_OPERATE_LOCK -> {
                                    ChatRoomManager.lockMic(context!!, if (userInfo.user_id == -1) {
                                        0
                                    } else {
                                        -1
                                    }, userInfo, position + 1)
                                }
                                MicOperateDialog.MIC_OPERATE_BAN -> {
                                    ChatRoomManager.banMic(context!!, position + 1, userInfo)
                                }
                                MicOperateDialog.MIC_OPERATE_CLEAR -> {
                                    val upDialog = CommonDialog(context!!)
                                    upDialog.setTitle("友情提示")
                                    upDialog.setContent("是否清除麦序魅力值？")
                                    upDialog.setLeftBt("取消") {
                                        upDialog.dismiss()
                                    }
                                    upDialog.setRightBt("清除") {
                                        ChatRoomManager.clearMike(context!!, position + 1)
                                        upDialog.dismiss()
                                    }
                                    upDialog.show()
                                }
                            }
                        }
                    }).show(childFragmentManager)
                }
                ChatRoomManager.isRoomManager() -> {

                    XqMicOperateDialog(userInfo).setOnMicOperateClickListener(object : XqMicOperateDialog.OnMicOperateClickListener {
                        override fun onMicOperateClick(operateType: Int) {
                            when (operateType) {
                                MicOperateDialog.MIC_OPERATE_LOCK -> {
                                    ChatRoomManager.lockMic(context!!, if (userInfo.user_id == -1) {
                                        0
                                    } else {
                                        -1
                                    }, userInfo, position + 1)
                                }
                                MicOperateDialog.MIC_OPERATE_BAN -> {
                                    ChatRoomManager.banMic(context!!, position + 1, userInfo)
                                }
                                MicOperateDialog.MIC_OPERATE_UP -> upToMic(userInfo, position)
                                MicOperateDialog.MIC_OPERATE_CLEAR -> {
                                    val upDialog = CommonDialog(context!!)
                                    upDialog.setTitle("友情提示")
                                    upDialog.setContent("是否清除麦序魅力值？")
                                    upDialog.setLeftBt("取消") {
                                        upDialog.dismiss()
                                    }
                                    upDialog.setRightBt("清除") {
                                        ChatRoomManager.clearMike(context!!, position + 1)
                                        upDialog.dismiss()
                                    }
                                    upDialog.show()
                                }
                            }
                        }
                    }).show(childFragmentManager)
                }
                else -> {

                    if (userInfo.user_id == -1) {
                        ToastUtil.error(context!!, "麦已上锁，无法上麦")
                        return
                    }
                    if (position == 0) {
                        if (ChatRoomManager.getUserRole() > 0) {
                            upToMic(userInfo, position)
                        } else {
                            ToastUtil.error(context!!, "用户无法上麦主持位")
                            return
                        }
                    }
                }
            }
        } else {
            showUserCard(userInfo)
        }
    }

    override fun onUpdateDelay(delay: Int) {
        activity!!.runOnUiThread {
            delay_tv?.apply {
                visibility = View.VISIBLE
                when (delay) {
                    in 1..100 -> {
//                        text = "流畅"
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.room_green_circle, 0, 0, 0)
                    }
                    in 101..200 -> {
//                        text = "拥挤"
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.room_orange_circle, 0, 0, 0)
                    }
                    else -> {
//                        text = "网络不稳定"
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.room_red_circle, 0, 0, 0)
                    }
                }
            }
        }
    }


    private fun upToMic(userInfo: UserInfo, position: Int) {
        goToMic(position)
    }

    private fun goToMic(position: Int) {
        if (ChatRoomManager.getMicPosition() == 0) {    //如果未在麦上
            when (chatMode) {
                ChatMode.OPEN -> {        //上麦模式-自由上麦
                    ChatRoomManager.micCtrl(context!!, position + 1, false)
                }
                ChatMode.ONLY_INVITE -> {    //仅邀请上麦
                    if (ChatRoomManager.getUserRole() > 0) {
                        ChatRoomManager.micCtrl(context!!, position + 1, false)
                    } else {
                        val dialog = CommonDialog(context!!)
                        dialog.setTitle("申请上麦")
                        dialog.setContent("当前房间仅邀请上麦，是否申请上麦")
                        dialog.setLeftBt("取消") {
                            dialog.dismiss()
                        }
                        dialog.setRightBt("确定") {
                            ChatRoomManager.applyMic(context!!)
                            dialog.dismiss()
                        }
                        dialog.show()
                    }
                }
            }
        } else { //如果已上麦，则切换
            ChatRoomManager.micCtrl(context!!, position + 1, true)
        }
    }

    override fun showUserCard(userInfo: UserInfo) {
        if (DoubleClickUtil.isFastDoubleClick()) {
            UserCardDialog(ChatRoomManager.getRoomId(), userInfo, object : UserCardDialog.ClickListener {
                override fun joinNewRoom(roomId: Int, hostId: Int) {
                    ChatRoomManager.joinChat(context!!, roomId.toString(), object : JoinChatCallBack {
                        override fun onSuc() {
//                        loadData()
                        }

                        override fun onFail(msg: String) {
                            ToastUtil.error(context!!, msg)
                        }
                    })
                }

                override fun report(userInfo: UserInfo) {
                    report(userInfo.user_id.toString(), 1)
                }

                override fun sendGift(userInfo: UserInfo) {
                    saveMicUser()
                    GiftManager.getInstance().isInitGift(context!!) {
                        giftDialog.show(userInfo.nickname
                                , userInfo.user_id, userInfo.face, true, userInfo.mystery, childFragmentManager)
                    }
                }

                override fun sendRedEnvelope(userInfo: UserInfo) {

                }

                override fun callSomebody(userInfo: UserInfo) {
                    roomCommonOprateCallback.onShowInputDialog(true,wordSeletedType)
//                    inputDialog.show("@" + userInfo.nickname + " ", childFragmentManager)
                }

                override fun micCtrl4Host(userInfo: UserInfo) {
                    ChatRoomManager.micCtrl4Host(context!!, userInfo)
                }

                override fun banUser(userInfo: UserInfo, opt: Int) {
                    ChatRoomManager.banUser4Host(context!!, userInfo, opt)
                }

                override fun banMic(userInfo: UserInfo) {
                    ChatRoomManager.banMic(context!!, userInfo.type, userInfo)
                }

                override fun kickOut(userInfo: UserInfo) {
                    val confirmDialog = CommonDialog(context!!)
                    confirmDialog.setTitle("友情提示")
                    confirmDialog.setContent("您确定要将该用户踢出房间吗？")
                    confirmDialog.setLeftBt("取消") {
                        confirmDialog.dismiss()
                    }
                    confirmDialog.setRightBt("确定") {
                        ChatRoomManager.kickOut(context!!, userInfo)
                        confirmDialog.dismiss()
                    }
                    confirmDialog.show()
                }
            }).show(childFragmentManager)
        }

    }


    inner class ChatCallback : ChatRoomCallBack() {

        override fun speaking(uid: HashMap<Int, Int>) {
            if (uid.containsKey(ChatRoomManager.getRoomHostUid())) {
                mic_water.visibility = View.VISIBLE
                mic_water.start()
            } else {
                mic_water.stop()
            }
            for (i in mic_rv.data.indices) {
                val bean = mic_rv.data[i]
                bean.mic_speaking = uid.containsKey(bean.user_id)
                mic_rv.showVoiceWave(i, mic_rv.data[i])
            }
        }


        // 房主踢用户 更新自身micView,通知其他用户更新micView，通知对应用户退出房间
        override fun kickOut() {
            //sendMsg 给对应用户发送踢出房间，退出房间通知由用户自己发起
            ToastUtil.suc(context!!, "踢出成功")
        }

        //sendMsg 通知所有用户自己离开房间 用户收到离开房间的消息更新界面，如果用户在麦序上需额外处理,如果用户为房主需要退出房间
        override fun levelChat() {
//            finish()
        }

        override fun sendEmoji() {
            emojiDialog.dismiss()
        }

        override fun showMusicIcon(isRun: Boolean) {
            music_iv.visibility = View.VISIBLE
            if (isRun) {
                var anim = AnimationUtils.loadAnimation(context!!, R.anim.anim_music_play)
                anim.interpolator = LinearInterpolator()
                music_iv.startAnimation(anim)
            } else {
                music_iv.clearAnimation()
            }
        }

        override fun closeMusicIcon() {
            music_iv.visibility = View.INVISIBLE
        }
    }

//    override fun onRoomUsersUpdate(type: Int, uid: Int, onlineNum: Int, msgBean: ArrayList<MsgBean>) {
//        MsgManager.INSTANCE.getGroupNumSize(ChatRoomManager.getRoomId()) {
//            chat_online_num.text = "在线:$it"
//        }
//        if (uid == ChatRoomManager.getRoomHostUid()) {
//            if (type == ChatRoomUpdateViewCallBack.TYPE_USER_JOIN) {
//                host_status.setBackgroundResource(R.drawable.chatting_transparent)
//            } else {
//                host_status.setBackgroundResource(R.drawable.chatting_host_offline)
//            }
//        }
//        if (type == ChatRoomUpdateViewCallBack.TYPE_USER_JOIN) {
//            if (msgBean[0].fromUserInfo.super_manager != 1) {
//                var view = NobleEntryEffectsView(context!!)
//                if (msgBean[0].fromUserInfo.body_img.isNotEmpty() && msgBean[0].fromUserInfo.effects != 0){
//                    view.setEntryEffects(msgBean[0].fromUserInfo.effects, msgBean[0].fromUserInfo.head_img, msgBean[0].fromUserInfo.body_img)
//                    view.setImageLevel(if (msgBean[0].fromUserInfo.mystery == 1) 0 else msgBean[0].fromUserInfo.wealth_level.grade)
//                    view.setAvter(msgBean[0].fromUserInfo.face)
//                    view.setContent(msgBean[0].fromUserInfo.nickname + " 进入房间")
//                }
//                if (!TextUtils.isEmpty(msgBean[0].fromUserInfo.seat)){
//                    view.setNobleAnim(msgBean[0].fromUserInfo.seat)
//                }
//                if (!TextUtils.isEmpty(msgBean[0].fromUserInfo.effect_svga)){
//                    view.showSvgaEnter(msgBean[0].fromUserInfo.effect_svga,msgBean[0].fromUserInfo.face,msgBean[0].fromUserInfo.nickname + " 进入房间",msgBean[0].fromUserInfo.wealth_level.grade)
//                }
//                bv_horn.addBarrage(view)
//            }
//        }
//    }

    override fun onUpdate(type: Int, msgBean: ArrayList<MsgBean>) {
        when (type) {
            ChatRoomUpdateViewCallBack.QUIT_XIANGQING_QUEUE -> {
                if (rowDialog != null && rowDialog.getDialog() != null
                        && rowDialog.getDialog().isShowing()) {
                    rowDialog.showView()
                }
            }
            ChatRoomUpdateViewCallBack.XIANGQING_NEXT_STEP -> {
                for (bean in msgBean) {
                    updateHost(bean)
                }
            }
            ChatRoomUpdateViewCallBack.TYPE_HOST -> {
                for (bean in msgBean) {
                    updateHost(bean)
                }
            }
            ChatRoomUpdateViewCallBack.TYPE_MIC -> {
                for (bean in msgBean) {
                    updateHost(bean)
                    updateMicView(bean)
                }
            }
//            ChatRoomUpdateViewCallBack.TYPE_WORD -> {
//                updateWordList(msgBean)
//            }
//            ChatRoomUpdateViewCallBack.TYPE_WORD_ALL_REFRESH -> {
//                updateWordList(msgBean)
//            }
            ChatRoomUpdateViewCallBack.TYPE_BOTTOM -> {
                for (bean in msgBean) {
                    updateBottomView4Host(bean)
                }
                updateBottomView()
            }
            ChatRoomUpdateViewCallBack.TYPE_ALL -> {
                isClearChatHistory = false
                loadData()
            }
//            ChatRoomUpdateViewCallBack.TYPE_FULL_NOTICE -> {
//                for (bean in msgBean) {
//                    showFullServiceGift(bean)
//                }
//            }
            ChatRoomUpdateViewCallBack.TYPE_INPUT -> {
                for (bean in msgBean) {
                    roomCommonOprateCallback.onShowInputDialog(false,wordSeletedType)
                }
            }
//            ChatRoomUpdateViewCallBack.TYPE_GIFT_LUCK->{
//                for (bean in msgBean){
//                    GiftManager.getInstance().addLuckGiftAnim(bean.fromUserInfo.face,
//                            bean.fromUserInfo.nickname ?: "",
//                            bean.giftBean!!.giftName, bean.toUserInfo.nickname, bean.giftBean!!.giftIcon, bean.giftBean!!.giftNum,
//                            bean.fromUserInfo.user_id, bean.toUserInfo.user_id, bean.giftBean!!.giftId,bean.giftBean!!.giftRewardType,bean.giftBean!!.giftReward)
//                    if(bean.toUserInfo.user_id == ChatRoomManager.getRoomHostUid()){
//                        host_charm_fl.visibility = View.VISIBLE
//                        host_charm_tv.text = (host_charm_tv.text.toString().toInt() + bean.giftBean!!.giftPrice * bean.giftBean!!.giftNum).toString()
//                    }else{
//                        mic_rv.updateItemMike(bean)
//                    }
//                }
//            }
            ChatRoomUpdateViewCallBack.TYPE_GIFT_CHEST -> {
                for (bean in msgBean) {
                    GiftManager.getInstance().addChestGiftAnim(bean.giftBean, getMicPosition(bean.toUserInfo.user_id))
//                    if(bean.toUserInfo.user_id == ChatRoomManager.getRoomHostUid()){
//                        host_charm_tv.visibility = View.VISIBLE
//                        host_charm_tv.text = (host_charm_tv.text.toString().toInt() + bean.giftBean!!.giftPrice * bean.giftBean!!.giftNum).toString()
//                    }else{
//                        mic_rv.updateItemMike(bean)
//                    }
                }
            }
//            ChatRoomUpdateViewCallBack.TYPE_RED_PACKET -> {
//                for (bean in msgBean){
//                    if (bean.opt == MsgType.RED_PACKET_MSG) {
//                        bean.collection!!.face = bean.fromUserInfo.face
//                        bean.collection!!.nickname = bean.fromUserInfo.nickname
//                        bean.collection!!.user_id = bean.fromUserInfo.user_id.toString()
//                        redPacketsData.add(bean.collection!!)
//                        EventBus.getDefault().post(bean.collection)
//                        red_packet_layout.visibility = View.VISIBLE
//                        red_packet_num_tv.text = "${redPacketsData.size}个"
//                        if (isRedPacketDeadline) {
//                            setRedPacketCountDownTimer(redPacketsData[0].expired_at * 1000 - System.currentTimeMillis())
//                        }
//                    }
////                    else if (bean.opt == MsgType.RED_PACKET_BOARDCAST) {
////                        showFullServiceRedPacket(bean)
////                    }
//                }
//            }
//            ChatRoomUpdateViewCallBack.TYPE_JOIN_GUARD -> {
//                for (bean in msgBean) {
//                    if (bean.fromUserInfo.user_id == DataHelper.getUID()) {
//                        mUserStatus!!.is_guard = 0
//                        chatMode = when {
//                            mUserStatus!!.is_guard != -1 && DataHelper.getLocalUser()!!.noble_status -> ChatMode.OPEN
//                            mUserStatus!!.is_guard != -1 -> ChatMode.ONLY_GUARD
//                            DataHelper.getLocalUser()!!.noble_status -> ChatMode.ONLY_NOBLE
//                            else -> ChatMode.CLOSE
//                        }
//                    }
//                    guardNum += 1
//                    guard_anchor_tv.text = if (mUserStatus?.is_guard == -1) "守护主播" else "${guardNum}人守护"
//                }
//            }
        }
    }

    override fun onEnterRoomClick(userInfo: UserInfo) {
        roomCommonOprateCallback.onEnterRoom(userInfo)
    }

    override fun onUpdateWord(type: Int, msgBean: MsgBean) {
        if (msgBean.opt == MsgType.CLEAR_CHAT) {
            wordListAdapter.clearData()
        }
        updateWord(msgBean)
    }


    override fun onLigHtUp(type: Int,lightupbean: LightUpBean) {
        var i: Int = 0
        var t: Int = 0
        for (bean in lightupbean.bright) {
            i++
            mic_rv.upLightUpItem(lightupbean.step,type, i, bean,lightupbean.have_people.get(t),lightupbean.lights_music)
            t++
        }


    }
    override fun onRefreshMic(users: List<UserInfo>) {
        mic_rv.refreshData(users)
    }

    override fun onRefreshBottomBt(msgType: MsgType) {
        if (msgType == MsgType.DOWN_FROM_MIC) {
            RoomMusicManager.pausePlayMusic()
            music_iv.visibility = View.INVISIBLE
        }
        updateBottomView()
    }

    private fun updateHost(msgBean: MsgBean) {

        when (msgBean.opt) {

            MsgType.MIC_CTRL -> {
//                if (ChatRoomManager.getRoomHostUid() == msgBean.fromUserInfo.user_id) {
//                    if (msgBean.fromUserInfo.status != 2) {
//                        host_status.setBackgroundResource(R.drawable.chatting_transparent)
//                    } else {
//                        host_status.setBackgroundResource(R.drawable.chatting_mic_disable)
//                    }
//                }
            }
            MsgType.XIANGQING_NEXT_STEP -> {
                if (msgBean.step == 0) {
                    iv_blind_start.text = "开始"
                } else if (msgBean.step == 5) {
                    iv_blind_start.text = "结束"
                } else {
                    iv_blind_start.text = "下一步"
                }
                step = msgBean.step
                checkBilidStatus(msgBean.step)
            }
            MsgType.INVITE_TO_MIC -> {
                ChatRoomManager.micCtrl(context!!, -1, false, true)
            }
            MsgType.EMOJI -> {
                if (msgBean.fromUserInfo.user_id == ChatRoomManager.getRoomHostUid()) {
                    if (msgBean.emojiBean != null) {
//                        if (host_emoji_iv.visibility == View.VISIBLE) {
//                            return
//                        }
//                        val emojiBean = msgBean.emojiBean
//                        if (emojiBean!!.emoji_id == 105 && emojiBean.emoji_group_id == 1) {
//                            host_emoji_iv.visibility = View.VISIBLE
//                            DiceUtil.showDice(host_emoji_iv, emojiBean.emoji_result)
//                        } else if (emojiBean.emoji_id == 103) {
//                            host_emoji_iv.visibility = View.VISIBLE
//                            BaodengUtil.showBaodeng(host_emoji_iv)
//                        } else {
//                            host_emoji_iv.setVisibility(View.VISIBLE)
//                            ImgUtil.loadGifOrWebp(context!!, emojiBean.emoji_gif, host_emoji_iv, 1)
//                        }
//                        host_emoji_iv.visibility = View.VISIBLE
//                        ImgUtil.loadGifOrWebp(context!!, msgBean.emojiBean!!.emoji_gif, host_emoji_iv, 1)
                    }
                }
            }
            MsgType.GIFT -> {
                if (msgBean.toUserInfo.user_id == ChatRoomManager.getRoomHostUid()) {
//                    host_charm_tv.visibility = View.VISIBLE
//                    host_charm_tv.text = (host_charm_tv.text.toString().toInt() + msgBean.giftBean!!.giftPrice * msgBean.giftBean!!.giftNum).toString()
                    ChatRoomManager.contribute += msgBean.giftBean!!.giftPrice * msgBean.giftBean!!.giftNum
                } else {
                    mic_rv.updateItemMike(msgBean)
                }
                GiftManager.getInstance().addAnim(msgBean.content, msgBean.giftBean!!.giftNum, msgBean.giftBean!!.giftUrl, msgBean.giftBean!!.giftIcon, GiftManager.GIFT_SEND_BT_POSITION, getMicPosition(msgBean.toUserInfo.user_id))

            }
            MsgType.REFRESH_MIC -> {
//                refreshHostMic(msgBean, host_seat_iv)
            }
            MsgType.CLEAR_MIKE -> {
//                if (msgBean.toUserInfo.type == 0 || msgBean.toUserInfo.type == 9) host_charm_tv.text = "0"
            }
            else -> {
            }
        }
    }


    private fun refreshHostMic(msgBean: MsgBean, view: SimpleDraweeView) {
//        if (TextUtils.isEmpty(msgBean.fromUserInfo.seat_frame)) {
//            view.visibility = View.GONE
//        } else {
//            view.visibility = View.VISIBLE
//            view.setBackgroundResource(0)
//            ImgUtil.loadWebpGif(context!!, msgBean.fromUserInfo.seat_frame, view, Int.MAX_VALUE)
//        }
    }


    private fun getMicPosition(userId: Int): Int {
        if (userId == ChatRoomManager.getRoomHostUid()) {
            return 0
        } else {
            return mic_rv.getItemLayout(userId)
        }
    }


    private fun updateMicView(msgBean: MsgBean) {
        when (msgBean.opt) {
            MsgType.DOWN_FROM_MIC -> {
                if (msgBean.fromUserInfo != null && msgBean.fromUserInfo.user_id == DataHelper.getUID()) {
                    RoomMusicManager.pausePlayMusic()
                    music_iv.visibility = View.INVISIBLE
                }
                if (ChatRoomManager.getUserRole() > 1) {
                    iv_blind_start.visibility = View.VISIBLE
                    iv_blind_reset.visibility = View.VISIBLE
                }else{
                    iv_blind_start.visibility = View.GONE
                    iv_blind_reset.visibility = View.GONE
                }
                for (i in mic_rv.data.indices) {
                    var bean = mic_rv.data[i]
                    if (msgBean.fromUserInfo.user_id == bean.user_id) {
                        val status = bean.status
                        bean = UserInfo()
                        bean.nickname = (i + 1).toString() + "号麦"
                        bean.type = i + 1
                        bean.status = if (status == 2) 2 else 0
                        mic_rv.updateItem(i, bean)
                    }
                }
            }
            MsgType.UP_TO_MIC -> {
                if (msgBean.fromUserInfo != null) {
                    if(ChatRoomManager.getUserRole() > 0 && msgBean.fromUserInfo.type == 1 &&DataHelper.getUID() == msgBean.fromUserInfo!!.user_id){
                        iv_blind_start.visibility = View.VISIBLE
                        iv_blind_reset.visibility = View.VISIBLE
                    }
                    mic_rv.clearPreInfo(msgBean.fromUserInfo)
                    mic_rv.updateItem(msgBean.fromUserInfo.type - 1, msgBean.fromUserInfo)
                }
            }
            MsgType.BAN_MIC -> {
                mic_rv.updateItem(msgBean.toUserInfo.type - 1, msgBean.toUserInfo)
            }
            MsgType.BAN_USER_MIC -> {
                mic_rv.updateItem(msgBean.toUserInfo.type - 1, msgBean.toUserInfo)
            }
            MsgType.APPLY_MIC_AGREE -> {
                mic_rv.updateItem(msgBean.toUserInfo.type - 1, msgBean.toUserInfo)
            }
            MsgType.REMOVE_MIC -> {
                for (i in mic_rv.data.indices) {
                    var bean = mic_rv.data[i]
                    if (msgBean.toUserInfo.user_id == bean.user_id) {
                        var status = bean.status
                        bean = UserInfo()
                        bean.nickname = (i + 1).toString() + "号麦"
                        bean.status = if (status == 2) 2 else 0
                        mic_rv.updateItem(i, bean)
                    }
                }
            }
            MsgType.LEVEL_CHAT -> {
                for (i in mic_rv.data.indices) {
                    var bean = mic_rv.data[i]
                    if (msgBean.fromUserInfo.user_id == bean.user_id) {
                        var status = bean.status
                        bean = UserInfo()
                        bean.nickname = (i + 1).toString() + "号麦"
                        bean.type = i + 1
                        bean.status = if (status == 2) 2 else 0
                        mic_rv.updateItem(i, bean)
                    }
                }
            }
            MsgType.MIC_CTRL, MsgType.REFRESH_MIC -> {
                mic_rv.updateItem(msgBean.fromUserInfo.type - 1, msgBean.fromUserInfo)
            }
            MsgType.LOCK_MIC -> {
                mic_rv.updateItem(msgBean.toUserInfo.type - 1, msgBean.toUserInfo)
            }
            MsgType.EMOJI -> {
                if (msgBean.emojiBean != null) {
                    mic_rv.showEmoji(msgBean.fromUserInfo.type - 1, msgBean.emojiBean)
                }
            }
            MsgType.KICK_OUT -> {
                for (i in mic_rv.data.indices) {
                    var bean = mic_rv.data[i]
                    if (msgBean.toUserInfo.user_id == bean.user_id) {
                        var status = bean.status
                        bean = UserInfo()
                        bean.nickname = (i + 1).toString() + "号麦"
                        bean.type = i + 1
                        bean.status = if (status == 2) 2 else 0
                        mic_rv.updateItem(i, bean)
                    }
                }
            }
            MsgType.CLEAR_MIKE -> {
                if (msgBean.toUserInfo.type != 0) mic_rv.clearItemMike(msgBean.toUserInfo.type - 1)
            }
            else -> {
                //do nothing
            }
        }
    }


    private fun updateBottomView4Host(msgBean: MsgBean) {
        when (msgBean.opt) {
            MsgType.APPLY_MIC -> {
                if (ChatRoomManager.getUserRole() > 0) {
//                    apply_notice.visibility = View.VISIBLE
                }
            }
            else -> {
            }
        }
    }

    private fun updateBottomView() {
        if (ChatRoomManager.getMicPosition() != 0 || ChatRoomManager.isRoomHost()) {      //状态为上麦
//            mic_go_btn.text = "下麦"
//            mic_go_btn.isSelected = true
            mic_status.visibility = View.VISIBLE
//            emoji_btn.visibility = View.VISIBLE
            if (ChatRoomManager.isMicEnable()) {
                mic_status.setImageResource(R.drawable.room_icon_bottom_open_mic)
            } else {
                mic_status.setImageResource(R.drawable.room_icon_mic_close)
            }
        } else {
            mic_status.visibility = View.GONE
//            emoji_btn.visibility = View.GONE
            when (chatMode) {
                ChatMode.OPEN -> {
//                    mic_go_btn.text = "上麦"
//                    mic_go_btn.isSelected = false
                }
                ChatMode.ONLY_INVITE -> {
                    if (ChatRoomManager.getUserRole() > 0) {
//                        mic_go_btn.text = "上麦"
//                        mic_go_btn.isSelected = false
                    } else {
//                        mic_go_btn.text = "排麦"
//                        mic_go_btn.isSelected = false
                    }
                }
            }
        }
    }

    private fun report(id: String, reportObject: Int) {

        ARouter.getInstance().build(RouterUrl.report)
                .withString("USER_ID", id)
                .withInt("REPORT_OBJECT", reportObject)
                .navigation(context!!)
//        ReportDialog(context!!, reportObject, object : ReportDialog.CallBack {
//            override fun onCancel(dialog: ReportDialog) {
//            }
//
//            override fun onSubmit(dialog: ReportDialog, type: Int) {
//                NetService.getInstance(context!!).report(id, reportObject, type, object : Callback<BaseBean>() {
//                    override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
//                        ToastUtil.suc(context!!, "举报成功")
//                        dialog.dismiss()
//                    }
//
//                    override fun onError(msg: String, throwable: Throwable, code: Int) {
//                        ToastUtil.error(context!!, msg)
//                    }
//
//                    override fun isAlive(): Boolean {
//                        return isLive
//                    }
//
//                })
//            }
//
//        }).show()
    }

//    private fun setRedPacketCountDownTimer(time: Long) {
//        redPacketCountDownTimer?.cancel()
//        isRedPacketDeadline = false
//        redPacketCountDownTimer = object : CountDownTimer(time, 1000) {
//            override fun onFinish() {
//                if (redPacketsData.isEmpty()) return
//                redPacketsData.removeAt(0)
//                redPacketCountDownTimer?.cancel()
//                red_packet_num_tv.text = "${redPacketsData.size}个"
//                if (redPacketsData.size > 0) {
//                    if (redPacketsData[0].expired_at * 1000 <= System.currentTimeMillis()) {
//                        redPacketsData.removeAt(0)
//                        red_packet_num_tv.text = "${redPacketsData.size}个"
//                    } else {
//                        setRedPacketCountDownTimer(redPacketsData[0].expired_at * 1000 - System.currentTimeMillis())
//                    }
//                } else {
//                    red_packet_layout.visibility = View.GONE
//                    red_packet_num_tv.text = "0个"
//                    isRedPacketDeadline = true
//                }
//            }
//
//            override fun onTick(millisUntilFinished: Long) {
//                red_packet_time_tv.text = "${millisUntilFinished/1000}s"
//            }
//        }.start()
//    }

    override fun onStartRoom() {
    }

    override fun onCloseRoom() {
        ChatRoomManager.unregisterLight()
        ChatRoomManager.unregisterNetDelay()
        ChatRoomManager.removeRecordVolumeObserver(this)
        ChatRoomManager.unregister()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            101 -> {
                isClearChatHistory = false
                ChatRoomManager.sendMsg(MsgType.UPDATE_CHAT_INFO, "", ChatRoomManager.getRoomId())
            }
        }
    }


    private fun initGiftPoint() {
        send_gift_btn.post {
            GiftManager.getInstance().addDefalutPoint(send_gift_btn)
        }
        mic_rv.refreshPosition()
        host_icon_iv.post {
            GiftManager.getInstance().addHostPoint(host_icon_iv)
        }
    }

    private fun requestWritePermission() {
        if (XXPermissions.isHasPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            musicDialog.show(childFragmentManager)
        } else {
            XXPermissions.with(activity!!)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request(object : OnPermission {
                        override fun noPermission(denied: MutableList<String>, quick: Boolean) {

                        }

                        override fun hasPermission(granted: MutableList<String>, isAll: Boolean) {
                        }

                    })
        }
    }
//
//    private fun requestRecordPermission() {
//        if (!XXPermissions.isHasPermission(context!!, Manifest.permission.RECORD_AUDIO)) {
//            XXPermissions.with(activity)
//                    .permission(Manifest.permission.RECORD_AUDIO)
//                    .request(object : OnPermission {
//                        override fun noPermission(denied: MutableList<String>, quick: Boolean) {
//
//                        }
//
//                        override fun hasPermission(granted: MutableList<String>, isAll: Boolean) {
//                        }
//
//                    })
//        }
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        chatHistoryJob?.cancel()
        countDownTimer?.cancel()
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun networkListen(networkBean: NetWorkBean) {
        if (networkBean.isConnect && !TextUtils.isEmpty(ChatRoomManager.getRoomId())) {
            ChatRoomManager.isReJoin = true
//            loadData()
            ChatRoomManager.reconnection(activity!!, ChatRoomManager.getRoomId(), object : ChatRoomBaseCallBack {
                override fun onSuc() {
//                    ToastUtil.error(context!!, "聊天室重新连接～")
                    loadData()
                }

                override fun onFail(msg: String) {
//                    ToastUtil.error(context!!, "聊天室重连失败～")
//                    ToastUtil.error(context!!, msg)
//                    finish()
                }
            })
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshC2CUnReadMsg(bean: UnReadNumBean) {
        if (bean.unReadNum > 0) {
            msg_notice.visibility = View.VISIBLE
        } else {
            msg_notice.visibility = View.GONE
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun reloadRoomData(bean: RefreshRoomBean) {
        loadData()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showUserCardDialog(bean: UserCardEvent) {
        showUserCard(bean.userInfo)
        roomRankDialog?.dismiss()
    }


    override fun onChange(volume: Int) {
        mic_status.setImageResource(if (volume > 0) R.drawable.room_icon_bottom_open_mic else R.drawable.room_icon_mic_close)
    }

    private fun createRulePopupWindow(content: String?): PopupWindow {
        val popupWindow = PopupWindow(context!!)
        popupWindow.setBackgroundDrawable(resources.getDrawable(R.color.transparent))
        // 设置宽度
        popupWindow.width = DensityUtil.dp2px(context!!, 240f)
        // 设置高度
        popupWindow.height = DensityUtil.dp2px(context!!, 270f)
        // 设置背景
        val view = LayoutInflater.from(context!!).inflate(R.layout.dialog_room_rlue, null, false)
        val contentTv = view.findViewById<EditText>(R.id.tv_content)
        val changeTv = view.findViewById<TextView>(R.id.tv_change)
        // 设置界面
        contentTv.setText(content)
        if (ChatRoomManager.isRoomHost() || ChatRoomManager.isRoomManager()) {
            changeTv.visibility = View.VISIBLE
            contentTv.isEnabled = true
            contentTv.setSelection(content!!.length)
        } else {
            changeTv.visibility = View.GONE
            contentTv.isEnabled = false
        }
        changeTv.setOnClickListener {
            setRoomNote(contentTv.text.toString())
        }
        popupWindow.contentView = view
        // true时界面可点
        popupWindow.isTouchable = true
        // true时PopupWindow处理返回键
        popupWindow.isFocusable = true
        // true时点击外部消失，如果touchable为false时，点击界面也消失
        popupWindow.isOutsideTouchable = true
        // dismiss监听器
        popupWindow.setOnDismissListener { }
        return popupWindow
    }

    private fun setRoomNote(note: String) {
        NetService.getInstance(context!!).setRoomNote(ChatRoomManager.getRoomId(), note, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                mHostInfo?.note = note
                ToastUtil.suc(context!!, "修改公告成功")
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

    private fun setRoomPwd(pwd: String) {
        NetService.getInstance(context!!).setRoomPwd(ChatRoomManager.getRoomId(), pwd, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                if (!TextUtils.isEmpty(pwd)) {
                    ToastUtil.suc(context!!, "房间上锁")
                } else {
                    ToastUtil.suc(context!!, "房间解锁")
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

    private fun refreshRankTop() {
        NetService.getInstance(context!!).getRoomTopThree(ChatRoomManager.getRoomId(), object : Callback<List<RankBean>>() {
            override fun onSuccess(nextPage: Int, bean: List<RankBean>, code: Int) {
                showRankTopThree(bean)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {

            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    private fun showRankTopThree(ranklist: List<RankBean>) {
        if (ranklist.isEmpty()) {
            return
        }
        if (ranklist.size == 1) {
            chat_popularity_fl.showImg1(ranklist[0].face)
            chat_popularity_fl.showImg2("")
            chat_popularity_fl.showImg3("")
            return
        }
        if (ranklist.size == 2) {
            chat_popularity_fl.showImg1(ranklist[0].face)
            chat_popularity_fl.showImg2(ranklist[1].face)
            chat_popularity_fl.showImg3("")
            return
        }
        if (ranklist.size >= 3) {
            chat_popularity_fl.showImg1(ranklist[0].face)
            chat_popularity_fl.showImg2(ranklist[1].face)
            chat_popularity_fl.showImg3(ranklist[2].face)
        }
    }

    private fun checkBilidStatus(status: Int) {
        tv_blind_status_4.isSelected = false
        tv_blind_status_3.isSelected = false
        tv_blind_status_2.isSelected = false
        tv_blind_status_1.isSelected = false
        if (status == 1 || status == 2) {
            tv_blind_status_1.isSelected = true
        }
        if (status == 3) {
            tv_blind_status_2.isSelected = true
        }
        if (status == 4) {
            tv_blind_status_3.isSelected = true
        }
        if (status >= 5) {
            tv_blind_status_4.isSelected = true
        }
    }

    override fun onUpdateHand(status: Int,xqResultBean:XqResultBean) {
        loveType.clear()
        if (status == ChatRoomUpdateViewCallBack.XIANGQING_FAIL) {
            GiftManager.getInstance().xQresult(status,xqResultBean)
        } else {
            tv_hand_num.text = "牵手" + xqResultBean.hold_hands_count + "对"
            GiftManager.getInstance().xQresult(status,xqResultBean)
        }
    }

    override fun onInVite(inviteBean: InviteBean) {
        for (bean in inviteBean.zhu_tongzhi_list) {
            if(getUID()==Integer.parseInt(bean)){
                ChatRoomManager.micCtrl4XqHost(context!!,bean)
            }
        }
        for (bean in inviteBean.ci_tongzhi_list) {
            if(getUID()==Integer.parseInt(bean)){
                ChatRoomManager.micCtrl4XqHost(context!!,bean)
            }
        }
    }

}