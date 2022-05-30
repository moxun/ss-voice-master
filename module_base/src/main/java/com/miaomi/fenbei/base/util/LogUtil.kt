package com.miaomi.fenbei.base.util

import com.miaomi.fenbei.base.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger



object LogUtil {

    fun initLog(){
        Logger.addLogAdapter(AndroidLogAdapter())
    }


    fun d(message: String, vararg args: Any) {
        Logger.d(message, *args)
    }

    fun d(message: Any?) {
        Logger.d(message)
    }

    fun e(message: String, vararg args: Any) {
        Logger.e(null, message, *args)
    }

    fun e(throwable: Throwable?, message: String, vararg args: Any) {
        Logger.e(throwable, message, *args)
    }

    fun i(message: String, vararg args: Any) {
        if(BuildConfig.DEBUG){
            Logger.i(message, *args)
        }
    }

    fun v(message: String, vararg args: Any) {
        Logger.v(message, *args)
    }

    fun w(message: String, vararg args: Any) {
        Logger.w(message, *args)
    }
}