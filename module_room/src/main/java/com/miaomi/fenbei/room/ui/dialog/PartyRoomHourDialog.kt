package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.base.bean.PartyRoomHourBean
import com.miaomi.fenbei.base.bean.RoomGiftHistoryBean
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.Data
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.LoadHelper
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PartyRoomHourDialog: BaseBottomDialog() {

    val TYPE_REFRESH = 0
    val TYPE_LOADMROE = 1
//    private lateinit var loadHelper: LoadHelper
    private var mPage = 1
    private lateinit var adapter: RoomPartyHourAdapter
    private lateinit var applyList: XRecyclerView

    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_party_room_hour
    }

    override fun bindView(v: View) {
        applyList = v.findViewById(R.id.rv_hour_list)
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
        adapter = RoomPartyHourAdapter()
        applyList.adapter = adapter
//        loadHelper = LoadHelper()
//        loadHelper.registerLoad(applyList)
        getData(TYPE_REFRESH)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }

    private fun getData(type:Int) {
        if (type == TYPE_REFRESH){
            mPage = 1
        }
        NetService.getInstance(context!!).getPartyRoomHourRank(ChatRoomManager.getRoomId(),object : Callback<PartyRoomHourBean>(){
            override fun onSuccess(nextPage: Int, bean: PartyRoomHourBean, code: Int) {
                applyList.refreshComplete()
//                if (list.isEmpty()) {
//                    loadHelper.setEmptyCallback(0,"空~")
//                } else {
//                    loadHelper.bindView(Data.CODE_SUC)
//                }
                if (type == TYPE_REFRESH){
                    adapter.setData(bean.list)
                }else{
                    adapter.addData(bean.list)
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