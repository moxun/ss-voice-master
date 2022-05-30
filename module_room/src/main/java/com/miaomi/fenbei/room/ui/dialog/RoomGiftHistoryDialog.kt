package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.ui.adapter.RoomGiftHistoryAdapter
import com.miaomi.fenbei.base.bean.RoomGiftHistoryBean
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.Data
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.LoadHelper
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView

class RoomGiftHistoryDialog: BaseBottomDialog() {
    val TYPE_REFRESH = 0
    val TYPE_LOADMROE = 1
    private lateinit var loadHelper: LoadHelper
    private var mPage = 1
    private lateinit var adapter: RoomGiftHistoryAdapter
    private lateinit var applyList: XRecyclerView

    override fun getLayoutRes(): Int {

        return R.layout.room_dialog_gift_history
    }


    override fun bindView(v: View) {

        applyList = v.findViewById(R.id.gift_rv)
        applyList.layoutManager = LinearLayoutManager(context)
        applyList.setPullRefreshEnabled(true)
        applyList.setLoadingMoreEnabled(true)
        applyList.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                getData(TYPE_LOADMROE)
            }

            override fun onRefresh() {
                getData(TYPE_REFRESH)
            }

        })
        adapter = RoomGiftHistoryAdapter(activity!!)
        applyList.adapter = adapter
        loadHelper = LoadHelper()
        loadHelper.registerLoad(applyList)
        getData(TYPE_REFRESH)
    }

    private fun getData(type:Int) {
        if (type == TYPE_REFRESH){
            mPage = 1
        }
        NetService.getInstance(context!!).getRoomGiftHistory(mPage, ChatRoomManager.getRoomId(),object : Callback<List<RoomGiftHistoryBean>>(){
            override fun onSuccess(nextPage: Int, list: List<RoomGiftHistoryBean>, code: Int) {
                applyList.refreshComplete()
                if (list.isEmpty()) {
                    loadHelper.setEmptyCallback(0,"暂无送礼记录~")
                } else {
                    loadHelper.bindView(Data.CODE_SUC)
                }
                if (type == TYPE_REFRESH){
                    adapter.setData(list)
                }else{
                    adapter.addData(list)
                }
                mPage++
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                applyList.refreshComplete()
            }

            override fun noMore() {
                super.noMore()
                applyList.setNoMore(true)
            }

            override fun isAlive(): Boolean {
                return true
            }

        })
    }
}