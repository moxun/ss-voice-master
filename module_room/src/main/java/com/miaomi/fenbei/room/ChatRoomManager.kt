package com.miaomi.fenbei.room

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.miaomi.fenbei.base.VerifyPwdDialog
import com.miaomi.fenbei.room.callback.*
import com.miaomi.fenbei.room.ui.callback.RecordVolumeObservable
import com.miaomi.fenbei.base.bean.*
import com.miaomi.fenbei.base.config.BaseConfig
import com.miaomi.fenbei.base.config.EmojiConfig
import com.miaomi.fenbei.base.core.CommonLib
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.miaomi.fenbei.base.core.dialog.LoadingDialog
import com.miaomi.fenbei.base.core.msg.MsgListener
import com.miaomi.fenbei.base.core.msg.MsgManager
import com.miaomi.fenbei.base.core.emoji.EmojiManager
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.Data
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.*
import com.miaomi.fenbei.gift.GiftManager
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import java.util.*

/**
 * Created by
 * on 2019-10-11.
 */
object ChatRoomManager : MsgListener, RecordVolumeObservable.RecordVolumeObserver {

    const val ROOM_TYPE_PERSONAL = 0
    const val ROOM_TYPE_LABOR_UNION = 1
    const val ROOM_TYPE_BLIND = 2
    const val ROOM_TYPE_RADIO = 3

    private lateinit var mContext: Application
    private var roomSyncLock = false
    lateinit var mRtcEngine: RtcEngine
    private const val WORD_LIST_MAX_SIZE = 150
    private var mUserMicStatus: Int = 0
    private var mChatId: String = ""

    //    var headline_cost: String = ""
    var isCloseVoice: Boolean = false
    var isEarMonitoring: Boolean = false
    var isFollow: Int = 0
    var contribute: Long = 0
    var voiceType: Int = 0
    var isSelfCloseLive: Boolean = false
    var mRoomGoodNumber: Int = 0
    private var mHostUid: Int = 0
    var mCloseChat: Int = 0

    var mStatusBean: UserStatusBean = UserStatusBean(0, 0, 0, 0, 0, 0, false)
    var mUserHostInfo: UserInfo? = null

    var mCallback: ChatRoomCallBack? = null
    private var mUpdateCallBack: ChatRoomUpdateViewCallBack? = null
    private var mNetDelayCallBack: ChatRoomOnNetDelayCallBack? = null
    private var mOnLiveFinishCallBack: ChatRoomOnLiveFinishCallBack? = null
    private var onChatRoomLightCallBack: ChatRoomLightCallBack? = null
    private var onChatRoomRadioCallBack: ChatRoomRadioCallBack? = null

    private lateinit var mMsgManager: MsgManager
    private lateinit var user: UserInfo
    private var mWordList: ArrayList<MsgBean> = ArrayList()
    var mChatRoomInfo: ChatRoomInfo? = null

    /**
     * ???????????????
     */
    var roomStatus = RoomStatus.status_default


    var isEmojiClickEnable = true
    var isReJoin: Boolean = false
    var isVoiceLive: Boolean = false
    var mRoomType: Int = ROOM_TYPE_PERSONAL
    var previousRoomType: Int = ROOM_TYPE_PERSONAL

    private val monitors = ArrayList<RoomStatusListener>()

    fun addMonitor(monitor: RoomStatusListener) {
        monitors.add(monitor)
    }

    fun removeMonitor(monitor: RoomStatusListener) {
        monitors.remove(monitor)
    }

    fun disPatchInRoom() {
        roomStatus = RoomStatus.status_in_Room
        monitors.forEach {
            it.inRoom()
        }
    }

    fun disPatchMinWindow() {
        roomStatus = RoomStatus.status_min_window
        monitors.forEach {
            it.onMin()
        }
    }


