package com.miaomi.fenbei.voice.ui.main.fragment.home

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.miaomi.fenbei.base.bean.ChatListBean
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.Data
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.LoadHelper
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.voice.ui.main.adapter.HomeHotRoomAdapter
import kotlinx.android.synthetic.main.chatting_fragment_hot.*
import java.util.*

class RecommandHotRoomFragment : BaseFragment() , XRecyclerView.LoadingListener {

    private lateinit var loadHelper: LoadHelper
    private lateinit var adapter: HomeHotRoomAdapter

    companion object {
        @JvmStatic
        fun newInstance(): RecommandHotRoomFragment {
            return RecommandHotRoomFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.chatting_fragment_hot
    }

    override fun initView(view: View) {
        adapter = HomeHotRoomAdapter(activity!!)

        hot_rv.layoutManager = GridLayoutManager(mContext, 3)
        hot_rv.adapter =adapter
        loadHelper = LoadHelper()
        loadHelper.registerLoad(hot_rv)
        onRefresh()
    }




    fun loadData(type:Int){
        NetService.getInstance(mContext!!).getHotChats(page,object : Callback<ArrayList<ChatListBean>>() {
            override fun onSuccess(nextPage: Int, bean: ArrayList<ChatListBean>, code: Int) {
                if (bean.size == 0) {
                    loadHelper.setRoomEmptyCallback(View.OnClickListener { v: View? -> loadData(TYPE_REFRESH) })
                } else {
                    loadHelper.bindView(code)
                }

                when (type){
                    TYPE_REFRESH -> {
                        adapter.setData(bean)
                    }
                    TYPE_LOADMROE -> {
                        adapter.addData(bean)
                    }
                }
                page = nextPage
            }

            override fun noMore() {
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                loadHelper.bindView(Data.CODE_SUC)
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
