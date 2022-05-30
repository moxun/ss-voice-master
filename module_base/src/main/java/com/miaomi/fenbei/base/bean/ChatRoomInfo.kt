package com.miaomi.fenbei.base.bean


data class ChatRoomInfo(
        val host_info: HostInfoBean,    //房间信息
        val user_host: UserInfo,    //房主信息
        val mic_list: ArrayList<UserInfo>? = ArrayList(),  //麦序信息
        val user_status: UserStatusBean, //用户自身信息
        val radio_station:List<RadioStationBean>,
        val radio_station_now:RadioStationBean
)

data class UserStatusBean(
        var is_manager: Int = 0, //0为普通用户 1为管理员
        var super_manager: Int = 0,
        var type: Int = 0,    //麦序
        var state: Int = 0,    //0不在线 1在线
        var status: Int = 0,   //0正常 1禁麦 2房主禁麦
        var speak: Int = 0, //0正常 1禁言
        var mic_speaking: Boolean = false,
        var seat_frame: Int = 0,
        var first_sign: Int = 0,//首充
        var screen: Int = 0,//是否关公屏
        var balance: Int = 0

)
