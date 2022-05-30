package com.miaomi.fenbei.room.ui.dialog.xb

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.miaomi.fenbei.base.bean.XBRankBean
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView
import com.miaomi.fenbei.room.R

class GameXBRankChildFragment : BaseFragment(){

    private var rankType = 0
    lateinit var gameXBRankChildAdapter: GameXBRankChildAdapter
    lateinit var xRecyclerView: XRecyclerView

    companion object {

        const val TYPE_TODAY: Int = 0
        const val TYPE_YESTERDAY: Int = 1
        const val TYPE_WEEK: Int = 2

        @JvmStatic
        fun getInstance(type: Int): GameXBRankChildFragment {
            val eggsRankingFragment = GameXBRankChildFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            eggsRankingFragment.arguments = bundle
            return eggsRankingFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.room_fragment_child_xy_ranking
    }

    override fun initView(view: View) {
        rankType = arguments!!.getInt("type")
        gameXBRankChildAdapter = GameXBRankChildAdapter(context)
        xRecyclerView = view.findViewById(R.id.ranking_rv)
        xRecyclerView.layoutManager = LinearLayoutManager(context)
        xRecyclerView.setPullRefreshEnabled(true)
        xRecyclerView.setLoadingMoreEnabled(false)
        xRecyclerView.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
            }

            override fun onRefresh() {
                getData()
            }

        })
        xRecyclerView.adapter = gameXBRankChildAdapter
        getData()
    }

    private fun getData(){
        NetService.getInstance(context!!).getHideRankList(rankType,object : Callback<List<XBRankBean>>(){
            override fun onSuccess(nextPage: Int, bean: List<XBRankBean>, code: Int) {
                xRecyclerView.refreshComplete()
                gameXBRankChildAdapter.setData(bean)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }
}