package com.miaomi.fenbei.base.util

import androidx.collection.ArrayMap
import com.miaomi.fenbei.base.core.CommonLib
import java.net.URLEncoder


object SignUtil {


//    val appId=""
//    val appSecret="00c4b036b8b87545b690c3f04203810f"


    fun sign(keyMap: ArrayMap<String, String>):Map<String,String>{

        keyMap["os"] = "0"
        keyMap["token"] = DataHelper.getLoginToken()
        keyMap["device_id"]= DeviceUtil.getDeviceId()
        keyMap["channel"]= ChannelUtil.getAppChannel(CommonLib.mContext)
        keyMap["version_code"]= "v"+ AppUtils.versionCode.toString()
        keyMap["timestamp"]= (System.currentTimeMillis()/1000).toString()
//        keyMap["nonceStr"]= createNonceStr()
//        keyMap["sign"]= signInner(keyMap)

        return EncryptHelp.encrypt(keyMap)
    }


//    private fun createNonceStr():String{
//
//        val sourceStr = (System.currentTimeMillis()).toString() + DeviceUtil.getDeviceId() + Math.random() * 1000
//
//        return EncryptUtil.MD5(sourceStr)
//
//    }
//
//
//    private fun signInner(keyMap: Map<String, String>): String {
//
//        var strBusiness = ""
//        var sortMap = keyMap.toSortedMap()
//        for (key in sortMap.keys) {
//            if (strBusiness !== "") {
//                strBusiness += "&"
//            }
//            strBusiness += key
//            strBusiness += "="
//            strBusiness += URLEncoder.encode(sortMap[key].toString().trim())  //中文处理
//        }
//        return EncryptUtil.MD5(strBusiness + appSecret)
//    }

}