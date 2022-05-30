package com.miaomi.fenbei.voice.ui

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.LeopardGoodNumberBean
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.RouterUrl
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.widget.GoodNumberAnimLinearLayout
import com.miaomi.fenbei.voice.R

@Route(path = RouterUrl.getRoomGoodNumber)
class GetGoodNumberActivity : BaseActivity() {

    private lateinit var adapter: GetGoodNumberAdapter
    @JvmField
    @Autowired
    var roomId = ""
    private var numberOfTimes = 0
    private lateinit var numberTv: GoodNumberAnimLinearLayout
    private var goodNumber: String = ""
    private lateinit var getGoodNumberTv: TextView
    private lateinit var submitTv: TextView
    private lateinit var priceTv: TextView
    private lateinit var goodNumberRv: RecyclerView


    override fun getLayoutId(): Int {
        return R.layout.dialog_get_good_numer
    }

    override fun initView() {
        ARouter.getInstance().inject(this)
        setBaseStatusBar(useThemestatusBarColor = false, useStatusBarColor = false)
        adapter = GetGoodNumberAdapter(object : GetGoodNumberAdapter.OnItemClickLiseter {
            override fun onItemCick(bean: LeopardGoodNumberBean) {
                val dialog = CommonDialog(this@GetGoodNumberActivity)
                dialog.setTitle("友情提示")
                        .setContent("是否购买当前靓号")
                        .setLeftBt("取消") {
                            dialog.dismiss()
                        }
                        .setRightBt("购买") {
                            buyGoodNumber(bean.good_number)
                            dialog.dismiss()
                        }
                        .show()
            }

        })
        numberTv = findViewById(R.id.tv_number)
        getGoodNumberTv = findViewById(R.id.tv_get)
        priceTv = findViewById(R.id.tv_price)
        goodNumberRv = findViewById(R.id.rv_good_number)
        getGoodNumberTv.setOnClickListener {
            getPartyGoodNumber()
        }
        goodNumberRv.layoutManager = GridLayoutManager(this@GetGoodNumberActivity,3)
        goodNumberRv.adapter = adapter
        submitTv = findViewById(R.id.tv_submit)
        submitTv.setOnClickListener {
            if (!TextUtils.isEmpty(goodNumber)) {
                bindPartyGoodNumber(goodNumber)
            } else {
                ToastUtil.error(this@GetGoodNumberActivity, "请摇号")
            }
        }
        numberTv.visibility = View.GONE
        priceTv.text = "本次免费"
        submitTv.visibility = View.GONE
        getData()
    }

    private fun getData(){
        NetService.getInstance(this@GetGoodNumberActivity).getLeopardPartyGoodNumber(object : Callback<List<LeopardGoodNumberBean>>(){
            override fun onSuccess(nextPage: Int, bean: List<LeopardGoodNumberBean>, code: Int) {
                adapter.setNewData(bean)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })

    }



    private fun getPartyGoodNumber() {
        numberOfTimes++
        numberTv.visibility = View.VISIBLE
        NetService.getInstance(this@GetGoodNumberActivity).getPartyGoodNumber(object : Callback<Any>() {
            override fun onSuccess(nextPage: Int, bean: Any, code: Int) {
                if (bean is String){
                    goodNumber = bean
                    numberTv.addNumber(bean.toInt())
                }
                submitTv.visibility = View.VISIBLE
                getGoodNumberTv.setText("再摇一次")
                priceTv.text = "本次需消耗1000钻石"
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@GetGoodNumberActivity, msg)
                numberOfTimes--
                if (code == 1004) {
                    ARouter.getInstance().build("/app/pay").navigation()
                }
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

    private fun bindPartyGoodNumber(goodNumber: String) {
        NetService.getInstance(this@GetGoodNumberActivity).bindPartyGoodNumber(roomId, goodNumber, object : Callback<Any>() {
            override fun onSuccess(nextPage: Int, bean: Any, code: Int) {
                ToastUtil.suc(this@GetGoodNumberActivity, "绑定成功")
                setResult(RESULT_OK)
                finish()
//                setResult(Da)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@GetGoodNumberActivity, msg)

            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

    private fun buyGoodNumber(goodNumber: String) {
        NetService.getInstance(this@GetGoodNumberActivity).buyPartyGoodNumber(roomId, goodNumber, object : Callback<Any>() {
            override fun onSuccess(nextPage: Int, bean: Any, code: Int) {
                ToastUtil.suc(this@GetGoodNumberActivity, "购买并绑定成功")
                setResult(RESULT_OK)
                finish()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(this@GetGoodNumberActivity, msg)
                if (code == 1004) {
                    ARouter.getInstance().build("/app/pay").navigation()
                }
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }
}