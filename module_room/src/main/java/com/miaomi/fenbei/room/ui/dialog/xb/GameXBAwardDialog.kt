package com.miaomi.fenbei.room.ui.dialog.xb

import android.content.DialogInterface
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.*
import com.miaomi.fenbei.base.core.dialog.BaseDialogFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.TimeUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.DiamondsBean
import com.miaomi.fenbei.base.bean.MsgType
import com.miaomi.fenbei.base.bean.XBItemBean
import com.miaomi.fenbei.base.bean.XBRewardBean
import com.plattysoft.leonids.ParticleSystem
import kotlinx.android.synthetic.main.room_dialog_xb_award.view.iv_close
import kotlinx.android.synthetic.main.room_dialog_xb_award.view.tv_pay
import kotlinx.android.synthetic.main.room_dialog_xb_award.view.tv_submit

class GameXBAwardDialog(private val item: XBItemBean, private var onTFSuccessListener: OnOprateSuccessListener) : BaseDialogFragment(){


    lateinit var giftIcon:ImageView
    lateinit var giftTotal:TextView
//    lateinit var gif500CoinView: Gif500CoinView
    lateinit var particleSystem:ParticleSystem
    private var curXBItemBean: XBItemBean? = null
    private var contentFl:FrameLayout? = null
    lateinit var titleTv:TextView
    lateinit var diamondTv:TextView
    lateinit var digPriceTv:TextView
    private var canOpen = true
    lateinit var timeTv:TextView

    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_xb_award
    }

    override fun bindView(v: View) {
        v.tv_submit.setOnClickListener {
            if (curXBItemBean != null){
                hideDig(curXBItemBean!!.id,curXBItemBean!!.dig_price)
            }
        }
        v.iv_close.setOnClickListener {
            dismiss()
        }
        v.tv_pay.setOnClickListener {
            ARouter.getInstance().build("/app/pay").navigation()
            dismiss()
        }
        digPriceTv = v.findViewById(R.id.tv_dig_price)
        diamondTv = v.findViewById(R.id.tv_price_diamonds)
        giftIcon = v.findViewById(R.id.iv_reward)
        giftTotal = v.findViewById(R.id.tv_price)
        contentFl = v.findViewById(R.id.fl_content)
        titleTv = v.findViewById(R.id.tv_1)
        timeTv = v.findViewById(R.id.tv_time)
        particleSystem = ParticleSystem(contentFl, 1000, resources.getDrawable(R.drawable.icon_km_pay_kd), 8000)
        particleSystem.setScaleRange(0.7f, 2f)
        particleSystem.setSpeedModuleAndAngleRange(0.5f, 0.8f, 75, 105)
        particleSystem.setRotationSpeedRange(0f, 180f)
        getData()
        getDiamonds()
//        gif500CoinView = v.findViewById(R.id.gif_500_coinview)
    }

    override fun onDestroy() {
        super.onDestroy()
        particleSystem.cancel()
    }


    override fun getCancelOutside(): Boolean {
        return false
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        onTFSuccessListener.onSuccess()
    }


    private fun hideDig(id: String, dig_price: String){
        if (!canOpen){
            return
        }
        NetService.getInstance(context!!).hideDig(id, dig_price, object : Callback<XBRewardBean>() {
            override fun onSuccess(nextPage: Int, bean: XBRewardBean, code: Int) {
                if (isLive){
                    if (bean.type == 1) {
                        titleTv.text = "恭喜！成功挖到宝藏"
                        ImgUtil.loadImg(context!!,bean!!.gift_icon,giftIcon)
                        giftTotal.text = "获得宝藏价值："+bean!!.total+"钻石"
                        timeTv.visibility = View.GONE
                        var time = 5000
                        try {
                            particleSystem.emit(500, -130, 100, time - 1000)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        ChatRoomManager.sendXBMsg(MsgType.CB_FULL_SERVICE_REWARD, "（价值："+bean.total+"钻石）", ChatRoomManager.getRoomId())
                    } else {
                        titleTv.text = "很遗憾！未挖掘到宝藏"
                    }
                    getData()
                    getDiamonds()
                }

            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                if (code == 1004){
                    ARouter.getInstance().build("/app/pay").navigation()
                    dismiss()
                }
                if (code == 1050){
                    canOpen = false
                    titleTv.text = msg
                }
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

    private fun getData(){
        NetService.getInstance(context!!).getSingleTreasure(item.id, object : Callback<XBItemBean>() {
            override fun onSuccess(nextPage: Int, item: XBItemBean, code: Int) {
                if (isLive){
                    curXBItemBean = item
                    giftTotal.text = "宝藏总价值："+curXBItemBean!!.price+"钻石"
                    digPriceTv.text = "单次消耗："+curXBItemBean!!.dig_price+"钻石"
                    timeTv.visibility = View.VISIBLE
                    timeTv.text = TimeUtil.getTimeSFM(item.except_time-System.currentTimeMillis()/1000);
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                dismiss()
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

    private fun getDiamonds() {
        NetService.getInstance(context!!).getDiamonds(object : Callback<DiamondsBean>() {
            override fun onSuccess(nextPage: Int, bean: DiamondsBean, code: Int) {
                diamondTv.text = "剩余 "+bean.balance+"钻石"
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {}
            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

}