package com.miaomi.fenbei.base.util

import android.content.Context
import com.miaomi.fenbei.base.core.CommonLib

object SPUtil {

    private const val NAME = "config"


    const val CONFIG_LOGIN_TOKEN = "config_login_token"
    const val CONFIG_UID = "config_user_id"
    const val CONFIG_TIM_SIGN = "config_tim_sign"
    const val CONFIG_ICON = "config_icon"
    const val CONFIG_NICKNAME = "config_nickname"
    const val CONFIG_LOCAL_USER = "config_local_user"
    const val CONFIG_IM_DEVELOP = "config_im_develop"
    const val CONFIG_IS_OPEN_EGGS = "config_is_open_eggs"
    const val CONFIG_TOP_MSG = "config_top_msg"
    const val CONFIG_BROADCAST_MSG = "config_broadcast_msg"
    const val CONFIG_IS_OPEN_ANIM = "config_is_open_anim"
    const val CONFIG_IS_OPEN_FULL_CHAT = "config_is_open_full_chat"
    const val CONFIG_IS_FIRST_OPEN_APP = "config_is_first_open_app"
    const val CONFIG_ROOM_ID = "config_room_id"

    const val CONFIG_MUSIC_REPEAT_MODE = "config_music_repeat_mode"
    const val CONFIG_MUSIC_ID = "config_music_id"
    const val CONFIG_MUSIC_VOLUME = "config_music_volume"
    const val CONFIG_RECORD_VOLUME = "config_record_volume"
    const val CONFIG_LAST_LAUNCHER = "config_last_launcher"

    const val CONFIG_IS_NEW_USER = "CONFIG_IS_NEW_USER"
    const val CONFIG_IS_FROM_THRID = "CONFIG_IS_FROM_THRID"
    const val CONFIG_IS_YOUNG_MODEL_SETTING = "CONFIG_IS_YOUNG_MODEL_SETTING"
    const val CONFIG_IS_YOUNG_MODEL_SHOW_TODAY = "CONFIG_IS_YOUNG_MODEL_SHOW_TODAY"
    const val CONFIG_IS_SIGN_IN_DIALOG_SHOW_TODAY = "CONFIG_IS_SIGN_IN_DIALOG_SHOW_TODAY"
    const val CONFIG_IS_NOT_OPEN_SMASH_EGG_MSG = "CONFIG_IS_NOT_OPEN_SMASH_EGG_MSG"

    const val CONFIG_SEND_SQUARE_TIME = "CONFIG_SEND_SQUARE_TIME"
    const val CONFIG_SELECTED_XX_TYPE = "CONFIG_SELECTED_XX_TYPE"

    const val CONFIG_GIFT_LONG_CLICK_TIPS = "CONFIG_GIFT_LONG_CLICK_TIPS"

    const val CJ_USER_OPEN_BOX_RULE = "CJ_USER_OPEN_BOX_RULE"



    /**
     * 存储String类型的值
     * @param key      key值
     * @param value    要存储的String值
     */
    fun putString(key: String, value: String) {
        val sharedPreferences = CommonLib.mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, value).apply()
    }

    /**
     * 获取String类型的值
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    fun getString(key: String, defValue: String): String {
        val sharedPreferences = CommonLib.mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defValue)
    }


    /**
     * 存储Int类型的值
     * @param key      key
     * @param value    要存储的Int值
     */
    fun putInt(key: String, value: Int) {
        val sharedPreferences = CommonLib.mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(key, value).apply()
    }


    /**
     * 获取Int类型的值
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    fun getInt(key: String, defValue: Int): Int {
        val sharedPreferences = CommonLib.mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, defValue)
    }


    /**
     * 存储Boolean类型的值
     * @param key      key
     * @param value    要存储Boolean值
     */
    fun putBoolean(key: String, value: Boolean) {
        val sharedPreferences = CommonLib.mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    /**
     * 获取Boolean类型的值
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    fun getBoolean(key: String, defValue: Boolean): Boolean {
        val sharedPreferences = CommonLib.mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, defValue)
    }


    fun putLong(key: String, value: Long) {
        val sharedPreferences = CommonLib.mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getLong(key: String,defValue :Long):Long {
        val sharedPreferences = CommonLib.mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(key, defValue)
    }

    //删除 单个 key
    fun deleShare(key: String) {
        val sharedPreferences = CommonLib.mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(key).apply()
    }

    //删除全部 key
    fun deleAll() {
        val sharedPreferences = CommonLib.mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

}
