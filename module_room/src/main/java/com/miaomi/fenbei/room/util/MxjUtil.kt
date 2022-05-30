package com.miaomi.fenbei.room.util

import com.miaomi.fenbei.room.R

object MxjUtil {
    fun getIcon(index: Int): Int {
        return when (index) {
            1 -> R.drawable.room_icon_mxj_point_1
            2 -> R.drawable.room_icon_mxj_point_2
            3 -> R.drawable.room_icon_mxj_point_3
            4 -> R.drawable.room_icon_mxj_point_4
            5 -> R.drawable.room_icon_mxj_point_5
            6 -> R.drawable.room_icon_mxj_point_6
            7 -> R.drawable.room_icon_mxj_point_7
            8 -> R.drawable.room_icon_mxj_point_8
            else -> R.drawable.room_icon_mxj_point_9
        }
    }
}