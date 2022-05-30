package com.miaomi.fenbei.base.util

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.miaomi.fenbei.base.bean.IMDevelopBean
import com.miaomi.fenbei.base.bean.LocalUserBean
import com.miaomi.fenbei.base.config.BaseConfig


object DataHelper {
    lateinit var iMDevelopBean : IMDevelopBean
//    var currentCity : String = ""


    fun saveConfigLongClickTips(time: Boolean) {
        SPUtil.putBoolean(SPUtil.CONFIG_GIFT_LONG_CLICK_TIPS, time)
    }

    fun getConfigLongClickTips(): Boolean {
        return SPUtil.getBoolean(SPUtil.CONFIG_GIFT_LONG_CLICK_TIPS, true)
    }



    fun saveCJOpenBoxRule(number:String){
        if (TextUtils.isEmpty(number)){
            return
        }
        SPUtil.putString(SPUtil.CJ_USER_OPEN_BOX_RULE, number)
    }
    fun getCJOpenBoxRule():String{
        return SPUtil.getString(SPUtil.CJ_USER_OPEN_BOX_RULE, "")
    }

    fun saveXXType(number:Int){

        SPUtil.putInt(SPUtil.CONFIG_SELECTED_XX_TYPE, number)
    }
    fun getXXType():Int{
        return SPUtil.getInt(SPUtil.CONFIG_SELECTED_XX_TYPE, 3)
    }

    fun saveIsNewUser(isNew:Boolean){
        SPUtil.putBoolean(SPUtil.CONFIG_IS_NEW_USER, isNew)
    }
    fun isNewUser():Boolean{
        return SPUtil.getBoolean(SPUtil.CONFIG_IS_NEW_USER, false)
    }

    fun saveIsFromThird(isNew:Boolean){
        SPUtil.putBoolean(SPUtil.CONFIG_IS_FROM_THRID, isNew)
    }

    fun isFromThird():Boolean{
        return SPUtil.getBoolean(SPUtil.CONFIG_IS_FROM_THRID, false)
    }

    fun saveLoginToken(token:String){
        SPUtil.putString(SPUtil.CONFIG_LOGIN_TOKEN, token)
    }

    fun getLoginToken(): String{
        return SPUtil.getString(SPUtil.CONFIG_LOGIN_TOKEN, "")
    }

    fun clearLoginToken(){
        SPUtil.putString(SPUtil.CONFIG_LOGIN_TOKEN, "")
    }


    fun saveUID(uid:Int){
        SPUtil.putInt(SPUtil.CONFIG_UID, uid)
    }

    fun getUID(): Int{
        return SPUtil.getInt(SPUtil.CONFIG_UID, 0)
    }

    fun saveRoomId(roomId:Int){
        SPUtil.putInt(SPUtil.CONFIG_ROOM_ID, roomId)
    }

    fun getRoomId(): Int{
        return SPUtil.getInt(SPUtil.CONFIG_ROOM_ID, 0)
    }

    fun saveIsFirstOpenApp(isFistOpen: Boolean) {
        SPUtil.putBoolean(SPUtil.CONFIG_IS_FIRST_OPEN_APP, isFistOpen)
    }

    fun getIsFirstOpenApp(): Boolean {
        return SPUtil.getBoolean(SPUtil.CONFIG_IS_FIRST_OPEN_APP, true)
    }

    fun clearUID(){
        SPUtil.putInt(SPUtil.CONFIG_UID, 0)
    }

    fun saveTimSign(sign:String){
        SPUtil.putString(SPUtil.CONFIG_TIM_SIGN, sign)
    }

    fun getTimSign(): String{
        return SPUtil.getString(SPUtil.CONFIG_TIM_SIGN, "")
    }

    fun clearTimSign(){
        SPUtil.putString(SPUtil.CONFIG_TIM_SIGN, "")
    }


