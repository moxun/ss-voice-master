package com.miaomi.fenbei.base.util

import android.content.Context
import android.text.TextUtils
import com.miaomi.fenbei.base.net.NetService

object StatisticHelper {

    fun postLoginLog(context: Context) {

        val today = DateUtil.ms2DateOnlyDay(System.currentTimeMillis())
        val lastDay = SPUtil.getString(SPUtil.CONFIG_LAST_LAUNCHER, "")
        if (!TextUtils.isEmpty(DataHelper.getLoginToken()) && today!= lastDay) {
            NetService.getInstance(context.applicationContext).postLoginLog()
            SPUtil.putString(SPUtil.CONFIG_LAST_LAUNCHER, today)
        }
    }
}