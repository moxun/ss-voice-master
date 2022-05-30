package com.miaomi.fenbei.voice.ui.mine.account_manage

import android.app.Activity
import android.graphics.Color
import android.os.CountDownTimer
import android.text.TextUtils
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.BaseBean
import com.miaomi.fenbei.base.bean.event.LoginEvent
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.RouterUrl
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.user_activity_account_logoff.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by 
 * on 2019-11-18.
 */
@Route(path = RouterUrl.accountLogoff)
class AccountLogoffActivity: BaseActivity() {

    companion object {
        const val TYPE_REMOVE_WARRANT_WECHAT = 1
        const val TYPE_REMOVE_WARRANT_QQ = 2
        const val TYPE_LOGOFF_PHONE = 3
    }

    @JvmField
    @Autowired
    var type: Int = 0

    private lateinit var timer: CountDownTimer

    override fun getLayoutId(): Int {
        return R.layout.user_activity_account_logoff
    }

    override fun initView() {
        ARouter.getInstance().inject(this)
        setBaseStatusBar(false,false)
        findViewById<TextView>(R.id.main_tv).setText(if (type == TYPE_REMOVE_WARRANT_QQ || type == TYPE_REMOVE_WARRANT_WECHAT) "解除绑定" else "账号注销")
        et_phone_number.setText(DataHelper.getUserInfo()!!.mobile)
        initCountDownTimer()
        initListener()
    }

    private fun initCountDownTimer() {
        timer = object : CountDownTimer(60000, 1000) {
            override fun onFinish() {
                tv_obtain_code.setTextColor(resources.getColor(R.color.colorPrimary))
                tv_obtain_code.text = "获取验证码"
                tv_obtain_code.isClickable = true
            }

            override fun onTick(millisUntilFinished: Long) {
                tv_obtain_code.setTextColor(Color.parseColor("#aaaaaa"))
                tv_obtain_code.text = "${millisUntilFinished / 1000}s后重新发送"
                tv_obtain_code.isClickable = false
            }
        }
    }

    private fun initListener() {
        tv_obtain_code.setOnClickListener {
            timer.start()
        }
        tv_sure.setOnClickListener {
            if (type == TYPE_REMOVE_WARRANT_QQ || type == TYPE_REMOVE_WARRANT_WECHAT) {
                CommonDialog(this)
                .setTitle("友情提示").setContent("解除授权后系统将会解除该第三方账号的登录授权，您将无法通过该第三方账号登录，为了您的账号正常登录，请确保您设置登录密码可正常使用。")
                .setContentCenter(false)
                .setLeftBt("确定") { removeWarrant() }
                .setRightBt("取消", null).show()
            } else if (type == TYPE_LOGOFF_PHONE) {
                CommonDialog(this)
                        .setTitle("友情提示").setContent("注销账号后系统将会删除该账号相关所有数据，包含但不限：用户授权信息(如网络权限，麦克风权限，录音权限，位置等信息)基本信息，用户等级，充值，消费数据，用户背包等，请谨慎操作，注销后若您使用该账号进行应用登录，则为全新注册账号不会继承现有数据。\n\n你确定要注销账号吗？")
                        .setContentCenter(false)
                        .setLeftBt("确定") { removeAccount() }
                        .setRightBt("取消", null).show()
            }
        }
        tv_obtain_code.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        val phone: String = et_phone_number.text.toString()
        if (TextUtils.isEmpty(phone) || phone.length != 11) {
            ToastUtil.error( this,"手机号码格式错误，请重新输入")
            return
        }
        NetService.getInstance(this).getCode(phone, 4, object : Callback<BaseBean>() {

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@AccountLogoffActivity, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }

            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                ToastUtil.suc(this@AccountLogoffActivity, "验证码发送成功")
                timer.start()
            }
        })
    }

    private fun removeWarrant() {
        NetService.getInstance(this).removeWarrant(type.toString(),
                et_phone_number.text.toString(), vcode_edit.text.toString(), object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                ToastUtil.suc(this@AccountLogoffActivity, "已成功解除授权")
                setResult(Activity.RESULT_OK)
                finish()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@AccountLogoffActivity, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    private fun removeAccount() {
        NetService.getInstance(this).removeAccountMobile(et_phone_number.text.toString(),
                vcode_edit.text.toString(), object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                ToastUtil.suc(this@AccountLogoffActivity, "账号注销成功")
                EventBus.getDefault().post(LoginEvent(false))
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@AccountLogoffActivity, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}