    fun init(context: Application) {
        mContext = context
        GiftManager.getInstance().initGiftList(context.applicationContext)
        EmojiManager.initEmojiData(context.applicationContext)
        try {
            mRtcEngine = RtcEngine.create(mContext, BaseConfig.AGORA_APPID, object : IRtcEngineEventHandler() {
                /* ---------------------????????????---------------------*/

                override fun onAudioVolumeIndication(speakers: Array<out AudioVolumeInfo>, totalVolume: Int) {
                    Handler(Looper.getMainLooper()).post {
                        if (mCallback != null) {
                            val uidMap = HashMap<Int, Int>()
                            for (speaker in speakers) {
                                if (speaker.uid != 0 && speaker.volume > 0) {
                                    uidMap[speaker.uid] = speaker.volume
                                } else {
                                    if (DataHelper.getUserInfo() != null && speaker.volume > 0) {
                                        uidMap[DataHelper.getUserInfo()!!.user_id] = speaker.volume
                                    }
                                }
                            }
                            mCallback!!.speaking(uidMap)
                        }
                    }
                }

                override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
                    super.onJoinChannelSuccess(channel, uid, elapsed)
                    setVoiceChange(0)
                    setEnableInEarMonitoring(false)
                    remoteVoiceCtrl(false)
                }

                override fun onAudioMixingFinished() {
                    Handler(Looper.getMainLooper()).post {
                        RoomMusicManager.playNextMusic()
                    }
                }

                override fun onRemoteAudioStats(stats: RemoteAudioStats) {
                    mNetDelayCallBack!!.onUpdateDelay(stats.networkTransportDelay)
                }

                override fun onRequestToken() {
                    super.onRequestToken()
                    LogUtil.d("~~~~~onRequestToken")
                }

                override fun onError(p0: Int) {
                    super.onError(p0)
                    LogUtil.d("~~~~~onError"+p0)
                }

                override fun onConnectionStateChanged(p0: Int, p1: Int) {
                    super.onConnectionStateChanged(p0, p1)
                    LogUtil.d("~~~~~onConnectionStateChanged"+p0)

                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
        mMsgManager = MsgManager.INSTANCE
        mMsgManager.addMsgListener(this)
        mRecordVolumeObservable.registerObserver(this)
    }

    fun register(callback: ChatRoomCallBack, updateCallBack: ChatRoomUpdateViewCallBack, onLiveFinishCallBack: ChatRoomOnLiveFinishCallBack) {
        mUpdateCallBack = updateCallBack
        mCallback = callback
        mOnLiveFinishCallBack = onLiveFinishCallBack
    }

    fun unregister() {
        mUpdateCallBack = null
        mCallback = null
        mOnLiveFinishCallBack = null
    }

    fun registerLight(chatRoomLightCallBack: ChatRoomLightCallBack) {
        onChatRoomLightCallBack = chatRoomLightCallBack
    }

    fun unregisterLight() {
        onChatRoomLightCallBack = null
    }

    fun registerRadio(chatRoomRadioCallBack: ChatRoomRadioCallBack) {
        onChatRoomRadioCallBack = chatRoomRadioCallBack
    }

    fun unregisterRadio() {
        onChatRoomRadioCallBack = null
    }

    fun registerNetDelay(netDelayCallBack: ChatRoomOnNetDelayCallBack) {
        mNetDelayCallBack = netDelayCallBack
    }

    fun unregisterNetDelay() {
        mNetDelayCallBack = null
    }


    fun isInRoom(): Boolean {
        return roomStatus != RoomStatus.status_default
    }

    fun createRoom(context: Activity, roomType: Int, roomId: String, agoraToken: String, joinCallBack: JoinChatCallBack) {
        if (mChatId.isNotEmpty()) {
            previousRoomType = mRoomType
        }
        MsgManager.INSTANCE.createGroup(roomId, object : MsgManager.MsgCallBack {
            override fun onSendSuc() {
                mRoomType = roomType
                val localUser = DataHelper.getUserInfo()
                localUser!!.room_id = roomId
                DataHelper.updatalUserInfo(localUser)
                CommonLib.isInRoom = true
                joinAgoraChannel(context, roomId, agoraToken, true, joinCallBack)
            }

            override fun onSendFail(i: Int, s: String?) {
                joinCallBack.onFail("?????????????????????????????? $i $s")
                NetService.getInstance(context).createChatRoomCallBack(roomId, object : Callback<BaseBean>() {
                    override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                    }

                    override fun isAlive(): Boolean {
                        return true
                    }

                    override fun onError(msg: String, throwable: Throwable, code: Int) {

                    }
                })
            }
        })
    }


//    fun createChat(context: Activity,roomType:Int, topic: String, chatName: String, chatRoomIcon: String, chatRoomBg: String, micType: Int, micWay: Int, chatPassword: String, chatNotice: String, labelId: String, joinCallBack: JoinChatCallBack) {
//
//        NetService.getInstance(context).createChatRoom(roomType,topic, chatName, chatRoomIcon, chatRoomBg, micType, micWay, chatPassword, chatNotice, labelId, object : Callback<CreateChatBean>() {
//            override fun onSuccess(nextPage: Int, bean: CreateChatBean, code: Int) {
//                if (mChatId.isNotEmpty()){
//                    previousRoomType = mRoomType
//                }
//                MsgManager.INSTANCE.createGroup(bean.room_id, object : MsgManager.MsgCallBack {
//                    override fun onSendSuc() {
//                        mRoomType = roomType
//                        val localUser = DataHelper.getUserInfo()
//                        localUser!!.room_id = bean.room_id
//                        DataHelper.updatalUserInfo(localUser)
//                        CommonLib.isInRoom = true
//                        joinAgoraChannel(context, bean.room_id, bean.agora_token, true, joinCallBack)
//                    }
//
//                    override fun onSendFail(i: Int, s: String?) {
//                        joinCallBack.onFail("?????????????????????????????? $i $s")
//                        NetService.getInstance(context).createChatRoomCallBack(bean.room_id, object : Callback<BaseBean>() {
//                            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
//                            }
//
//                            override fun isAlive(): Boolean {
//                                return true
//                            }
//
//                            override fun onError(msg: String, throwable: Throwable, code: Int) {
//
//                            }
//                        })
//                    }
//                })
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                when (code) {
//                    1003 -> {
//                        val upDialog = CommonDialog(context)
//                        upDialog.setTitle("????????????")
//                        upDialog.setContent("?????????????????????????????????")
//                        upDialog.setLeftBt("??????") {
//                            upDialog.dismiss()
//                        }
//                        upDialog.setRightBt("?????????") {
//                            ARouter.getInstance().build(RouterUrl.identityAuthentication).navigation()
//                            upDialog.dismiss()
//                        }
//                        upDialog.show()
//                    }
//                    else -> ToastUtil.error(context, msg)
//                }
//                joinCallBack.onFail(msg)
//            }
//
//            override fun isAlive(): Boolean {
//                return true
//
//            }
//
//        })
//    }


    fun joinChat(context: Context, chatId: String, joinCallBack: JoinChatCallBack) {
        if (TextUtils.isEmpty(chatId)) {
            joinCallBack.onFail("??????ID????????????")
            return
        }
//        if (!TimeUtil.isToday(Date(DataHelper.getSignInDialogShowTime()))) {
//            EnterRoomTipDialog(context).setTitle("??????????????????")
//                    .setContent("????????????????????????????????????,???????????????????????????????????????,??????????????????" +
//                            "????????????????????????,?????????????????????," +
//                            "???????????????????????????????????????????????????" +
//                            "?????????????????????????????????,???????????????" +
//                            "?????????????????????????????????,???????????????" +
//                            "????????????!\n" +
//                            "????????????????????????????????????!")
//                    .setLeftBt("??????", null)
//                    .setRightBt("??????") {
//                        DataHelper.saveSignInDialogShowTime(System.currentTimeMillis())
//                        joinChatBefore(context, chatId, joinCallBack = joinCallBack)
//                    }
//                    .show()
//            return
//        }

        joinChatBefore(context, chatId, joinCallBack = joinCallBack)
    }

    @SuppressLint("WrongConstant")
    private fun joinChatBefore(context: Context, chatId: String, isOpenChatRoom: Boolean = true, joinCallBack: JoinChatCallBack) {

        if (roomSyncLock) {
            Handler().postDelayed({
                roomSyncLock = false
            }, 1000)
            return
        }

        roomSyncLock = true
        if (chatId != "" && mChatId != "" && chatId == mChatId) {
            ARouter.getInstance().build(RouterUrl.partyRoom)
                    .withTransition(R.anim.anim_bottom_in, R.anim.anim_bottom_out)
                    .navigation(context)
            joinCallBack.onSuc()
            roomSyncLock = false
        } else if (chatId != "" && mChatId != "" && chatId != mChatId && isRoomHost() && mRoomType == ROOM_TYPE_PERSONAL) {
            val upDialog = CommonDialog(context)
            upDialog.setTitle("????????????")
            upDialog.setContent("???????????????????????????????????????????????????????????????????????????????????????????????????")
            upDialog.setLeftBt("??????") {
                upDialog.dismiss()
            }
            upDialog.setRightBt("??????") {
                val loadingDialog = LoadingDialog(context)
                loadingDialog.show()
                loadingDialog.setCancelable(false)
                joinChat(context, chatId, password = "", isOpenChatRoom = isOpenChatRoom, joinCallBack = object : JoinChatCallBack {
                    override fun onSuc() {
                        roomSyncLock = false
                        loadingDialog.dismiss()
                        joinCallBack.onSuc()
                    }

                    override fun onFail(msg: String) {
                        roomSyncLock = false
                        loadingDialog.dismiss()
                        joinCallBack.onFail(msg)
                    }
                })
                upDialog.dismiss()
            }
            upDialog.show()
        } else {
            val loadingDialog = LoadingDialog(context)
            loadingDialog.show()
            loadingDialog.setCancelable(false)
            joinChat(context, chatId, password = "", isOpenChatRoom = isOpenChatRoom, joinCallBack = object : JoinChatCallBack {
                override fun onSuc() {
                    roomSyncLock = false
                    loadingDialog.dismiss()
                    joinCallBack.onSuc()
                }

                override fun onFail(msg: String) {
                    roomSyncLock = false
                    loadingDialog.dismiss()
                    joinCallBack.onFail(msg)
                }
            })
        }
    }


    private fun joinChat(context: Context, chatId: String, password: String, isOpenChatRoom: Boolean = true, joinCallBack: JoinChatCallBack) {

        if (chatId == "") {
            roomSyncLock = false
            return
        }
        NetService.getInstance(context).joinChatRoom(chatId, password, object : Callback<JoinChatBean>() {
            override fun onSuccess(nextPage: Int, bean: JoinChatBean, code: Int) {
                if (mChatId.isNotEmpty()) {
                    previousRoomType = mRoomType
                }
                mRoomType = bean.room_type
                var userInfo = DataHelper.getUserInfo()

                if (chatId == userInfo!!.room_id && userInfo!!.room_status == 1) {

                    MsgManager.INSTANCE.createGroup(chatId, object : MsgManager.MsgCallBack {
                        override fun onSendSuc() {

                            CommonLib.isInRoom = true
                            joinAgoraChannel(context, chatId, bean.agora_token, true, joinCallBack)
                        }

                        override fun onSendFail(i: Int, s: String?) {

                        }
                    })
                } else {

                    MsgManager.INSTANCE.joinGroup(chatId, object : MsgManager.MsgCallBack {
                        override fun onSendSuc() {
                            CommonLib.isInRoom = true
                            joinAgoraChannel(context, chatId, bean.agora_token, isOpenChatRoom, joinCallBack)
                        }

                        override fun onSendFail(i: Int, s: String?) {
                            if ((i == 10010 || i == 10015) && (DataHelper.getUserInfo()?.room_id == chatId)) {
                                MsgManager.INSTANCE.createGroup(chatId, object : MsgManager.MsgCallBack {
                                    override fun onSendSuc() {
                                        CommonLib.isInRoom = true
                                        joinAgoraChannel(context, chatId, bean.agora_token, isOpenChatRoom, joinCallBack)
                                    }

                                    override fun onSendFail(i: Int, s: String?) {
                                    }
                                })
                            } else {
                                joinCallBack.onFail("??????????????????????????????????????????")
//                                ARouter.getInstance().build("/chatting/roomClosing").navigation()
                            }
                        }
                    })
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                if (code == Data.JOIN_ROOM_NEED_PWD) {
                    if (!TextUtils.isEmpty(password)) {
                        ToastUtil.error(context, "???????????????")
                    }
                    showPwd(context, chatId, joinCallBack)
                    return
                } else if (code == Data.CODE_ROOM_CLOSED) {
                    joinCallBack.onFail("??????????????????????????????????????????")
//                    ARouter.getInstance().build("/main/main").navigation()
//                    ARouter.getInstance().build("/chatting/roomClosing").navigation()
                    return
                }
                joinCallBack.onFail(msg)
            }

            override fun isAlive(): Boolean {
                return true

            }
        })
    }

    fun reconnection(context: Activity, chatId: String, callback: ChatRoomBaseCallBack) {
        NetService.getInstance(context).reconnection(chatId, object : Callback<ReconnectionBean>() {
            override fun onSuccess(nextPage: Int, bean: ReconnectionBean, code: Int) {
                if (bean.type == 1) {
                    joinChat(context, chatId, bean.password, false, object : JoinChatCallBack {
                        override fun onSuc() {
                            val msgBean = MsgBean(MsgType.JOIN_CHAT, "",
                                    chatId, "", "", 0, 0, 0, null, null, null, toUserInfo = UserInfo(),
                                    fromUserInfo = UserInfo(nickname = DataHelper.getUserInfo()!!.nickname, user_id = DataHelper.getUserInfo()!!.user_id,
                                            wealth_level = DataHelper.getUserInfo()!!.wealth_level, charm_level = DataHelper.getUserInfo()!!.charm_level,
                                            seat_frame = DataHelper.getUserInfo()!!.seat_frame, effects = DataHelper.getUserInfo()!!.effects,
                                            head_img = DataHelper.getUserInfo()!!.head_img, body_img = DataHelper.getUserInfo()!!.body_img,
                                            first_sign = mStatusBean.first_sign))
                            onNewMsg(Gson().toJson(msgBean).toString())
//                            MsgManager.INSTANCE.sendTextMsg(chatId, Gson().toJson(msgBean).toString(), object : MsgManager.MsgCallBack {
//                                override fun onSendSuc() {
//                                    onNewMsg(Gson().toJson(msgBean).toString())
//                                }
//
//                                override fun onSendFail(i: Int, s: String) {
//                                    mMsgManager.sendTextMsg(chatId, Gson().toJson(msgBean).toString(), this)
//                                    LogUtil.e("???????????? $i  $s")
//                                }
//                            })
                            callback.onSuc()
                        }

                        override fun onFail(msg: String) {
                            callback.onFail(msg)
                        }
                    })
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, "????????????")
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    private fun showPwd(context: Context, chatId: String, joinCallBack: JoinChatCallBack) {
        val verifyPwdDialog = VerifyPwdDialog(context)
        verifyPwdDialog.setOnClickListener(object : VerifyPwdDialog.OnClickListener {
            override fun onRefuseClick() {
                joinCallBack.onFail("")
                verifyPwdDialog.dismiss()
            }

            override fun onAgreeClick(str: String) {
                if (TextUtils.isEmpty(str)) {
                    joinCallBack.onFail("")
                    return
                }
                joinChat(context, chatId, password = str, joinCallBack = joinCallBack)
                verifyPwdDialog.dismiss()
            }

        })
        verifyPwdDialog.show()
        verifyPwdDialog.setContent("??????????????????")
    }


    //????????????
    private fun joinAgoraChannel(context: Context, room_id: String, agora_token: String, isOpenChatRoom: Boolean = true, joinCallBack: JoinChatCallBack) {

        if (mChatId != room_id) {
            if (mChatId != "") {
                if (RoomMusicManager.isPlaying()) {
                    RoomMusicManager.stopPlayMusic()
                    RoomMusicManager.quitMusic()
                }
                if (mRtcEngine.leaveChannel() < 0) mRtcEngine.leaveChannel()
            }
        }

        mRtcEngine.setAudioProfile(Constants.AUDIO_PROFILE_MUSIC_STANDARD_STEREO, Constants.AUDIO_SCENARIO_GAME_STREAMING)
        // ??????+????????? ??????????????????
//        mRtcEngine.setParameters("{\"che.audio.enable.agc\":false}")
//        mRtcEngine.setParameters("{\"che.audio.enable.aec\":false}")
//        mRtcEngine.setParameters("{\"che.audio.enable.ns\":false}")
        if (mRtcEngine.joinChannel(agora_token, room_id, "", DataHelper.getUID()) == 0) {
            if (mChatId == room_id) {//??????????????????
                isReJoin = true
            } else {     //???????????????
                isReJoin = false
                if (!TextUtils.isEmpty(mChatId)) {  //????????????????????????????????????
                    NetService.getInstance(mContext).levelChat(mChatId, DataHelper.getUID(), 1, object : Callback<UniqueIdBean>() {
                        override fun onSuccess(nextPage: Int, bean: UniqueIdBean, code: Int) {

                        }

                        override fun onError(msg: String, throwable: Throwable, code: Int) {
                            ToastUtil.error(mContext, msg)
                        }

                        override fun isAlive(): Boolean {
                            return true
                        }
                    })
                    MsgManager.INSTANCE.quitGroup(mChatId, object : MsgManager.MsgCallBack {
                        override fun onSendSuc() {

                        }

                        override fun onSendFail(i: Int, s: String?) {

                        }

                    })
                }
            }
            mChatId = room_id
            GiftManager.getInstance().roomId = room_id
            CommonLib.roomId = room_id
            mRtcEngine.enableAudioVolumeIndication(1000, 3, true)
            if (isOpenChatRoom) {
                loadRoomData(context, mChatId, joinCallBack)
            } else {
                joinCallBack.onSuc()
            }
        } else {
            joinCallBack.onFail("??????????????????")
        }
    }

    @SuppressLint("WrongConstant")
    private fun loadRoomData(context: Context, chatId: String, joinCallBack: JoinChatCallBack) {
        NetService.getInstance(context).getChatRoomInfo(chatId, object : Callback<ChatRoomInfo>() {
            override fun onSuccess(nextPage: Int, bean: ChatRoomInfo, code: Int) {
                mChatRoomInfo = bean
//                headline_cost = bean.user_status.headline_cost
                ARouter.getInstance().build(RouterUrl.partyRoom)
                        .navigation(context)
                setRoomHostId(bean.user_host.user_id)
                setStatusBean(bean.user_status)
                joinCallBack.onSuc()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                mChatId = ""
                joinCallBack.onFail("???????????????????????????")
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    fun setStatusBean(statusBean: UserStatusBean) {
        mStatusBean = statusBean
        mUserMicStatus = statusBean.status
        setRole(mStatusBean.status)
    }

    fun getStatusBean(): UserStatusBean {
        return mStatusBean
    }

    //????????????,?????????????????????or????????????????????????,??????????????????????????????
    fun micCtrl(context: Context, way: Int, switchMic: Boolean, invitedMic: Boolean = false) {
//        loadingDialog = LoadingDialog(context)
        //userInfo.type -1 ?????????????????????0????????????1-8?????????
//        loadingDialog.show()
        NetService.getInstance(context).micCtrl(mChatId, way, object : Callback<MicStatusBean>() {
            override fun onSuccess(nextPage: Int, bean: MicStatusBean, code: Int) {
//                loadingDialog.dismiss()
                mStatusBean.status = bean.status
                if (!switchMic) {
                    if (bean.type == 0) {    //??????
                        setMicPosition(bean.way)
                        sendMsg(MsgType.UP_TO_MIC, DataHelper.getUserInfo()!!.nickname + "?????????", mChatId, unique_id = bean.unique_id)
                        if (invitedMic) {
                            mStatusBean.status = 0
                            val upDialog = CommonDialog(context!!)
                            upDialog.setTitle("????????????")
                            upDialog.setContent("????????????????????????????????????")
                            upDialog.setRightBt("??????") {
                                upDialog.dismiss()
                            }
                            upDialog.show()
                        }
                    } else {
                        setMicPosition(0)
                        sendMsg(MsgType.DOWN_FROM_MIC, DataHelper.getUserInfo()!!.nickname + "?????????", mChatId, unique_id = bean.unique_id)
                    }
                } else {  //??????
                    if (bean.type == 0) {    //??????
                        setMicPosition(bean.way)
                        sendMsg(MsgType.UP_TO_MIC, DataHelper.getUserInfo()!!.nickname + "?????????", mChatId, unique_id = bean.unique_id)
                    }
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                loadingDialog.dismiss()
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }


    //????????????,?????????????????????or????????????????????????,??????????????????????????????
    fun micCtrl(context: Context, way: Int, switchMic: Boolean, invitedMic: Boolean = false, onUpMicListener: OnUpMicListener) {
//        loadingDialog = LoadingDialog(context)
        //userInfo.type -1 ?????????????????????0????????????1-8?????????
//        loadingDialog.show()
        NetService.getInstance(context).micCtrl(mChatId, way, object : Callback<MicStatusBean>() {
            override fun onSuccess(nextPage: Int, bean: MicStatusBean, code: Int) {
//                loadingDialog.dismiss()
                mStatusBean.status = bean.status
                if (onUpMicListener != null) {
                    onUpMicListener.onUpMicSuccess()
                }
                if (!switchMic) {
                    if (bean.type == 0) {    //??????
                        setMicPosition(bean.way)
                        sendMsg(MsgType.UP_TO_MIC, DataHelper.getUserInfo()!!.nickname + "?????????", mChatId, unique_id = bean.unique_id)
                        if (invitedMic) {
                            mStatusBean.status = 1
                            val upDialog = CommonDialog(context!!)
                            upDialog.setTitle("????????????")
                            upDialog.setContent("????????????????????????????????????")
                            upDialog.setRightBt("??????") {
                                upDialog.dismiss()
                            }
                            upDialog.show()
                        }
                    } else {
                        setMicPosition(0)
                        sendMsg(MsgType.DOWN_FROM_MIC, DataHelper.getUserInfo()!!.nickname + "?????????", mChatId, unique_id = bean.unique_id)
                    }
                } else {  //??????
                    if (bean.type == 0) {    //??????
                        setMicPosition(bean.way)
                        sendMsg(MsgType.UP_TO_MIC, DataHelper.getUserInfo()!!.nickname + "?????????", mChatId, unique_id = bean.unique_id)
                    }
                }
                for (itemStr in bean.user_out_list) {
                    var userInfo = UserInfo()
                    userInfo.user_id = itemStr.toInt()
                    userInfo.nickname = itemStr
                    sendMsg(MsgType.REMOVE_MIC, "", mChatId, unique_id = bean.unique_id, toUserInfo = userInfo)
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                loadingDialog.dismiss()
                if (onUpMicListener != null) {
                    onUpMicListener.onUpMicFaile(code, msg)
                }
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    fun setEnableInEarMonitoring(isEnable: Boolean) {
        isEarMonitoring = isEnable
        mRtcEngine.enableInEarMonitoring(isEnable)
        mRtcEngine.setInEarMonitoringVolume(80)
    }

    fun setVoiceChange(type: Int) {
        voiceType = type
        mRtcEngine.setLocalVoiceChanger(type)
    }

    fun setBGMVolume(volume: Int) {
        SPUtil.putInt(SPUtil.CONFIG_MUSIC_VOLUME, volume)
        mRtcEngine.adjustAudioMixingVolume(volume)
    }

    fun setRecordingVolume(volume: Int) {
        SPUtil.putInt(SPUtil.CONFIG_RECORD_VOLUME, volume)
        mRtcEngine.adjustRecordingSignalVolume(volume)
        mRecordVolumeObservable.changeValue(volume)
    }

    fun getBGMVolume(): Int {
        return SPUtil.getInt(SPUtil.CONFIG_MUSIC_VOLUME, 100)
    }

    fun getRecordingVolume(): Int {
        return SPUtil.getInt(SPUtil.CONFIG_RECORD_VOLUME, 100)
    }

    fun applyMic(context: Context) {
        NetService.getInstance(context).applyMic(mChatId, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                ToastUtil.suc(context, "????????????")
                sendMsg(MsgType.APPLY_MIC, DataHelper.getUserInfo()!!.nickname + "????????????", mChatId)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    fun remoteVoiceCtrl(close: Boolean) {
        isCloseVoice = close
        mRtcEngine.muteAllRemoteAudioStreams(close)
    }

    fun micCtrl4Host(context: Context, userInfo: UserInfo) {
        val type = if (userInfo.type == 0) {//-1??????????????????0????????????1-8???????????????
            -1
        } else {
            0
        }

        if (type == 0) {
            NetService.getInstance(context).chatDwon(mChatId, userInfo.user_id, object : Callback<MicStatusBean>() {
                override fun onSuccess(nextPage: Int, bean: MicStatusBean, code: Int) {
                    //????????????
                    userInfo.type = 0
                    sendMsg(MsgType.REMOVE_MIC, "", mChatId, unique_id = bean.unique_id, toUserInfo = userInfo)

                }

                override fun onError(msg: String, throwable: Throwable, code: Int) {
                    ToastUtil.error(context, msg)
                }

                override fun isAlive(): Boolean {
                    return true

                }
            })
        } else {
            NetService.getInstance(context).micCtrl4Host(mChatId, userInfo.user_id, type, object : Callback<MicStatusBean>() {
                override fun onSuccess(nextPage: Int, bean: MicStatusBean, code: Int) {
                    when (type) {
                        -1 -> {
                            //????????????
                            sendMsg(MsgType.INVITE_TO_MIC, "", mChatId, unique_id = bean.unique_id, toUserInfo = userInfo)
                        }
                        0 -> {
                            //????????????
                            userInfo.type = 0
                            sendMsg(MsgType.REMOVE_MIC, "", mChatId, unique_id = bean.unique_id, toUserInfo = userInfo)
                        }
                        -2 -> {
                            userInfo.type = bean.type  //-1????????????0?????????
                            sendMsg(MsgType.LOCK_MIC, "", mChatId, unique_id = bean.unique_id, toUserInfo = userInfo)
                        }
                    }
                }

                override fun onError(msg: String, throwable: Throwable, code: Int) {
                    ToastUtil.error(context, msg)
                }

                override fun isAlive(): Boolean {
                    return true

                }
            })
        }
    }

    fun micCtrl4XqHost(context: Context, userid: String) {
        NetService.getInstance(context).micCtrl(mChatId, -1, object : Callback<MicStatusBean>() {
            override fun onSuccess(nextPage: Int, bean: MicStatusBean, code: Int) {
//                loadingDialog.dismiss()
                setMicPosition(bean.way)
                sendMsg(MsgType.UP_TO_MIC, DataHelper.getUserInfo()!!.nickname + "?????????", mChatId, unique_id = bean.unique_id)

            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                loadingDialog.dismiss()
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    fun applyMicOpt(context: Context, type: Int, userInfo: UserInfo, callback: ChatRoomBaseCallBack) {
        NetService.getInstance(context).applyMicOpt(mChatId, userInfo.user_id, type, object : Callback<MicStatusBean>() {
            override fun onSuccess(nextPage: Int, bean: MicStatusBean, code: Int) {
                when (type) {
                    0 -> { //????????????
                        userInfo.type = bean.way
                        userInfo.status = bean.status
                        sendMsg(MsgType.APPLY_MIC_AGREE, "????????????", mChatId, toUserInfo = userInfo)
                    }
                    1 -> { //????????????
                        sendMsg(MsgType.APPLY_MIC_DENY, "????????????", mChatId, toUserInfo = userInfo)
                    }
                }
                callback.onSuc()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
                callback.onFail(msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    fun lockMic(context: Context, type: Int, userInfo: UserInfo, position: Int) {
        NetService.getInstance(context).lockMic(mChatId, type, position, object : Callback<MicStatusBean>() {
            override fun onSuccess(nextPage: Int, bean: MicStatusBean, code: Int) {
                userInfo.user_id = bean.type  //-1????????????0?????????
//                userInfo.type = position
                sendMsg(MsgType.LOCK_MIC, "", mChatId, unique_id = bean.unique_id, toUserInfo = userInfo)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true

            }
        })
    }

    fun clearMike(context: Context, micWay: Int) {
        NetService.getInstance(context).clearMike(mChatId, micWay, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                sendMsg(MsgType.CLEAR_MIKE, "", mChatId, toUserInfo = UserInfo(type = micWay))
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    fun turnoffLight(context: Context) {
        NetService.getInstance(context).TurnLight(mChatId, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {

            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    fun banMic(context: Context, micWay: Int, userInfo: UserInfo) {
        NetService.getInstance(context).chatBarley(mChatId, userInfo.user_id, micWay, object : Callback<MicStatusBean>() {
            override fun onSuccess(nextPage: Int, bean: MicStatusBean, code: Int) {
                userInfo.status = bean.type
                sendMsg(MsgType.BAN_MIC, "", mChatId, unique_id = bean.unique_id, toUserInfo = userInfo)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    fun banUser4Host(context: Context, userInfo: UserInfo, opt: Int) {
//        if (userInfo.status == 1 && opt == 0) {
//            ToastUtil.suc(this, "???????????????")
//            return
//        }
        NetService.getInstance(context).banUser4Host(mChatId, userInfo.user_id, opt, object : Callback<BanUserStatusBean>() {
            override fun onSuccess(nextPage: Int, bean: BanUserStatusBean, code: Int) {
                if (opt == 0) { //??????
                    if (bean.type == 0) {      //z????????????
                        userInfo.status = 0
                    } else {      //??????
                        userInfo.status = 2
                    }
                    sendMsg(MsgType.BAN_USER_MIC, "", mChatId, unique_id = bean.unique_id, toUserInfo = userInfo)
                } else {  // ??????
                    if (bean.type == 0) {      //z????????????
                        userInfo.speak = 0
                    } else {      //??????
                        userInfo.speak = 1
                    }
                    val content = if (userInfo.speak == 0) {
                        userInfo.nickname + "???????????????"
                    } else {
                        DataHelper.getUserInfo()!!.nickname + "?????????" + userInfo.nickname
                    }
                    sendMsg(MsgType.BAN_USER_WORD, content, mChatId, unique_id = bean.unique_id, toUserInfo = userInfo)
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true

            }
        })
    }

    public fun closeChat(type: Int) {
        if (getUserRole() > 0) {
            NetService.getInstance(mContext).chatCloseScreen(type.toString(), getRoomId(), object : Callback<BaseBean>() {
                override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                    if (type == 1) {
                        sendMsg(MsgType.CLOSE_CHAT, "${DataHelper.getUserInfo()!!.nickname}?????????????????????", getRoomId())

                    } else {
                        sendMsg(MsgType.OPEN_CHAT, "${DataHelper.getUserInfo()!!.nickname}?????????????????????", getRoomId())
                    }
                }

                override fun onError(msg: String, throwable: Throwable, code: Int) {
                    ToastUtil.suc(mContext, msg)
                }

                override fun isAlive(): Boolean {
                    return true
                }
            })
        } else {
            ToastUtil.suc(mContext, "?????????????????????")
        }
    }

    fun sendText(content: String) {
        sendMsg(MsgType.TEXT_MSG, content, mChatId)
    }

    fun kickOut(context: Context, userInfo: UserInfo) {
        NetService.getInstance(context).kickOut(mChatId, userInfo.user_id, object : Callback<UniqueIdBean>() {
            override fun onSuccess(nextPage: Int, bean: UniqueIdBean, code: Int) {
                if (mCallback != null) {
                    mCallback!!.kickOut()
                }
                sendMsg(MsgType.KICK_OUT, DataHelper.getUserInfo()!!.nickname + "?????????" + userInfo.nickname, mChatId, unique_id = bean.unique_id, toUserInfo = userInfo)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true

            }

        })
    }

    fun openMic(context: Context) {
        if (mStatusBean.status == 2) {
            ToastUtil.suc(mContext, "?????????????????????")
            return
        }
        NetService.getInstance(context).openMic(mChatId, if (!isMicEnable()) {
            0
        } else {
            1
        }, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                val status = if (!isMicEnable()) {
                    0
                } else {
                    1
                }
                setRole(status)
                mUserMicStatus = status
                sendMsg(MsgType.MIC_CTRL, "", mChatId)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true

            }

        })
    }

//    fun closeLiveForManager(context: Context, icon: String) {
//        isSelfCloseLive = true
//        NetService.getInstance(mContext!!).sociatyChatClose(mChatId, object : Callback<LiveFinishBean>() {
//            override fun onSuccess(nextPage: Int, bean: LiveFinishBean, code: Int) {
//                bean.icon = icon
////                LiveFinishDialog(context, bean).show()
//                var info = DataHelper.getUserInfo()
//                if (mChatId == info!!.room_id) {
//                    info.room_status = 1
//                    DataHelper.updatalUserInfo(info)
//                }
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                isSelfCloseLive = false
//            }
//
//            override fun isAlive(): Boolean {
//                return isAlive()
//            }
//        })
//    }


    fun leaveChat() {
        if (mRtcEngine.leaveChannel() < 0) mRtcEngine.leaveChannel()
        mRoomGoodNumber = 0
        if (TextUtils.isEmpty(mChatId)) {
            return
        }
        NetService.getInstance(mContext).levelChat(mChatId, DataHelper.getUID(), 1, object : Callback<UniqueIdBean>() {
            override fun onSuccess(nextPage: Int, bean: UniqueIdBean, code: Int) {
                CommonLib.isInRoom = false
                roomStatus = RoomStatus.status_default
                mChatRoomInfo = null
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(mContext, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
        MsgManager.INSTANCE.quitGroup(mChatId, object : MsgManager.MsgCallBack {
            override fun onSendSuc() {

            }

            override fun onSendFail(i: Int, s: String?) {

            }

        })
        mChatId = ""
        RoomMusicManager.quitMusic()
        if (mCallback != null) {
            mCallback!!.levelChat()
        }
    }

    fun leaveChat(leavelRoomCallBack: ChatRoomLeaveRoomCallBack) {
        if (mRtcEngine.leaveChannel() < 0) mRtcEngine.leaveChannel()
        mRoomGoodNumber = 0
        if (TextUtils.isEmpty(mChatId)) {
            return
        }
        NetService.getInstance(mContext).levelChat(mChatId, DataHelper.getUID(), 1, object : Callback<UniqueIdBean>() {
            override fun onSuccess(nextPage: Int, bean: UniqueIdBean, code: Int) {
                CommonLib.isInRoom = false
                MsgManager.INSTANCE.quitGroup(mChatId, object : MsgManager.MsgCallBack {
                    override fun onSendSuc() {

                    }

                    override fun onSendFail(i: Int, s: String?) {

                    }

                })
                if (leavelRoomCallBack != null) {
                    leavelRoomCallBack!!.leaveSucceed()
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(mContext, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
        mChatId = ""
        RoomMusicManager.quitMusic()
        if (mCallback != null) {
            mCallback!!.levelChat()
        }
    }

    /**
     * status 0?????????
     *        1?????????????????????
     *        2???????????????
     */
    private fun setRole(status: Int) {
        mStatusBean.status = status

        //????????????????????????
        if (mRoomType == ROOM_TYPE_RADIO) {
            if (isVoiceLive) {
                mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
                mRtcEngine.enableLocalAudio(true)
            } else {
                RoomMusicManager.stopPlayMusic()
                mRtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE)
                mRtcEngine.enableLocalAudio(false)
            }
        } else {
            if (isRoomHost()) {
                mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
                mRtcEngine.enableLocalAudio(true)
            } else {
                if (mStatusBean.type != 0 && isVoiceLive) {
                    mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
                    mRtcEngine.enableLocalAudio(true)
                } else {
                    RoomMusicManager.stopPlayMusic()
                    mRtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE)
                    mRtcEngine.enableLocalAudio(false)
                }
            }
        }

        when (status) {
            0 -> {
                setRecordingVolume(100)
            }
            1 -> {
                if (mCallback != null) {
                    val uidMap = HashMap<Int, Int>()
                    mCallback!!.speaking(uidMap)
                }
                setRecordingVolume(0)
            }
            2 -> {
                setRecordingVolume(0)
                if (RoomMusicManager.isPlaying()) {
                    RoomMusicManager.pausePlayMusic()
                }
            }
        }
    }

    fun setMicPosition(way: Int) {
        mStatusBean.type = way
        isVoiceLive = way != 0
        setRole(mStatusBean.status)
    }


    fun isMicEnable(): Boolean {
        return mStatusBean.status == 0
    }

    fun getMicPosition(): Int {
        return mStatusBean.type
    }

    fun getNoticeMsgBean(notice: String): MsgBean {
        return MsgBean(MsgType.NOTICE, notice, mChatId, "", "", 0, 0, 0, null, null, null, toUserInfo = UserInfo(), fromUserInfo = UserInfo())
    }

    fun sendEmoji(bean: EmojiItemBean) {
        if (mCallback != null) {
            mCallback!!.sendEmoji()
        }
        sendMsg(MsgType.EMOJI, "", mChatId, emojiBean = bean)
        isEmojiClickEnable = false
        Handler().postDelayed({
            isEmojiClickEnable = true
        }, 3000)
    }

    fun getMicStatus(): Int {
        return mStatusBean.status
    }

    fun setUserRole(role: Int, userInfo: UserInfo) {
        val msg = if (role == 0) {
            userInfo.nickname + "???????????????????????????"
        } else {
            userInfo.nickname + "?????????????????????"
        }
        userInfo.user_role = role
        sendMsg(type = MsgType.ROLE_SET, content = msg, sendChatId = mChatId, toUserInfo = userInfo)
    }


    fun sendMsg(type: MsgType, content: String, sendChatId: String, unique_id: String? = "", giftBean: MsgGiftBean? = null, emojiBean: EmojiItemBean? = null, toUserInfo: UserInfo = UserInfo()) {
        val fromUserInfo = UserInfo()
        var localUserInfo = DataHelper.getUserInfo()
        fromUserInfo.nickname = localUserInfo!!.nickname
        fromUserInfo.gender = localUserInfo!!.gender
//        fromUserInfo.age = localUserInfo!!.age
        fromUserInfo.recharge_residue = 0
        fromUserInfo.face = localUserInfo!!.face
        fromUserInfo.type = mStatusBean.type
        fromUserInfo.state = mStatusBean.state
        fromUserInfo.status = mStatusBean.status
        fromUserInfo.speak = mStatusBean.speak
        fromUserInfo.replaceArr = localUserInfo!!.replaceArr
        fromUserInfo.medal_name = localUserInfo!!.medal_name
        fromUserInfo.user_id = localUserInfo!!.user_id
        fromUserInfo.mic_speaking = mStatusBean.mic_speaking
        fromUserInfo.wealth_level = localUserInfo!!.wealth_level
        fromUserInfo.charm_level = localUserInfo!!.charm_level
        fromUserInfo.user_role = getUserRole()
        fromUserInfo.effects = localUserInfo!!.effects
//        fromUserInfo.body_img = localUserInfo!!.body_img
        fromUserInfo.seat_frame = localUserInfo!!.seat_frame
        fromUserInfo.good_number = localUserInfo!!.good_number
        fromUserInfo.good_number_state = localUserInfo!!.good_number_state
//        fromUserInfo.city = localUserInfo!!.city
//        fromUserInfo.fans_number = localUserInfo!!.fans_number
        fromUserInfo.activity_pic = localUserInfo!!.activity_pic
        fromUserInfo.mystery = localUserInfo!!.mystery
        fromUserInfo.seat = localUserInfo!!.seat
        fromUserInfo.medal = localUserInfo!!.medal
        fromUserInfo.medal_img = localUserInfo!!.medal_img
        fromUserInfo.super_manager = mStatusBean.super_manager
//        fromUserInfo.effect_svga = localUserInfo!!.effect_svga ?: ""
        fromUserInfo.rank_id = localUserInfo.rank_id
        val msgBean = MsgBean(type, content, sendChatId, sendChatId, unique_id, 0, 0, 0, null, giftBean, emojiBean, toUserInfo = toUserInfo, fromUserInfo = fromUserInfo)
        onNewMsg(Gson().toJson(msgBean).toString())
        mMsgManager.sendTextMsg(sendChatId, Gson().toJson(msgBean).toString(), object : MsgManager.MsgCallBack {
            override fun onSendSuc() {
            }

            override fun onSendFail(i: Int, s: String) {
                mMsgManager.sendTextMsg(sendChatId, Gson().toJson(msgBean).toString(), this)
            }
        })
    }

    fun sendZSRewardMsg(type: MsgType, watertype: Int, content: String, sendChatId: String, unique_id: String? = "", giftBean: MsgGiftBean? = null) {
        val fromUserInfo = UserInfo()
        var localUserInfo = DataHelper.getUserInfo()
        fromUserInfo.nickname = localUserInfo!!.nickname
        fromUserInfo.gender = localUserInfo!!.gender
        fromUserInfo.replaceArr = localUserInfo!!.replaceArr
        fromUserInfo.medal_name = localUserInfo!!.medal_name
//        fromUserInfo.age = localUserInfo!!.age
        fromUserInfo.recharge_residue = 0
        fromUserInfo.face = localUserInfo!!.face
        fromUserInfo.type = mStatusBean.type
        fromUserInfo.state = mStatusBean.state
        fromUserInfo.status = mStatusBean.status
        fromUserInfo.speak = mStatusBean.speak
//        fromUserInfo.city = localUserInfo!!.city
        fromUserInfo.user_id = localUserInfo!!.user_id
        fromUserInfo.mic_speaking = mStatusBean.mic_speaking
        fromUserInfo.wealth_level = localUserInfo!!.wealth_level
        fromUserInfo.charm_level = localUserInfo!!.charm_level
        fromUserInfo.user_role = getUserRole()
        fromUserInfo.effects = localUserInfo!!.effects
//        fromUserInfo.head_img = localUserInfo!!.head_img
//        fromUserInfo.body_img = localUserInfo!!.body_img
        fromUserInfo.seat_frame = localUserInfo!!.seat_frame
        fromUserInfo.good_number = localUserInfo!!.good_number
        fromUserInfo.good_number_state = localUserInfo!!.good_number_state
//        fromUserInfo.city = localUserInfo!!.city
//        fromUserInfo.fans_number = localUserInfo!!.fans_number
        fromUserInfo.activity_pic = localUserInfo!!.activity_pic
        fromUserInfo.mystery = localUserInfo!!.mystery
        fromUserInfo.seat = localUserInfo!!.seat
        fromUserInfo.medal = localUserInfo!!.medal
        fromUserInfo.super_manager = mStatusBean.super_manager
//        fromUserInfo.effect_svga = localUserInfo!!.effect_svga ?: ""
        fromUserInfo.rank_id = localUserInfo.rank_id
        val msgBean = MsgBean(type, content, sendChatId, sendChatId, unique_id, watertype, 0, 0, null, giftBean, null, toUserInfo = UserInfo(), fromUserInfo = fromUserInfo)
        onNewMsg(Gson().toJson(msgBean).toString())
        mMsgManager.sendTextMsg(sendChatId, Gson().toJson(msgBean).toString(), object : MsgManager.MsgCallBack {
            override fun onSendSuc() {
            }

            override fun onSendFail(i: Int, s: String) {
                mMsgManager.sendTextMsg(sendChatId, Gson().toJson(msgBean).toString(), this)
            }
        })
    }

    override fun onNewMsg(text: String): Boolean {
        if (TextUtils.isEmpty(text)) {
            return false
        }
        LogUtil.i(text)

        var msgBean: MsgBean? = null
        try {
            msgBean = Gson().fromJson(text, MsgBean::class.java) ?: return false
        } catch (e: Exception) {
            return false
        }

        if (msgBean.chatId != mChatId && msgBean.chatId != DataHelper.getIMDevelop().imBigGroupID) {
            return false
        }
//        if (mUpdateCallBack == null) {
//            return false
//        }
        if (msgBean.opt == null) {
            return false
        }

        when (msgBean.opt) {
            MsgType.JOIN_CHAT -> {
                if (msgBean.fromUserInfo.super_manager != 1) {
                    //?????????????????????
                    if (msgBean.fromUserInfo.effects > 0 || !TextUtils.isEmpty(msgBean.fromUserInfo.seat)) {
                        GiftManager.getInstance().addEnterRoom(msgBean.fromUserInfo)
                    }
                    addWordList(msgBean)
                    if (mUpdateCallBack == null) {
                        return false
                    }
                    mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
                }
            }
            MsgType.UP_TO_MIC -> {
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
                if (msgBean.fromUserInfo.user_id == DataHelper.getUID()) mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_BOTTOM, arrayListOf(msgBean))
            }    //??????????????????????????????
            MsgType.DOWN_FROM_MIC -> {
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
                if (msgBean.fromUserInfo.user_id == DataHelper.getUID()) mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_BOTTOM, arrayListOf(msgBean))
            }
            MsgType.APPLY_MIC -> {
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_BOTTOM, arrayListOf(msgBean))
            }
            MsgType.XIANGQING_INVITE -> {
                var inviteBean: InviteBean? = null
                try {
                    inviteBean = Gson().fromJson(text, InviteBean::class.java) ?: return false
                } catch (e: Exception) {
                    return false
                }
                onChatRoomLightCallBack!!.onInVite(inviteBean)
            }
            MsgType.XIANGQING_FAIL -> {
                var xqResultBean: XqResultBean? = null
                try {
                    xqResultBean = Gson().fromJson(text, XqResultBean::class.java) ?: return false
                } catch (e: Exception) {
                    return false
                }
                onChatRoomLightCallBack!!.onUpdateHand(ChatRoomUpdateViewCallBack.XIANGQING_FAIL, xqResultBean)
            }
            MsgType.XIANGQING_SUCCESS -> {
                var xqResultBean: XqResultBean? = null
                try {
                    xqResultBean = Gson().fromJson(text, XqResultBean::class.java) ?: return false
                } catch (e: Exception) {
                    return false
                }
                onChatRoomLightCallBack!!.onUpdateHand(ChatRoomUpdateViewCallBack.XIANGQING_SUCCESS, xqResultBean)
            }
            //??????????????????
            MsgType.XIANGQING_POWER_FANZHUAN -> {
                var lightUpBean: LightUpBean? = null
                LogUtil.i(text)
                try {
                    lightUpBean = Gson().fromJson(text, LightUpBean::class.java) ?: return false
                } catch (e: Exception) {
                    return false
                }
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onLigHtUp(ChatRoomUpdateViewCallBack.XIANGQING_POWER_FANZHUAN, lightUpBean)
            }
            //??????
            MsgType.XIANGQING_BRIGHT -> {
                var lightUpBean: LightUpBean? = null
                LogUtil.i(text)

                try {
                    lightUpBean = Gson().fromJson(text, LightUpBean::class.java) ?: return false
                } catch (e: Exception) {
                    return false
                }
                if (mUpdateCallBack == null) {
                    return false
                }

                mUpdateCallBack!!.onLigHtUp(ChatRoomUpdateViewCallBack.XIANGQING_BRIGHT, lightUpBean)
            }
            MsgType.APPLY_MIC_AGREE -> {
                if (msgBean.toUserInfo != null && msgBean.toUserInfo.user_id == DataHelper.getUserInfo()!!.user_id) {
                    mStatusBean.status = msgBean.toUserInfo.status
                    setMicPosition(msgBean.toUserInfo.type)
                }
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_BOTTOM, arrayListOf(msgBean))
            }
            MsgType.APPLY_MIC_DENY -> {
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_BOTTOM, arrayListOf(msgBean))
                if (msgBean.toUserInfo != null && msgBean.toUserInfo.user_id == DataHelper.getUserInfo()!!.user_id) {
                    ToastUtil.suc(mContext, "???????????????????????????")
                }
            }
            MsgType.BAN_USER_MIC -> {
                if (msgBean.toUserInfo != null && DataHelper.getUserInfo()!!.user_id == msgBean.toUserInfo.user_id && mStatusBean.type != 0) {  //???????????????????????????
                    if (mUserMicStatus == 1 && msgBean.toUserInfo.status == 0) {
                        msgBean.toUserInfo.status = 1
                    }
                    setRole(msgBean.toUserInfo.status)
                    if (mUpdateCallBack == null) {
                        return false
                    }
                    mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_BOTTOM, arrayListOf(msgBean))
//                    updateBottomView(msgBean.toUserInfo.status == 0, true)
                }
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
//                updateMicView(msgBean.toUserInfo)
            }
            MsgType.XIANGQING_QUEUE -> {
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.QUIT_XIANGQING_QUEUE, arrayListOf(msgBean))
            }

            MsgType.XIANGQING_NEXT_STEP -> {
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.XIANGQING_NEXT_STEP, arrayListOf(msgBean))
            }
            MsgType.BAN_USER_WORD -> {
                if (msgBean.toUserInfo != null && DataHelper.getUserInfo()!!.user_id == msgBean.toUserInfo.user_id) {
                    if (msgBean.toUserInfo.speak == 1) {
                        ToastUtil.suc(mContext, "???????????????")
                    } else {
                        ToastUtil.suc(mContext, "???????????????")
                    }
                    mStatusBean.speak = msgBean.toUserInfo.speak
                }
                addWordList(msgBean)
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
            }

            MsgType.INVITE_TO_MIC -> {
                if (msgBean.toUserInfo != null && DataHelper.getUserInfo()!!.user_id == msgBean.toUserInfo.user_id) {
                    if (mUpdateCallBack == null) {
                        return false
                    }
                    mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_HOST, arrayListOf(msgBean))
                }
            }
            MsgType.REMOVE_MIC -> {
                if (msgBean.toUserInfo != null && DataHelper.getUserInfo()!!.user_id == msgBean.toUserInfo.user_id) {
//                    micCtrl(this, 0, false)
                    setMicPosition(0)
                    sendMsg(MsgType.DOWN_FROM_MIC, DataHelper.getUserInfo()!!.nickname + "?????????", mChatId)
                }
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
                if (msgBean.fromUserInfo.user_id == DataHelper.getUID()) mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_BOTTOM, arrayListOf(msgBean))
            }
            MsgType.LOCK_MIC -> {
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
            }
            MsgType.KICK_OUT -> {
                if (msgBean.toUserInfo.type != 0) {       //??????????????????????????????
                    if (mUpdateCallBack == null) {
                        return false
                    }
                    mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
                }
                if (msgBean.toUserInfo != null && DataHelper.getUserInfo()!!.user_id == msgBean.toUserInfo.user_id) {
                    leaveChat()
                }
            }
            MsgType.LEVEL_CHAT -> {
                msgBean.fromUserInfo?.apply {
                    user_id?.apply {
                        if (this == DataHelper.getUserInfo()!!.user_id) {
                            var type = mStatusBean.type
                            setMicPosition(0)
                            if (type != 0) micCtrl(mContext, type, false)
                            if (mUpdateCallBack == null) {
                                return false
                            }
                            mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_BOTTOM, arrayListOf(msgBean))
                        }
                        if (this == 1) {
                            leaveChat()
                        }
                        if (mUpdateCallBack == null) {
                            return false
                        }
                        mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
                    }
                }
            }
            MsgType.UPDATE_CHAT_INFO -> {
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_ALL, arrayListOf(msgBean))
            }
            MsgType.GIFT -> {

                if (msgBean.giftBean == null) {
                    return true
                }
//                //????????????
//                if(msgBean.giftBean!!.giftType == GiftManager.GIFT_TYPE_LUCK){
//                    if (msgBean.giftBean!!.giftRewardType > 0){
//                        addWordList(msgBean)
//                        mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_WORD, arrayListOf(msgBean))
//                    }
//                    mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_GIFT_LUCK, arrayListOf(msgBean))
//                    return true
//                }
                //????????????
                if (msgBean.giftBean!!.giftType == GiftManager.GIFT_TYPE_CHEST) {
                    if (!TextUtils.isEmpty(msgBean.giftBean!!.giftChestName)) {
                        addWordList(msgBean)
                        if (mUpdateCallBack == null) {
                            return false
                        }
                        mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
                    }
                    if (mUpdateCallBack == null) {
                        return false
                    }
                    mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_GIFT_CHEST, arrayListOf(msgBean))
                    return true
                }
                //????????????&????????????
                addWordList(msgBean)
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_HOST, arrayListOf(msgBean))
            }
            MsgType.TEXT_MSG, MsgType.NOTICE, MsgType.OFFICE_MSG, MsgType.ROOM_COLLECT, MsgType.GAME_LSP_MSG, MsgType.GAME_LSP_RESULT -> {
                addWordList(msgBean)
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
            }
            MsgType.MIC_CTRL -> {
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))  //updateMicView(msgBean.fromUserInfo)
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_BOTTOM, arrayListOf(msgBean))  //updateMicView(msgBean.fromUserInfo)
            }
            MsgType.EMOJI -> {
                if (msgBean.emojiBean!!.emoji_group_id == 1 && msgBean.emojiBean!!.emoji_id == EmojiConfig.EMOJI_SZ) {
                    Handler().postDelayed({
                        addWordList(msgBean)
                        if (mUpdateCallBack != null) {
                            mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
                        }
                    }, 2000)
                } else if (msgBean.emojiBean!!.emoji_group_id == 1 && msgBean.emojiBean!!.emoji_id == EmojiConfig.EMOJI_MXJ) {
                    Handler().postDelayed({
                        addWordList(msgBean)
                        if (mUpdateCallBack != null) {
                            mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
                        }
                    }, 2000)
                } else {
                    addWordList(msgBean)
                    if (mUpdateCallBack == null) {
                        return false
                    }
                    mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
                }
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_HOST, arrayListOf(msgBean))
            }
            MsgType.ROLE_SET -> {
                if (msgBean.toUserInfo != null && DataHelper.getUID() == msgBean.toUserInfo.user_id) {
                    if (mUpdateCallBack == null) {
                        return false
                    }
                    mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_ALL, arrayListOf(msgBean))
                }
            }
            MsgType.SUPER_NOBLE_NOTICE -> {
                val bean = NobleOnlineBean(msgBean.fromUserInfo.face, msgBean.fromUserInfo.nickname, msgBean.fromUserInfo.rank_id)
                GiftManager.getInstance().addNobleOnline(bean)
            }
            MsgType.FULL_SERVICE_GIFT -> {
                if (msgBean.giftBean!!.giftType == GiftManager.GIFT_TYPE_EXPRESS) {
                    val bean = TopNotifyBean()
                    bean.roomId = msgBean.roomId
                    bean.fromUserAvatar = msgBean.fromUserInfo.face
                    bean.toUserAvatar = msgBean.toUserInfo.face
                    bean.giftIcon = msgBean.giftBean!!.giftIcon
                    bean.content = msgBean.content
                    bean.fromUserName = msgBean.fromUserInfo.nickname
                    bean.toUserName = msgBean.toUserInfo.nickname
                    bean.roomId = msgBean.roomId
                    GiftManager.getInstance().addExpressTopNotifyAnim(bean)
                } else {
                    val bean = TopNotifyBean("??????????????????????????????<font color='#FFDD7E'>${msgBean.fromUserInfo.nickname} </font>????????? ${msgBean.giftBean!!.giftName}???????????????~",
                            TopNotifyBean.TNB_GIFT_TOP_QF)
                    bean.roomId = msgBean.roomId
                    GiftManager.getInstance().addTopNotifyGiftAnim(bean)
                }

            }
            MsgType.REFRESH_MIC -> {
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
            }
            MsgType.WINNING_MSG -> {
                addWordList(msgBean)
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
                if (msgBean.giftBean!!.giftPrice >= 5000) {
                    var bean = TopNotifyBean("?????????<font color='#FFDD7E'> " +
                            "${msgBean.fromUserInfo.nickname} </font>????????????????????????<font color='#FFDD7E'> ${msgBean.giftBean!!.giftName} x ${msgBean.giftBean!!.giftNum} </font>",
                            TopNotifyBean.TNB_GIFT_TOP_XY)
                    bean.roomId = msgBean.roomId
                    GiftManager.getInstance().addTopNotifyGiftAnim(bean)
                }
            }

            MsgType.ZS_WINNING_MSG -> {
                addWordList(msgBean)
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
            }
            MsgType.CLEAR_CHAT -> {
                mWordList.clear()
                addWordList(msgBean)
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)

            }
            MsgType.CLOSE_CHAT -> {
                mCloseChat = 1
                addWordList(msgBean)
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_INPUT, arrayListOf(msgBean))
            }
            MsgType.OPEN_CHAT -> {
                mCloseChat = 0
                addWordList(msgBean)
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
            }
            MsgType.BAN_MIC -> {
                if (msgBean.toUserInfo != null && DataHelper.getUserInfo()!!.user_id == msgBean.toUserInfo.user_id) {
                    setRole(msgBean.toUserInfo.status)
                    if (mUpdateCallBack == null) {
                        return false
                    }
                    mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_BOTTOM, arrayListOf(msgBean))
                }
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
            }
            MsgType.CLEAR_MIKE -> {
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_MIC, arrayListOf(msgBean))
            }

            MsgType.FULL_SERVICE_MSG -> {
                if (msgBean.type.toString() != "1") {
                    addWordList(msgBean)
                    if (mUpdateCallBack == null) {
                        return false
                    }
                    mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
                } else {
                    if (mUpdateCallBack == null) {
                        return false
                    }
                    mUpdateCallBack!!.onUpdateTopMsg(msgBean)
                }
            }

            MsgType.DELETE_GROUP -> {
                if (mRtcEngine.leaveChannel() < 0) mRtcEngine.leaveChannel()
                mRoomGoodNumber = 0
                mChatId = ""
                RoomMusicManager.quitMusic()
                mCallback?.apply { levelChat() }
                mOnLiveFinishCallBack?.apply { onLiveFinish() }
                var info = DataHelper.getUserInfo()
                if (msgBean.chatId == info!!.room_id) {
                    info.room_status = 1
                    DataHelper.updatalUserInfo(info)
                }
            }
            MsgType.FULL_SERVICE_SMALL_GIFT -> {
                val bean = TopNotifyBean(msgBean.content, TopNotifyBean.TNB_GIFT_TOP_QF)
                bean.roomId = msgBean.roomId
                GiftManager.getInstance().addTopNotifyGiftAnim(bean)
            }

            MsgType.GAME_CB_FULL_SERVICE_PUT -> {
                val content = "<font color='#FFEC80'>${msgBean.fromUserInfo.nickname}" +
                        "</font>???<font color='#FFEC80'>${msgBean.RoomInfo.roomName}</font>??????????????????<font color='#FFDD7E'>??????${msgBean.giftBean!!.giftPrice}??????</font>?????????"
                var bean = TopNotifyBean(content, TopNotifyBean.TNB_GIFT_TOP_TREASURES)
                GiftManager.getInstance().addTopNotifyGiftAnim(bean)
            }

            MsgType.GAME_CB_FULL_SERVICE_REWARD -> {
                val content = "<font color='#FFEC80'>${msgBean.fromUserInfo.nickname}" +
                        "</font>???<font color='#FFEC80'>${msgBean.RoomInfo.roomName}</font>??????????????????<font color='#FFDD7E'>??????${msgBean.giftBean!!.giftPrice}??????</font>?????????"
                var bean = TopNotifyBean(content, TopNotifyBean.TNB_GIFT_TOP_TREASURES)
                GiftManager.getInstance().addTopNotifyGiftAnim(bean)
            }
            MsgType.RED_PACKET_MSG, MsgType.CB_FULL_SERVICE_PUT, MsgType.CB_FULL_SERVICE_REWARD -> {
                addWordList(msgBean)
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
                mUpdateCallBack!!.onUpdate(ChatRoomUpdateViewCallBack.TYPE_RED_PACKET, arrayListOf(msgBean))
            }
            MsgType.RED_PACKET_BOARDCAST -> {
                val content = "<font color='#FFEC80'>${msgBean.fromUserInfo.nickname}" +
                        "</font>???<font color='#FFEC80'>${msgBean.RoomInfo.roomName}</font>??????????????????</font>${msgBean.collection?.diamonds}??????</font>?????????"
                var bean = TopNotifyBean(content, TopNotifyBean.TNB_GIFT_TOP_XY)
                bean.roomId = msgBean.roomId
                GiftManager.getInstance().addTopNotifyGiftAnim(bean)
            }
            MsgType.NOBLE_SCREEN_ANIMATION -> {
                GiftManager.getInstance().addSvgaAnim(msgBean.url, msgBean.content)
            }
            MsgType.NOBLE_PAYMENT_BROADCAST -> {
                val bean = TopNotifyBean(msgBean.content, TopNotifyBean.TNB_GIFT_TOP_OPEN_NOBLE)
                bean.roomId = msgBean.roomId
                GiftManager.getInstance().addTopNotifyGiftAnim(bean)
            }
            MsgType.GAME_XY_ARRIVE_SERVICE_REWARD -> {
                val bean = TopNotifyBean("????????????????????????????????????????????????<font color='#FFEC80'>${msgBean.giftBean!!.giftPrice}</font> ???????????????????????????", TopNotifyBean.TNB_GIFT_TOP_CJ_XY)
                GiftManager.getInstance().addTopNotifyGiftAnim(bean)
            }
            MsgType.FULL_SERVICE_TREE_LUCKTIME -> {
                val bean = TopNotifyBean("??????????????????~??????????????????????????????????????????????????????~?????????????????????????????????,????????????????????????~", TopNotifyBean.TNB_GIFT_TOP_ZS_LUCK)
                GiftManager.getInstance().addTopNotifyGiftAnim(bean)
                addWordList(msgBean)
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
            }
            MsgType.NEW_USER_PAY_GIFT_PACK -> {
//            if(DataHelper.getUID()!=msgBean.fromUserInfo.user_id) {
                addWordList(msgBean)
                if (mUpdateCallBack == null) {
                    return false
                }
                mUpdateCallBack!!.onUpdateWord(ChatRoomUpdateViewCallBack.TYPE_WORD, msgBean)
//              }
            }
            MsgType.FULL_SERVICE_TREE_REWARD -> {
                val bean = TopNotifyBean("????????????~ <font color='#FFEC80'>${msgBean.fromUserInfo.nickname}</font> ?????????????????????????????? <font color='#FFEC80'>${msgBean.giftBean!!.giftName}</font>", TopNotifyBean.TNB_GIFT_TOP_ZX_LW)
                bean.roomId = msgBean.roomId
                GiftManager.getInstance().addTopNotifyGiftAnim(bean)
            }
            MsgType.BUY_TIE_FAN -> {
                //???????????????
                val bean = TopNotifyBean("??????????????????,<font color='#FFFFFF'>${msgBean.toUserInfo.nickname}</font>??????????????????<font color='#FFFFFF'>${msgBean.fromUserInfo.nickname}</font> ?????????<font color='#FFFFFF'>${msgBean.toUserInfo.nickname}</font>??????????????????????????????call~", TopNotifyBean.BUY_TIE_FAN)
                bean.roomId = msgBean.roomId
                GiftManager.getInstance().addTopNotifyGiftAnim(bean)
            }
            MsgType.BUY_AI_FAN -> {
                //???????????????
                val bean = TopNotifyBean("????????????????????????????????????????????????~ <font color='#FFFFFF'>${msgBean.fromUserInfo.nickname}</font>?????????<font color='#FFFFFF'>${msgBean.toUserInfo.nickname}</font>?????????????????????????????????~", TopNotifyBean.BUY_AI_FAN)
                bean.roomId = msgBean.roomId
                GiftManager.getInstance().addTopNotifyGiftAnim(bean)
            }
            MsgType.RADIO_COLUMN_CHANGE -> {
                //????????????????????????
                var columnInfoBean: ColumnInfoBean? = null
                try {
                    columnInfoBean = Gson().fromJson(text, ColumnInfoBean::class.java)
                            ?: return false
                } catch (e: Exception) {
                    return false
                }
                if (onChatRoomRadioCallBack == null) {
                    return false
                }
                onChatRoomRadioCallBack!!.onUpdateColumnInfo(columnInfoBean)

            }
            MsgType.GAME_XY_FULL_SERVICE_REWARD -> {
                val bean = TopNotifyBean("<font color='#FFEC80'>${msgBean.fromUserInfo.nickname}</font>  ????????????????????????????????????????????? <font color='#FFEC80'>${msgBean.giftBean!!.giftPrice} ?????????</font>", TopNotifyBean.TNB_GIFT_TOP_CJ_XY)
                bean.roomId = msgBean.roomId
                GiftManager.getInstance().addTopNotifyGiftAnim(bean)
            }
            else -> {
            }
        }

        return true
    }


    override fun onChange(volume: Int) {
        if (volume > 0) {
            mStatusBean.status = 0
        } else {
            if (mStatusBean.status != 2) {
                mStatusBean.status = 1
            }
        }
    }

    fun setWordList(wordList: ArrayList<MsgBean>) {
        mWordList = wordList
    }

    fun getWordList(): ArrayList<MsgBean> {
        return mWordList
    }

    fun addWordList(bean: MsgBean) {
        if (mWordList.isNotEmpty() && mWordList.size > WORD_LIST_MAX_SIZE) {
            mWordList.removeAt(0)
        }
        mWordList.add(bean)
    }

    fun getRoomId(): String {
        return mChatId
    }

    fun setRoomHostId(hostUid: Int) {
        mHostUid = hostUid
    }

    fun getRoomHostUid(): Int {
        return mHostUid
    }

    fun isRoomHost(): Boolean {
        return mHostUid == DataHelper.getUID()
    }

    fun isRoomManager(): Boolean {
        return mStatusBean.is_manager == 1
    }

    fun isSuperManager(): Boolean {
        return mStatusBean.super_manager == 1
    }

    fun getUserRole(): Int {
        if (isRoomHost()) {
            return 2
        }
        if (isRoomManager()) {
            return 1
        }
        return 0
    }


    /****************************************************** ????????????*********************************/


    private val mRecordVolumeObservable: RecordVolumeObservable = RecordVolumeObservable()

    fun addRecordVolumeObserver(recordVolumeObserver: RecordVolumeObservable.RecordVolumeObserver) {
        mRecordVolumeObservable.registerObserver(recordVolumeObserver)
    }

    fun removeRecordVolumeObserver(recordVolumeObserver: RecordVolumeObservable.RecordVolumeObserver) {
        try {
            mRecordVolumeObservable.unregisterObserver(recordVolumeObserver)
        } catch (e: java.lang.Exception) {
            LogUtil.e(e.printStackTrace().toString())
        }
    }

    fun sendGameXXReslutMsg(type: MsgType, content: String, sendChatId: String, unique_id: String? = "", giftBean: MsgGiftBean? = null, emojiBean: EmojiItemBean? = null, toUserInfo: UserInfo = UserInfo(), gameXXBean: ArrayList<GameXXBean>) {
        val fromUserInfo = UserInfo()
        var localUserInfo = DataHelper.getUserInfo()
        fromUserInfo.nickname = localUserInfo!!.nickname
        fromUserInfo.gender = localUserInfo!!.gender
        fromUserInfo.age = localUserInfo!!.age
//        fromUserInfo.recharge_residue = 0
        fromUserInfo.face = localUserInfo!!.face
        fromUserInfo.type = mStatusBean.type
        fromUserInfo.state = mStatusBean.state
        fromUserInfo.status = mStatusBean.status
        fromUserInfo.speak = mStatusBean.speak
//        fromUserInfo.city = localUserInfo!!.city
        fromUserInfo.user_id = localUserInfo!!.user_id
        fromUserInfo.mic_speaking = mStatusBean.mic_speaking
        fromUserInfo.wealth_level = localUserInfo!!.wealth_level
        fromUserInfo.charm_level = localUserInfo!!.charm_level
        fromUserInfo.user_role = getUserRole()
        fromUserInfo.effects = localUserInfo!!.effects
//        fromUserInfo.head_img = localUserInfo!!.head_img
//        fromUserInfo.body_img = localUserInfo!!.body_img
        fromUserInfo.seat_frame = localUserInfo!!.seat_frame
        fromUserInfo.good_number = localUserInfo!!.good_number
        fromUserInfo.good_number_state = localUserInfo!!.good_number_state
//        fromUserInfo.city = localUserInfo!!.city
//        fromUserInfo.fans_number = localUserInfo!!.fans_number
        fromUserInfo.activity_pic = localUserInfo!!.activity_pic
        fromUserInfo.mystery = localUserInfo!!.mystery
        fromUserInfo.seat = localUserInfo!!.seat
        fromUserInfo.medal = localUserInfo!!.medal
        fromUserInfo.super_manager = mStatusBean.super_manager
//        fromUserInfo.effect_svga = localUserInfo!!.effect_svga ?: ""
        val msgBean = MsgBean(type, content, sendChatId, sendChatId, unique_id, 0, 0, 0, null, giftBean, emojiBean, toUserInfo = toUserInfo, fromUserInfo = fromUserInfo, dataGameXXBean = gameXXBean)
        onNewMsg(Gson().toJson(msgBean).toString())
        mMsgManager.sendTextMsg(sendChatId, Gson().toJson(msgBean).toString(), object : MsgManager.MsgCallBack {
            override fun onSendSuc() {
            }

            override fun onSendFail(i: Int, s: String) {
                mMsgManager.sendTextMsg(sendChatId, Gson().toJson(msgBean).toString(), this)
            }
        })
    }


    fun sendXBMsg(type: MsgType, content: String, sendChatId: String, unique_id: String? = "", giftBean: MsgGiftBean? = null, emojiBean: EmojiItemBean? = null, toUserInfo: UserInfo = UserInfo()) {
        val fromUserInfo = UserInfo()
        var localUserInfo = DataHelper.getUserInfo()
        fromUserInfo.nickname = localUserInfo!!.nickname
        fromUserInfo.gender = localUserInfo!!.gender
        fromUserInfo.age = localUserInfo!!.age
//        fromUserInfo.recharge_residue = 0
        fromUserInfo.face = localUserInfo!!.face
        fromUserInfo.type = mStatusBean.type
        fromUserInfo.state = mStatusBean.state
        fromUserInfo.status = mStatusBean.status
        fromUserInfo.speak = mStatusBean.speak
//        fromUserInfo.city = localUserInfo!!.city
        fromUserInfo.user_id = localUserInfo!!.user_id
        fromUserInfo.mic_speaking = mStatusBean.mic_speaking
        fromUserInfo.wealth_level = localUserInfo!!.wealth_level
        fromUserInfo.charm_level = localUserInfo!!.charm_level
        fromUserInfo.user_role = getUserRole()
        fromUserInfo.effects = localUserInfo!!.effects
//        fromUserInfo.head_img = localUserInfo!!.head_img
//        fromUserInfo.body_img = localUserInfo!!.body_img
        fromUserInfo.seat_frame = localUserInfo!!.seat_frame
        fromUserInfo.good_number = localUserInfo!!.good_number
        fromUserInfo.good_number_state = localUserInfo!!.good_number_state
//        fromUserInfo.city = localUserInfo!!.city
//        fromUserInfo.fans_number = localUserInfo!!.fans_number
        fromUserInfo.activity_pic = localUserInfo!!.activity_pic
        fromUserInfo.mystery = localUserInfo!!.mystery
        fromUserInfo.seat = localUserInfo!!.seat
        fromUserInfo.medal = localUserInfo!!.medal
        fromUserInfo.super_manager = mStatusBean.super_manager
//        fromUserInfo.effect_svga = localUserInfo!!.effect_svga ?: ""
        val msgBean = MsgBean(type, content, sendChatId, sendChatId, unique_id, 0, 0, 0, null, giftBean, emojiBean, toUserInfo = toUserInfo, fromUserInfo = fromUserInfo)
        onNewMsg(Gson().toJson(msgBean).toString())
        mMsgManager.sendTextMsg(sendChatId, Gson().toJson(msgBean).toString(), object : MsgManager.MsgCallBack {
            override fun onSendSuc() {
            }

            override fun onSendFail(i: Int, s: String) {
                mMsgManager.sendTextMsg(sendChatId, Gson().toJson(msgBean).toString(), this)
            }
        })
    }
}