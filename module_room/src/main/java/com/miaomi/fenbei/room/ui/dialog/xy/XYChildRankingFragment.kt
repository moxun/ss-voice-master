package com.miaomi.fenbei.room.ui.dialog.xy

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.EggRankBean
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.util.ToastUtil
import kotlinx.android.synthetic.main.room_fragment_child_xy_ranking.*

/**
 * Created by 
 * on 2019-06-03.
 */
class XYChildRankingFragment: BaseFragment() {

    private var data: ArrayList<EggRankBean> = ArrayList()
    private lateinit var eggsRankingAdapter: XyRankingAdapter

    companion object {

        const val TYPE_TODAY: Int = 0
        const val TYPE_YESTERDAY: Int = 1
        const val TYPE_WEEK: Int = 2

        @JvmStatic
        fun getInstance(type: Int): XYChildRankingFragment {
            var eggsRankingFragment = XYChildRankingFragment()
            var bundle = Bundle()
            bundle.putInt("type", type)
            eggsRankingFragment.arguments = bundle
            return eggsRankingFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.room_fragment_child_xy_ranking
    }

    override fun initView(view: View) {
        eggsRankingAdapter = XyRankingAdapter()
        ranking_rv.adapter = eggsRankingAdapter
        ranking_rv.layoutManager = LinearLayoutManager(context!!)
        loadData()
    }

    private fun loadData() {
        NetService.getInstance(context!!).eggRank(arguments!!.getInt("type").toString(), object : Callback<List<EggRankBean>>() {
            override fun onSuccess(nextPage: Int, bean: List<EggRankBean>, code: Int) {
                data.clear()
                data.addAll(bean)
                eggsRankingAdapter.setData(data)
                eggsRankingAdapter.notifyDataSetChanged()
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