package com.miaomi.fenbei.room.ui.dialog.xb

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.miaomi.fenbei.base.bean.XBIndexBean
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_fragment_game_xb_home.*

class GameXBHomeFragment : BaseFragment(){

    lateinit var gameXBHomeAdapter: GameXBHomeAdapter
    var mBean: XBIndexBean? = null

    override fun getLayoutId(): Int {
        return R.layout.room_fragment_game_xb_home
    }

    override fun initView(view: View) {
        iv_rank.setOnClickListener {
            (parentFragment as GameXBDialog).goRank()
        }
        iv_record.setOnClickListener {
            (parentFragment as GameXBDialog).goRecord()
        }
        iv_close.setOnClickListener {
            (parentFragment as GameXBDialog).dismiss()
        }
        tv_tf.setOnClickListener {
            val dialog = GameXBTFDialog(OnOprateSuccessListener { getData() })
            dialog.show(childFragmentManager)
        }
        tv_explain.setOnClickListener {
            if (mBean != null){
                (parentFragment as GameXBDialog).goRule(mBean!!.rule)
            }
        }
        rv_hide.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        gameXBHomeAdapter = GameXBHomeAdapter(context)
        gameXBHomeAdapter.setOnItemClickListener {
            val dialog = GameXBAwardDialog(it, OnOprateSuccessListener { getData() })
            dialog.show(childFragmentManager)
        }
        rv_hide.adapter = gameXBHomeAdapter
        getData()

    }

    private fun getData(){
        NetService.getInstance(context!!).getHideIndex(ChatRoomManager.getRoomId(), object : Callback<XBIndexBean>() {
            override fun onSuccess(nextPage: Int, bean: XBIndexBean, code: Int) {
                if (isLive){
                    mBean = bean
                    gameXBHomeAdapter.setData(bean.list)
                    val list = bean.log.asSequence().map {
                        " <font color='#F68100'> ${it.nickname}寻宝获得</font> <font color='#ED5B4B'> ${it.gift_name}（ ${it.lucky_price} 钻石）</font>"
                    }.toList()
                    xb_msg_tb.setDatas(list, true)
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {

            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

//    private fun hideDig(id: String, dig_price: String){
//        NetService.getInstance(context!!).hideDig(id, dig_price, object : Callback<XBRewardBean>() {
//            override fun onSuccess(nextPage: Int, bean: XBRewardBean, code: Int) {
//                if (isLive){
//                    if (bean.type == 1) {
//                        val dialog = GameXBAwardDialog()
//                        dialog.show(childFragmentManager)
//                        dialog.bindData(bean)
//                        ChatRoomManager.sendXBMsg(MsgType.CB_FULL_SERVICE_REWARD, "（价值："+bean.total+"钻石）", ChatRoomManager.getRoomId())
//                    } else {
//                        ToastUtil.suc(context!!, "很遗憾！未挖掘到宝藏")
//                    }
//                    getData()
//                }
//
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                if (code == 1004){
//                    ARouter.getInstance().build("/app/pay").navigation()
//                }
//                ToastUtil.error(context!!, msg)
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//
//        })
//    }
}