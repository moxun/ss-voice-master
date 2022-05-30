package com.miaomi.fenbei.room.ui.dialog.xy

import android.graphics.Color
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.BaseBean
import com.miaomi.fenbei.base.bean.DiamondsBean
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import kotlinx.android.synthetic.main.room_fragment_xy_buy_xyq.*

class BugXyQFragment : BaseFragment() {

    var buyHammerNumber = 1


    override fun getLayoutId(): Int {
        return R.layout.room_fragment_xy_buy_xyq
    }

    override fun onResume() {
        super.onResume()
        getDiamonds()
    }

    override fun initView(v: View) {
        content_fl.setOnClickListener {
            (parentFragment as XYDialog).dismiss()
        }
        back_iv.setOnClickListener { (parentFragment as XYDialog).goHome() }
       tv_buy.setOnClickListener {
           buyHammer(buyHammerNumber)
       }
        recharge_tv.setOnClickListener {
            ARouter.getInstance().build("/app/pay").navigation()
        }
        selectItem(1)
        time_dj_1.setOnClickListener{
            selectItem(1)
        }
        time_dj_2.setOnClickListener{
            selectItem(2)
        }
        time_dj_3.setOnClickListener{
            selectItem(3)
        }
        time_dj_4.setOnClickListener{
            selectItem(4)
        }
    }

    private fun selectItem(type:Int){
        time_dj_1.isSelected = false
        time_dj_2.isSelected = false
        time_dj_3.isSelected = false
        time_dj_4.isSelected = false
        tv_time_10.setTextColor(Color.parseColor("#9877FE"))
        tv_time_100.setTextColor(Color.parseColor("#9877FE"))
        tv_time_1000.setTextColor(Color.parseColor("#9877FE"))
        tv_time_10000.setTextColor(Color.parseColor("#9877FE"))
        if(type == 1){
            time_dj_1.isSelected = true
            tv_time_10.setTextColor(Color.parseColor("#FFFFFF"))
            buyHammerNumber = 1
        }
        if(type == 2){
            tv_time_100.setTextColor(Color.parseColor("#FFFFFF"))
            time_dj_2.isSelected = true
            buyHammerNumber = 10
        }
        if(type == 3){
            tv_time_1000.setTextColor(Color.parseColor("#FFFFFF"))
            time_dj_3.isSelected = true
            buyHammerNumber = 100
        }
        if(type == 4){
            tv_time_10000.setTextColor(Color.parseColor("#FFFFFF"))
            time_dj_4.isSelected = true
            buyHammerNumber = 1000
        }
    }


    private fun buyHammer(number: Int) {
        NetService.getInstance(context!!).buyHammer(number, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
               if (isLive){
                   ToastUtil.suc(context!!,"购买成功")
                   getDiamonds()
               }
//                (parentFragment as GoldEggNewDialog).goHome()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
                ARouter.getInstance().build("/app/pay").navigation()
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    private fun getDiamonds(){
        NetService.getInstance(context!!).getDiamonds(object : Callback<DiamondsBean>(){
            override fun onSuccess(nextPage: Int, bean: DiamondsBean, code: Int) {
                if (isLive){
                    diamond_tv.text = bean.balance
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }
    fun loadGiftImg(url:String){
        context?.let { ImgUtil.loadGifOrWebp(it,url,iv_gift_seat) }
    }
}