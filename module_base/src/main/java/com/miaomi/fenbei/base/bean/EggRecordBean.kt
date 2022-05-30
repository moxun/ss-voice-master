package com.miaomi.fenbei.base.bean


data class EggRecordBean (

        val total: Int,
        val per_page: Int,
        val current_page: Int,
        val last_page: Int,
        val data: ArrayList<RecordBean>
)

data class RecordBean (
        val name: String = "",
        val icon: String = "",
        val price: String = "0",
        val number: String = "1",
        val create_time: String
)