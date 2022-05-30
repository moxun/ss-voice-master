package com.miaomi.fenbei.voice.ui.main.fragment.home

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.miaomi.fenbei.base.bean.ChatListBean
import com.miaomi.fenbei.base.core.BaseLazyFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.DensityUtil
import com.miaomi.fenbei.base.util.LoadHelper
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.voice.ui.main.adapter.PartyRoomListAdapter
import kotlinx.android.synthetic.main.room_fragment_all_room.*
import java.util.*

class AllHotRoomFragment : BaseLazyFragment() , XRecyclerView.LoadingListener {

    private lateinit var loadHelper: LoadHelper

    private lateinit var adapter: PartyRoomListAdapter

    companion object {
        @JvmStatic
        fun newInstance(): AllHotRoomFragment {
            return AllHotRoomFragment()
        }
    }

    override fun loadData() {
        loadData(TYPE_REFRESH)
    }

    override fun getLayoutId(): Int {
        return R.layout.room_fragment_all_room
    }



    override fun initView(view: View) {
//        headAdapter = ChatHotHeadListAdapter(activity!!)
//        headView = LayoutInflater.from(context!!).inflate(R.layout.chatting_hot_room_list_head,null)
//        headView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
//        headView.making_friends_msg_layout.setOnClickListener { startActivity(MakingFriendsActivity.getIntent(context!!)) }
        adapter = PartyRoomListAdapter(activity!!)

        hot_rv.layoutManager = GridLayoutManager(mContext, 2)
        hot_rv.addItemDecoration(GridSpacingItemDecoration(2,DensityUtil.dp2px(activity!!,8f),false))
        hot_rv.adapter =adapter
//        hot_rv.addHeaderView(headView)
//        hot_rv.setLoadingListener(this)
//        hot_rv.setLoadingMoreEnabled(false)
//        hot_rv.setPullRefreshEnabled(true)
        loadHelper = LoadHelper()
        loadHelper.registerLoad(hot_rv)
//        if (LoginHelper.INSTANCE.isLogin()) {
//            onRefresh()
//        }
//        loadData(TYPE_REFRESH)
//        MsgManager.INSTANCE.addMsgListener(this)
    }



    public fun loadData(type:Int){
        NetService.getInstance(mContext!!).getHotChats(page,object : Callback<ArrayList<ChatListBean>>() {
            override fun onSuccess(nextPage: Int, bean: ArrayList<ChatListBean>, code: Int) {
                if (bean.size == 0) {
                    loadHelper.setEmptyCallback(R.drawable.common_empty_bg, "空空如也")
                } else {
                    loadHelper.bindView(code)
                }

                when (type){
                    TYPE_REFRESH -> {
//                        RoomUtils.saveAllChatListBean(bean)
                        adapter.setData(bean)
                        if(hot_rv != null){
//                            hot_rv?.refreshComplete()
                        }
                    }
                    TYPE_LOADMROE -> {
                        adapter.addData(bean)
                        if(hot_rv != null){
//                            hot_rv?.loadMoreComplete()
                        }
                    }
                }
                page = nextPage
            }

            override fun noMore() {
//                hot_rv?.setLoadingMoreEnabled(false)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                loadHelper.setErrorCallback(code, View.OnClickListener {
                    loadData(TYPE_REFRESH)
                })
//                hot_rv?.refreshComplete()
//                adapter.setData(RoomUtils.getChatListBeanList())
                if(!TextUtils.isEmpty(msg) && mContext != null){
                    ToastUtil.error(mContext!!,msg)
                }
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    override fun onRefresh() {
        page =1
        loadData(TYPE_REFRESH)
    }

    override fun onLoadMore() {
        loadData(TYPE_LOADMROE)
    }


}