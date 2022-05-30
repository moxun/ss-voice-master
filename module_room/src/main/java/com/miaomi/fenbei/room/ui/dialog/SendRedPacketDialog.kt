package com.miaomi.fenbei.room.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.RedPacketBaseBean
import com.miaomi.fenbei.base.bean.RedPacketBean
import com.miaomi.fenbei.base.config.BaseConfig
import com.miaomi.fenbei.base.core.dialog.LoadingDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.RouterUrl
import com.miaomi.fenbei.base.util.ToastUtil
import kotlinx.android.synthetic.main.room_dialog_send_red_packet.*

class SendRedPacketDialog(context: Context,bean: RedPacketBaseBean?) : Dialog(context, R.style.common_dialog){

    var data: RedPacketBaseBean? = bean
    private var type = BaseConfig.RED_PACKET_TYPE_LUCK
    private var sendDiamond = 0
    private var redPacketNum = 0
    private var fullServicePrice = 0

    private var isSendingPackage = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setWindowAnimations(R.style.dialogAnimStyle)//添加动画
        setContentView(R.layout.room_dialog_send_red_packet)
        initView()
    }

    private fun initView() {
        type_tv.text = "手气红包"
        balance_tv.text = "你的钻石：${data?.recharge_residue}钻石"
        realm_notice_price_tv.text = "${data?.realm_notice_price}"
        send_diamond_et.hint = "${data!!.lucky_minimum}~${data!!.lucky_maximum}"
        num_limit.text = "红包数量限制${data?.min_count}~${data?.max_count}个"
//        agreement_check.visibility = if (ChatRoomManager.roomType != ChatRoomManager.ROOM_TYPE_BROADCASTING_STATION) View.GONE else View.VISIBLE
        getRedPacketBaseData()

        iv_type.setOnClickListener {
            if (type == BaseConfig.RED_PACKET_TYPE_LUCK) {
                type = BaseConfig.RED_PACKET_TYPE_COMMON
                send_diamond_title_tv.text = "单个红包"
                type_tv.text = "普通红包"
                send_diamond_et.hint = "${data!!.minimum}~${data!!.maximum}"
                total_diamond_tv.text = "总消耗${sendDiamond * redPacketNum + fullServicePrice}钻石"
            } else {
                type = BaseConfig.RED_PACKET_TYPE_LUCK
                send_diamond_title_tv.text = "总钻石"
                type_tv.text = "手气红包"
                send_diamond_et.hint = "${data!!.lucky_minimum}~${data!!.lucky_maximum}"
                total_diamond_tv.text = "总消耗${sendDiamond + fullServicePrice}钻石"
            }
        }

        send_diamond_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                sendDiamond = if (s.isEmpty()) 0 else s.toString().toInt()
                if (type == BaseConfig.RED_PACKET_TYPE_LUCK) {
                    total_diamond_tv.text = "总消耗${sendDiamond + fullServicePrice}钻石"
                } else {
                    total_diamond_tv.text = "总消耗${sendDiamond * redPacketNum + fullServicePrice}钻石"
                }
            }

        })

        num_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                redPacketNum = if (s.isEmpty()) 0 else s.toString().toInt()
                if (type == BaseConfig.RED_PACKET_TYPE_LUCK) {
                    total_diamond_tv.text = "总消耗${sendDiamond + fullServicePrice}钻石"
                } else {
                    total_diamond_tv.text = "总消耗${sendDiamond * redPacketNum + fullServicePrice}钻石"
                }
            }
        })

        full_service_st.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                fullServicePrice = data!!.realm_notice_price
                if (type == BaseConfig.RED_PACKET_TYPE_LUCK) {
                    total_diamond_tv.text = "总消耗${sendDiamond + fullServicePrice}钻石"
                } else {
                    total_diamond_tv.text = "总消耗${sendDiamond * redPacketNum + fullServicePrice}钻石"
                }
            } else {
                fullServicePrice = 0
                if (type == BaseConfig.RED_PACKET_TYPE_LUCK) {
                    total_diamond_tv.text = "总消耗${sendDiamond + fullServicePrice}钻石"
                } else {
                    total_diamond_tv.text = "总消耗${sendDiamond * redPacketNum + fullServicePrice}钻石"
                }
            }
        }


        send_btn.setOnClickListener {
            sendRedPacket()
        }
    }

    private fun sendRedPacket() {
        if (isSendingPackage) {
            return
        }
        isSendingPackage = true
        if (sendDiamond == 0) {
            ToastUtil.suc(context, if (type == BaseConfig.RED_PACKET_TYPE_COMMON) "单个红包钻石不能为空" else "总钻石不能为空")
            isSendingPackage = false
            return
        }
        if (redPacketNum == 0) {
            ToastUtil.suc(context, "红包个数不能为空")
            isSendingPackage = false
            return
        }
        if (type == BaseConfig.RED_PACKET_TYPE_LUCK) {
            if (sendDiamond / redPacketNum > data!!.maximum) {
                ToastUtil.error(context, "单个红包不得超过${data!!.maximum}钻石")
                isSendingPackage = false
                return
            } else if (sendDiamond / redPacketNum < data!!.minimum) {
                ToastUtil.error(context, "单个红包不得低于${data!!.minimum}钻石")
                isSendingPackage = false
                return
            }
        } else {
            if (sendDiamond > data!!.maximum) {
                ToastUtil.error(context, "单个红包不得超过${data!!.maximum}钻石")
                isSendingPackage = false
                return
            } else if (sendDiamond < data!!.minimum) {
                ToastUtil.error(context, "单个红包不得低于${data!!.minimum}钻石")
                isSendingPackage = false
                return
            }
        }
        if (redPacketNum > data!!.max_count || redPacketNum < data!!.min_count) {
            ToastUtil.error(context, "请输入${data!!.min_count}~${data!!.max_count}的红包个数")
            isSendingPackage = false
            return
        }
        if (sendDiamond > data!!.recharge_residue) {
            ToastUtil.suc(context, "余额不足，请充值")
            ARouter.getInstance().build(RouterUrl.pay).navigation()
            isSendingPackage = false
            return
        }
        val kouling = password_et.text.toString()
        if (kouling.length in 2..3){
            isSendingPackage = false
            ToastUtil.suc(context, "请输入4位数口令")
            return
        }
        var secretStatus = 1
        if (kouling.isEmpty()){
            secretStatus = 0
        }
        val loadingDialog = LoadingDialog(context)
        loadingDialog.show()
        loadingDialog.setCancelable(false)
        NetService.getInstance(context).sendRedPacket(ChatRoomManager.getRoomId(), sendDiamond.toString(),
                redPacketNum.toString(), type.toString(),"!!",
                "0", "0", secretStatus.toString(), kouling,
                object : Callback<RedPacketBean>() {
                    override fun onSuccess(nextPage: Int, bean: RedPacketBean, code: Int) {
//                        isSendingPackage = false
                        loadingDialog.dismiss()
                        ToastUtil.error(context, "支付成功")
                        dismiss()
                    }

                    override fun onError(msg: String, throwable: Throwable, code: Int) {
                        isSendingPackage = false
                        loadingDialog.dismiss()
                        ToastUtil.error(context, msg)
                    }

                    override fun isAlive(): Boolean {
                        return true
                    }

                })
    }

    /**
     * 获取红包配置数据
     */
    private fun getRedPacketBaseData() {
        NetService.getInstance(context).getRedPacketBaseData(ChatRoomManager.getRoomId(), object : Callback<RedPacketBaseBean>() {
            override fun onSuccess(nextPage: Int, bean: RedPacketBaseBean, code: Int) {
                data = bean
                balance_tv.text = "钻石余额：${data?.recharge_residue}钻石"
                realm_notice_price_tv.text = "${data?.realm_notice_price}"
                send_diamond_et.hint = "${data!!.lucky_minimum}~${data!!.lucky_maximum}"
                num_limit.text = "红包数量限制${data?.min_count}~${data?.max_count}个"
                password_layout?.visibility = if (data?.secret_order_status == 1) View.GONE else View.VISIBLE
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {

            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }
}