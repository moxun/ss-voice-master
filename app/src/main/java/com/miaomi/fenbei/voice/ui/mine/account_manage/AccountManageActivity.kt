package com.miaomi.fenbei.voice.ui.mine.account_manage

//import com.mier.share.core.ShareLogin
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
import com.miaomi.fenbei.base.share.callback.ThirdLoginCallback
import com.miaomi.fenbei.base.share.core.ThirdLogin
import com.miaomi.fenbei.voice.R
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.user_activity_account_manage.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by 
 * on 2019-11-18.
 */
@Route(path = RouterUrl.accountManage)
class AccountManageActivity: BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.user_activity_account_manage
    }

    override fun initView() {
        setBaseStatusBar(false,false)
        initListener()
    }

    private fun initListener() {
        account_logoff_tv.setOnClickListener {
            when (DataHelper.getUserInfo()!!.authorization) {
                0 -> ARouter.getInstance().build(RouterUrl.accountLogoff).withInt("type", AccountLogoffActivity.TYPE_LOGOFF_PHONE).navigation()
                1 -> {
                    CommonDialog(this)
                            .setTitle("友情提示").setContent("注销账号后系统将会删除该账号相关所有数据，包含但不限：用户授权信息(如网络权限，麦克风权限，录音权限，位置等信息)基本信息，用户等级，充值，消费数据，用户背包等，请谨慎操作，注销后若您使用该账号进行应用登录，则为全新注册账号不会继承现有数据。\n\n你确定要注销账号吗？")
                            .setContentCenter(false)
                            .setLeftBt("确定") {
                                ThirdLogin.oauthLogin(this, SHARE_MEDIA.WEIXIN,object : ThirdLoginCallback {
                                    override fun onSuc(openId: String, unionId: String, gender: String, nickName: String, face: String) {
                                        removeAccountThird(openId, unionId)
                                    }

                                    override fun onFail(msg: String) {
                                    }

                                    override fun onCancel() {

                                    }
                                })
                            }
                            .setRightBt("取消", null).show()
                }
                2 -> {
                    CommonDialog(this)
                            .setTitle("友情提示").setContent("注销账号后系统将会删除该账号相关所有数据，包含但不限：用户授权信息(如网络权限，麦克风权限，录音权限，位置等信息)基本信息，用户等级，充值，消费数据，用户背包等，请谨慎操作，注销后若您使用该账号进行应用登录，则为全新注册账号不会继承现有数据。\n\n你确定要注销账号吗？")
                            .setContentCenter(false)
                            .setLeftBt("确定") {
                                ThirdLogin.oauthLogin(this,SHARE_MEDIA.QQ,object : ThirdLoginCallback {
                                    override fun onSuc(openId: String, unionId: String, gender: String, nickName: String, face: String) {
                                        removeAccountThird(openId, unionId)
                                    }

                                    override fun onFail(msg: String) {
                                    }

                                    override fun onCancel() {

                                    }
                                })
                            }
                            .setRightBt("取消", null).show()
                }
            }
        }

    }


    private fun removeAccountThird(openId: String, unionId: String) {
        NetService.getInstance(this).removeAccountThird(openId, unionId, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                ToastUtil.suc(this@AccountManageActivity, "账号注销成功")
                EventBus.getDefault().post(LoginEvent(false))
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@AccountManageActivity, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

}