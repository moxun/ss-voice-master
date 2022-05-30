//package com.miaomi.fenbei.voice.ui
//
//import android.content.DialogInterface
//import android.text.TextUtils
//import android.view.View
//import android.widget.TextView
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.alibaba.android.arouter.launcher.ARouter
//import com.imuxuan.floatingview.FloatingView
//import com.miaomi.fenbei.base.bean.LeopardGoodNumberBean
//import com.miaomi.fenbei.base.core.dialog.BaseDialogFragment
//import com.miaomi.fenbei.base.core.dialog.CommonDialog
//import com.miaomi.fenbei.voice.Callback
//import com.miaomi.fenbei.voice.NetService
//import com.miaomi.fenbei.voice.ToastUtil
//import com.miaomi.fenbei.base.widget.GoodNumberAnimLinearLayout
//import com.miaomi.fenbei.voice.ChatRoomManager
//import com.miaomi.fenbei.voice.R
//
//class GetGoodNumberDialog(private val roomId: String, private val onGetGoodNumberListener: OnGetGoodNumberListener) : BaseDialogFragment() {
//
//    private lateinit var numberTv: GoodNumberAnimLinearLayout
//    private var goodNumber: String = ""
//    private lateinit var getGoodNumberTv: TextView
//    private lateinit var submitTv: TextView
//    private lateinit var priceTv: TextView
//    private lateinit var goodNumberRv:RecyclerView
//    private lateinit var adapter: GetGoodNumberAdapter
//    private var numberOfTimes = 0
//
//    override fun getLayoutRes(): Int {
//        return R.layout.dialog_get_good_numer
//    }
//
//    override fun bindView(v: View) {
//        adapter = GetGoodNumberAdapter(object : GetGoodNumberAdapter.OnItemClickLiseter {
//            override fun onItemCick(bean: LeopardGoodNumberBean) {
//                val dialog = CommonDialog(context!!)
//                dialog.setTitle("友情提示")
//                        .setContent("是否购买当前靓号")
//                        .setLeftBt("取消") {
//                            dialog.dismiss()
//                        }
//                        .setRightBt("购买") {
//                            buyGoodNumber(bean.good_number)
//                            dialog.dismiss()
//                        }
//                        .show()
//            }
//
//        })
//        numberTv = v.findViewById(R.id.tv_number)
//        getGoodNumberTv = v.findViewById(R.id.tv_get)
//        priceTv = v.findViewById(R.id.tv_price)
//        goodNumberRv = v.findViewById(R.id.rv_good_number)
//        getGoodNumberTv.setOnClickListener {
//            getPartyGoodNumber()
//        }
//        goodNumberRv.layoutManager = GridLayoutManager(context,3)
//        goodNumberRv.adapter = adapter
//        submitTv = v.findViewById(R.id.tv_submit)
//        submitTv.setOnClickListener {
//            if (!TextUtils.isEmpty(goodNumber)) {
//                bindPartyGoodNumber(goodNumber)
//            } else {
//                ToastUtil.error(context!!, "请摇号")
//            }
//        }
//        numberTv.visibility = View.GONE
//        priceTv.text = "本次免费"
//        submitTv.visibility = View.GONE
//        getData()
//    }
//
//    private fun getData(){
//        NetService.getInstance(context!!).getLeopardPartyGoodNumber(object :Callback<List<LeopardGoodNumberBean>>(){
//            override fun onSuccess(nextPage: Int, bean: List<LeopardGoodNumberBean>, code: Int) {
//                adapter.setNewData(bean)
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//
//        })
//
//    }
//
//
//
//    private fun getPartyGoodNumber() {
//        numberOfTimes++
//        numberTv.visibility = View.VISIBLE
//        NetService.getInstance(context!!).getPartyGoodNumber(object : Callback<String>() {
//            override fun onSuccess(nextPage: Int, bean: String, code: Int) {
//                goodNumber = bean
//                numberTv.addNumber(bean.toInt())
//                submitTv.visibility = View.VISIBLE
//                getGoodNumberTv.setText("再摇一次")
//                priceTv.text = "本次需消耗1000钻石"
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                ToastUtil.error(context!!, msg)
//                numberOfTimes--
//                if (code == 1004) {
//                    ARouter.getInstance().build("/app/pay").navigation()
//                }
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//
//        })
//    }
//
//    private fun bindPartyGoodNumber(goodNumber: String) {
//        NetService.getInstance(context!!).bindPartyGoodNumber(roomId, goodNumber, object : Callback<Any>() {
//            override fun onSuccess(nextPage: Int, bean: Any, code: Int) {
//                ToastUtil.suc(context!!, "绑定成功")
//                dismiss()
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                ToastUtil.error(context!!, msg)
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//
//        })
//    }
//
//    private fun buyGoodNumber(goodNumber: String) {
//        NetService.getInstance(context!!).buyPartyGoodNumber(roomId, goodNumber, object : Callback<Any>() {
//            override fun onSuccess(nextPage: Int, bean: Any, code: Int) {
//                ToastUtil.suc(context!!, "购买成功")
//                dismiss()
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                ToastUtil.error(context!!, msg)
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//
//        })
//    }
//
//    interface OnGetGoodNumberListener {
//        fun onSuccess()
//    }
//
//    override fun onDismiss(dialog: DialogInterface?) {
//        super.onDismiss(dialog)
//        onGetGoodNumberListener.onSuccess()
//    }
//}