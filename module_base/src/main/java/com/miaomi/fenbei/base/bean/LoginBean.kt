package com.miaomi.fenbei.base.bean

data class LoginBean(
        val token: String,
        val user_id: Int,
        val room_id: Int,
        val sig: String,
        val is_new_user:Boolean,
        val new:Int
)
