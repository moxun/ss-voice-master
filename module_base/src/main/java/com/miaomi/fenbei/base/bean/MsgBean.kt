package com.miaomi.fenbei.base.bean


data class MsgBean (
        var opt: MsgType,
        val content:String,
        val chatId:String,
        val roomId:String,
        val unique_id:String?,
        val type: Int,
        val intervalTime: Int,
        val time:Long,
        val collection: RedPacketBean?,
        val giftBean: MsgGiftBean?,
        val emojiBean: EmojiItemBean?,
        val RoomInfo: RoomInfoBean = RoomInfoBean(),
        val toUserInfo: UserInfo,
        val fromUserInfo: UserInfo,
        var url:String = "",
        val step:Int = 0,
        val data: CCMLotteryBean = CCMLotteryBean(),
        val dataGameXXBean:ArrayList<GameXXBean> = ArrayList()


)

data class GameXXBean(
        var type:Int = 0,
        var level:Int = 0
)


data class CCMLotteryBean(
        var nickname: String = "",
        val coin:Int = 0
        )



data class MsgGiftBean(
        val giftId: Int,
        val giftNum:Int = 1,
        val giftUrl:String = "",
        val giftIcon :String,
        val giftPrice: Int = 0,
        val giftName: String = "",
        val giftType:Int = 0,
        val giftRewardType:Int = 0,
        val giftReward:Int = 0,
        val giftChestUrl:String = "",
        val giftChestName:String = ""
)

data class RoomInfoBean(
        val roomId: String = "",
        val roomName: String = "",
        val roomIcon: String = ""
)


enum class MsgType{
    TEXT_MSG,
    GIFT,
    JOIN_CHAT,
    UP_TO_MIC,
    DOWN_FROM_MIC,
    APPLY_MIC,
    APPLY_MIC_AGREE,
    APPLY_MIC_DENY,
    INVITE_TO_MIC,
    REMOVE_MIC,
    BAN_USER_MIC,
    BAN_USER_WORD,
    KICK_OUT,
    LEVEL_CHAT,
    UPDATE_CHAT_INFO,
    NOTICE,
    OFFICE_MSG,
    MIC_CTRL,
    LOCK_MIC,
    EMOJI,
    ROLE_SET,
    ROOM_COLLECT,
    FULL_SERVICE_GIFT,
    REFRESH_MIC,
    WINNING_MSG,
    ZS_WINNING_MSG,
    CLEAR_CHAT,
    CLOSE_CHAT,
    OPEN_CHAT,
    FULL_SERVICE_MSG,
    BAN_MIC,
    CLEAR_MIKE,
    DELETE_GROUP,
    FULL_SERVICE_SMALL_GIFT,
//    RADIO_ATTENTION,
//    RADIO_JOIN_GUARD,
//    SWITCH_MYSTERY,
//猫捉老鼠游戏
//    FULL_SERVICE_GAME_CCM,
//    FULL_SERVICE_GAME_CCM_LOTTERY
//    SPECIAL_MSG,
//    FULL_SERVICE_REWARD,
//    REWADR_LUCK,
//    BAN_ACCOUNT,
    RED_PACKET_BOARDCAST,
    RED_PACKET_MSG,
    //开通爵位房间消息
    NOBLE_SCREEN_ANIMATION,
    //开通爵位全服消息,
    NOBLE_PAYMENT_BROADCAST,
    GAME_LSP_MSG,
    GAME_LSP_RESULT,
    GAME_CB_FULL_SERVICE_PUT,
    CB_FULL_SERVICE_PUT,
    GAME_CB_FULL_SERVICE_REWARD,
    CB_FULL_SERVICE_REWARD,
    //贵族上线消息
    SUPER_NOBLE_NOTICE,
    //许愿中奖
    GAME_XY_FULL_SERVICE_REWARD,
    //许愿金币累计通知
    GAME_XY_ARRIVE_SERVICE_REWARD,
    FULL_SERVICE_TREE_LUCKTIME,
    FULL_SERVICE_TREE_REWARD,
    FULL_SERVICE_TREE_PROGRESS,
    //麦序排队踢出/置顶
    XIANGQING_QUEUE,
    //相亲下一步
    XIANGQING_NEXT_STEP,
    //亮灯
    XIANGQING_BRIGHT,
    //权力反转
    XIANGQING_POWER_FANZHUAN,
    //相亲失败
    XIANGQING_FAIL,
    //相亲成功
    XIANGQING_SUCCESS,
    //抱麦
    XIANGQING_INVITE,
    //新用户首充
    NEW_USER_PAY_GIFT_PACK,
    //购买铁杆粉
    BUY_TIE_FAN,
    //购买真爱粉
    BUY_AI_FAN,
    //更新电梯房栏目信息
    RADIO_COLUMN_CHANGE


}