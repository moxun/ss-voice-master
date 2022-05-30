package com.miaomi.fenbei.base.bean

data class MicStatusBean (
        val type:Int, //0为上麦，1为下麦
        val way:Int,  //1-8为上麦对应麦序
        val status: Int, // 麦序状态
        val unique_id:String,
        val mai_status: Int, // 是否在麦位上
        val user_out_list:List<String> = ArrayList<String>()
)