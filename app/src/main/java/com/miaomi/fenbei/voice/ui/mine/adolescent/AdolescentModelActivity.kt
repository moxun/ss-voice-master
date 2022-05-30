package com.miaomi.fenbei.voice.ui.mine.adolescent

import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.voice.ui.mine.about.AgreementActivity
import kotlinx.android.synthetic.main.activity_adolescent_model.*

/**
 * Created by
 * on 2019-09-26.
 * 青少年模式
 */
@Route(path = "/mine/adolescentModel")
class AdolescentModelActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_adolescent_model
    }

    override fun initView() {
        setBaseStatusBar(false,false)
        adolescent_plan.movementMethod = LinkMovementMethod.getInstance()
        var sp = SpannableString("了解《未成年保护计划》")
        sp.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorPrimary)), 2, sp.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        sp.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                AgreementActivity.startActivity(this@AdolescentModelActivity, AgreementActivity.AGREE_TYPE_ADOLESCENT)
            }
        }, 2, sp.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        adolescent_plan.text = sp
        if (DataHelper.getIsYoungModelSetting() == 0) {
            open_bt.text = "开启青少年模式"
        } else {
            open_bt.text = "关闭青少年模式"
        }
        open_bt.setOnClickListener {
            ARouter.getInstance().build("/mine/adolescentModelPwd").navigation()
            finish()
        }
    }
}