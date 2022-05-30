package com.miaomi.fenbei.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.R
import com.miaomi.fenbei.base.bean.LocalUserBean
import com.miaomi.fenbei.base.bean.event.LoginEvent
import com.miaomi.fenbei.base.core.CommonLib
import com.miaomi.fenbei.base.core.msg.MsgManager
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMManager
import org.greenrobot.eventbus.EventBus


enum class LoginHelper {
    INSTANCE;

    interface Callback {
        fun onSuc()
        fun onFail(msg: String)
    }

    @SuppressLint("WrongConstant")
    fun checkLogin(context: Context = CommonLib.mContext, autoLogin: Boolean = true, callback: Callback): Boolean {

        return if (TextUtils.isEmpty(DataHelper.getLoginToken()) || DataHelper.getUID() == 0 || TextUtils.isEmpty(DataHelper.getTimSign()) || DataHelper.getUserInfo() == null) {
            if (autoLogin) {
                ToastUtil.suc(context, "请先登录")
                startLoginActivity(context)
                callback.onFail("请先登录")
            }
            false
        } else {
            if (TextUtils.isEmpty(TIMManager.getInstance().loginUser)) {
                loginTim(DataHelper.getUID(), DataHelper.getTimSign(), object : Callback {
                    override fun onSuc() {
                        MsgManager.INSTANCE.joinBigGroup()
                        callback.onSuc()
                    }

                    @SuppressLint("WrongConstant")
                    override fun onFail(msg: String) {
//                        ToastUtil.error(context, msg)
                        startLoginActivity(context)
                        callback.onFail(msg)
                    }
                })
            } else {
                MsgManager.INSTANCE.joinBigGroup()
                callback.onSuc()
            }
            true
        }
    }

    fun isLogin(): Boolean {
        return !(TextUtils.isEmpty(DataHelper.getLoginToken()) || DataHelper.getUID() == 0 || TextUtils.isEmpty(DataHelper.getTimSign()) || DataHelper.getUserInfo() == null)
    }

    @SuppressLint("WrongConstant")
    fun startLoginActivity(context: Context = CommonLib.mContext) {
        loginOut() //先退出登录
        ARouter.getInstance().build("/login/login").withTransition(R.anim.anim_bottom_in, R.anim.anim_bottom_out).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK).navigation()

    }

    fun loginTim(callback: Callback) {
        if (TextUtils.isEmpty(TIMManager.getInstance().loginUser)) {
            loginTim(DataHelper.getUID(), DataHelper.getTimSign(), object : Callback {
                override fun onSuc() {
                    callback.onSuc()
                }

                @SuppressLint("WrongConstant")
                override fun onFail(msg: String) {
                    ToastUtil.error(CommonLib.mContext, msg)
                    ARouter.getInstance().build("/login/login").withTransition(R.anim.anim_bottom_in, R.anim.anim_bottom_out).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK).navigation()
                }
            })
        } else {
            callback.onSuc()
        }
    }

    fun loginSuc(uid: Int, token: String, sign: String, bean: LocalUserBean) {


        DataHelper.saveLoginToken(token)
        DataHelper.saveUID(uid)
        DataHelper.saveTimSign(sign)
        DataHelper.updatalUserInfo(bean)

        EventBus.getDefault().post(LoginEvent(true))
    }

    fun loginOut() {
        DataHelper.clearLoginToken()
        DataHelper.clearUID()
//        DataHelper.clearSignInDialogShowTime()
        loginOutTim()
        DataHelper.clearLocalUserInfo()
    }

    fun loginTim(uid: Int, sign: String, callback: Callback) {
        TIMManager.getInstance().login(DataHelper.getIMDevelop()?.prefix + uid.toString(), sign, object : TIMCallBack {
            override fun onError(code: Int, desc: String) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                callback.onFail("聊天服务器异常：$desc$code")
            }

            override fun onSuccess() {
                callback.onSuc()
            }
        })
    }

    private fun loginOutTim() {
        DataHelper.clearTimSign()
        TIMManager.getInstance().logout(object : TIMCallBack {
            override fun onSuccess() {
            }

            override fun onError(p0: Int, p1: String?) {
            }
        })
    }

}