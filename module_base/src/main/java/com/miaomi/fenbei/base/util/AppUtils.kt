package com.miaomi.fenbei.base.util

import android.content.pm.PackageManager

import com.miaomi.fenbei.base.core.CommonLib

object AppUtils {

    val versionCode: Int
        get() {
            try {
                return CommonLib.mContext.packageManager.getPackageInfo(CommonLib.mContext.packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return -1
        }

    val versionName: String?
        get() {
            try {
                return CommonLib.mContext.packageManager.getPackageInfo(CommonLib.mContext.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return null
        }
}
