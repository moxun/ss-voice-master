package com.miaomi.fenbei.room.ui.dialog.xy

import android.view.View
import androidx.fragment.app.DialogFragment
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.EggIndexBean
import com.miaomi.fenbei.base.bean.XYRewardGiftInfo
import com.miaomi.fenbei.base.bean.MsgGiftBean
import com.miaomi.fenbei.base.bean.MsgType
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.DoubleClickUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.gift.GiftManager
import kotlinx.android.synthetic.main.room_fragment_xy_home.*

/**
 * Created by 
 * on 2020/4/22.
 */
class XYHomeFragment: BaseFragment() {

    interface SmashAgainCallback {
        fun smashAgain()
        fun dismiss()
    }

    private var isShow: Int = 1
    private var hammerPrice: Int = 0
    private var hammerNum: Int = 0
    private var type = 0
    private var isClickEnable = true
    private var awardList = ArrayList<XYRewardGiftInfo>()
    private var giftSeatImg = ""

    override fun getLayoutId(): Int {
        return R.layout.room_fragment_xy_home
    }

    override fun initView(view: View) {
        content_fl.setOnClickListener {
            (parentFragment as XYDialog).dismiss()
        }
        content_cl.setOnClickListener{

        }
        record_iv.setOnClickListener { (parentFragment as XYDialog).goRecord() }

        rule_iv.setOnClickListener { (parentFragment as XYDialog).goRule() }

        rank_tv.setOnClickListener { (parentFragment as XYDialog).goRank() }

        prize_pool_iv.setOnClickListener { (parentFragment as XYDialog).goJackpot() }

        back_iv.setOnClickListener{ (parentFragment as XYDialog).dismiss()}

        buy_tv.background.alpha=75
        buy_tv.setOnClickListener { showBuyHammerDialog(100) }
        isShow = if (DataHelper.isSmashEggMsgNotOpen()) 0 else 1
        mi_iv.isChecked = DataHelper.isSmashEggMsgNotOpen()
        mi_iv.setOnClickListener {
            DataHelper.saveSmashEggMsgNotOpen(!DataHelper.isSmashEggMsgNotOpen())
            if (isShow == 1) {
                isShow = 0
//                ToastUtil.suc(context!!, "中奖信息将不对外提示")
                mi_iv.isChecked = true
            } else {
                isShow = 1
//                ToastUtil.suc(context!!, "中奖信息将对外提示")
                mi_iv.isChecked = false
            }
        }

        smash_one_bt.setOnClickListener { if (DoubleClickUtil.isFastDoubleClick()) smash(0) }
        smash_ten_bt.setOnClickListener { if (DoubleClickUtil.isFastDoubleClick()) smash(1) }
        smash_hundred_bt.setOnClickListener { if (DoubleClickUtil.isFastDoubleClick()) smash(2) }
        loadData()
    }

    override fun onResume() {
        super.onResume()
        isLive = true
    }

    public fun refresh(){
        loadData()
    }

    private fun showBuyHammerDialog(num: Int) {
        (parentFragment as XYDialog).goBuy(giftSeatImg)
//        HammerBuyDialog(context!!, hammerPrice, num, object : HammerBuyDialog.OnBuyHammer {
//            override fun onSuccess(number: Int) {
//                ToastUtil.suc(context!!,"购买成功")
//                loadData()
//            }
//        }).show()
    }

//    private fun showSmashAnim() {
//        smashEggSucceed(type, isShow, awardList)
//    }

    private fun smash(type: Int) {
        if (!isClickEnable) return
        isClickEnable = false
        this.type = type
        fun goSmash() {
//            showSmashAnim()
//            showAnim()
            smashEgg(type, isShow)
        }
        when(type) {
            0 -> {
                if (hammerNum < 1) {
                    ToastUtil.suc(context!!, " 许愿签数量不足，请购买后继续")
                    showBuyHammerDialog(1)
                    isClickEnable = true
                } else {
                    goSmash()
                }
            }
            1 -> {
                if (hammerNum < 10) {
                    ToastUtil.suc(context!!, " 许愿签数量不足，请购买后继续")
                    showBuyHammerDialog(10)
                    isClickEnable = true
                } else {
                    goSmash()
                }
            }
            2 -> {
                if (hammerNum < 100) {
                    ToastUtil.suc(context!!, " 许愿签数量不足，请购买后继续")
                    showBuyHammerDialog(100)
                    isClickEnable = true
                } else {
                    goSmash()
                }
            }
        }
    }

    private fun loadData() {
        NetService.getInstance(context!!).eggIndex(ChatRoomManager.getRoomId(), object : Callback<EggIndexBean>() {
            override fun onSuccess(nextPage: Int, bean: EggIndexBean, code: Int) {
                if (isLive){
                    giftSeatImg = bean.dress
                    hammerPrice = bean.price
                    hammerNum = bean.hammer
                    hammer_num_tv?.text = "许愿签：$hammerNum"
                    GiftManager.getInstance().accountBalance = bean.balance
                    val list = bean.record.asSequence().map {
                        " 恭喜 <u>  ${it.nickname}</u> 获得了 <font color='#CA5CF3'><u>  ${it.gift_name} * ${it.gift_number }</u>"
                    }.toList()
                    egg_msg_tb?.setDatas(list, true)
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    private fun smashEgg(type: Int, show: Int) {
        NetService.getInstance(context!!).smashEgg(ChatRoomManager.getRoomId(), type, show,
                object : Callback<ArrayList<XYRewardGiftInfo>>() {
                    override fun onSuccess(nextPage: Int, bean: ArrayList<XYRewardGiftInfo>, code: Int) {
                        if (isLive){
                            awardList.clear()
                            awardList.addAll(bean)
                            smashEggSucceed(type, show, awardList)
                        }
                    }

                    override fun onError(msg: String, throwable: Throwable, code: Int) {
                        ToastUtil.error(context!!, msg)
                        isClickEnable = true
                    }

                    override fun isAlive(): Boolean {
                        return isLive
                    }

                })
    }



    private fun smashEggSucceed(type: Int, show: Int, bean: ArrayList<XYRewardGiftInfo>) {
        if (show == 1) {
            bean.forEach {
                if (it.send == 1) {
                    ChatRoomManager.sendMsg(MsgType.WINNING_MSG,
                            "恭喜 ${DataHelper.getUserInfo()!!.nickname} 许愿获得 ${it.name}（${it.price}钻石）x${it.number}",
                            ChatRoomManager.getRoomId(), giftBean = MsgGiftBean(0, it.number, "", if (it.icon == null) "" else it.icon,
                            if (it.price == null) 0 else it.price.toInt(), it.name))
                }
            }
        }
        if (bean.isEmpty()) {
            ToastUtil.suc(context!!, "没有中奖~")
            (parentFragment as DialogFragment).dialog!!.setCanceledOnTouchOutside(true)
        } else {
            mContext?.let {
                XYAwardDialog(mContext!!, bean, object : SmashAgainCallback {
                    override fun smashAgain() {
                        smash(type)
                    }

                    override fun dismiss() {
                        (parentFragment as DialogFragment).dialog?.setCanceledOnTouchOutside(true)
                    }
                }).show()
            }
            when (type) {
                0 -> hammerNum -= 1
                1 -> hammerNum -= 10
                2 -> hammerNum -= 100
            }
            hammer_num_tv.text = "许愿签：$hammerNum"
            isClickEnable = true
        }
    }


}