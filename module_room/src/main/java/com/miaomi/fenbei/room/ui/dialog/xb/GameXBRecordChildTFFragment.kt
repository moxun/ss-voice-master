package com.miaomi.fenbei.room.ui.dialog.xb

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.miaomi.fenbei.base.bean.XBTFRecordBean
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView
import com.miaomi.fenbei.room.R

class GameXBRecordChildTFFragment: BaseFragment(){
    lateinit var gameXBRecordChildTFAdapter: GameXBRecordChildTFAdapter
    lateinit var xRecyclerView: XRecyclerView
    var mPage = 1
    override fun getLayoutId(): Int {
        return R.layout.room_fragment_child_xy_ranking
    }

    override fun initView(view: View) {
        xRecyclerView = view.findViewById(R.id.ranking_rv);
        gameXBRecordChildTFAdapter = GameXBRecordChildTFAdapter(context)
        xRecyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                getData(TYPE_LOADMROE)
            }

            override fun onRefresh() {
                getData(TYPE_REFRESH)
            }

        })
        xRecyclerView.layoutManager = LinearLayoutManager(context)
        xRecyclerView.adapter = gameXBRecordChildTFAdapter
        getData(TYPE_REFRESH)
    }

    private fun getData(type:Int){
        if (type == TYPE_REFRESH){
            mPage = 1
        }
        NetService.getInstance(context!!).getXBReleaseRecordList(mPage,object : Callback<List<XBTFRecordBean>>(){
            override fun onSuccess(nextPage: Int, bean: List<XBTFRecordBean>, code: Int) {
                xRecyclerView.refreshComplete()
                if (type == TYPE_REFRESH){
                    gameXBRecordChildTFAdapter.setData(bean)
                }else{
                    gameXBRecordChildTFAdapter.addData(bean)
                }
                mPage = nextPage
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                xRecyclerView.refreshComplete()
            }

            override fun noMore() {
                super.noMore()
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }

}