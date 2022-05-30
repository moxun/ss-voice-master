package com.miaomi.fenbei.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.miaomi.fenbei.base.core.CommonLib
import java.lang.reflect.Method
import java.util.*


/**
 *
 */
object DeviceUtil {

    val sdkVersionName: String
        get() = android.os.Build.VERSION.RELEASE
    val sdkVersionCode: Int
        get() = android.os.Build.VERSION.SDK_INT
    val manufacturer: String
        get() = Build.MANUFACTURER
    val model: String
        get() {
            var model: String? = Build.MODEL
            if (model != null) {
                model = model.trim { it <= ' ' }.replace("\\s*".toRegex(), "")
            } else {
                model = ""
            }
            return model
        }

    @SuppressLint("HardwareIds")
    fun getDeviceId(): String {
        val android_id = Secure.getString(CommonLib.mContext.contentResolver, Secure.ANDROID_ID)
        if (!TextUtils.isEmpty(android_id)) {
            return android_id
        } else {

            val m_szDevIDShort = "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10
            var serial: String? = null
            try {
                serial = Build::class.java.getField("SERIAL").get(null).toString()
                return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
            } catch (exception: Exception) {
                serial = "serial" // some value
            }

            return UUID(m_szDevIDShort.hashCode().toLong(), serial!!.hashCode().toLong()).toString()
        }
    }

    fun getOnlyDeviceId(): String {
        val android_id = Secure.getString(CommonLib.mContext.contentResolver, Secure.ANDROID_ID)
        if (!TextUtils.isEmpty(android_id)) {
            return android_id + "voice"
        } else {

            val m_szDevIDShort = "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10
            var serial: String? = null
            try {
                serial = Build::class.java.getField("SERIAL").get(null).toString()
                return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString() + "voice"
            } catch (exception: Exception) {
                serial = "serial" // some value
            }

            return UUID(m_szDevIDShort.hashCode().toLong(), serial!!.hashCode().toLong()).toString() + "voice"
        }
    }


    fun getNavigationBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("navigation_bar_height","dimen", "android")
        //获取NavigationBar的高度
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    fun getIMEI(): String {
        val manager = CommonLib.mContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            val method: Method = manager.javaClass.getMethod("getImei", Int::class.javaPrimitiveType)
            val imei1 = method.invoke(manager, 0) as String
            val imei2 = method.invoke(manager, 1) as String
            if (TextUtils.isEmpty(imei2)) {
                return imei1
            }
            if (!TextUtils.isEmpty(imei1)) {
                //因为手机卡插在不同位置，获取到的imei1和imei2值会交换，所以取它们的最小值,保证拿到的imei都是同一个
                var imei = ""
                imei = if (imei1.compareTo(imei2) <= 0) {
                    imei1
                } else {
                    imei2
                }
                return imei
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return ""
        }
        return ""
    }


}
