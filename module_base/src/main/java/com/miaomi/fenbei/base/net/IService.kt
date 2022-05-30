package com.miaomi.fenbei.base.net

import com.miaomi.fenbei.base.bean.UserDividedBean
import com.miaomi.fenbei.base.bean.*
import com.miaomi.fenbei.base.bean.db.MusicBean
import com.miaomi.fenbei.base.bean.event.ConversationBean
import retrofit2.Call
import retrofit2.http.*

interface IService {

    @FormUrlEncoded
    @POST("user/login_log")
    fun postLoginLog(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 拉黑
     */
    @FormUrlEncoded
    @POST("user/black_operate")
    fun addBlack(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 获取房间排行榜数据
     */
    @POST("gifts/rank_list_one")
    fun getRankRoomList(@QueryMap map: Map<String, String>): Call<Data<RankRoomBean>>

    /**
     * 获取聊天人信息（单人）
     */
    @FormUrlEncoded
    @POST("user/user_batch_info")
    fun getConversation(@FieldMap map: Map<String, String>): Call<Data<List<ConversationBean>>>

    /**
     * 获取表情列表
     */
    @FormUrlEncoded
    @POST("index/emoji_list")
    fun getEmojiList(@FieldMap map: Map<String, String>): Call<Data<List<EmojiGroupBean>>>

    /**
     * 获取表情列表（私聊）
     */
    @FormUrlEncoded
    @POST("index/personal_emoji_list")
    fun getPersonEmojiList(@FieldMap map: Map<String, String>): Call<Data<List<EmojiGroupBean>>>

    @FormUrlEncoded
    @POST("user/follower_operate")
    fun addConcern(@FieldMap map: Map<String, String>): Call<Data<FollowBean>>


    /*
     房间设置创建房间接口
     */
    @FormUrlEncoded
    @POST("chatroom/chat_set")
    fun updateChatInfo(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /*
      电台房间设置创建房间接口
      */
    @FormUrlEncoded
    @POST("radiostation/radio_room_setup")
    fun updateRadioChatInfo(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /*
       编辑栏目
       */
    @FormUrlEncoded
    @POST("radiostation/radio_romm_column_setup_edit")
    fun radioRommColumnEdit(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /*
          新增栏目
          */
    @FormUrlEncoded
    @POST("radiostation/radio_romm_column_setup")
    fun radioRommColumnAdd(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /*
     获取房间标签接口
     */
    @FormUrlEncoded
    @POST("livebroadcast/get_room_label")
    fun getRoomLabel(@FieldMap map: Map<String, String>): Call<Data<List<RoomlabelBean>>>


    /**
     * 钻石兑换现金兑换记录
     */
    @FormUrlEncoded
    @POST("mys/exchange_list")
    fun getBalanceHistory(@FieldMap map: Map<String, String>): Call<Data<List<BalanceHistoryBean>>>


    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST("opinion/feedback")
    fun submitFeedback(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


    /**
     * 个人信息认证
     */
    @FormUrlEncoded
    @POST("user/user_authentication")
    fun submitAuthentication(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


    /**
     * 获取个人信息
     */
    @FormUrlEncoded
//    @POST("draw2009/own_info")
    @POST("user/own_info")
    fun getMineInfo(@FieldMap map: Map<String, String>): Call<Data<MineBean>>

    /**
     * 编辑个人信息
     */
    @FormUrlEncoded
//    @POST("welfare2005/edit_info")
    @POST("user/edit_info")
    fun editInfo(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


    /**
     * 完善个人信息接口（新用户注册需要使用）
     */
    @FormUrlEncoded
    @POST("user/new_edit_info")
    fun pecfectInfo(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


    /**
     * 提现记录
     */
    @FormUrlEncoded
    @POST("details/cashout_record")
    fun getCashOutRecord(@FieldMap map: Map<String, String>): Call<Data<CashOutRecordBean>>

    /**
     * 获取收益信息
     */
    @FormUrlEncoded
    @POST("mys/earnings")
    fun getBalanceInfo(@FieldMap map: Map<String, String>): Call<Data<BalanceInfoBean>>

    /**
     * 钻石收益明细
     */
    @FormUrlEncoded
    @POST("details/earnings_record")
    fun getIncomeHistory(@FieldMap map: Map<String, String>): Call<Data<IncomeHistoryBean>>

    /**
     * 收发礼物记录
     */
    @FormUrlEncoded
    @POST("details/gift_record")
    fun getGiftHistory(@FieldMap map: Map<String, String>): Call<Data<GiftHistoryBean>>

    /**
     * 获取系统消息（官方小助手）
     */
    @FormUrlEncoded
    @POST("message/info_list")
    fun getMessageInfo(@FieldMap map: Map<String, String>): Call<Data<List<MessageBean>>>


    /**
     * 检查更新
     */
    @FormUrlEncoded
//    @POST("info2009/version")
    @POST("version/version")
    fun checkVersion(@FieldMap map: Map<String, String>): Call<Data<VersionBean>>


    /**
     * 收送红包明细
     */
    @FormUrlEncoded
    @POST("details/red_record")
    fun getRedListRecord(@FieldMap map: Map<String, String>): Call<Data<List<RedPacketRecordBean>>>


    /**
     * 获取关注列表
     */
    @FormUrlEncoded
    @POST("chat/follower_list")
    fun getFollowList(@FieldMap map: Map<String, String>): Call<Data<List<FriendsBean>>>

    /**
     * 获取粉丝列表
     */
    @FormUrlEncoded
    @POST("chat/fans_list")
    fun getFansList(@FieldMap map: Map<String, String>): Call<Data<List<FriendsBean>>>

    /**
     * 获取好友列表
     */
    @FormUrlEncoded
    @POST("user/my_friends")
    fun getFriends(@FieldMap map: Map<String, String>): Call<Data<List<FriendsBean>>>


    /**
     * 搜索关注的人或粉丝
     */
    @FormUrlEncoded
    @POST("chat/relation_search")
    fun searchFriend(@FieldMap map: Map<String, String>): Call<Data<List<FriendsBean>>>

//    /**
//     * 个人主页点赞
//     */
//    @FormUrlEncoded
//    @POST("person1009/light_love")
//    fun lightLove(@FieldMap map: Map<String, String>): Call<Data<LoveBean>>

//    /**
//     *
//     */
//    @FormUrlEncoded
//    @POST("chat2002/user_info")
//    fun getUserInfo(@FieldMap map: Map<String, String>): Call<Data<UserInfoBean>>


    /**
     * 获取收益信息
     */
    @FormUrlEncoded
    @POST("chat/income_info")
    fun getIncomeInfo(@FieldMap map: Map<String, String>): Call<Data<ExchangeDiamondsBean>>

    /**
     * 兑换钻石
     */
    @FormUrlEncoded
    @POST("chat/exchange")
    fun exchangeDiamonds(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


//    @FormUrlEncoded
//    @POST("chat1005/pwd_set")
//    fun setPwd(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

//    @FormUrlEncoded
//    @POST("chat1005/chat_level")
//    fun getMyLevel(@FieldMap map: Map<String, String>): Call<Data<List<LevelBean>>>

    /**
     * 登录
     */
    @FormUrlEncoded
//    @POST("chat2002/login")
    @POST("index/login")
    fun login(@FieldMap map: Map<String, String>): Call<Data<MineBean>>

    /**
     * 发送验证码
     */
    @FormUrlEncoded
    @POST("sms/send_verify_code_liya")
    fun sendCode(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


    /**
     * 获取热门音乐列表
     */
    @FormUrlEncoded
    @POST("index/hot_music_library")
    fun getHotMusicList(@FieldMap map: Map<String, String>): Call<Data<List<MusicBean>>>

    /**
     * 搜索热门音乐
     */
    @FormUrlEncoded
    @POST("index/hot_music_search")
    fun searcheHotMusic(@FieldMap map: Map<String, String>): Call<Data<HotMusicSearchBean>>


//    @FormUrlEncoded
//    @POST("chat1005/change_pwd")
//    fun changePwd(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("user/change_pwd")
    fun changePwd(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


//    /**
//     *
//     */
//    @FormUrlEncoded
//    @POST("chat1005/sms_validation")
//    fun verilyCode(@FieldMap map: Map<String, String>): Call<Data<ValidaBean>>

    /**
     * 获取排行榜数据
     */
    @POST("gifts/rank_list_one")
    fun getRankList(@QueryMap map: Map<String, String>): Call<Data<List<RankBean>>>


    /**
     * 获取余额
     */
    @POST("user/user_balance")
    fun getDiamonds(@QueryMap map: Map<String, String>): Call<Data<DiamondsBean>>


    /**
     * 获取钻石明细
     */
    @POST("details/diamondsDetail")
    fun payHistory(@QueryMap map: Map<String, String>): Call<Data<List<PayHistroyBean>>>

    /**
     * 获取banner数据
     */
    @POST("banner/lists")
    fun getBanner(@QueryMap map: Map<String, String>): Call<Data<List<BannerBean>>>

    @FormUrlEncoded
    @POST("chat/visit_record")
    fun getVisitRecord(@FieldMap map: Map<String, String>): Call<Data<List<CallOnBean>>>


    /**
     * 朋友圈
     */
    @FormUrlEncoded
    @POST("user/friends_circle_list")
    fun getFriendsCircleList(@FieldMap map: Map<String, String>): Call<Data<List<FindBean>>>

    /**
     * 朋友圈详情
     */
    @FormUrlEncoded
    @POST("friends/friends_circle_info")
    fun getFriendsCircle(@FieldMap map: Map<String, String>): Call<Data<FindBean>>

    /**
     * 评论朋友圈
     */
    @FormUrlEncoded
    @POST("friends/send_friends_circle_comment")
    fun sendFriendsCircleComment(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 删除评论
     */
    @FormUrlEncoded
    @POST("friends/del_comment")
    fun delFriendsComment(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 获取用户朋友圈
     */
    @FormUrlEncoded
    @POST("activitys/my_tidings")
    fun getFriendsCircleListByUid(@FieldMap map: Map<String, String>): Call<Data<List<FindBean>>>

    /*------家族相关start模块保留功能隐藏---*/

//    @POST("chat1004/family_search")
//    fun searchFamily(@QueryMap map:Map<String,String>):Call<Data<List<FamilyBean.ListBean>>>
//
//    @POST("chat1004/family_rank")
//    fun getFamilyList(@QueryMap map:Map<String,String>):Call<Data<FamilyBean>>
//
//    @POST("chat1004/family_apply")
//    fun createFamily(@QueryMap map:Map<String,String>):Call<Data<Any>>
//
//    @POST("chat1004/family_info")
//    fun getFamilyInfo(@QueryMap map:Map<String,String>):Call<Data<FamilyCenterInfoBean>>
//
//    @POST("chat1011/family_member")
//    fun getFamilyMemberList(@QueryMap map:Map<String,String>):Call<Data<List<FamilyMemberBean>>>
//
//    @POST("chat1004/family_rank_list")
//    fun getFamilyRankList(@QueryMap map:Map<String,String>):Call<Data<List<FamilyRankBean>>>
//
//    @POST("chat1004/family_out")
//    fun outFamily(@QueryMap map:Map<String,String>):Call<Data<Any>>
//
//    @POST("chat1004/family_edit")
//    fun editFamily(@QueryMap map:Map<String,String>):Call<Data<Any>>
//
//    @POST("chat1004/family_join")
//    fun joinFamily(@QueryMap map:Map<String,String>):Call<Data<Any>>
//
//
//    @POST("chat1004/family_operate")
//    fun operateFamily(@QueryMap map:Map<String,String>):Call<Data<Any>>

    /*------家族相关end---*/


//    /**
//     * 获取等级列表
//     */
//    @POST("chat2002/chat_level")
//    fun getChatLevel(@QueryMap map:Map<String,String>):Call<Data<ChatLevelBean>>

    /**
     * 设置兑换密码
     */
    @POST("chat/exchange_pwd_set")
    fun exchangePwdSet(@QueryMap map: Map<String, String>): Call<Data<Any>>


    /**
     * 更换手机前手机验证
     */
    @POST("sms/update_mobile_verify")
    fun mobileVerify(@QueryMap map: Map<String, String>): Call<Data<String>>

    /**
     * 更换绑定手机
     */
    @POST("user/update_mobile_bind")
    fun mobileChange(@QueryMap map: Map<String, String>): Call<Data<Any>>


    /**
     * 获取个人主页信息
     */
    @FormUrlEncoded
    @POST("user/personal_homepage")
    fun personalHomepage(@FieldMap map: Map<String, String>): Call<Data<UserHomePageInfoBean>>

    /**
     * 获取收到礼物
     */
    @FormUrlEncoded
    @POST("gifts/gift_wall")
    fun getGiftWall(@FieldMap map: Map<String, String>): Call<Data<GiftWallBean>>

    /**
     * 获取送出礼物
     */
    @FormUrlEncoded
    @POST("gifts/send_gift_wall")
    fun getSendGiftWall(@FieldMap map: Map<String, String>): Call<Data<List<GiftWallBean>>>

    /**
     * 获取个人主页信息
     */
    @FormUrlEncoded
    @POST("user/user_visit")
    fun userVisit(@FieldMap map: Map<String, String>): Call<Data<String>>


    /**
     * 获取个人相册
     */
    @FormUrlEncoded
    @POST("user/img_list")
    fun getphotoList(@FieldMap map: Map<String, String>): Call<Data<List<PreviewBean>>>

//
//    /**
//     * 获取大神认证数据
//     */
//    @GET("legend2005/getCategories")
//    fun getGreatGodCategories(@QueryMap map: Map<String, String>): Call<Data<List<GreateGodCateGories>>>
//
//    /**
//     * 获取电台列表
//     */
//    @GET("chat2006/radio_list")
//    fun getRadioRoomList(@QueryMap map: Map<String, String>): Call<Data<ArrayList<RadioRoomBean>>>
//
//    /**
//     * 获取大神认证详情
//     */
//    @FormUrlEncoded
//    @POST("legend2005/getCategory")
//    fun getCategoryDetail(@FieldMap map: Map<String, String>): Call<Data<GreatGodDetailBean>>
//
//    /**
//     * 修改大神认证信息
//     */
//    @FormUrlEncoded
//    @POST("legend2005/apply")
//    fun applyGreatGod(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>
//
//    /**
//     * 获取签到信息
//     */
//    @FormUrlEncoded
//    @POST("welfare2007/getUserSignData")
//    fun getUserSignData(@FieldMap map: Map<String, String>): Call<Data<SignatureBean>>

//    /**
//     * 签到
//     */
//    @FormUrlEncoded
//    @POST("welfare2005/sign")
//    fun signIn(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

//    /**
//     * 购买守护
//     */
//    @FormUrlEncoded
//    @POST("chat2006/buy_guard")
//    fun buyGuard(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>
//
//    /**
//     * 电台房间设置
//     */
//    @FormUrlEncoded
//    @POST("chat2007/radio_chat_set")
//    fun updateRadioChatInfo(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>
//
//    /**
//     * 获取守护信息
//     */
//    @FormUrlEncoded
//    @POST("chat2006/regiment_info")
//    fun getGuardInfo(@FieldMap map: Map<String, String>): Call<Data<GuardBean>>


//    /**
//     * 获取电台列表
//     */
//    @FormUrlEncoded
//    @POST("chat2006/radio_room_label")
//    fun getRadioRoomLabel(@FieldMap map: Map<String, String>): Call<Data<List<RoomlabelBean>>>

//    /**
//     * 获取启动页广告
//     */
//    @FormUrlEncoded
//    @POST("draw2007/flash_screen")
//    fun getAdvert(@FieldMap map: Map<String, String>): Call<Data<List<AdvertBean>>>

//    /**
//     * 神秘人模式切换
//     */
//    @FormUrlEncoded
//    @POST("chat2007/mystery_switch")
//    fun mysterySwitch(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>
//
//    /**
//     * 获取贵族信息
//     */
//    @FormUrlEncoded
//    @POST("noble2008/noble_info")
//    fun getNobleInfo(@FieldMap map: Map<String, String>): Call<Data<List<NobleBean>>>
//
//    /**
//     * 购买贵族
//     */
//    @FormUrlEncoded
//    @POST("user2010/buy")
//    fun bugNoble(@FieldMap map: Map<String, String>): Call<BuyNobleBean>


    /**
     * 首页
     */
    @FormUrlEncoded
    @POST("chatroom/follow_live_broadcast")
    fun getHomepage(@FieldMap map: Map<String, String>): Call<Data<List<HomepageBean>>>


    /**
     * 加入房间
     */
    @FormUrlEncoded
    @POST("chatroom/chat_join")
    fun joinChatRoom(@FieldMap map: Map<String, String>): Call<Data<JoinChatBean>>

    /**
     *上麦
     */
    @FormUrlEncoded
    @POST("chat/chat_change")
    fun micCtrl(@FieldMap map: Map<String, String>): Call<Data<MicStatusBean>>
//
//    /**
//     * 电台上麦
//     */
//    @FormUrlEncoded
//    @POST("chat2007/radio_chat_change")
//    fun radioMicCtrl(@FieldMap map: Map<String, String>): Call<Data<MicStatusBean>>

    //    /**
//     * 获取电台排行榜数据
//     */
//    @POST("chat2006/radio_external_rank")
//    fun getRadioList(@QueryMap map: Map<String, String>): Call<Data<RoomRadioCharmRankBean>>


//    /**
//     * 电台下麦
//     */
//    @FormUrlEncoded
//    @POST("chat2007/radio_chat_down")
//    fun radioChatDown(@FieldMap map: Map<String, String>): Call<Data<MicStatusBean>>
//
//    /**
//     * 电台申请上麦
//     */
//    @FormUrlEncoded
//    @POST("chat2007/radio_chat_apply")
//    fun stationApplyMic(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 申请上麦
     */
    @FormUrlEncoded
    @POST("chat/chat_apply")
    fun applyMic(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    @POST("makes/get_user_cellphone")
    fun getUserCellPhone(@QueryMap map: Map<String, String>): Call<Data<String>>


//    /**
//     * 邀请上麦是否同意（电台）
//     */
//    @FormUrlEncoded
//    @POST("chat2007/radio_chat_audit_apply")
//    fun radioApplyMicOpt(@FieldMap map: Map<String, String>): Call<Data<MicStatusBean>>


    /**
     * 获取房间信息
     */
    @FormUrlEncoded
    @POST("chatroom/chat_detail")
    fun getChatRoomInfo(@FieldMap map: Map<String, String>): Call<Data<ChatRoomInfo>>

    /**
     * 解锁麦位
     */
    @FormUrlEncoded
    @POST("chat/chat_switch")
    fun openMic(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 关闭房间
     */
    @FormUrlEncoded
    @POST("index/chat_close")
    fun levelChat(@FieldMap map: Map<String, String>): Call<Data<UniqueIdBean>>


    /**
     * 获取用户信息（房间弹窗）
     */
    @FormUrlEncoded
    @POST("user/single_info")
    fun getUserInfo(@FieldMap map: Map<String, String>): Call<Data<UserCardBean>>


    /**
     * 收藏房间
     */
    @FormUrlEncoded
    @POST("chatroom/chat_collect")
    fun followChat(@FieldMap map: Map<String, String>): Call<Data<FollowResultBean>>

    /**
     * 举报房间
     */
    @FormUrlEncoded
    @POST("chat/chat_report")
    fun report(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 删除节目单
     */
    @FormUrlEncoded
    @POST("radiostation/del_column")
    fun deleteColumn(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

//    /**
//     * 砸蛋积分兑换兑换记录
//     */
//    @FormUrlEncoded
//    @POST("chat1009/egg_exchange_list")
//    fun eggExchangeList(@FieldMap map: Map<String, String>): Call<Data<ExchangeListBean>>

//    /**
//     * 砸蛋积分兑换
//     */
//    @FormUrlEncoded
//    @POST("chat1009/egg_exchange")
//    fun eggExchange(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

//    /**
//     * 房间用户uid列表
//     */
//    @FormUrlEncoded
//    @POST("chat2006/room_uid_list")
//    fun roomUidList(@FieldMap map: Map<String, String>): Call<Data<List<UserInfo>>>
//
//    /**
//     * 房间banner
//     */
//    @FormUrlEncoded
//    @POST("chat1011/egg_img")
//    fun roomActivityImg(@FieldMap map: Map<String, String>): Call<Data<List<RoomActivityImgBean>>>
//
//    /**
//     * 广场交友发消息
//     */
//    @FormUrlEncoded
//    @POST("chat2006/chat_square")
//    fun chatSquare(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


    /**
     * 获取我收藏的音乐
     */
    @FormUrlEncoded
    @POST("mys/my_music_library")
    fun getMyMusicList(@FieldMap map: Map<String, String>): Call<Data<List<MusicBean>>>

    /**
     * 搜索我的音乐
     */
    @FormUrlEncoded
    @POST("mys/my_music_search")
    fun getSearchMusicList(@FieldMap map: Map<String, String>): Call<Data<HotMusicSearchBean>>


    /**
     * 获取推荐房间列表
     */
    @FormUrlEncoded
    @POST("index/hot_list")
    fun getHotChats(@FieldMap map: Map<String, String>): Call<Data<ArrayList<ChatListBean>>>


//    /**
//     * 电台房间创建
//     */
//    @FormUrlEncoded
//    @POST("chat2006/radio_chat_create")
//    fun radioChatCreate(@FieldMap map: Map<String, String>): Call<Data<CreateChatBean>>
//
//    /**
//     * 电台房间详情
//     */
//    @FormUrlEncoded
//    @POST("chat2007/radio_chat_detail")
//    fun radioChatDetail(@FieldMap map: Map<String, String>): Call<Data<ChatRoomInfo>>
//
//    /**
//     * 退出电台房间
//     */
//    @FormUrlEncoded
//    @POST("chat2006/radio_chat_close")
//    fun levelRadioChat(@FieldMap map: Map<String, String>): Call<Data<LiveFinishBean>>

//    /**
//     * 获取守护榜单
//     */
//    @FormUrlEncoded
//    @POST(" chat2006/regiment_rank_list")
//    fun getGuardRankList(@FieldMap map: Map<String, String>): Call<Data<List<GuardWeekRankBean>>>

    /**
     * 开启修仙
     */
    @FormUrlEncoded
    @POST("lingsp/mortal_open")
    fun openMortal(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 修仙获取结果
     */
    @FormUrlEncoded
    @POST("lingsp/mortal_result")
    fun getMortalResult(@FieldMap map: Map<String, String>): Call<Data<List<GameXXBean>>>

    /**
     * 修仙获取结果
     */
    @FormUrlEncoded
    @POST("lingsp/mortal_index")
    fun isGetMortalResult(@FieldMap map: Map<String, String>): Call<Data<MortalResultBean>>


    /**
     * 展示结果
     */
    @FormUrlEncoded
    @POST("lingsp/mortal_status")
    fun showXXReslut(@FieldMap map: Map<String, String>): Call<Data<Boolean>>

    /**
     * 获取电台banner
     */
    @FormUrlEncoded
    @POST("banner/room_banner_info")
    fun getRadioBanner(@FieldMap map: Map<String, String>): Call<Data<List<BannerBean>>>

    /**
     * 嘉宾席位亮灯情况
     */
    @FormUrlEncoded
    @POST("xiangqing/bright_status")
    fun getBrightStatus(@FieldMap map: Map<String, String>): Call<Data<BrightStatusBean>>
//    /**
//     * 获取电台房间贡献排行榜
//     */
//    @POST("chat2006/radio_rank_list")
//    fun getRoomRadioRankList(@QueryMap map:Map<String,String>):Call<Data<RoomRankBean>>
//
//    /**
//     * 排名数据轮播
//     */
//    @POST("chat2006/radio_hour_info")
//    fun radioHourInfo(@QueryMap map:Map<String,String>):Call<Data<RadioHourInfoBean>>
//
//    /**
//     * 获取电台魅力值排行榜
//     */
//    @POST("chat2006/radio_external_rank")
//    fun getRoomRadioCharmRankList(@QueryMap map:Map<String,String>):Call<Data<RoomRadioCharmRankBean>>


//    /**
//     * 发送广场消息
//     */
//    @FormUrlEncoded
//    @POST("draw2009/square_info")
//    fun getMakeFriengMsgList(@FieldMap map: Map<String, String>): Call<Data<List<MakeFriendBean>>>

    @FormUrlEncoded
    @POST("user/if_black")
    fun isBlack(@FieldMap map: Map<String, String>): Call<Data<IsBlackBean>>

    /**
     * 普通礼物
     * @param map
     * @return
     */
    @POST("gifts/gift_send")
    fun giveCommonGift(@QueryMap map: Map<String, String>): Call<Data<DiamondsBean>>

    /**
     * 礼物列表（不带数量的礼物）
     * @param map
     * @return
     */
    @POST("index/gift_list")
    fun getAllGift(@QueryMap map: Map<String, String>): Call<Data<List<GiftBean.DataBean>>>

    /**
     * 背包礼物
     * @param map
     * @return
     */
    @POST("user/my_backpack")
    fun getAllPack(@QueryMap map: Map<String, String>): Call<Data<MyPackBean>>

    /**
     * 红包明细
     */
    @FormUrlEncoded
    @POST("details/red_record")
    fun getRedRecord(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


    /**
     * 获取房间用户列表
     */
    @FormUrlEncoded
    @POST("chatroom/room_list_info")
    fun getUserList(@FieldMap map: Map<String, String>): Call<Data<ArrayList<UserInfo>>>

    /**
     * 背包送礼
     * @param map
     * @return
     */
    @POST("user/backpack_gift_send")
    fun givePackGift(@QueryMap map: Map<String, String>): Call<Data<DiamondsBean>>


    /**
     * 宝箱礼物
     * @param map
     * @return
     */
    @POST("egg/send_treasure_chest")
    fun giveChestGift(@QueryMap map: Map<String, String>): Call<Data<ChestGiftBean>>


    /**
     * 贵族礼物
     * @param map
     * @return
     */
    @POST("chatroom/nuble_gift_send")
    fun giveNobleGift(@QueryMap map: Map<String, String>): Call<Data<DiamondsBean>>


    /**
     * 表白礼物
     * @param map
     * @return
     */
    @POST("confessions/express_gift_send")
    fun giveExpressGift(@QueryMap map: Map<String, String>): Call<Data<DiamondsBean>>


    @POST("chat/nuble_gift_list")
    fun getAllNobleGift(@QueryMap map: Map<String, String>): Call<Data<List<GiftBean.DataBean>>>


    /**
     * 搜索
     */
    @POST("chatroom/search")
    fun search(@QueryMap map: Map<String, String>): Call<Data<List<SearchItemBean>>>


    /**
     * 支付宝支付
     */
    @POST("recharges/buy_diamond")
    fun aliPay(@QueryMap map: Map<String, String>): Call<Data<AliPayBean>>

    /**
     * 下麦
     */
    @FormUrlEncoded
    @POST("chat/chat_down")
    fun chatDown(@FieldMap map: Map<String, String>): Call<Data<MicStatusBean>>

    /**
     * 切麦
     */
    @FormUrlEncoded
    @POST("chat/chat_invite")
    fun micCtrl4Host(@FieldMap map: Map<String, String>): Call<Data<MicStatusBean>>

    /**
     * 相亲房抱麦
     */
    @FormUrlEncoded
    @POST("xiangqing/xiangqing_chat_invite")
    fun micCtrl4XqHost(@FieldMap map: Map<String, String>): Call<Data<MicStatusBean>>

    /**
     * 锁麦
     */
    @FormUrlEncoded
    @POST("chat/chat_lock")
    fun lockMic(@FieldMap map: Map<String, String>): Call<Data<MicStatusBean>>


    /**
     * 禁言
     */
    @FormUrlEncoded
    @POST("chat/chat_forbid")
    fun banUser4Host(@FieldMap map: Map<String, String>): Call<Data<BanUserStatusBean>>

    /**
     * 私聊送礼
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("user/private_gift_send")
    fun sendPrivateGift(@FieldMap map: Map<String, String>): Call<Data<DiamondsBean>>

    @FormUrlEncoded
    @POST("chat/set_room_note")
    fun setRoomNote(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    @FormUrlEncoded
    @POST("chatroom/set_room_pwd")
    fun setRoomPwd(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    @FormUrlEncoded
    @POST("chatroom/get_room_pwd")
    fun getRoomPwd(@FieldMap map: Map<String, String>): Call<Data<String>>


    /**
     * 朋友圈点赞
     */
    @FormUrlEncoded
    @POST("friends/praise_friends_circle")
    fun heartPYQ(@FieldMap map: Map<String, String>): Call<Data<HeartBean>>

    /**
     * 删除朋友圈
     */
    @FormUrlEncoded
    @POST("friends/del_friends_circle")
    fun delFriendsCircle(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


    /**
     * 表白墙点赞
     */
    @FormUrlEncoded
    @POST("confessions/praise_friends_circle")
    fun heartBBQ(@FieldMap map: Map<String, String>): Call<Data<List<String>>>


    /**
     * 获得推荐用户
     */
    @FormUrlEncoded
    @POST("chat/speak_mate")
    fun getRecommandUser(@FieldMap map: Map<String, String>): Call<Data<UserInfo>>

    /**
     * 获得推荐房间
     */
    @FormUrlEncoded
    @POST("mortal/room_mate")
    fun getRecommandRoom(@FieldMap map: Map<String, String>): Call<Data<String>>


    /**
     * 表白墙列表
     */
    @FormUrlEncoded
    @POST("details/express_record")
    fun getExpressRecord(@FieldMap map: Map<String, String>): Call<Data<List<ExpressItemBean>>>


    /**
     * 投放宝藏
     */
    @FormUrlEncoded
    @POST("games/hide_treasure")
    fun hideTreasure(@FieldMap map: Map<String, String>): Call<Data<DiamondsBean>>

    /**
     * 宝藏首页
     */
    @FormUrlEncoded
    @POST("games/hide_index")
    fun getHideIndex(@FieldMap map: Map<String, String>): Call<Data<XBIndexBean>>


    /**
     * 单个宝藏
     */
    @FormUrlEncoded
    @POST("games/hide_single")
    fun getSingleTreasure(@FieldMap map: Map<String, String>): Call<Data<XBItemBean>>

    /**
     * 挖宝藏
     */
    @FormUrlEncoded
    @POST("games/hide_dig")
    fun hideDig(@FieldMap map: Map<String, String>): Call<Data<XBRewardBean>>

    /**
     * 宝藏投放明细
     */
    @FormUrlEncoded
    @POST("games/release_record")
    fun getXBReleaseRecordList(@FieldMap map: Map<String, String>): Call<Data<List<XBTFRecordBean>>>

    /**
     * 宝藏寻宝明细
     */
    @FormUrlEncoded
    @POST("games/hunting_record")
    fun getXBHuntingRecordList(@FieldMap map: Map<String, String>): Call<Data<List<XBXBRecordBean>>>

    /**
     * 宝藏礼物列表
     */
    @FormUrlEncoded
    @POST("games/hide_gift_list")
    fun getHideGiftList(@FieldMap map: Map<String, String>): Call<Data<List<XBHideGiftBean>>>


    /**
     * 寻宝排行榜
     */
    @FormUrlEncoded
    @POST("games/hide_rank")
    fun getHideRankList(@FieldMap map: Map<String, String>): Call<Data<List<XBRankBean>>>


    /**
     * 广场交友发消息
     */
    @FormUrlEncoded
    @POST("chatroom/chat_square")
    fun chatSquare(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 发送广场消息
     */
    @FormUrlEncoded
    @POST("index/square_info")
    fun getMakeFriengMsgList(@FieldMap map: Map<String, String>): Call<Data<List<MakeFriendBean>>>

    /**
     * 获取头条消息
     */
    @FormUrlEncoded
    @POST("index/head_info")
    fun getSquareHeadInfo(@FieldMap map: Map<String, String>): Call<Data<MakeFriendBean>>


    @POST("family/family_search")
    fun searchFamily(@QueryMap map: Map<String, String>): Call<Data<List<FamilyBean.ListBean>>>

    @POST("family/family_rank")
    fun getFamilyList(@QueryMap map: Map<String, String>): Call<Data<FamilyBean>>

    @POST("family/administrator_del")
    fun administrator_del(@QueryMap map: Map<String, String>): Call<Data<Any>>

    @POST("family/family_apply")
    fun createFamily(@QueryMap map: Map<String, String>): Call<Data<Any>>

    @POST("family/family_info")
    fun getFamilyInfo(@QueryMap map: Map<String, String>): Call<Data<FamilyCenterInfoBean>>

    @POST("family/get_family_divided_type")
    fun getFamilyDivided_Type(@QueryMap map: Map<String, String>): Call<Data<FamilyProportionBean>>

    @POST("family/get_family_diviede")
    fun getFamilyDivided(@QueryMap map: Map<String, String>): Call<Data<FamilyProportionBean>>

    @POST("family/get_user_family_divided")
    fun getFamily_User_Divided(@QueryMap map: Map<String, String>): Call<Data<UserDividedBean>>


    @POST("family/checkout_divided_type")
    fun getFamily_Checkout_Divided_Type(@QueryMap map: Map<String, String>): Call<Data<BaseBean>>

    @POST("family/set_up_divided_all")
    fun getFamily_Split_Into_All(@QueryMap map: Map<String, String>): Call<Data<BaseBean>>

    @POST("family/my_family")
    fun meFamily(@QueryMap map: Map<String, String>): Call<Data<MeFamilyBean>>

    @POST("chatroom/room_rand_list")
    fun RoomRandList(@QueryMap map: Map<String, String>): Call<Data<List<RoomListBean.DataBean>>>

    @POST("family/push_family")
    fun recommedFamily(@QueryMap map: Map<String, String>): Call<Data<FamilyBean>>

    @POST("family/get_user_family_divided_detail")
    fun getFamilyUserDividedDetail(@QueryMap map: Map<String, String>): Call<Data<DividedUserBean>>

    @POST("family/family_member")
    fun getFamilyMemberList(@QueryMap map: Map<String, String>): Call<Data<List<FamilyMemberBean>>>

    @POST("family/add_divided_user")
    fun getFamilyAddDividedUser(@QueryMap map: Map<String, String>): Call<Data<DividedUserBean>>

    @POST("family/family_rank_list")
    fun getFamilyRankList(@QueryMap map: Map<String, String>): Call<Data<List<FamilyRankBean>>>

    @POST("family/family_out")
    fun outFamily(@QueryMap map: Map<String, String>): Call<Data<Any>>

    @POST("family/family_room")
    fun getFamilyRoomList(@QueryMap map: Map<String, String>): Call<Data<List<FamilyCenterInfoBean.RoomInfoBean>>>

    @POST("family/family_out_apply")
    fun outFamilyApply(@QueryMap map: Map<String, String>): Call<Data<Any>>

    @POST("family/family_disband")
    fun dissolutionFamilyApply(@QueryMap map: Map<String, String>): Call<Data<Any>>

    @POST("family/family_edit")
    fun editFamily(@QueryMap map: Map<String, String>): Call<Data<Any>>

    @POST("family/family_join")
    fun joinFamily(@QueryMap map: Map<String, String>): Call<Data<Any>>

    @POST("family/family_operate")
    fun operateFamily(@QueryMap map: Map<String, String>): Call<Data<Any>>

    @POST("family/set_up_divided_section")
    fun upDividedSection(@QueryMap map: Map<String, String>): Call<Data<Any>>

    @POST("family/family_apply_list")
    fun getFamilyApplyList(@QueryMap map: Map<String, String>): Call<Data<List<ApplyListBean>>>


    @POST("chat/im_private_chat_callback")
    fun updataSendMsgInfo(@QueryMap map: Map<String, String>): Call<Data<String>>


    @POST("giftintegral/click_exchange")
    fun integralExchange(@QueryMap map: Map<String, String>): Call<Data<Any>>

    @POST("giftintegral/gift_integral_list")
    fun getGiftIntegralList(@QueryMap map: Map<String, String>): Call<Data<List<CJntegralExchangeBean>>>


    @POST("chat/fontana_cash")
    fun getActivityIntegral(@QueryMap map: Map<String, String>): Call<Data<CJintegralActivityIntegralBean>>

    @POST("details/egg_exchange_record")
    fun getEggExchangeRecord(@QueryMap map: Map<String, String>): Call<Data<List<CJntegralExchangeRecordBean>>>

    @POST("details/fontana_record")
    fun getCJFontanaRecord(@QueryMap map: Map<String, String>): Call<Data<List<CJFontanaRecordBean>>>

    @POST("chat/fontana_dig")
    fun openCJBox(@QueryMap map: Map<String, String>): Call<Data<CJntegralOpenBoxBean>>

    @POST("banner/game_room_banner_info")
    fun gameInfo(@QueryMap map: Map<String, String>): Call<Data<List<RoomGameListBean>>>


    @POST("tree/water")
    fun useWater(@QueryMap map: Map<String, String>): Call<Data<ZSUseWaterBean>>

    @POST("tree/buywater")
    fun buywater(@QueryMap map: Map<String, String>): Call<Data<ZSBuyWaterBean>>

    @POST("tree/index")
    fun getZSIndex(@QueryMap map: Map<String, String>): Call<Data<ZSIndexBean>>

    @POST("tree/prizelog")
    fun getZSRecordList(@QueryMap map: Map<String, String>): Call<Data<ArrayList<ZSPrizeRecordBean>>>

    @POST("tree/prize")
    fun getZSGiftPrizePool(@QueryMap map: Map<String, String>): Call<Data<List<ZSGiftPrizePoolBean>>>

    @POST("tree/rank")
    fun getZSRankList(@QueryMap map: Map<String, String>): Call<Data<List<ZSRankItemBean>>>

    @POST("tree2014/show")
    fun showZsMessage(@QueryMap map: Map<String, String>): Call<Data<Any>>


    /**
     * 获取前端上传的音乐列表
     */
    @POST("user/family_music")
    fun getUserUploadMusic(@QueryMap map: Map<String, String>): Call<Data<List<MusicBean>>>

    /**
     * 通过类型获取首页房间列表
     */
    @POST("index/label_room_party_list")
    fun getRoomListByLabel(@QueryMap map: Map<String, String>): Call<Data<List<ChatListBean>>>

    /**
     * 通过类型获取首页声圈房间列表
     */
    @POST("index/label_room_personal_list")
    fun getPersonalRoomListByLabel(@QueryMap map: Map<String, String>): Call<Data<List<ChatListBean>>>


    /**
     * 获取靓号
     */
    @POST("chat/draw_good_number")
    fun getPartyGoodNumber(@QueryMap map: Map<String, String>): Call<Data<Any>>

    /**
     * 绑定靓号
     */
    @POST("chat/bind_good_number")
    fun bindPartyGoodNumber(@QueryMap map: Map<String, String>): Call<Data<Any>>

    /**
     * 获取豹子号
     */
    @POST("leopards/leopard_good_number")
    fun getLeopardPartyGoodNumber(@QueryMap map: Map<String, String>): Call<Data<List<LeopardGoodNumberBean>>>

    /**
     * 购买豹子号
     */
    @POST("chatroom/buy_leopard_number")
    fun buyPartyGoodNumber(@QueryMap map: Map<String, String>): Call<Data<Any>>


    /**
     * 获取收藏房间列表
     */
    @FormUrlEncoded
    @POST("user/collect_list")
    fun getFollowChats(@FieldMap map: Map<String, String>): Call<Data<ArrayList<ChatListBean>>>


    /**
     *
     */
    @FormUrlEncoded
    @POST("user/manager_search")
    fun searchRoomUser(@FieldMap map: Map<String, String>): Call<Data<List<UserInfo>>>

    /**
     * 管理员页面搜索接口
     */
    @FormUrlEncoded
    @POST("chatroom/room_user_list")
    fun getManagerInfo(@FieldMap map: Map<String, String>): Call<Data<ManagerInfoBean>>

    /**
     * 设置用户角色接口（管理员）
     */
    @FormUrlEncoded
    @POST("chat/manager_operate")
    fun setUserRole(@FieldMap map: Map<String, String>): Call<Data<RoleSetBean>>

    /**
     * 获取房间黑名单列表
     */
    @FormUrlEncoded
    @POST("chat/black_list")
    fun getBlackList(@FieldMap map: Map<String, String>): Call<Data<ArrayList<UserInfo>>>

    /**
     * 移出黑名单
     */
    @FormUrlEncoded
    @POST("chat/black_remove")
    fun blackRemove(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 绑定手机
     */
    @FormUrlEncoded
    @POST("user/bind")
    fun bindPhone(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


    /**
     * 绑定支付宝
     */
    @FormUrlEncoded
    @POST("user/bind_alipay_account")
    fun bindAliAccount(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 获取提现信息
     */
    @FormUrlEncoded
    @POST("user/cashout_info")
    fun getCashOutInfo(@FieldMap map: Map<String, String>): Call<Data<CashOutBean>>

    /**
     * 提现
     */
    @FormUrlEncoded
    @POST("user/cashout")
    fun CashWithdrawal(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 上传图片供审核
     */
    @FormUrlEncoded
    @POST("uploads/upload_image")
    fun uploadImage(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 上传图片供审核
     */
    @FormUrlEncoded
    @POST("user/upload_video")
    fun uploaVideo(@FieldMap map: Map<String, String>): Call<BaseBean>


    /**
     * 获取个性装扮（头像，进场特效）
     */
    @FormUrlEncoded
    @POST("dressup/shopping_mall")
    fun getShoppingMall(@FieldMap map: Map<String, String>): Call<Data<List<ShopMallItemBean>>>

    @FormUrlEncoded
    @POST("mys/my_decorate")
    fun getMyDress(@FieldMap map: Map<String, String>): Call<Data<List<DressItemBean>>>

    @FormUrlEncoded
    @POST("dressup/buy_decorate")
    fun buyDress(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 使用个性装扮
     */
    @FormUrlEncoded
    @POST("dressup/new_use_decorate")
    fun useDress(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 删除个人主页相册图片
     */
    @FormUrlEncoded
    @POST("user/delete_image")
    fun deleteImage(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 删除音乐
     */
    @FormUrlEncoded
    @POST("user/my_music_del")
    fun deleteMusic(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     *禁麦
     */
    @FormUrlEncoded
    @POST("chat/chat_barley")
    fun chatBarley(@FieldMap map: Map<String, String>): Call<Data<MicStatusBean>>

    /**
     * 关闭房间
     */
    @FormUrlEncoded
    @POST("chatroom/super_room_close")
    fun superRoomClose(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>


    /**
     * 清除麦序魅力值
     */
    @FormUrlEncoded
    @POST("chat/clear_mike")
    fun clearMike(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 灭灯
     */
    @FormUrlEncoded
    @POST("xiangqing/lights_off")
    fun turnOfflight(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 之前过滤敏感字接口目前已用作存储历史数据
     */
    @FormUrlEncoded
    @POST("chat/chat_verify")
    fun sendText(@FieldMap map: Map<String, String>): Call<Data<TextStatusBean>>

    /**
     * 踢出
     */
    @FormUrlEncoded
    @POST("chatroom/chat_kick")
    fun kickOut(@FieldMap map: Map<String, String>): Call<Data<UniqueIdBean>>

    /**
     * 关闭房间
     */
    @FormUrlEncoded
    @POST("chatroom/sociaty_chat_close")
    fun sociatyChatClose(@FieldMap map: Map<String, String>): Call<Data<LiveFinishBean>>

    /**
     * 删除视频
     */
    @FormUrlEncoded
    @POST("user/delete_video")
    fun deleteVideo(@FieldMap map: Map<String, String>): Call<BaseBean>


    /**
     * 微信小程序支付
     */
    @FormUrlEncoded
    @POST("recharges/wx_xcx_lineno")
    fun wxMiniPay(@FieldMap map: Map<String, String>): Call<Data<WxMiniPayBean>>


    /**
     * 新微信小程序支付
     */
    @FormUrlEncoded
    @POST("pay/weixing_alipay")
    fun wxNewMiniPay(@FieldMap map: Map<String, String>): Call<Data<WxMiniPayBean>>


    /**
     * 获取支付方式
     */
    @FormUrlEncoded
    @POST("recharges/wx_pay_info")
    fun getPayWay(@FieldMap map: Map<String, String>): Call<Data<WxPayWayBean>>

    /**
     * 房间内魅力值排行榜
     */
    @POST("chatroom/room_rank_list")
    fun getRoomRankList(@QueryMap map: Map<String, String>): Call<Data<RoomRankBean>>

    /**
     *
     */
    @POST("chatroom/three_head_portrait")
    fun getRoomTopThree(@QueryMap map: Map<String, String>): Call<Data<List<RankBean>>>

    @FormUrlEncoded
    @POST("chatroom/create_room_callback")
    fun createChatRoomCallback(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 创建房间
     */
    @FormUrlEncoded
    @POST("chatroom/chat_create")
    fun createChatRoom(@FieldMap map: Map<String, String>): Call<Data<CreateChatBean>>

    @FormUrlEncoded
    @POST("chatroom/room_online_record")
    fun getRoomGiftHistory(@FieldMap map: Map<String, String>): Call<Data<List<RoomGiftHistoryBean>>>

    @FormUrlEncoded
    @POST("recommend/recommend_user")
    fun getRecommandUsers(@FieldMap map: Map<String, String>): Call<Data<RecommandUserBean>>

    /**
     * 获取贵族信息
     */
    @FormUrlEncoded
    @POST("nobles/noble_info")
    fun getNobleInfo(@FieldMap map: Map<String, String>): Call<Data<List<NobleBean>>>

    /**
     * 购买贵族
     */
    @FormUrlEncoded
    @POST("noble/buy")
    fun bugNoble(@FieldMap map: Map<String, String>): Call<Data<BuyNobleBean>>

    /**
     * 绑定银行卡
     */
    @FormUrlEncoded
    @POST("user/bind_bank_account")
    fun bindBankCard(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 获取银行卡列表
     */
    @GET("makes/bank_list")
    fun getBankList(@QueryMap map: Map<String, String>): Call<Data<List<BankListBean>>>

    /**
     * 发朋友圈
     */
    @FormUrlEncoded
    @POST("friends/issue_friends_circle")
    fun issueFriendsCircle(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 邀请上麦是否同意
     */
    @FormUrlEncoded
    @POST("chatroom/chat_audit_apply")
    fun applyMicOpt(@FieldMap map: Map<String, String>): Call<Data<MicStatusBean>>

    /**
     * 是否在家族中
     */
    @FormUrlEncoded
    @POST("family/is_family")
    fun getFamilyId(@FieldMap map: Map<String, String>): Call<Data<FamilyIdBean>>

    @FormUrlEncoded
    @POST("red/send_user")
    fun sendRedUser(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 添加喜欢的音乐
     */
    @FormUrlEncoded
    @POST("user/my_music_add")
    fun addMusic(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 找回密码
     */
    @FormUrlEncoded
    @POST("user/forget_pwd")
    fun findPwd(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 获取认证信息
     */
    @FormUrlEncoded
    @POST("user/info")
    fun getIdentifyInfo(@FieldMap map: Map<String, String>): Call<Data<IdentifyInfoBean>>

    /**
     * 获取支付选项
     */
    @POST("recharges/recharge_category")
    fun getGoodsList(@QueryMap map: Map<String, String>): Call<Data<List<GoodsBean>>>

    /**
     * 微信支付
     */
    @POST("recharges/buy_diamond")
    fun wechatPay(@QueryMap map: Map<String, String>): Call<Data<WechatPayBean>>

    /**
     * 红包明细
     */
    @FormUrlEncoded
    @POST("details/send_red_record")
    fun getRedRecordDetail(@FieldMap map: Map<String, String>): Call<Data<List<GetRedPacketUsersBean>>>

    /**
     * 获取红包配置
     */
    @POST("red/getBaseData")
    fun getRedPacketBaseData(@QueryMap map: Map<String, String>): Call<Data<RedPacketBaseBean>>

    /**
     * 发红包
     */
    @POST("red/send")
    fun sendRedPacket(@QueryMap map: Map<String, String>): Call<Data<RedPacketBean>>

    /**
     * 开红包
     */
    @POST("red/grab")
    fun grabRedPacket(@QueryMap map: Map<String, String>): Call<Data<GotRedPacketBean>>

    /**
     * 获取红包领取列表
     */
    @POST("red/getGrabList")
    fun getGrabList(@QueryMap map: Map<String, String>): Call<Data<RedPacketRankBean>>

    /**
     * 发送文字任务
     */
    @POST("chat/speakReport")
    fun upLoadTxt(@QueryMap map: Map<String, String>): Call<Data<AppearBean>>


    /**
     * 获取房间历史聊天消息
     */
    @POST("chatroom/historyConversation")
    fun historyConversation(@QueryMap map: Map<String, String>): Call<Data<List<MsgHistoryBean>>>

    /**
     * 青少年模式设置密码young_pwd_set
     */
    @FormUrlEncoded
    @POST("user/young_pwd_set")
    fun youngPwdSet(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 青少年模式密码关闭
     */
    @FormUrlEncoded
    @POST("user/young_pwd_close")
    fun youngPwdClose(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 青少年模式密码验证
     */
    @FormUrlEncoded
    @POST("user/young_pwd_verify")
    fun youngPwdVerify(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 解除授权
     */
    @FormUrlEncoded
    @POST("user/remove_warrant")
    fun removeWarrant(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 解除第三方绑定
     */
    @FormUrlEncoded
    @POST("user/remove_account_third")
    fun removeAccountThird(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 账号注销
     */
    @FormUrlEncoded
    @POST("user/remove_account_mobile")
    fun removeAccountMobile(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 更新兑换密码
     */
    @POST("user/exchange_pwd_update")
    fun exchangePwdUpdate(@QueryMap map: Map<String, String>): Call<Data<Any>>

    /**
     * 关闭兑换密码
     */
    @POST("user/exchange_pwd_close")
    fun exchangePwdClose(@QueryMap map: Map<String, String>): Call<Data<Any>>

    /**
     * 获取管理员列表
     */
    @FormUrlEncoded
    @POST("chat/chat_apply_list")
    fun getApplyList(@FieldMap map: Map<String, String>): Call<Data<ApplyInfoList>>

    /**
     * 获取相亲申请上麦嘉宾列表
     */
    @FormUrlEncoded
    @POST("xiangqing/queue_list")
    fun getXqQueueList(@FieldMap map: Map<String, String>): Call<Data<RowBean>>

    /**
     * 获取相亲麦序的状态
     */
    @FormUrlEncoded
    @POST("xiangqing/maixu_status")
    fun getXqMaixuStatus(@FieldMap map: Map<String, String>): Call<Data<MaixuStatusBean>>

    /**
     * 申请加入队列
     */
    @FormUrlEncoded
    @POST("xiangqing/join_queue")
    fun getXqJoin_Queue(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 退出相亲麦序
     */
    @FormUrlEncoded
    @POST("xiangqing/quit_queue")
    fun getXqOutMaixuStatus(@FieldMap map: Map<String, String>): Call<Data<MaixuStatusBean>>

    /**
     * 相亲房麦序队列踢人
     */
    @FormUrlEncoded
    @POST("xiangqing/kick_people")
    fun getkOutStatus(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 相亲房麦序队列置顶
     */
    @FormUrlEncoded
    @POST("xiangqing/jump_in_line")
    fun getktopStatus(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 相亲开始相亲
     */
    @FormUrlEncoded
    @POST("xiangqing/next_step")
    fun getNextStep(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 相亲最终选择
     */
    @FormUrlEncoded
    @POST("xiangqing/final_choice")
    fun getFinal_choice(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 权立反转选择2个嘉宾
     */
    @FormUrlEncoded
    @POST("xiangqing/select_love")
    fun getReversal(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 获取房间信息
     */
    @FormUrlEncoded
    @POST("chatroom/chat_info")
    fun getChatInfo(@FieldMap map: Map<String, String>): Call<Data<HostInfoBean>>

    /**
     * 关注用户
     */
    @FormUrlEncoded
    @POST("user/follower_operate")
    fun followSomeBodyAction(@FieldMap map: Map<String, String>): Call<Data<FollowUserBean>>


    /**
     * 断线重连
     */
    @FormUrlEncoded
    @POST("chatroom/chat_reconnection")
    fun reconnection(@FieldMap map: Map<String, String>): Call<Data<ReconnectionBean>>

    /**
     * 获取砸蛋信息
     */
    @FormUrlEncoded
    @POST("egg/egg_index")
    fun eggIndex(@FieldMap map: Map<String, String>): Call<Data<EggIndexBean>>

    /**
     * 许愿池
     */
    @FormUrlEncoded
    @POST("egg/egg_jackpot")
    fun eggJackpot(@FieldMap map: Map<String, String>): Call<Data<List<JackpotBean>>>

    /**
     * 获取砸蛋排行
     */
    @FormUrlEncoded
    @POST("egg/egg_rank")
    fun eggRank(@FieldMap map: Map<String, String>): Call<Data<List<EggRankBean>>>

    /**
     * 砸蛋规则
     */
    @FormUrlEncoded
    @POST("egg/egg_rule")
    fun eggRule(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 买 许愿笺
     */
    @FormUrlEncoded
    @POST("egg/buy_hammer")
    fun buyHammer(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 砸蛋
     */
    @FormUrlEncoded
    @POST("egg/smash_egg")
    fun smashEgg(@FieldMap map: Map<String, String>): Call<Data<ArrayList<XYRewardGiftInfo>>>

    /**
     * 砸蛋记录
     */
    @FormUrlEncoded
    @POST("egg/egg_record")
    fun eggRecord(@FieldMap map: Map<String, String>): Call<Data<List<RecordBean>>>

    /**
     * 关闭公屏聊天
     */
    @FormUrlEncoded
    @POST("chatroom/chat_close_screen")
    fun chatCloseScreen(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 活动中
     */
    @FormUrlEncoded
    @POST("index/activity_center_list")
    fun getActivityCenterList(@FieldMap map: Map<String, String>): Call<Data<List<HdCenterItemBean>>>

    /**
     * 活动中
     */
    @FormUrlEncoded
    @POST("chatroom/room_hour_rank")
    fun getPartyRoomHourRank(@FieldMap map: Map<String, String>): Call<Data<PartyRoomHourBean>>

    /**
     * 活动中
     */
    @FormUrlEncoded
    @POST("chatroom/room_rand_list")
    fun getRandomRoomBean(@FieldMap map: Map<String, String>): Call<Data<List<RandomRoomBean>>>


    /**
     * 获取用户当前所在房间
     */
    @FormUrlEncoded
    @POST("roomusers/search_room")
    fun getCurrentRoom(@FieldMap map: Map<String, String>): Call<Data<CurrentRoomBean>>

    /**
     * 获取电台主播粉丝榜单
     */
    @FormUrlEncoded
    @POST("fangroup/ranking_list")
    fun getFangroupAnchorFansList(@FieldMap map: Map<String, String>): Call<Data<AnchorRankBean>>

    /**
     * 获取电台主播榜单
     */
    @FormUrlEncoded
    @POST("fangroup/anchor_list")
    fun getFangroupAnchorList(@FieldMap map: Map<String, String>): Call<Data<AnchorRankBean>>

    /**
     * 获取粉丝配置
     */
    @FormUrlEncoded
    @POST("fangroup/fan_config")
    fun getFangroupAnchorFanConfig(@FieldMap map: Map<String, String>): Call<Data<DtFansBean>>


    /**
     * 获取粉丝配置
     */
    @FormUrlEncoded
    @POST("fangroup/buy_fan")
    fun buyFangroupAnchorFan(@FieldMap map: Map<String, String>): Call<Data<AnchorRankBean>>

    /**
     * 修改粉丝名
     */
    @FormUrlEncoded
    @POST("fangroup/set_fan_name")
    fun editFangroupName(@FieldMap map: Map<String, String>): Call<Data<BaseBean>>

    /**
     * 获取新用户推荐房间
     */
    @FormUrlEncoded
    @POST("user/new_user_room_id")
    fun getUserRoomId(@FieldMap map: Map<String, String>): Call<Data<GetUserRoomIdBean>>


}