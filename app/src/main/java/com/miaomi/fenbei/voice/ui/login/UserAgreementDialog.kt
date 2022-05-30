package com.miaomi.fenbei.voice.ui.login

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.miaomi.fenbei.base.config.BaseConfig
import com.miaomi.fenbei.base.web.WebActivity
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.user_agreement_dialog.*

/**
 * Created by 
 * on 2019-08-31.
 */
class UserAgreementDialog(context: Context) : Dialog(context, R.style.common_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setWindowAnimations(com.miaomi.fenbei.room.R.style.dialogAnimStyle)//添加动画
        setContentView(R.layout.user_agreement_dialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        content_tv.movementMethod = LinkMovementMethod.getInstance()
        var string = SpannableString(context.resources.getString(R.string.user_agreement_notice))
        string.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                WebActivity.start(context, BaseConfig.XY_YSXY,"隐私政策")
//                AgreementActivity.startActivity(context, AgreementActivity.AGREE_TYPE_CONCEAL_ZC)
            }
        }, string.lastIndexOf("《隐私政策》"), string.lastIndexOf("《隐私政策》") + 6, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        string.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                WebActivity.start(context, BaseConfig.XY_YHXY,"用户协议")
//                AgreementActivity.startActivity(context, AgreementActivity.AGREE_TYPE_REG)
            }
        }, string.lastIndexOf("《用户协议》"), string.lastIndexOf("《用户协议》") + 6, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        string.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.colorPrimary)),
                string.lastIndexOf("《用户协议》"), string.lastIndexOf("《用户协议》") + 6, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        string.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.colorPrimary)),
                string.lastIndexOf("《隐私政策》"), string.lastIndexOf("《隐私政策》") + 6, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        content_tv.text = string
        tv_left.setOnClickListener {
            onClickListener?.apply {
                onRefuseClick()
                dismiss()
            }
        }
        tv_right.setOnClickListener {
            onClickListener?.apply {
                onAgreeClick()
                dismiss()
            }
        }
    }

    interface OnClickListener {
        fun onRefuseClick()
        fun onAgreeClick()
    }

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener): UserAgreementDialog {
        this.onClickListener = onClickListener
        return this
    }
}