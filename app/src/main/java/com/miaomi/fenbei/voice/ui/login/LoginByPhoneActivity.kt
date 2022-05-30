package com.miaomi.fenbei.voice.ui.login

import android.os.CountDownTimer
import android.text.TextUtils
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.BaseBean
import com.miaomi.fenbei.base.bean.MineBean
import com.miaomi.fenbei.base.bean.event.LoginEvent
import com.miaomi.fenbei.base.config.BaseConfig
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.CommonLib
import com.miaomi.fenbei.base.core.dialog.LoadingDialog
import com.miaomi.fenbei.base.core.msg.MsgManager
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.LoginHelper
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.activity_login_by_phone.*
import org.greenrobot.eventbus.EventBus


@Route(path = "/login/loginByPhone")
class LoginByPhoneActivity : BaseActivity() {



    private var logining = false

    @Autowired(name = "phone_number")
    @JvmField
    var phoneNumber = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_login_by_phone
    }

    private lateinit var mCountDownTimer: CountDownTimer

    private lateinit var loadingDialog: LoadingDialog

    override fun initView() {
        setBaseStatusBar(false,false)
        ARouter.getInstance().inject(this)

        loadingDialog = LoadingDialog(this)
        loadingDialog.setOnShowListener {
            logining = true
        }
        loadingDialog.setOnDismissListener {
            logining = false
        }
        tv_phone.setText(phoneNumber)

        vcode_edit.initStyle(R.drawable.common_bg_pwd, 6, 5f, R.color.translucent, R.color.white, 16)
        vcode_edit.setShowPwd(true)
        vcode_edit.setOnTextFinishListener {
            if (!logining) {
                loadingDialog.setCancelable(false)
                if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(it)) {
                    ToastUtil.error(this, "手机号和验证码不可为空")
                } else {
                    loadingDialog.show()
                    startLogin(BaseConfig.LOGIN_BY_USERNAME)
                }
            }
        }
//        login_btn.setOnClickListener {
//            if (!logining) {
//                loadingDialog.setCancelable(false)
//                if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(vcode_edit.text.toString())) {
//                    ToastUtil.error(this, "手机号和验证码不可为空")
//                } else {
//                    if (DoubleClickUtil.isFastDoubleClick()){
//                        loadingDialog.show()
//                        startLogin(BaseConfig.LOGIN_BY_USERNAME)
//                    }
//                }
//            }
//        }
        bt_re_get_yzm.setOnClickListener {
            sendCode()
        }

        mCountDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                bt_re_get_yzm.setText("(" + (millisUntilFinished / 1000).toString() + "s后重新发送)")
                bt_re_get_yzm.isClickable = false
            }

            override fun onFinish() {
                bt_re_get_yzm.setText("获取验证码")
                bt_re_get_yzm.isClickable = true
            }
        }
    }


    private fun sendCode() {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length != 11) {
            return
        }

        NetService.getInstance(this).sendCode(1,phoneNumber, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                ToastUtil.suc(this@LoginByPhoneActivity, "验证码发送成功")
                mCountDownTimer.start()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@LoginByPhoneActivity, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

//    private lateinit var valueAnimator: ValueAnimator

    private fun startLogin(type: Int, openId: String = "", unionId: String = "", gender: String = "", nickName: String = "",face: String = "") {
        DataHelper.saveIsFromThird(false)

        if (type == BaseConfig.LOGIN_BY_QQ || type == BaseConfig.LOGIN_BY_WX){
            DataHelper.saveIsFromThird(true)
        }

        doLogin(face,type, openId, unionId, gender, nickName)
    }

    private fun upLoadLoginData(type: Int,id:String){
        when (type) {
            BaseConfig.LOGIN_BY_WX -> CommonLib.onProfileSignIn("WX",id)
            BaseConfig.LOGIN_BY_QQ -> CommonLib.onProfileSignIn("QQ",id)
            BaseConfig.LOGIN_BY_PWD -> CommonLib.onProfileSignIn("PWD",id)
            else -> CommonLib.onProfileSignIn("PHONE",id)
        }
    }

    private fun doLogin(face:String,type: Int, openId: String = "", unionId: String = "", gender: String = "", nickName: String = "") {
        NetService.getInstance(this).login(face,"",phoneNumber, vcode_edit.pwdText, type, openId, unionId, gender, nickName, object : Callback<MineBean>() {
            override fun onSuccess(nextPage: Int, bean: MineBean, code: Int) {
                upLoadLoginData(type,bean.user_id.toString())
                getUserInfo(bean)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@LoginByPhoneActivity, msg)
                loginFail()
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

    private fun getUserInfo(loginBean: MineBean) {
        DataHelper.saveIsNewUser(loginBean.isIs_new_user)
        DataHelper.saveLoginToken(loginBean.token)
        DataHelper.saveUID(loginBean.user_id)
        DataHelper.saveTimSign(loginBean.sig)
        LoginHelper.INSTANCE.loginSuc(loginBean.user_id, loginBean.token, loginBean.sig, loginBean)
        MsgManager.INSTANCE.joinBigGroup()
        ARouter.getInstance().build("/main/main")
                .withTransition(R.anim.anim_bottom_in, R.anim.anim_bottom_out)
                .navigation(this@LoginByPhoneActivity, object : NavCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        loginSuc()
                        finish()
                    }
                })
    }

    private fun loginSuc() {
        loadingDialog.loginDismiss()
    }

    private fun loginFail() {
        loadingDialog.loginDismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer.cancel()
    }

}