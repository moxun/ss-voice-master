package com.miaomi.fenbei.base.bean


data class HostInfoBean(
        val name: String,
        val icon: String? = "",
        val backdrop: String,
        val type: Int? = 0,
        val way: Int? = 0,
        var note: String? = "",
        val online_num: Int? = 0,
        val popularity_int: String? = "",
        val is_follow: Int,
        val online_int: Int,
        val password: String? = "",
        val labelId: Int? = 0,
        val label: String? = "",
        val room_good_number: Int? = 0,
        val room_topic: String? = "",
        val contribute: Long,
        val room_type: Int,
        val mvp: UserInfo,
        val step:Int=0,
        val hot_value:String = "",
        val hold_hands_count:Int,
        val radio_station_name:String,
        val introduction:String,
        val open_time:String
)
