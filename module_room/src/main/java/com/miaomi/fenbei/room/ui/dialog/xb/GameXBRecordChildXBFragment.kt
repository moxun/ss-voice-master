package com.miaomi.fenbei.room.ui.dialog.xb

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.miaomi.fenbei.base.bean.XBXBRecordBean
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView
import com.miaomi.fenbei.room.R

class GameXBRecordChildXBFragment : BaseFragment(){
    lateinit var gameXBRecordChildXBAdapter: GameXBRecordChildXBAdapter
    lateinit var xRecyclerView: XRecyclerView
    var mPage = 1
    override fun getLayoutId(): Int {
        return R.layout.room_fragment_child_xy_ranking
    }

    override fun initView(view: View) {
        xRecyclerView = view.findViewById(R.id.ranking_rv);
        gameXBRecordChildXBAdapter = GameXBRecordChildXBAdapter(context)
        xRecyclerView.layoutManager = LinearLayoutManager(context)
        xRecyclerView.adapter = gameXBRecordChildXBAdapter
        xRecyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                getData(TYPE_LOADMROE)
            }

            override fun onRefresh() {
                getData(TYPE_REFRESH)
            }

        })
        getData(TYPE_REFRESH)
    }

    private fun getData(type:Int){
        if (type == TYPE_REFRESH){
            mPage = 1
        }
        NetService.getInstance(context!!).getXBHuntingRecordList(mPage,object : Callback<List<XBXBRecordBean>>(){
            override fun onSuccess(nextPage: Int, bean: List<XBXBRecordBean>, code: Int) {
                xRecyclerView.refreshComplete()
                if (type == TYPE_REFRESH){
                    gameXBRecordChildXBAdapter.setData(bean)
                }else{
                    gameXBRecordChildXBAdapter.addData(bean)
                }
                mPage += 1
            }

            override fun noMore() {
                super.noMore()
                xRecyclerView.setNoMore(true)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                xRecyclerView.refreshComplete()
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

}