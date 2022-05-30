package com.miaomi.fenbei.room.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.view.View
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.base.bean.GotRedPacketBean
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.bean.FollowBean
import com.miaomi.fenbei.base.bean.RedPacketBean
import com.miaomi.fenbei.base.core.dialog.LoadingDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import kotlinx.android.synthetic.main.room_dialog_red_packet.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import com.miaomi.fenbei.base.config.BaseConfig

import org.greenrobot.eventbus.ThreadMode

/**
 * Created by 
 * on 2020/1/2.
 */
class RedPacketDialog(context: Context, private var redPacketBean: RedPacketBean, private var isFollow: Boolean, var onDismissListener : OnDismissListener?): Dialog(context, R.style.common_dialog) {

    interface OnDismissListener {
        fun dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setWindowAnimations(R.style.dialogAnimStyle)//添加动画
        setContentView(R.layout.room_dialog_red_packet)
        EventBus.getDefault().register(this)

        password_pt.initStyle(R.drawable.common_bg_pwd_white, 4, 10f, R.color.red, R.color.text_color_00, 15)
        password_pt.setShowPwd(true)
        password_pt.setOnTextFinishListener{ grabRedPacket() }

        close_iv.setOnClickListener { dismiss() }

        send_tv.setOnClickListener {
            onSendRedPacketClickListener?.let { it.onSendRedPacketClick() }
            dismiss()
        }

        get_btn.setOnClickListener { if (password_pt.pwdText.length != 4) ToastUtil.error(context, "请输入4位数的红包口令") }

        look_other_tv.setOnClickListener { RedPacketLuckRankDialog(context, redPacketBean.id.toString()).show() }

        attention_btn.setOnClickListener { followChat() }
        ImgUtil.loadCircleImg(context.applicationContext, redPacketBean.face, header_iv, R.drawable.common_avter_placeholder)
        nick_tv.text = "${redPacketBean.nickname}的红包"
        say_hi_tv.text = redPacketBean.say_text
        total_diamond_tv.text = "红包共${redPacketBean.diamonds}钻石"
        total_diamond_attention_tv.text = "红包共${redPacketBean.diamonds}钻石"
        if (redPacketBean.need_follow == 1 && !isFollow && !ChatRoomManager.isRoomHost()) {
            attention_layout.visibility = View.VISIBLE
            total_diamond_attention_tv.visibility = View.VISIBLE
            grab_layout.visibility = View.GONE
            send_tv.visibility = View.INVISIBLE
        } else if (redPacketBean.secret_status == "1") {
            password_layout.visibility = View.VISIBLE
            if (redPacketBean.user_id == DataHelper.getUID().toString()) {
                password_tv.visibility = View.VISIBLE
                password_tv.text = "口令：${redPacketBean.secret_order}"
            }
            total_diamond_attention_tv.visibility = View.VISIBLE
        } else {
            grabRedPacket()
        }
        if (redPacketBean.split_type == BaseConfig.RED_PACKET_TYPE_COMMON && redPacketBean.user_id != DataHelper.getUID().toString()) {
            total_diamond_attention_tv.visibility = View.GONE
            look_other_tv.visibility = View.GONE
            total_diamond_tv.visibility = View.GONE
        }
    }

    interface OnSendRedPacketClickListener {
        fun onSendRedPacketClick()
    }

    private var onSendRedPacketClickListener: OnSendRedPacketClickListener? = null

    fun setOnSendRedPacketClickListener(onSendRedPacketClickListener: OnSendRedPacketClickListener) {
        this.onSendRedPacketClickListener = onSendRedPacketClickListener
    }

    private fun bindView(data: GotRedPacketBean) {
        password_layout.visibility = View.GONE
        attention_layout.visibility = View.GONE
        total_diamond_attention_tv.visibility = View.GONE
        grab_layout.visibility = View.VISIBLE
        send_tv.visibility = View.VISIBLE
        var sp = SpannableString(if (data.got) "${data.diamonds}钻石" else "手慢了，红包抢完了")
        sp.setSpan(AbsoluteSizeSpan(24, true), if (data.got) sp.length - 2 else 0, sp.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        win_diamond_tv.text = sp
    }

    /**
     * 抢红包
     */
    private fun grabRedPacket() {
        var loadingDialog = LoadingDialog(context!!)
        loadingDialog.show()
        NetService.getInstance(context!!).grabRedPacket(ChatRoomManager.getRoomId(), redPacketBean.id.toString(), password_pt.pwdText, object : Callback<GotRedPacketBean>() {
            override fun onSuccess(nextPage: Int, bean: GotRedPacketBean, code: Int) {
                loadingDialog.dismiss()
                bindView(bean)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                loadingDialog.dismiss()
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    private fun followChat() {
        NetService.getInstance(context!!).addConcern(DataHelper.getLoginToken(), ChatRoomManager.getRoomHostUid().toString(), object : Callback<FollowBean>() {
            override fun onSuccess(nextPage: Int, bean: FollowBean, code: Int) {
                ToastUtil.suc(context!!, "关注成功")
                if (redPacketBean.secret_status == "1") {
                    password_layout.visibility = View.VISIBLE
                    password_pt.setFocus()
                    attention_layout.visibility = View.GONE
                    if (redPacketBean.user_id == DataHelper.getUID().toString()) {
                        password_tv.visibility = View.VISIBLE
                        password_tv.text = "口令：${redPacketBean.secret_order}"
                    }
                } else {
                    grabRedPacket()
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {}
            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    override fun show() {
        super.show()
        if (password_layout.visibility == View.VISIBLE) password_pt.setFocus()
    }

    override fun dismiss() {
        super.dismiss()
        onDismissListener?.dismiss()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        if (isShowing) dismiss()
    }

}