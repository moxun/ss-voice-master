package com.miaomi.fenbei.room.ui.dialog.xy

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.JackpotBean
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.util.DensityUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.room_dialog_xy_jackpot.view.*
import kotlinx.android.synthetic.main.room_dialog_xy_jackpot.view.content_fl
import kotlinx.android.synthetic.main.room_dialog_xy_rank.*

/**
 * Created by 
 * on 2019-06-03.
 */
class XYJackpotFragment: BaseFragment() {

    private var data: ArrayList<JackpotBean> = ArrayList()
    private lateinit var jackpotListAdapter: XyJackpotListAdapter

    override fun getLayoutId(): Int {
        return R.layout.room_dialog_xy_jackpot
    }

    override fun initView(v: View) {
        v.content_fl.setOnClickListener {
            (parentFragment as XYDialog).dismiss()
        }
        back_iv.setOnClickListener { (parentFragment as XYDialog).goHome() }
        jackpotListAdapter = XyJackpotListAdapter()
        v.jackpot_rv.adapter = jackpotListAdapter
        v.jackpot_rv.addItemDecoration(GridSpacingItemDecoration(3, DensityUtil.dp2px(context!!, 10f), false))
        v.jackpot_rv.layoutManager = GridLayoutManager(context!!,3)
        loadData()
    }

    private fun loadData() {
        NetService.getInstance(context!!).eggJackpot(object : Callback<List<JackpotBean>>(){
            override fun onSuccess(nextPage: Int, bean: List<JackpotBean>, code: Int) {
                data.clear()
                data.addAll(bean)
                jackpotListAdapter.setData(data)
                jackpotListAdapter.notifyDataSetChanged()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }
}