    fun getIMDevelop(): IMDevelopBean {
        if (this::iMDevelopBean.isInitialized){
            return iMDevelopBean
        }
        val imDevelopStr = SPUtil.getString(SPUtil.CONFIG_IM_DEVELOP, "")
        if (TextUtils.isEmpty(imDevelopStr)) {
            val bean = IMDevelopBean()
            bean.imAccountType=36862
            bean.prefix=""
            bean.upload="im-file-oss.miaorubao.com"
            bean.imBigGroupID= BaseConfig.BASE_BIG_GROUNP_ID
            bean.imSdkAppId = BaseConfig.BASE_IM_ID
            bean.new_main = BaseConfig.BASE_URL
            iMDevelopBean = bean
            return iMDevelopBean
        }else{
            iMDevelopBean = Gson().fromJson(imDevelopStr, IMDevelopBean::class.java)
        }
        return iMDevelopBean
    }

    public fun saveDebugDevelopBean(){
        val bean = IMDevelopBean()
        bean.imAccountType=36862
        bean.prefix=""
        bean.upload="im-file-oss.miaorubao.com"
        bean.imBigGroupID= "@TGS#aNC6EKFHU"
        bean.imSdkAppId = 1400523941
        bean.new_main = "http://test-new-km.94miao.com"
        SPUtil.putString(SPUtil.CONFIG_IM_DEVELOP,Gson().toJson(bean))
    }

    public fun saveReleaseDevelopBean(){
        val bean = IMDevelopBean()
        bean.imAccountType = 36862
        bean.prefix=""
        bean.upload="im-file-oss.miaorubao.com"
        bean.imBigGroupID= "@TGS#aP2PRTEHV"
        bean.imSdkAppId = 1400518115
        bean.new_main = "https://apidecibel.cnciyin.com"
        SPUtil.putString(SPUtil.CONFIG_IM_DEVELOP,Gson().toJson(bean))
    }



    fun getUserInfo(): LocalUserBean?{
        val localUserStr = SPUtil.getString(SPUtil.CONFIG_LOCAL_USER, "")
        return Gson().fromJson(localUserStr, object : TypeToken<LocalUserBean>(){}.type)
    }

    fun updatalUserInfo(localUserBean: LocalUserBean){
        SPUtil.putString(SPUtil.CONFIG_LOCAL_USER, Gson().toJson(localUserBean).toString())
    }

    fun clearLocalUserInfo(){
        SPUtil.putString(SPUtil.CONFIG_LOCAL_USER, "")
    }


    fun saveGiftAnimOpen(open: Boolean) {
        SPUtil.putBoolean(SPUtil.CONFIG_IS_OPEN_ANIM, open)
    }

    fun saveOpenFullChat(open: Boolean) {
        SPUtil.putBoolean(SPUtil.CONFIG_IS_OPEN_FULL_CHAT, open)
    }

    fun  isGiftAnimOpen(): Boolean {
        return SPUtil.getBoolean(SPUtil.CONFIG_IS_OPEN_ANIM, true)
    }

    fun isFullChatOpen(): Boolean {
        return SPUtil.getBoolean(SPUtil.CONFIG_IS_OPEN_FULL_CHAT, true)
    }


    fun saveIsYoungModelSetting(isSetting: Int) {
        SPUtil.putInt(SPUtil.CONFIG_IS_YOUNG_MODEL_SETTING, isSetting)
    }

    fun getIsYoungModelSetting(): Int{
        return SPUtil.getInt(SPUtil.CONFIG_IS_YOUNG_MODEL_SETTING, 0)
    }

    fun saveYoungModelShowTime(time: Long) {
        SPUtil.putLong(SPUtil.CONFIG_IS_YOUNG_MODEL_SHOW_TODAY, time)
    }

    fun getYoungModelShowTime(): Long {
        return SPUtil.getLong(SPUtil.CONFIG_IS_YOUNG_MODEL_SHOW_TODAY, 0)
    }

    fun saveSmashEggMsgNotOpen(isNotOpen: Boolean) {
        SPUtil.putBoolean(SPUtil.CONFIG_IS_NOT_OPEN_SMASH_EGG_MSG, isNotOpen)
    }

    fun saveSendSquaeTime(currentTime: Long) {
        SPUtil.putLong(SPUtil.CONFIG_SEND_SQUARE_TIME, currentTime)
    }

    fun getSendSquaeTime():Long {
        return SPUtil.getLong(SPUtil.CONFIG_SEND_SQUARE_TIME, 0)
    }

    fun isSmashEggMsgNotOpen(): Boolean {
        return SPUtil.getBoolean(SPUtil.CONFIG_IS_NOT_OPEN_SMASH_EGG_MSG, false)
    }

}