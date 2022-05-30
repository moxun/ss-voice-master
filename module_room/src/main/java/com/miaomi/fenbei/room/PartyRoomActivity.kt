package com.miaomi.fenbei.room//package com.miaomi.fenbei.room
//
//import android.Manifest
//import android.app.Activity
//import android.content.Intent
//import android.graphics.Color
//import android.graphics.Paint
//import android.os.CountDownTimer
//import android.text.TextUtils
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.WindowManager
//import android.view.animation.AnimationUtils
//import android.view.animation.LinearInterpolator
//import android.widget.EditText
//import android.widget.PopupWindow
//import android.widget.TextView
//import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.alibaba.android.arouter.facade.annotation.Route
//import com.alibaba.android.arouter.launcher.ARouter
//import com.facebook.drawee.view.SimpleDraweeView
//import com.hjq.permissions.OnPermission
//import com.hjq.permissions.XXPermissions
//import com.imuxuan.floatingview.FloatingView
//import com.miaomi.fenbei.voice.RoomSetPwdDialog
//import com.miaomi.fenbei.base.bean.*
//import com.miaomi.fenbei.base.bean.event.NetWorkBean
//import com.miaomi.fenbei.base.bean.event.UnReadNumBean
//import com.miaomi.fenbei.voice.UserCardEvent
//import com.miaomi.fenbei.base.config.BaseConfig
//import com.miaomi.fenbei.voice.BaseActivity
//import com.miaomi.fenbei.voice.JoinChatCallBack
//import com.miaomi.fenbei.base.core.dialog.CommonDialog
//import com.miaomi.fenbei.voice.Callback
//import com.miaomi.fenbei.voice.Data
//import com.miaomi.fenbei.voice.NetService
//import com.miaomi.fenbei.base.util.*
//import com.miaomi.fenbei.base.web.WebActivity
//import com.miaomi.fenbei.base.widget.ChatLineRecyclerView
//import com.miaomi.fenbei.base.widget.WordListSelectedView
//import com.miaomi.fenbei.gift.GiftManager
//import com.miaomi.fenbei.room.callback.*
//import com.miaomi.fenbei.voice.WordListAdapter
//import com.miaomi.fenbei.voice.MicClickListener
//import com.miaomi.fenbei.voice.RecordVolumeObservable
//import com.miaomi.fenbei.voice.WordClickListener
//import com.miaomi.fenbei.room.ui.dialog.*
//import com.miaomi.fenbei.voice.CJDialog
//import com.miaomi.fenbei.voice.GameXBDialog
//import com.miaomi.fenbei.room.ui.dialog.zs.ZSHomeDialog
//import com.miaomi.fenbei.voice.ChatMode
//import com.opensource.svgaplayer.*
//import com.opensource.svgaplayer.SVGAParser.ParseCompletion
//import kotlinx.android.synthetic.main.activity_room_bottom_layout.*
//import kotlinx.android.synthetic.main.activity_room.*
//import kotlinx.android.synthetic.main.activity_room_content.*
//import kotlinx.android.synthetic.main.activity_room_toolbar.*
//import kotlinx.coroutines.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import java.util.*F
//import kotlin.collections.ArrayList
//
//
//@Route(path = RouterUrl.partyRoom)
//class PartyRoomActivity : BaseActivity() , MicClickListener, WordClickListener, ChatRoomUpdateViewCallBack, RecordVolumeObservable.RecordVolumeObserver, ChatRoomOnNetDelayCallBack, ChatRoomOnLiveFinishCallBack {
//
//    private var isClearChatHistory = true
//    private var chatMode: Int = 0
//    private var unreadMsgPos: Int = 0
//    private var unreadMsgCount: Int = 0
//    private var redPacketCountDownTimer: CountDownTimer? = null
//    private var chatHistoryJob: Job? = null
//
//
//    private var bannerData = ArrayList<BannerBean>()
//
//    private var mHostInfo: HostInfoBean? = null
//    private var mUserHostInfo: UserInfo? = null
//    private var mUserStatus: UserStatusBean? = null
//    private var mRedPacketBaseData: RedPacketBaseBean? = null
//    private var redPacketsData: ArrayList<RedPacketBean> = ArrayList()
//    private var isRedPacketDeadline = true
//
//    private lateinit var wordListAdapter: WordListAdapter
//
//    private lateinit var inputDialog: InputDialog
//    private lateinit var musicDialog: MusicDialog
//    private lateinit var giftDialog: GiftDialog
//    private lateinit var shareDialog: ShareDialog
//    private lateinit var emojiDialog: EmojiDialog
//    private lateinit var applyListDialog: ApplyListDialog
//    private lateinit var msgListDialog: MsgListDialog
//    //    private lateinit var errorDialog: CommonDialog
//    private var roomRankDialog: RoomRankDialog? = null
//    private var voiceSettingDialog: VoiceSettingDialog? = null
//    private var chattingMoreOperateNewDialog: ChattingMoreOperateNewDialog? = null
//    private var clearMikeDialog: ClearMikeDialog? = null
//    private var operateMusicDialog: OperateMusicDialog? = null
//    private var redPacketDialog: RedPacketDialog? = null
//    private var redPacketListDialog: RedPacketListDialog? = null
//    private var wordSeletedType = WordListSelectedView.WSV_SELECTED_ALL
//
//
//
//
//    override fun getLayoutId(): Int {
//        return R.layout.activity_room
//    }
//
//    override fun initView() {
//        needFloatingView = false
//        EventBus.getDefault().register(this)
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        setBaseStatusBar(false, false)
//        ChatRoomManager.register(ChatCallback(), this, this)
//        requestRecordPermission()
//        initHostMicWaterRipple()
//        initWordList()
//        initDialog()
//        initRoomBanner()
//        initListener()
//        initGiftPoint()
//        GiftManager.getInstance().setGiftAnimView(gift_view)
//        gift_view.setOnTopNotifyClick {
//            if (it == ChatRoomManager.getRoomId()){
//                ToastUtil.suc(this@PartyRoomActivity,"已在该房间")
//            }else{
//                ChatRoomManager.joinChat(this@PartyRoomActivity,it,object : JoinChatCallBack {
//                    override fun onSuc() {
////                    finish()
//                    }
//                    override fun onFail(msg: String) {
//
//                    }
//                })
//            }
//        }
//        ChatRoomManager.registerNetDelay(this)
//        ChatRoomManager.addRecordVolumeObserver(this)
//        initPreStatus()
//        RoomLib.getChatHelper(application, true)?.apply {
//            startForeground(110, getNotification(ChatRoomManager.getRoomId())) }
//    }
//
//    private fun initPreStatus(){
//        ChatRoomManager.disPatchInRoom()
//        FloatingView.get().remove()
//        if (ChatRoomManager.isReJoin) {    //重新进入房间
//            loadData()
//            if(RoomMusicManager.isShowIcon()){
//                music_iv.visibility = View.VISIBLE
//                music_iv.clearAnimation()
//                if(RoomMusicManager.isPlaying()){
//                    val anim = AnimationUtils.loadAnimation(this@PartyRoomActivity, R.anim.anim_music_play)
//                    anim.interpolator = LinearInterpolator()
//                    music_iv.startAnimation(anim)
//                }
//            }else{
//                music_iv.visibility = View.INVISIBLE
//            }
//        }else{
//            music_iv.visibility = View.INVISIBLE
//            if (ChatRoomManager.mChatRoomInfo != null){
//                renderView(ChatRoomManager.mChatRoomInfo!!)
//            }
//        }
//    }
//
//    /**
//     * 房主麦克风水波纹效果
//     */
//    private fun initHostMicWaterRipple() {
//        mic_water.setDuration(1500)
//        mic_water.setSpeed(500)
//        mic_water.setColor(Color.rgb(255, 255, 255))
//        mic_water.setStyle(Paint.Style.FILL)
//        mic_water.setInterpolator(LinearOutSlowInInterpolator())
//        mic_water.setInitialRadius(5f)
//
//    }
//
//    /**
//     * 公屏聊天列表
//     */
//    private fun initWordList() {
//        wordListAdapter = WordListAdapter(this@PartyRoomActivity, this)
//        val layoutManager = LinearLayoutManager(this@PartyRoomActivity, LinearLayoutManager.VERTICAL, false)
//        layoutManager.stackFromEnd = true
//        word_list.layoutManager = layoutManager
//        word_list.adapter = wordListAdapter
//        word_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (unreadMsgPos != 0 && (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() >= unreadMsgPos) {
//                    unreadMsgPos = 0
//                    unreadMsgCount = 0
//                    new_msg_tv.visibility = View.GONE
//                }
//            }
//        })
//
//        word_list.setOnTransListener(object: ChatLineRecyclerView.OnTransListener{
//
//            override fun toTop() {
//                word_selet_view.hide()
//            }
//
//            override fun toBottom() {
//                word_selet_view.show()
//            }
//
//        })
//    }
//
//    /**
//     * dialog初始化
//     */
//    private fun initDialog() {
//        inputDialog = InputDialog()
//        musicDialog = MusicDialog()
//        giftDialog = GiftDialog()
//        shareDialog = ShareDialog()
//        emojiDialog = EmojiDialog()
//        applyListDialog = ApplyListDialog()
//        msgListDialog = MsgListDialog()
//    }
//
//    private fun initRoomBanner() {
//        banner.setOnBannerListener {
//            when (it.mold) {
////                BaseConfig.BANNER_MOLD_GAME_XX -> {
////                    val dialog = GameXXDialog(this@PartyRoomActivity)
////                    dialog.show()
////                }
//                BaseConfig.BANNER_MOLD_SMASH_EGG ->{
//                    CJDialog().show(supportFragmentManager)
//                }
//                BaseConfig.BANNER_MOLD_GAME_CB -> {
//                    val dialog = GameXBDialog()
//                    dialog.show(supportFragmentManager)
//                }
//
//                BaseConfig.BANNER_MOLD_GAME_ZS ->{
//                    ZSHomeDialog().show(supportFragmentManager)
//                }
//                BaseConfig.BANNER_MOLD_WEB, BaseConfig.BANNER_MOLD_WEEK_STAR -> {
////                    GameDialog(it.url).show(supportFragmentManager,"")
//                    WebActivity.start(this, it.url, it.title)
//
//                }
//                else -> {
//                    ToastUtil.suc(this@PartyRoomActivity,"请升级版本查看")
//                }
//            }
//        }
//    }
//
////    private fun initGuardHeaderList() {
////        guardHeaders = ArrayList()
////        guardHeaderAdapter = GuardHeaderAdapter(guardHeaders)
////        rv_contribute_users.adapter = guardHeaderAdapter
////        guardHeaderAdapter.setOnItemClickListener { _, _, _ ->
////            roomRankDialog = RoomRankDialog.getInstance(TAB_TITLE_TYPE_BROADCASTING_STATION_CONTRIBUTION,RoomRankFragment.RANK_TYPE_RADIO_GUARD)
////            roomRankDialog?.show(supportFragmentManager)
////        }
////        rv_contribute_users.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
////    }
//
//    /**
//     * 控件监听
//     */
//    private fun initListener() {
//
//        word_selet_view.setOnSeletedListener {
//            wordSeletedType = it
//            wordListAdapter.setData(ChatRoomManager.getWordList(),wordSeletedType)
//            word_list.scrollToPosition(wordListAdapter.itemCount - 1)
//        }
//
//        tv_reload.setOnClickListener {
//            loadData()
//        }
//
//        tv_room_gift_history.setOnClickListener {
//            val dialog = RoomGiftHistoryDialog()
//            dialog.show(supportFragmentManager)
//        }
//        red_packet_layout.setOnClickListener {
//            openRedPack()
//        }
//
//
//
////        red_packet_btn.setOnClickListener {
////            val dialog = SendRedPacketDialog(this@PartyRoomActivity,mRedPacketBaseData)
////            dialog.show()
////        }
////            ARouter.getInstance().build(RouterUrl.sendRedPacket)
////                    .withParcelable("data", mRedPacketBaseData).navigation() }
//
//
//        user_layout.setOnClickListener {
//            if (DoubleClickUtil.isFastDoubleClick()){
//                val dialog = RoomUsersDialog()
//                dialog.setItemClickListener(object : RoomUsersDialog.OnItemClickListener{
//                    override fun onItemClick(userInfo: UserInfo) {
//                        showUserCard(userInfo)
//                    }
//
//                })
//                dialog.show(supportFragmentManager)
//            }
//        }
//
//        tv_open_mortal.setOnClickListener {
//            openMortal()
//        }
//
//
//        mic_rv.addItemClickListener(this)
//
//        remote_voice_ctrl_btn.setOnCheckedChangeListener { _, isChecked ->
//            ChatRoomManager.remoteVoiceCtrl(!isChecked)
//        }
//        follow_btn.setOnClickListener { followChat() }
//
//
//        iv_finish.setOnClickListener {
//            onBackPressed()
//        }
//
//
//        notice_tv.setOnClickListener {
//
//            createRulePopupWindow(mHostInfo?.note).showAsDropDown(notice_tv)
//        }
//
//
//        more_btn.setOnClickListener { showChattingMoreOperateDialog() }
//
//
//
//        word_chat_btn.setOnClickListener {
//            NetService.getInstance(this@PartyRoomActivity).sendText(ChatRoomManager.getRoomId(), "", object : Callback<TextStatusBean>() {
//                override fun onSuccess(nextPage: Int, bean: TextStatusBean, code: Int) {
//                    if (!isAlive()){
//                        return
//                    }
//                    if (bean.speak == 0) {
//                        inputDialog.show(supportFragmentManager)
//                    } else {
//                        ToastUtil.error(this@PartyRoomActivity, "禁言剩余时间" + bean.time)
//                    }
//                }
//
//                override fun onError(msg: String, throwable: Throwable, code: Int) {
//                    ToastUtil.error(this@PartyRoomActivity, msg)
//                }
//
//                override fun isAlive(): Boolean {
//                    return isLive
//                }
//            })
//        }
//
//        emoji_btn.setOnClickListener { emojiDialog.show(supportFragmentManager) }
//
//
//        msg_btn.setOnClickListener {
//            msgListDialog.show(supportFragmentManager)
//            msg_notice.visibility = View.GONE
//        }
//
//
//        mic_go_btn.setOnClickListener {
//            if (ChatRoomManager.getMicPosition() == 0) {
//                when (chatMode) {
//                    ChatMode.OPEN -> {        //上麦模式-自由上麦
//                        if (mic_rv.hasEmpty()) {
//                            ChatRoomManager.micCtrl(this@PartyRoomActivity, -1, false)
//                        } else {
//                            val upDialog = CommonDialog(this@PartyRoomActivity)
//                            upDialog.setTitle("友情提示")
//                            upDialog.setContent("当前麦位已满，确定申请上麦？")
//                            upDialog.setLeftBt("取消") {
//                                upDialog.dismiss()
//                            }
//                            upDialog.setRightBt("上麦") {
//                                ChatRoomManager.applyMic(this@PartyRoomActivity)
//                                upDialog.dismiss()
//                            }
//                            upDialog.show()
//                        }
//                    }
//                    ChatMode.ONLY_INVITE -> {    //仅邀请上麦
//                        if (ChatRoomManager.getUserRole() > 0) {
//                            if (mic_rv.hasEmpty()) {
//                                ChatRoomManager.micCtrl(this@PartyRoomActivity, -1, false)
//                            } else {
//                                val upDialog = CommonDialog(this@PartyRoomActivity)
//                                upDialog.setTitle("友情提示")
//                                upDialog.setContent("当前麦位已满，确定申请上麦？")
//                                upDialog.setLeftBt("取消") {
//                                    upDialog.dismiss()
//                                }
//                                upDialog.setRightBt("上麦") {
//                                    ChatRoomManager.applyMic(this@PartyRoomActivity)
//                                    upDialog.dismiss()
//                                }
//                                upDialog.show()
//                            }
//                        } else {
//                            val dialog = CommonDialog(this@PartyRoomActivity)
//                            dialog.setTitle("友情提示")
//                            dialog.setContent("当前房间仅邀请上麦，是否申请上麦")
//                            dialog.setLeftBt("取消") {
//                                dialog.dismiss()
//                            }
//                            dialog.setRightBt("上麦") {
//                                ChatRoomManager.applyMic(this@PartyRoomActivity)
//                                dialog.dismiss()
//                            }
//                            dialog.show()
//                        }
//                    }
//                }
//            } else {
//                val downDialog = CommonDialog(this@PartyRoomActivity)
//                downDialog.setTitle("友情提示")
//                downDialog.setContent("是否下麦")
//                downDialog.setLeftBt("取消") {
//                    downDialog.dismiss()
//                }
//                downDialog.setRightBt("下麦") {
//                    ChatRoomManager.micCtrl(this@PartyRoomActivity, 0, false)
//                    downDialog.dismiss()
//                }
//                downDialog.show()
//            }
//        }
//
//
////        playSvgaGiftAnim(send_gift_btn,"room_gift.svga")
//
//
//        send_gift_btn.setOnClickListener {
//            saveMicUser()
//            GiftManager.getInstance().isInitGift(this@PartyRoomActivity) {
//                if (mUserHostInfo != null){
//                    giftDialog.show(mUserHostInfo!!.nickname,
//                            mUserHostInfo!!.user_id,mUserHostInfo!!.face,false,mUserHostInfo!!.mystery,supportFragmentManager)
//                }
//            }
//        }
//
//
//        mic_status.setOnClickListener { ChatRoomManager.openMic(this@PartyRoomActivity) }
//
//        new_msg_tv.setOnClickListener { word_list.scrollToPosition(wordListAdapter.itemCount - 1) }
//
//        chat_popularity_fl.setOnClickListener {
//            roomRankDialog = RoomRankDialog()
//            roomRankDialog?.show(supportFragmentManager)
//        }
//
//        music_iv.setOnClickListener{
//            operateMusicDialog = OperateMusicDialog(this@PartyRoomActivity)
//            operateMusicDialog?.setOnOperateMusic(object : OperateMusicDialog.OnOperateMusic{
//                override fun onClose() {
//                    music_iv.visibility = View.GONE
//                }
//
//                override fun onShowList() {
//                    musicDialog.show(supportFragmentManager)
//                }
//            })
//            operateMusicDialog?.show()
//        }
//
//    }
//
//    private fun openRedPack(){
//        if (redPacketsData.size > 1) {
//            redPacketListDialog = RedPacketListDialog(this@PartyRoomActivity)
//            redPacketListDialog?.setOnGrabRedPacketItemClickListener(object : RedPacketListDialog.OnGrabRedPacketItemClickListener {
//                override fun onGrabRedPacketItemClick(position: Int) {
//                    getRedPacketBaseData()
//                }
//            })
//            redPacketListDialog?.show()
//        } else {
//            if (redPacketsData.isNotEmpty()) {
//                redPacketDialog = RedPacketDialog(this@PartyRoomActivity, redPacketsData[0], false,
//                        object : RedPacketDialog.OnDismissListener {
//                            override fun dismiss() {
//                                getRedPacketBaseData()
//                            }
//                        })
//                redPacketDialog?.setOnSendRedPacketClickListener(object : RedPacketDialog.OnSendRedPacketClickListener {
//                    override fun onSendRedPacketClick() {
////                            ARouter.getInstance().build(RouterUrl.sendRedPacket)
////                                    .withParcelable("data", mRedPacketBaseData).navigation()
//                        val dialog = SendRedPacketDialog(this@PartyRoomActivity,mRedPacketBaseData)
//                        dialog.show()
//                    }
//                })
//                redPacketDialog?.show()
//            }
//        }
//    }
//    /**
//     * 房间操作弹窗
//     */
//    private fun showChattingMoreOperateDialog() {
//        chattingMoreOperateNewDialog = ChattingMoreOperateNewDialog.init(false)
//        chattingMoreOperateNewDialog?.setOnOperateClickListener(object : ChattingMoreOperateNewDialog.OnOperateClickListener {
//            override fun onVoiceClick() {
//                ChatRoomManager.remoteVoiceCtrl(!ChatRoomManager.isCloseVoice)
//            }
//
//            override fun onRoomReport() {
//                report(ChatRoomManager.getRoomId(), 0)
//            }
//
//            override fun onRoomSetting() {
//                ARouter.getInstance().build(RouterUrl.roomSetting)
//                        .withString("mChatId", ChatRoomManager.getRoomId())
//                        .withInt("type", ChatRoomManager.mRoomType).navigation(this@PartyRoomActivity, 101)
//            }
//
//            override fun onMusicClick() {
//                requestWritePermission()
//            }
//
//            override fun onVoiceSettingClick() {
//                voiceSettingDialog = VoiceSettingDialog()
//                voiceSettingDialog?.show(supportFragmentManager)
//            }
//
//            override fun onClearChat() {
//                clearChatList()
//            }
//
//            override fun onCloseOrOpenChat(type: Int) {
//                closeChat(type)
//            }
//
//            override fun onCloseOrOpenSpecial() {
//                GiftManager.getInstance().changeAnimstatus()
//            }
//
//            override fun onShare() {
//                shareDialog.show(supportFragmentManager)
////                DataHelper.saveFullChatOpen(!DataHelper.isFullChatOpen())
//            }
//
//            override fun dismiss() {
//            }
//
//            override fun onRoomLocked() {
//                val verifyPwdDialog = RoomSetPwdDialog(this@PartyRoomActivity,ChatRoomManager.getRoomId())
//                verifyPwdDialog.setOnClickListener(object : RoomSetPwdDialog.OnClickListener{
//                    override fun onRefuseClick() {
//                        verifyPwdDialog.dismiss()
//                    }
//
//                    override fun onAgreeClick(str: String) {
//                        setRoomPwd(str)
//                        verifyPwdDialog.dismiss()
//                    }
//
//                })
//                verifyPwdDialog.show()
//                verifyPwdDialog.setContent("设置房间密码（4位数）")
//            }
//
//            override fun onRoomManager() {
//                ARouter.getInstance().build(RouterUrl.roomManager)
//                        .withString("chat_id", ChatRoomManager.getRoomId())
//                        .navigation(this@PartyRoomActivity)
//            }
//
//            override fun onSendRedPack() {
//                val dialog = SendRedPacketDialog(this@PartyRoomActivity,mRedPacketBaseData)
//                dialog.show()
//            }
//
//            override fun onRoomClose() {
//                superCloseRoom()
//            }
//
//            override fun onClearMike() {
//                clearMikeDialog = ClearMikeDialog.newInstance(mic_rv.data.size)
//                clearMikeDialog?.setOnOperateClickListener(object : ClearMikeDialog.OnClearMikeClickListener {
//                    override fun clearMikeClick(position: Int) {
//                        val upDialog = CommonDialog(this@PartyRoomActivity)
//                        upDialog.setTitle("友情提示")
//                        upDialog.setContent("是否清除麦序魅力值？")
//                        upDialog.setLeftBt("取消") {
//                            upDialog.dismiss()
//                        }
//                        upDialog.setRightBt("清除") {
//                            ChatRoomManager.clearMike(this@PartyRoomActivity, position)
//                            upDialog.dismiss()
//                        }
//                        upDialog.show()
//                    }
//                })
//                clearMikeDialog?.show(supportFragmentManager)
//            }
//
//            override fun onLiveClose() {
//                val commonDialog = CommonDialog(this@PartyRoomActivity)
//                commonDialog.setTitle("友情提示")
//                commonDialog.setContent("是否关闭房间？")
//                commonDialog.setLeftBt("取消") {
//                    commonDialog.dismiss()
//                }
//                commonDialog.setRightBt("确定") {
////                    ChatRoomManager.closeLiveForManager(this@PartyRoomActivity, mHostInfo!!.icon!!)
//                    commonDialog.dismiss()
//                }
//                commonDialog.show()
//            }
//
//            override fun onApplyList() {
//                applyListDialog.show(supportFragmentManager)
//            }
//
//            override fun onCloseOrOpenMic() {
//                ChatRoomManager.openMic(this@PartyRoomActivity)
//            }
//
//            override fun onEmoji() {
//                emojiDialog.show(supportFragmentManager)
//            }
//        })
//        chattingMoreOperateNewDialog?.show(supportFragmentManager)
//    }
//
////    override fun onBackPressed() {
////        super.onBackPressed()
////        ChatRoomManager.disPatchMinWindow()
////    }
//
//    /**
//     * 保存麦序
//     */
//    fun saveMicUser(){
//        if (mUserHostInfo == null){
//            return
//        }
//        val micList = ArrayList<GiftInfoBean.ListBean>()
//        val hostInfo = GiftInfoBean.ListBean()
//        hostInfo.nickname = mUserHostInfo?.nickname
//        hostInfo.face = mUserHostInfo?.face
//        hostInfo.type = 0
//        hostInfo.user_id = mUserHostInfo!!.user_id
//        hostInfo.rank_id = mUserHostInfo!!.rank_id
//        micList.add(hostInfo)
//        for (info in mic_rv.data) {
//            if (info.user_id > 0 ){
//                val bean = GiftInfoBean.ListBean()
//                bean.nickname = info.nickname
//                bean.user_id = info.user_id
//                bean.face = info.face
//                bean.type = info.type
//                bean.mystery = info.mystery
//                bean.rank_id = info.rank_id
//                micList.add(bean)
//            }
//        }
//        GiftManager.getInstance().saveMicUserList(micList)
//    }
//
//
//
//    fun loadData() {
//        NetService.getInstance(this@PartyRoomActivity).getChatRoomInfo(ChatRoomManager.getRoomId(), object : Callback<ChatRoomInfo>() {
//            override fun onSuccess(nextPage: Int, bean: ChatRoomInfo, code: Int) {
//                ll_load_error.visibility = View.GONE
//                renderView(bean)
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                if (code == Data.CODE_ILLEGAL_REQUEST) {
//                    return
//                }
//                ll_load_error.visibility = View.VISIBLE
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//        })
//    }
//
//    private fun getMsgHistoryList() {
////        var msgBean: MsgBean? = null
////        if (isClearChatHistory && mUserStatus?.super_manager != 1) {
////            val localUser = DataHelper.getUserInfo()
////            msgBean = MsgBean(MsgType.JOIN_CHAT, "",
////                    ChatRoomManager.getRoomId(), "", "", 0, 0, 0, null,null, null, toUserInfo = UserInfo(),
////                    fromUserInfo = UserInfo(nickname = localUser!!.nickname, user_id = localUser.user_id,
////                            wealth_level = localUser.wealth_level, charm_level = localUser.charm_level,
////                            seat_frame = localUser.seat_frame, effects = localUser.effects, face = localUser.face, user_role = ChatRoomManager.getUserRole(),
////                            head_img = localUser.head_img, body_img = localUser.body_img
////                            , first_sign = mUserStatus!!.first_sign, good_number = localUser.good_number, good_number_state = localUser.good_number_state
////                            ,activity_pic = localUser.activity_pic, mystery = localUser.mystery,seat = localUser.seat,medal = localUser.medal))
////        }
//        NetService.getInstance(this@PartyRoomActivity).getMsgHisroyList(ChatRoomManager.getRoomId(), object : Callback<List<MsgHistoryBean>>() {
//            override fun onSuccess(nextPage: Int, bean: List<MsgHistoryBean>, code: Int) {
//                chatHistoryJob = GlobalScope.launch {
//                    if (!ChatRoomManager.isRoomHost() && isClearChatHistory) {
//                        withContext(Dispatchers.Main) {
//                            ChatRoomManager.addWordList(ChatRoomManager.getNoticeMsgBean(mHostInfo?.note!!))
//                            wordListAdapter.addItem(ChatRoomManager.getNoticeMsgBean(mHostInfo?.note!!))
////                            wordListAdapter.notifyItemInserted(ChatRoomManager.getWordList().size - 1)
//                            word_list.scrollToPosition(wordListAdapter.itemCount - 1)
//                        }
//                        delay(100)
//                    }
//                    bean.reversed().forEach {
//                        withContext(Dispatchers.Main) {
//                            val msgBean = MsgBean(MsgType.TEXT_MSG, it.content,
//                                    it.room_id.toString(), "", "", 0, 0, 0, null,null, null, toUserInfo = UserInfo(),
//                                    fromUserInfo = UserInfo(nickname = it.nickname, user_id = it.user_id,
//                                            wealth_level = it.wealth_level, charm_level = it.charm_level,
//                                            seat_frame = it.seat_frame, effects = 0, face = it.face, user_role = it.user_role, is_guard = it.is_guard,
//                                            first_sign = mUserStatus!!.first_sign, good_number = it.good_number, good_number_state = it.good_number_state
//                                            ,activity_pic = it.activity_pic, mystery = it.mystery,medal = it.medal,rank_id = it.rank_id))
//                            ChatRoomManager.addWordList(msgBean)
//                            wordListAdapter.addItem(msgBean)
//                            word_list.scrollToPosition(wordListAdapter.itemCount - 1)
//                        }
//                        delay(200)
//                    }
//                    if (isClearChatHistory && mUserStatus?.super_manager != 1) {
//                        withContext(Dispatchers.Main) {
//                            ChatRoomManager.sendMsg(MsgType.JOIN_CHAT, "进入房间", ChatRoomManager.getRoomId())
//                        }
//
//                    }
//                }
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//        })
//    }
//
//    private fun getRoomBanner() {
//        NetService.getInstance(this@PartyRoomActivity).getRadioBanner(getString(R.string.banner_type), "2", object : Callback<List<BannerBean>>() {
//            override fun onSuccess(nextPage: Int, bean: List<BannerBean>, code: Int) {
//                bannerData.clear()
//                if (bean.isNotEmpty()) {
//                    bannerData.addAll(bean)
////                    var data = BannerBean()
////                    bannerData.forEach {
////                        if (it.mold == BaseConfig.BANNER_MOLD_FIRST_RECHARGE && mUserStatus!!.is_first == 0) {
////                            data = it
////                        }
////                    }
////                    bannerData.remove(data)
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
//                }
//                banner?.update(bannerData)
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                ToastUtil.error(this@PartyRoomActivity, msg)
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//        })
//    }
//
//    private fun followChat() {
//        NetService.getInstance(this@PartyRoomActivity).followChat(ChatRoomManager.getRoomId(), object : Callback<FollowResultBean>() {
//            override fun onSuccess(nextPage: Int, bean: FollowResultBean, code: Int) {
//                follow_btn.visibility = View.GONE
//                ChatRoomManager.sendMsg(MsgType.ROOM_COLLECT, "", ChatRoomManager.getRoomId())
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                ToastUtil.error(this@PartyRoomActivity, msg)
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
//
//    /**
//     * 获取红包配置数据
//     */
//    private fun getRedPacketBaseData() {
//        NetService.getInstance(this@PartyRoomActivity).getRedPacketBaseData(ChatRoomManager.getRoomId(), object: Callback<RedPacketBaseBean>() {
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
//
//
//    private fun clearChatList() {
//        if (ChatRoomManager.getUserRole() > 0) {
//            ChatRoomManager.sendMsg(MsgType.CLEAR_CHAT, "${DataHelper.getUserInfo()!!.nickname}清理了公屏聊天", ChatRoomManager.getRoomId())
//        } else {
//            ToastUtil.suc(this@PartyRoomActivity, "您没有该权限哦")
//        }
//    }
//
//    private fun closeChat(type: Int) {
//        ChatRoomManager.closeChat(type)
//    }
//
//    private fun superCloseRoom() {
//        NetService.getInstance(this@PartyRoomActivity).superRoomClose(ChatRoomManager.getRoomId(), object : Callback<BaseBean>() {
//            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
//
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//
//            }
//
//            override fun isAlive(): Boolean {
//                return isAlive()
//            }
//        })
//    }
//
//    //渲染页面
//    private fun renderView(bean: ChatRoomInfo) {
////        shareDialog.share_desc = bean.share_desc
////        shareDialog.share_title = bean.share_title
////        shareDialog.share_icon = bean.share_icon
////        shareDialog.share_url = bean.share_url
//        mHostInfo = bean.host_info
//        mUserHostInfo = bean.user_host
//        ChatRoomManager.mUserHostInfo = bean.user_host
//        mUserStatus = bean.user_status
//        ChatRoomManager.mRoomType = bean.host_info.room_type
//        ImgUtil.loadWebpGif(this@PartyRoomActivity, bean.host_info.backdrop, chat_bg, R.drawable.common_bg_room_default)
//        GiftManager.getInstance().accountBalance = bean.user_status.balance
//        ChatRoomManager.mCloseChat = bean.user_status.screen
//        ChatRoomManager.setRoomHostId(bean.user_host.user_id)
//        ChatRoomManager.setStatusBean(bean.user_status)
//        ChatRoomManager.isFollow = bean.host_info.is_follow
//        if (!ChatRoomManager.isReJoin) {    //第一次进入房间
//            new_msg_tv.visibility = View.GONE
//            getMsgHistoryList()
//            if (isClearChatHistory) ChatRoomManager.setWordList(ArrayList())
//        }
//        getRoomBanner()
//        getRedPacketBaseData()
//        loadCommonRoomView(bean)
//        updateBottomView()
//        wordListAdapter.setData(ChatRoomManager.getWordList())
//        ChatRoomManager.isReJoin = true
//        isClearChatHistory = true
//        refreshRankTop()
//    }
//
//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        initPreStatus()
//    }
//
//    /**
//     * 加载普通房间view（个人/工会）
//     */
//    private fun loadCommonRoomView(bean: ChatRoomInfo) {
//        input_layout.visibility = View.VISIBLE
//        chatting_title_layout.visibility = View.VISIBLE
////        common_top_layout.visibility = View.VISIBLE
//
//        mic_rv.data = bean.mic_list  //更新mic区域
//        EventBus.getDefault().post(MicNumBean(mic_rv.data.size))
//        chatMode = bean.host_info.way!!
//        ChatRoomManager.mRoomGoodNumber = bean.host_info.room_good_number!!
//        if (bean.user_status.type != 0 && DataHelper.getUID() == bean.mic_list!![bean.user_status.type - 1].user_id) {
//            ChatRoomManager.isVoiceLive = true
//        } else {
//            ChatRoomManager.setMicPosition(0)
//        }
//        delay_tv.visibility = View.GONE
//        bean.mic_list!!.forEach {
//            if (it.user_id > 0 && it.user_id != DataHelper.getUID()) {
//                delay_tv.visibility = View.VISIBLE
//            }
//        }
//        if (bean.user_host.mike == 0){
//            host_charm_tv.visibility = View.INVISIBLE
//        }else{
//            host_charm_tv.visibility = View.VISIBLE
//        }
//        host_charm_tv.text = bean.user_host.mike.toString()
//        if (ChatRoomManager.isRoomHost()) {
//            follow_btn.visibility = View.GONE
//            mic_go_btn.visibility = View.GONE
//        } else {
//            follow_btn.visibility = View.VISIBLE
//            mic_go_btn.visibility = View.VISIBLE
//            if (bean.host_info.is_follow == 1){
//                follow_btn.visibility = View.GONE
//            }
//        }
//        if (bean.host_info.room_good_number == 0){
//            iv_liang.show(ChatRoomManager.getRoomId(),false)
//        }else{
//            iv_liang.show(bean.host_info.room_good_number.toString(),true)
//        }
////        room_id.text = "ID:" + if (bean.host_info.room_good_number == 0) ChatRoomManager.getRoomId() else bean.host_info.room_good_number
////        room_id.setCompoundDrawablesWithIntrinsicBounds(if (bean.host_info.room_good_number == 0) 0 else R.drawable.common_user_icon_liang, 0, 0, 0)
//        main_tv.text = bean.host_info.name
//        ImgUtil.loadCircleImg(this@PartyRoomActivity, bean.user_host.face, host_icon_iv)
//        if (TextUtils.isEmpty(bean.user_host.seat_frame)) {
//            host_seat_iv.setBackgroundResource(0)
//        } else {
////            host_seat_iv.visibility = View.VISIBLE
//            host_seat_iv.setBackgroundResource(0)
//            ImgUtil.loadGifOrWebp(this@PartyRoomActivity, bean.user_host.seat_frame, host_seat_iv, Int.MAX_VALUE)
//        }
////        host_name_tv.text = bean.user_host.nickname
//        chat_online_num.text = "热度："+bean.host_info.popularity_int.toString()
//
//        host_icon_iv.setOnClickListener {
//            bean.user_host.user_role = 2
//            showUserCard(bean.user_host)
//        }
//        mic_water.visibility = if (!ChatRoomManager.isReJoin) View.INVISIBLE else View.VISIBLE
//    }
//
//
//    private fun updateWord(msgBean: MsgBean) {
//        wordListAdapter.addItem(msgBean,wordSeletedType)
//        if (isVisBottom()) {
//            new_msg_tv.visibility = View.GONE
//            unreadMsgCount = 0
//            word_list.scrollToPosition(wordListAdapter.itemCount - 1)
//        } else {
//            unreadMsgCount ++
//            new_msg_tv.visibility = View.VISIBLE
//            new_msg_tv.text = if (unreadMsgCount >= 99) "99+条新消息" else "${unreadMsgCount}条新消息"
//        }
//    }
//
//    // 消息列表是否在底部
//    private fun isVisBottom(): Boolean {
//        var layoutManager = word_list.layoutManager as LinearLayoutManager
//        var visibleItemCount = layoutManager.childCount
//        var totalItemCount = layoutManager.itemCount
//        var lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
//        var state = word_list.scrollState
//        if (visibleItemCount >= totalItemCount) {
//            unreadMsgPos = 0
//            return true
//        }
//        if (lastVisibleItemPosition >= totalItemCount - 4 && state == RecyclerView.SCROLL_STATE_IDLE) {
//            unreadMsgPos = 0
//            return true
//        }
//        if (unreadMsgPos == 0) unreadMsgPos = totalItemCount - 1
//        return false
//    }
//
//    override fun onLiveFinish() {
//        if (!ChatRoomManager.isSelfCloseLive){
//            ToastUtil.suc(this,"房间已关闭")
//        }
////            RoomClosedDialog(this@PartyRoomActivity, mHostInfo!!.icon!!).show()
//        ChatRoomManager.isSelfCloseLive = false
//    }
//
//    override fun onUserItemClick(view: View, userInfo: UserInfo) {
//        showUserCard(userInfo)
//    }
//
//    override fun onWordItemClick(type: MsgType, roomId: String) {
//        if (type == MsgType.WINNING_MSG) {
//            CJDialog().show(supportFragmentManager)
//        }
//        if (type == MsgType.RED_PACKET_MSG) {
//            openRedPack()
//        }
//    }
//
//    override fun onMicItemClick(view: View, userInfo: UserInfo, position: Int) {
//
//        if (userInfo.user_id <= 0) {//当前麦无人
//            when {
//                ChatRoomManager.isRoomHost() -> {
//                    MicOperateDialog(userInfo).setOnMicOperateClickListener(object : MicOperateDialog.OnMicOperateClickListener {
//                        override fun onMicOperateClick(operateType: Int) {
//                            when (operateType) {
//                                MicOperateDialog.MIC_OPERATE_LOCK -> {
//                                    ChatRoomManager.lockMic(this@PartyRoomActivity, if (userInfo.user_id == -1) {
//                                        0
//                                    } else {
//                                        -1
//                                    }, userInfo, position + 1)
//                                }
//                                MicOperateDialog.MIC_OPERATE_BAN -> {
//                                    ChatRoomManager.banMic(this@PartyRoomActivity, position + 1, userInfo)
//                                }
//                                MicOperateDialog.MIC_OPERATE_CLEAR -> {
//                                    val upDialog = CommonDialog(this@PartyRoomActivity)
//                                    upDialog.setTitle("友情提示")
//                                    upDialog.setContent("是否清除麦序魅力值？")
//                                    upDialog.setLeftBt("取消") {
//                                        upDialog.dismiss()
//                                    }
//                                    upDialog.setRightBt("清除") {
//                                        ChatRoomManager.clearMike(this@PartyRoomActivity, position + 1)
//                                        upDialog.dismiss()
//                                    }
//                                    upDialog.show()
//                                }
//                            }
//                        }
//                    }).show(supportFragmentManager)
//                }
//                ChatRoomManager.isRoomManager() -> {
//                    MicOperateDialog(userInfo).setOnMicOperateClickListener(object : MicOperateDialog.OnMicOperateClickListener {
//                        override fun onMicOperateClick(operateType: Int) {
//                            when (operateType) {
//                                MicOperateDialog.MIC_OPERATE_LOCK -> {
//                                    ChatRoomManager.lockMic(this@PartyRoomActivity, if (userInfo.user_id == -1) {
//                                        0
//                                    } else {
//                                        -1
//                                    }, userInfo, position + 1)
//                                }
//                                MicOperateDialog.MIC_OPERATE_BAN -> {
//                                    ChatRoomManager.banMic(this@PartyRoomActivity, position + 1, userInfo)
//                                }
//                                MicOperateDialog.MIC_OPERATE_UP -> upToMic(userInfo,position)
//                                MicOperateDialog.MIC_OPERATE_CLEAR -> {
//                                    val upDialog = CommonDialog(this@PartyRoomActivity)
//                                    upDialog.setTitle("友情提示")
//                                    upDialog.setContent("是否清除麦序魅力值？")
//                                    upDialog.setLeftBt("取消") {
//                                        upDialog.dismiss()
//                                    }
//                                    upDialog.setRightBt("清除") {
//                                        ChatRoomManager.clearMike(this@PartyRoomActivity, position + 1)
//                                        upDialog.dismiss()
//                                    }
//                                    upDialog.show()
//                                }
//                            }
//                        }
//                    }).show(supportFragmentManager)
//                }
//                else -> {
//                    if (userInfo.user_id == -1) {
//                        ToastUtil.error(this@PartyRoomActivity, "麦已上锁，无法上麦")
//                        return
//                    }
//                    upToMic(userInfo,position)
//                }
//            }
//        } else {
//            showUserCard(userInfo)
//        }
//    }
//
//    override fun onUpdateDelay(delay: Int) {
//        this@PartyRoomActivity.runOnUiThread {
//            delay_tv?.apply {
//                visibility = View.VISIBLE
//                when (delay) {
//                    in 1..100 -> {
////                        text = "流畅"
//                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.room_green_circle, 0, 0, 0)
//                    }
//                    in 101..200 -> {
////                        text = "拥挤"
//                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.room_orange_circle, 0, 0, 0)
//                    }
//                    else -> {
////                        text = "网络不稳定"
//                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.room_red_circle, 0, 0, 0)
//                    }
//                }
//            }
//        }
//    }
//
//
//    private fun upToMic(userInfo: UserInfo, position: Int){
//        goToMic(position)
//    }
//
//    private fun goToMic(position: Int) {
//        if (ChatRoomManager.getMicPosition() == 0) {    //如果未在麦上
//            when (chatMode) {
//                ChatMode.OPEN -> {        //上麦模式-自由上麦
//                    ChatRoomManager.micCtrl(this@PartyRoomActivity, position + 1, false)
//                }
//                ChatMode.ONLY_INVITE -> {    //仅邀请上麦
//                    if (ChatRoomManager.getUserRole() > 0) {
//                        ChatRoomManager.micCtrl(this@PartyRoomActivity, position + 1, false)
//                    } else {
//                        val dialog = CommonDialog(this@PartyRoomActivity)
//                        dialog.setTitle("申请上麦")
//                        dialog.setContent("当前房间仅邀请上麦，是否申请上麦")
//                        dialog.setLeftBt("取消") {
//                            dialog.dismiss()
//                        }
//                        dialog.setRightBt("确定") {
//                            ChatRoomManager.applyMic(this@PartyRoomActivity)
//                            dialog.dismiss()
//                        }
//                        dialog.show()
//                    }
//                }
//            }
//        } else { //如果已上麦，则切换
//            ChatRoomManager.micCtrl(this@PartyRoomActivity, position + 1, true)
//        }
//    }
//
//    private fun showUserCard(userInfo: UserInfo) {
//        if (DoubleClickUtil.isFastDoubleClick()){
//            UserCardDialog(ChatRoomManager.getRoomId(), userInfo, object : UserCardDialog.ClickListener {
//                override fun joinNewRoom(roomId: Int, hostId: Int) {
//                    ChatRoomManager.joinChat(this@PartyRoomActivity, roomId.toString(), object : JoinChatCallBack {
//                        override fun onSuc() {
////                        loadData()
//                        }
//
//                        override fun onFail(msg: String) {
//                            ToastUtil.error(this@PartyRoomActivity, msg)
//                        }
//                    })
//                }
//
//                override fun report(userInfo: UserInfo) {
//                    report(userInfo.user_id.toString(), 1)
//                }
//
//                override fun sendGift(userInfo: UserInfo) {
//                    saveMicUser()
//                    GiftManager.getInstance().isInitGift(this@PartyRoomActivity) { giftDialog.show(userInfo.nickname
//                            , userInfo.user_id,userInfo.face, true, userInfo.mystery, supportFragmentManager) }
//                }
//
//                override fun callSomebody(userInfo: UserInfo) {
//                    inputDialog.show("@" + userInfo.nickname + " ", supportFragmentManager)
//                }
//
//                override fun micCtrl4Host(userInfo: UserInfo) {
//                    ChatRoomManager.micCtrl4Host(this@PartyRoomActivity, userInfo)
//                }
//
//                override fun banUser(userInfo: UserInfo, opt: Int) {
//                    ChatRoomManager.banUser4Host(this@PartyRoomActivity, userInfo, opt)
//                }
//
//                override fun banMic(userInfo: UserInfo) {
//                    ChatRoomManager.banMic(this@PartyRoomActivity, userInfo.type, userInfo)
//                }
//
//                override fun kickOut(userInfo: UserInfo) {
//                    val confirmDialog = CommonDialog(this@PartyRoomActivity)
//                    confirmDialog.setTitle("友情提示")
//                    confirmDialog.setContent("您确定要将该用户踢出房间吗？")
//                    confirmDialog.setLeftBt("取消") {
//                        confirmDialog.dismiss()
//                    }
//                    confirmDialog.setRightBt("确定") {
//                        ChatRoomManager.kickOut(this@PartyRoomActivity, userInfo)
//                        confirmDialog.dismiss()
//                    }
//                    confirmDialog.show()
//                }
//            }).show(supportFragmentManager)
//        }
//
//    }
//
//
//    inner class ChatCallback : ChatRoomCallBack() {
//
//        override fun speaking(uid: HashMap<Int, Int>) {
//            if (uid.containsKey(ChatRoomManager.getRoomHostUid())) {
//                mic_water.visibility = View.VISIBLE
//                mic_water.start()
//            } else {
//                mic_water.stop()
//            }
//            for (i in mic_rv.data.indices) {
//                val bean = mic_rv.data[i]
//                bean.mic_speaking = uid.containsKey(bean.user_id)
//                mic_rv.showVoiceWave(i, mic_rv.data[i])
//            }
//        }
//
//
//        // 房主踢用户 更新自身micView,通知其他用户更新micView，通知对应用户退出房间
//        override fun kickOut() {
//            //sendMsg 给对应用户发送踢出房间，退出房间通知由用户自己发起
//            ToastUtil.suc(this@PartyRoomActivity, "踢出成功")
//        }
//
//        //sendMsg 通知所有用户自己离开房间 用户收到离开房间的消息更新界面，如果用户在麦序上需额外处理,如果用户为房主需要退出房间
//        override fun levelChat() {
//            finish()
//        }
//
//        override fun sendEmoji() {
//            emojiDialog.dismiss()
//        }
//
//        override fun showMusicIcon(isRun: Boolean) {
//            music_iv.visibility = View.VISIBLE
//            if (isRun){
//                var anim = AnimationUtils.loadAnimation(this@PartyRoomActivity, R.anim.anim_music_play)
//                anim.interpolator = LinearInterpolator()
//                music_iv.startAnimation(anim)
//            }else{
//                music_iv.clearAnimation()
//            }
//        }
//
//        override fun closeMusicIcon() {
//            music_iv.visibility = View.INVISIBLE
//        }
//    }
//
////    override fun onRoomUsersUpdate(type: Int, uid: Int, onlineNum: Int, msgBean: ArrayList<MsgBean>) {
////        MsgManager.INSTANCE.getGroupNumSize(ChatRoomManager.getRoomId()) {
////            chat_online_num.text = "在线:$it"
////        }
////        if (uid == ChatRoomManager.getRoomHostUid()) {
////            if (type == ChatRoomUpdateViewCallBack.TYPE_USER_JOIN) {
////                host_status.setBackgroundResource(R.drawable.chatting_transparent)
////            } else {
////                host_status.setBackgroundResource(R.drawable.chatting_host_offline)
////            }
////        }
////        if (type == ChatRoomUpdateViewCallBack.TYPE_USER_JOIN) {
////            if (msgBean[0].fromUserInfo.super_manager != 1) {
////                var view = NobleEntryEffectsView(this@PartyRoomActivity)
////                if (msgBean[0].fromUserInfo.body_img.isNotEmpty() && msgBean[0].fromUserInfo.effects != 0){
////                    view.setEntryEffects(msgBean[0].fromUserInfo.effects, msgBean[0].fromUserInfo.head_img, msgBean[0].fromUserInfo.body_img)
////                    view.setImageLevel(if (msgBean[0].fromUserInfo.mystery == 1) 0 else msgBean[0].fromUserInfo.wealth_level.grade)
////                    view.setAvter(msgBean[0].fromUserInfo.face)
////                    view.setContent(msgBean[0].fromUserInfo.nickname + " 进入房间")
////                }
////                if (!TextUtils.isEmpty(msgBean[0].fromUserInfo.seat)){
////                    view.setNobleAnim(msgBean[0].fromUserInfo.seat)
////                }
////                if (!TextUtils.isEmpty(msgBean[0].fromUserInfo.effect_svga)){
////                    view.showSvgaEnter(msgBean[0].fromUserInfo.effect_svga,msgBean[0].fromUserInfo.face,msgBean[0].fromUserInfo.nickname + " 进入房间",msgBean[0].fromUserInfo.wealth_level.grade)
////                }
////                bv_horn.addBarrage(view)
////            }
////        }
////    }
//
//    override fun onUpdate(type: Int, msgBean: ArrayList<MsgBean>) {
//        when (type) {
//            ChatRoomUpdateViewCallBack.TYPE_HOST -> {
//                for (bean in msgBean) {
//                    updateHost(bean)
//                }
//            }
//            ChatRoomUpdateViewCallBack.TYPE_MIC -> {
//                for (bean in msgBean) {
//                    updateHost(bean)
//                    updateMicView(bean)
//                }
//            }
////            ChatRoomUpdateViewCallBack.TYPE_WORD -> {
////                updateWordList(msgBean)
////            }
////            ChatRoomUpdateViewCallBack.TYPE_WORD_ALL_REFRESH -> {
////                updateWordList(msgBean)
////            }
//            ChatRoomUpdateViewCallBack.TYPE_BOTTOM -> {
//                for (bean in msgBean) {
//                    updateBottomView4Host(bean)
//                }
//                updateBottomView()
//            }
//            ChatRoomUpdateViewCallBack.TYPE_ALL -> {
//                isClearChatHistory = false
//                loadData()
//            }
////            ChatRoomUpdateViewCallBack.TYPE_FULL_NOTICE -> {
////                for (bean in msgBean) {
////                    showFullServiceGift(bean)
////                }
////            }
//            ChatRoomUpdateViewCallBack.TYPE_INPUT -> {
//                for (bean in msgBean) {
//                    if (inputDialog.dialog != null && inputDialog.dialog.isShowing) {
//                        inputDialog.dismiss()
//                    }
//                }
//            }
////            ChatRoomUpdateViewCallBack.TYPE_GIFT_LUCK->{
////                for (bean in msgBean){
////                    GiftManager.getInstance().addLuckGiftAnim(bean.fromUserInfo.face,
////                            bean.fromUserInfo.nickname ?: "",
////                            bean.giftBean!!.giftName, bean.toUserInfo.nickname, bean.giftBean!!.giftIcon, bean.giftBean!!.giftNum,
////                            bean.fromUserInfo.user_id, bean.toUserInfo.user_id, bean.giftBean!!.giftId,bean.giftBean!!.giftRewardType,bean.giftBean!!.giftReward)
////                    if(bean.toUserInfo.user_id == ChatRoomManager.getRoomHostUid()){
////                        host_charm_fl.visibility = View.VISIBLE
////                        host_charm_tv.text = (host_charm_tv.text.toString().toInt() + bean.giftBean!!.giftPrice * bean.giftBean!!.giftNum).toString()
////                    }else{
////                        mic_rv.updateItemMike(bean)
////                    }
////                }
////            }
//            ChatRoomUpdateViewCallBack.TYPE_GIFT_CHEST ->{
//                for (bean in msgBean){
//                    GiftManager.getInstance().addChestGiftAnim(bean.giftBean,getMicPosition(bean.toUserInfo.user_id))
//                    if(bean.toUserInfo.user_id == ChatRoomManager.getRoomHostUid()){
//                        host_charm_tv.visibility = View.VISIBLE
//                        host_charm_tv.text = (host_charm_tv.text.toString().toInt() + bean.giftBean!!.giftPrice * bean.giftBean!!.giftNum).toString()
//                    }else{
//                        mic_rv.updateItemMike(bean)
//                    }
//                }
//            }
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
////            ChatRoomUpdateViewCallBack.TYPE_JOIN_GUARD -> {
////                for (bean in msgBean) {
////                    if (bean.fromUserInfo.user_id == DataHelper.getUID()) {
////                        mUserStatus!!.is_guard = 0
////                        chatMode = when {
////                            mUserStatus!!.is_guard != -1 && DataHelper.getLocalUser()!!.noble_status -> ChatMode.OPEN
////                            mUserStatus!!.is_guard != -1 -> ChatMode.ONLY_GUARD
////                            DataHelper.getLocalUser()!!.noble_status -> ChatMode.ONLY_NOBLE
////                            else -> ChatMode.CLOSE
////                        }
////                    }
////                    guardNum += 1
////                    guard_anchor_tv.text = if (mUserStatus?.is_guard == -1) "守护主播" else "${guardNum}人守护"
////                }
////            }
//        }
//    }
//
//    override fun onUpdateWord(type: Int, msgBean: MsgBean) {
//        if (msgBean.opt == MsgType.CLEAR_CHAT){
//            wordListAdapter.clearData()
//        }
//        updateWord(msgBean)
//    }
//
//    private fun updateHost(msgBean: MsgBean) {
//
//        when (msgBean.opt) {
//
//            MsgType.MIC_CTRL -> {
////                if (ChatRoomManager.getRoomHostUid() == msgBean.fromUserInfo.user_id) {
////                    if (msgBean.fromUserInfo.status != 2) {
////                        host_status.setBackgroundResource(R.drawable.chatting_transparent)
////                    } else {
////                        host_status.setBackgroundResource(R.drawable.chatting_mic_disable)
////                    }
////                }
//            }
//            MsgType.INVITE_TO_MIC -> {
//                ChatRoomManager.micCtrl(this@PartyRoomActivity, -1, false, true)
//            }
//            MsgType.EMOJI -> {
//                if (msgBean.fromUserInfo.user_id == ChatRoomManager.getRoomHostUid()) {
//                    if (msgBean.emojiBean != null) {
////                        if (host_emoji_iv.visibility == View.VISIBLE) {
////                            return
////                        }
////                        val emojiBean = msgBean.emojiBean
////                        if (emojiBean!!.emoji_id == 105 && emojiBean.emoji_group_id == 1) {
////                            host_emoji_iv.visibility = View.VISIBLE
////                            DiceUtil.showDice(host_emoji_iv, emojiBean.emoji_result)
////                        } else if (emojiBean.emoji_id == 103) {
////                            host_emoji_iv.visibility = View.VISIBLE
////                            BaodengUtil.showBaodeng(host_emoji_iv)
////                        } else {
////                            host_emoji_iv.setVisibility(View.VISIBLE)
////                            ImgUtil.loadGifOrWebp(this@PartyRoomActivity, emojiBean.emoji_gif, host_emoji_iv, 1)
////                        }
//                        host_emoji_iv.visibility = View.VISIBLE
//                        ImgUtil.loadGifOrWebp(this@PartyRoomActivity, msgBean.emojiBean!!.emoji_gif, host_emoji_iv, 1)
//                    }
//                }
//            }
//            MsgType.GIFT -> {
//                if(msgBean.toUserInfo.user_id == ChatRoomManager.getRoomHostUid()){
//                    host_charm_tv.visibility = View.VISIBLE
//                    host_charm_tv.text = (host_charm_tv.text.toString().toInt() + msgBean.giftBean!!.giftPrice * msgBean.giftBean!!.giftNum).toString()
//                    ChatRoomManager.contribute += msgBean.giftBean!!.giftPrice * msgBean.giftBean!!.giftNum
//                }else{
//                    mic_rv.updateItemMike(msgBean)
//                }
//                GiftManager.getInstance().addAnim(msgBean.content,msgBean.giftBean!!.giftNum,msgBean.giftBean!!.giftUrl,msgBean.giftBean!!.giftIcon, GiftManager.GIFT_SEND_BT_POSITION,getMicPosition(msgBean.toUserInfo.user_id))
//
//            }
//            MsgType.REFRESH_MIC -> {
////                refreshHostMic(msgBean, host_seat_iv)
//            }
//            MsgType.CLEAR_MIKE -> {
//                if (msgBean.toUserInfo.type == 0 || msgBean.toUserInfo.type == 9) host_charm_tv.text = "0"
//            }
//            else -> {
//            }
//        }
//    }
//
//
//
//    private fun refreshHostMic(msgBean: MsgBean, view: SimpleDraweeView) {
////        if (TextUtils.isEmpty(msgBean.fromUserInfo.seat_frame)) {
////            view.visibility = View.GONE
////        } else {
////            view.visibility = View.VISIBLE
////            view.setBackgroundResource(0)
////            ImgUtil.loadWebpGif(this@PartyRoomActivity, msgBean.fromUserInfo.seat_frame, view, Int.MAX_VALUE)
////        }
//    }
//
//
//
//    private fun getMicPosition(userId:Int):Int{
//        if (userId == ChatRoomManager.getRoomHostUid()){
//            return 0
//        }else{
//            return mic_rv.getItemLayout(userId)
//        }
//    }
//
//
//    private fun updateMicView(msgBean: MsgBean) {
//        when (msgBean.opt) {
//            MsgType.DOWN_FROM_MIC -> {
//
//                if (msgBean.fromUserInfo != null && msgBean.fromUserInfo.user_id == DataHelper.getUID()) {
//                    RoomMusicManager.pausePlayMusic()
//                    music_iv.visibility = View.INVISIBLE
//                }
//                for (i in mic_rv.data.indices) {
//                    var bean = mic_rv.data[i]
//                    if (msgBean.fromUserInfo.user_id == bean.user_id) {
//                        val status = bean.status
//                        bean = UserInfo()
//                        bean.nickname = (i + 1).toString() + "号麦"
//                        bean.type = i+1
//                        bean.status = if (status == 2)  2 else 0
//                        mic_rv.updateItem(i, bean)
//                    }
//                }
//            }
//            MsgType.UP_TO_MIC -> {
//                if (msgBean.fromUserInfo != null){
//                    mic_rv.clearPreInfo(msgBean.fromUserInfo)
//                    mic_rv.updateItem(msgBean.fromUserInfo.type - 1, msgBean.fromUserInfo)
//                }
//            }
//            MsgType.BAN_MIC -> {
//                mic_rv.updateItem(msgBean.toUserInfo.type - 1, msgBean.toUserInfo)
//            }
//            MsgType.BAN_USER_MIC -> {
//                mic_rv.updateItem(msgBean.toUserInfo.type - 1, msgBean.toUserInfo)
//            }
//            MsgType.APPLY_MIC_AGREE -> {
//                mic_rv.updateItem(msgBean.toUserInfo.type - 1, msgBean.toUserInfo)
//            }
//            MsgType.REMOVE_MIC -> {
//                for (i in mic_rv.data.indices) {
//                    var bean = mic_rv.data[i]
//                    if (msgBean.toUserInfo.user_id == bean.user_id) {
//                        var status = bean.status
//                        bean = UserInfo()
//                        bean.nickname = (i + 1).toString() + "号麦"
//                        bean.status = if (status == 2)  2 else 0
//                        mic_rv.updateItem(i, bean)
//                    }
//                }
//            }
//            MsgType.LEVEL_CHAT -> {
//                for (i in mic_rv.data.indices) {
//                    var bean = mic_rv.data[i]
//                    if (msgBean.fromUserInfo.user_id == bean.user_id) {
//                        var status = bean.status
//                        bean = UserInfo()
//                        bean.nickname = (i + 1).toString() + "号麦"
//                        bean.type = i + 1
//                        bean.status = if (status == 2)  2 else 0
//                        mic_rv.updateItem(i, bean)
//                    }
//                }
//            }
//            MsgType.MIC_CTRL, MsgType.REFRESH_MIC -> {
//                mic_rv.updateItem(msgBean.fromUserInfo.type - 1, msgBean.fromUserInfo)
//            }
//            MsgType.LOCK_MIC -> {
//                mic_rv.updateItem(msgBean.toUserInfo.type - 1, msgBean.toUserInfo)
//            }
//            MsgType.EMOJI -> {
//                if (msgBean.emojiBean != null) {
//                    mic_rv.showEmoji(msgBean.fromUserInfo.type - 1, msgBean.emojiBean)
//                }
//            }
//            MsgType.KICK_OUT -> {
//                for (i in mic_rv.data.indices) {
//                    var bean = mic_rv.data[i]
//                    if (msgBean.toUserInfo.user_id == bean.user_id) {
//                        var status = bean.status
//                        bean = UserInfo()
//                        bean.nickname = (i + 1).toString() + "号麦"
//                        bean.type = i + 1
//                        bean.status = if (status == 2)  2 else 0
//                        mic_rv.updateItem(i, bean)
//                    }
//                }
//            }
//            MsgType.CLEAR_MIKE -> {
//                if (msgBean.toUserInfo.type != 0) mic_rv.clearItemMike(msgBean.toUserInfo.type - 1)
//            }
//            else -> {
//                //do nothing
//            }
//        }
//    }
//
//
//    private fun updateBottomView4Host(msgBean: MsgBean) {
//        when (msgBean.opt) {
//            MsgType.APPLY_MIC -> {
//                if (ChatRoomManager.getUserRole() > 0) {
////                    apply_notice.visibility = View.VISIBLE
//                }
//            }
//            else -> {
//            }
//        }
//    }
//
//    private fun updateBottomView() {
//        if (ChatRoomManager.getMicPosition() != 0 || ChatRoomManager.isRoomHost()) {      //状态为上麦
////            mic_go_btn.text = "下麦"
//            mic_go_btn.isSelected = true
//            mic_status.visibility = View.VISIBLE
//            emoji_btn.visibility = View.VISIBLE
//            if (ChatRoomManager.isMicEnable()) {
//                mic_status.setImageResource(R.drawable.room_icon_bottom_open_mic)
//            } else {
//                mic_status.setImageResource(R.drawable.room_icon_mic_close)
//            }
//        } else {
//            mic_status.visibility = View.GONE
//            emoji_btn.visibility = View.GONE
//            when (chatMode) {
//                ChatMode.OPEN -> {
////                    mic_go_btn.text = "上麦"
//                    mic_go_btn.isSelected = false
//                }
//                ChatMode.ONLY_INVITE -> {
//                    if (ChatRoomManager.getUserRole() > 0) {
////                        mic_go_btn.text = "上麦"
//                        mic_go_btn.isSelected = false
//                    } else {
////                        mic_go_btn.text = "排麦"
//                        mic_go_btn.isSelected = false
//                    }
//                }
//            }
//        }
//    }
//
//    private fun report(id: String, reportObject: Int) {
//        ReportDialog(this@PartyRoomActivity, reportObject, object : ReportDialog.CallBack {
//            override fun onCancel(dialog: ReportDialog) {
//            }
//
//            override fun onSubmit(dialog: ReportDialog, type: Int) {
//                NetService.getInstance(this@PartyRoomActivity).report(id, reportObject, type, object : Callback<BaseBean>() {
//                    override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
//                        ToastUtil.suc(this@PartyRoomActivity, "举报成功")
//                        dialog.dismiss()
//                    }
//
//                    override fun onError(msg: String, throwable: Throwable, code: Int) {
//                        ToastUtil.error(this@PartyRoomActivity, msg)
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
//    }
//
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
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode != Activity.RESULT_OK) {
//            return
//        }
//        when (requestCode) {
//            101 -> {
//                isClearChatHistory = false
//                ChatRoomManager.sendMsg(MsgType.UPDATE_CHAT_INFO, "", ChatRoomManager.getRoomId())
//            }
//        }
//    }
//
////    private fun hideAllDialog(): Boolean {
////        if ((inputDialog.dialog != null && inputDialog.dialog.isShowing)
////                || (musicDialog.dialog != null && musicDialog.dialog.isShowing)
////                || (giftDialog.dialog != null && giftDialog.dialog.isShowing)
////                || (shareDialog.dialog != null && shareDialog.dialog.isShowing)
////                || (emojiDialog.dialog != null && emojiDialog.dialog.isShowing)
////                || (applyListDialog.dialog != null && applyListDialog.dialog.isShowing)
////                || (voiceSettingDialog != null && (voiceSettingDialog!!.dialog != null && voiceSettingDialog!!.dialog.isShowing))
////                || (chattingMoreOperateNewDialog != null && (chattingMoreOperateNewDialog!!.dialog != null && chattingMoreOperateNewDialog!!.dialog.isShowing))
////                || (clearMikeDialog != null && (clearMikeDialog!!.dialog != null && clearMikeDialog!!.dialog.isShowing))
////                || (roomRankDialog != null && (roomRankDialog!!.dialog != null && roomRankDialog!!.dialog.isShowing))
////                || (operateMusicDialog != null && operateMusicDialog!!.isShowing)
////                || (msgListDialog.dialog != null && msgListDialog.dialog.isShowing)
////                || (redPacketDialog != null && redPacketDialog!!.isShowing)
////                || (redPacketListDialog != null && redPacketListDialog!!.isShowing)) {
////            inputDialog.dismiss()
////            musicDialog.dismiss()
////            shareDialog.dismiss()
////            giftDialog.dismiss()
////            emojiDialog.dismiss()
////            applyListDialog.dismiss()
////            voiceSettingDialog?.dismiss()
////            chattingMoreOperateNewDialog?.dismiss()
////            clearMikeDialog?.dismiss()
////            roomRankDialog?.dismiss()
////            operateMusicDialog?.dismiss()
////            msgListDialog.dismiss()
////            redPacketDialog?.dismiss()
////            return true
////        }
////        return false
////    }
//
//    private fun initGiftPoint(){
//        send_gift_btn.post {
//            GiftManager.getInstance().addDefalutPoint(send_gift_btn)
//        }
//        mic_rv.refreshPosition()
//        host_icon_iv.post {
//            GiftManager.getInstance().addHostPoint(host_icon_iv)
//        }
//    }
//
//    private fun requestWritePermission() {
//        if (XXPermissions.isHasPermission(this@PartyRoomActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            musicDialog.show(supportFragmentManager)
//        } else {
//            XXPermissions.with(this@PartyRoomActivity)
//                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
//
//    private fun requestRecordPermission() {
//        if (!XXPermissions.isHasPermission(this@PartyRoomActivity, Manifest.permission.RECORD_AUDIO)) {
//            XXPermissions.with(this@PartyRoomActivity)
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
//
//
//    override fun finish() {
//        super.finish()
//        ChatRoomManager.disPatchMinWindow()
//        GiftManager.getInstance().clearAnim()
//        chatHistoryJob?.cancel()
//        redPacketCountDownTimer?.cancel()
//        ChatRoomManager.unregisterNetDelay()
//        ChatRoomManager.removeRecordVolumeObserver(this)
//        ChatRoomManager.unregister()
//        EventBus.getDefault().unregister(this)
//        RoomLib.getChatHelper(application, true)?.apply {
//            startForeground(111, keepAliveNotification()) }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun networkListen(networkBean: NetWorkBean) {
//        if (networkBean.isConnect && !TextUtils.isEmpty(ChatRoomManager.getRoomId())) {
//            ChatRoomManager.isReJoin = true
////            loadData()
//            ChatRoomManager.reconnection(this@PartyRoomActivity, ChatRoomManager.getRoomId(), object : ChatRoomBaseCallBack {
//                override fun onSuc() {
//                    ToastUtil.error(this@PartyRoomActivity, "聊天室重新连接～")
//                    loadData()
//                }
//
//                override fun onFail(msg: String) {
//                    ToastUtil.error(this@PartyRoomActivity, "聊天室重连失败～")
////                    ToastUtil.error(this@PartyRoomActivity, msg)
////                    finish()
//                }
//            })
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun refreshC2CUnReadMsg(bean: UnReadNumBean) {
//        if (bean.unReadNum > 0){
//            msg_notice.visibility = View.VISIBLE
//        }else{
//            msg_notice.visibility = View.GONE
//        }
//    }
//
////    @Subscribe(threadMode = ThreadMode.MAIN)
////    fun refreshRoom(bean: ChatRoomInfo) {
////        GiftManager.getInstance().clearAnim()
////        renderView(bean)
////    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun reloadRoomData(bean: RefreshRoomBean) {
//        loadData()
//    }
//
////    @Subscribe(threadMode = ThreadMode.MAIN)
////    fun refreshFollowBtn(statusBean: FollowStatusBean) {
////        if (statusBean.user_id == ChatRoomManager.getRoomHostUid()) {
////            ChatRoomManager.isFollow = if (statusBean.isFollow) 1 else 0
////        }
////    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun showUserCardDialog(bean: UserCardEvent) {
//        showUserCard(bean.userInfo)
//        roomRankDialog?.dismiss()
//    }
//
//
//
////    private fun onBackPressed() {
////        if (hideAllDialog()) {
////            return
////        }else{
////            finish()
////        }
////
////    }
//
//
//
////    fun finish() {
//////        dismiss()
//////        EventBus.getDefault().post(RefreshEvent())
////        EventBus.getDefault().post(CloseAllDialogBean())
//////        hideAllDialog()
////        redPacketCountDownTimer?.cancel()
////        if (statusListen != null && mHostInfo != null) {
////            if (TextUtils.isEmpty(ChatRoomManager.getRoomId())){
////                statusListen!!.onDestroy()
////            }else{
////                statusListen!!.onDismiss(mHostInfo!!.icon!!, mHostInfo!!.name)
////            }
////        }
//////        GiftManager.getInstance().unregisterHandler()
////        ChatRoomManager.unregisterNetDelay()
////        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.anim_room_in, R.anim.anim_room_out).hide(this).commitAllowingStateLoss()
////        this@PartyRoomActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
////    }
////    fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
////        if (keyCode == KeyEvent.KEYCODE_BACK) {
////            onBackPressed()
////            return true
////        }
////        return false
////    }
////
////    override fun onDestroyView() {
////        super.onDestroyView()
////        redPacketCountDownTimer?.cancel()
////        ChatRoomManager.leaveChat()
////        ChatRoomManager.removeRecordVolumeObserver(this)
////    }
//
////    private var statusListen: StatusListen? = null
////
////    fun addDismissListen(statusListen: StatusListen) {
////        this.statusListen = statusListen
////    }
//
//    override fun onChange(volume: Int) {
//        mic_status.setImageResource(if (volume > 0) R.drawable.room_icon_bottom_open_mic else R.drawable.room_icon_mic_close)
//    }
//
//    private fun createRulePopupWindow( content: String?): PopupWindow {
//        val popupWindow = PopupWindow(this@PartyRoomActivity)
//        popupWindow.setBackgroundDrawable(resources.getDrawable(R.color.transparent))
//        // 设置宽度
//        popupWindow.width = ViewGroup.LayoutParams.MATCH_PARENT
//        // 设置高度
//        popupWindow.height = DensityUtil.dp2px(this@PartyRoomActivity, 507f)
//        // 设置背景
//        val view = LayoutInflater.from(this@PartyRoomActivity).inflate(R.layout.dialog_room_rlue, null, false)
//        val contentTv = view.findViewById<EditText>(R.id.tv_content)
//        val changeTv = view.findViewById<TextView>(R.id.tv_change)
//        // 设置界面
//        contentTv.setText(content)
//        if (ChatRoomManager.isRoomHost() || ChatRoomManager.isRoomManager()){
//            changeTv.visibility = View.VISIBLE
//            contentTv.isEnabled = true
//            contentTv.setSelection(content!!.length)
//        }else{
//            changeTv.visibility = View.GONE
//            contentTv.isEnabled = false
//        }
//        changeTv.setOnClickListener {
//            setRoomNote(contentTv.text.toString())
//        }
//        popupWindow.contentView = view
//        // true时界面可点
//        popupWindow.isTouchable = true
//        // true时PopupWindow处理返回键
//        popupWindow.isFocusable = true
//        // true时点击外部消失，如果touchable为false时，点击界面也消失
//        popupWindow.isOutsideTouchable = true
//        // dismiss监听器
//        popupWindow.setOnDismissListener { }
//        return popupWindow
//    }
//
//    private fun setRoomNote(note:String){
//        NetService.getInstance(this@PartyRoomActivity).setRoomNote(ChatRoomManager.getRoomId(),note,object : Callback<BaseBean>(){
//            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
//                mHostInfo?.note = note
//                ToastUtil.suc(this@PartyRoomActivity,"修改公告成功")
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                ToastUtil.error(this@PartyRoomActivity,msg)
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//
//        })
//    }
//
//    private fun setRoomPwd(pwd:String){
//        NetService.getInstance(this@PartyRoomActivity).setRoomPwd(ChatRoomManager.getRoomId(),pwd,object : Callback<BaseBean>(){
//            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
//                if (!TextUtils.isEmpty(pwd)){
//                    ToastUtil.suc(this@PartyRoomActivity,"房间上锁")
//                }else{
//                    ToastUtil.suc(this@PartyRoomActivity,"房间解锁")
//                }
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                ToastUtil.error(this@PartyRoomActivity,msg)
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//
//        })
//    }
//
//    private fun refreshRankTop(){
//        NetService.getInstance(this@PartyRoomActivity).getRoomTopThree(ChatRoomManager.getRoomId(), object : Callback<List<RankBean>>() {
//            override fun onSuccess(nextPage: Int, bean: List<RankBean>, code: Int) {
//                showRankTopThree(bean)
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
//
//    private fun showRankTopThree(ranklist:List<RankBean>){
//        if (ranklist.isEmpty()){
//            return
//        }
//        if (ranklist.size == 1){
//            chat_popularity_fl.showImg1(ranklist[0].face)
//            chat_popularity_fl.showImg2("")
//            chat_popularity_fl.showImg3("")
//            return
//        }
//        if (ranklist.size == 2){
//            chat_popularity_fl.showImg1(ranklist[0].face)
//            chat_popularity_fl.showImg2(ranklist[1].face)
//            chat_popularity_fl.showImg3("")
//            return
//        }
//        if (ranklist.size >= 3){
//            chat_popularity_fl.showImg1(ranklist[0].face)
//            chat_popularity_fl.showImg2(ranklist[1].face)
//            chat_popularity_fl.showImg3(ranklist[2].face)
//        }
//    }
//
//    private fun playSvgaGiftAnim(view:SVGAImageView,name: String) {
//        val parser = SVGAParser(this@PartyRoomActivity)
//        view.callback = object : SVGACallback {
//            override fun onPause() {}
//            override fun onFinished() {
//
//            }
//
//            override fun onRepeat() {
//
//            }
//
//            override fun onStep(i: Int, v: Double) {}
//        }
//        parser.decodeFromAssets(name, object : ParseCompletion {
//            override fun onComplete(videoItem: SVGAVideoEntity) {
//                val drawable = SVGADrawable(videoItem)
//                view.setImageDrawable(drawable)
//                view.startAnimation()
//            }
//
//            override fun onError() {}
//        })
//    }
//
//    fun openMortal(){
////        NetService.getInstance(this@PartyRoomActivity).openMortal(ChatRoomManager.getRoomId(),object : Callback<BaseBean>(){
////            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
////                ToastUtil.error(this@PartyRoomActivity,"开启成功")
////                ChatRoomManager.sendMsg(MsgType.GAME_XX_MSG, "开启灵兽牌玩法", ChatRoomManager.getRoomId())
////            }
////
////            override fun onError(msg: String, throwable: Throwable, code: Int) {
////                ToastUtil.error(this@PartyRoomActivity,msg)
////            }
////
////            override fun isAlive(): Boolean {
////                return isLive
////            }
////
////        })
//    }
//}