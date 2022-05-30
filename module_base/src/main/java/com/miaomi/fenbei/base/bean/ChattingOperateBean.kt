package com.miaomi.fenbei.base.bean


data class ChattingOperateBean (
        var icon: Int,
        var title: String,
        var type: Int,
        var isShowApplyNotice: Boolean = false
)