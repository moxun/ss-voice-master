package com.miaomi.fenbei.voice.ui

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.miaomi.fenbei.base.bean.UserInfo
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.LoadHelper
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.chatting_activity_black_list.*

class BlackListActivity : BaseActivity() , XRecyclerView.LoadingListener {


    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, BlackListActivity::class.java)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.chatting_activity_black_list
    }

    private lateinit var loadHelper: LoadHelper

    override fun initView() {
        setBaseStatusBar(false,false)
        black_user_list.layoutManager = LinearLayoutManager(this)
        black_user_list.setLoadingListener(this)
        black_user_list.setLoadingMoreEnabled(false)
        black_user_list.setPullRefreshEnabled(true)
        loadHelper = LoadHelper()
        loadHelper.registerLoad(black_user_list)

        loadData()
    }

    private fun loadData() {
        NetService.getInstance(this).getBlackList(object : Callback<ArrayList<UserInfo>>() {
            override fun onSuccess(nextPage: Int, bean: ArrayList<UserInfo>, code: Int) {
                loadHelper.bindView(code)
                black_user_list.adapter = BlackListAdapter(bean)
                black_user_list.refreshComplete()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                loadHelper.setErrorCallback(code, View.OnClickListener {
                    loadData()
                })
                ToastUtil.error(this@BlackListActivity, msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }
    override fun onRefresh() {
        loadData()

    }

    override fun onLoadMore() {
    }
}
