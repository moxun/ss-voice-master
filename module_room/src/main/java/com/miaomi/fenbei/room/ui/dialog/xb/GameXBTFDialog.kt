package com.miaomi.fenbei.room.ui.dialog.xb

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.DiamondsBean
import com.miaomi.fenbei.base.bean.MsgType
import com.miaomi.fenbei.base.bean.XBHideGiftBean
import com.miaomi.fenbei.base.core.dialog.BaseDialogFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_dialog_xb_tf.view.*

class GameXBTFDialog(private var onTFSuccessListener: OnOprateSuccessListener) : BaseDialogFragment(){

    lateinit var recyclerView:RecyclerView
    lateinit var gameXBTFAdapter: GameXBTFAdapter
    lateinit var diamondTv:TextView
    lateinit var allCheckBox:CheckBox
    lateinit var singleCheckBox:CheckBox
    private var selectType:Int = 1
    private var selectedXBHideGiftBean: XBHideGiftBean? = null


    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_xb_tf
    }

    override fun bindView(v: View) {
        v.tv_submit.setOnClickListener {
            hideTreasure()
        }
        v.tv_pay.setOnClickListener {
            ARouter.getInstance().build("/app/pay").navigation()
        }
        v.iv_close.setOnClickListener {
            dismiss()
        }
        allCheckBox = v.findViewById(R.id.check_box_all)
        singleCheckBox = v.findViewById(R.id.check_box_single)
        diamondTv = v.findViewById(R.id.tv_price)
        recyclerView = v.findViewById(R.id.rv_gift)
        gameXBTFAdapter = GameXBTFAdapter(context)
        gameXBTFAdapter.setOnItemSeletedListener {
            selectedXBHideGiftBean = it
        }
        recyclerView.layoutManager = LinearLayoutManager(context,HORIZONTAL,false)
        recyclerView.adapter = gameXBTFAdapter
        singleCheckBox.isChecked = false
        allCheckBox.isChecked = true
        singleCheckBox.setOnClickListener {
            selectType = 2
            singleCheckBox.isChecked = true
            allCheckBox.isChecked = false
        }
        allCheckBox.setOnClickListener {
            selectType = 1
            singleCheckBox.isChecked = false
            allCheckBox.isChecked = true
        }
        getData()
        getDiamonds()
    }


    private fun getData(){
        NetService.getInstance(context!!).getHideGiftList(object : Callback<List<XBHideGiftBean>>(){
            override fun onSuccess(nextPage: Int, bean: List<XBHideGiftBean>, code: Int) {
                gameXBTFAdapter.setData(bean)

            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

    private fun getDiamonds() {
        NetService.getInstance(context!!).getDiamonds(object : Callback<DiamondsBean>() {
            override fun onSuccess(nextPage: Int, bean: DiamondsBean, code: Int) {
                diamondTv.text = "剩余"+bean.balance+"钻石"
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {}
            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    private fun hideTreasure(){
        if (selectedXBHideGiftBean == null){
            ToastUtil.error(context!!,"请选择投放的礼物")
            return
        }
        NetService.getInstance(context!!).hideTreasure(selectType, ChatRoomManager.getRoomId(),selectedXBHideGiftBean!!.id,object : Callback<DiamondsBean>(){
            override fun onSuccess(nextPage: Int, bean: DiamondsBean, code: Int) {
                ChatRoomManager.sendXBMsg(MsgType.CB_FULL_SERVICE_PUT, "（价值："+selectedXBHideGiftBean!!.price+"钻石）", ChatRoomManager.getRoomId())
                diamondTv.text = "剩余"+bean.balance+"钻石"
                dismiss()
                onTFSuccessListener.onSuccess()
            }

            override fun onSuccessHasMsg(msg: String, bean: DiamondsBean, code: Int) {
                super.onSuccessHasMsg(msg, bean, code)
                ToastUtil.suc(context!!,msg)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!,msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }
}