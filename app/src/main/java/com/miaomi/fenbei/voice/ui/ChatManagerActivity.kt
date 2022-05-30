package com.miaomi.fenbei.voice.ui

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.miaomi.fenbei.base.bean.ManagerInfoBean
import com.miaomi.fenbei.base.bean.UserInfo
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.LoadHelper
import com.miaomi.fenbei.base.util.RouterUrl
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.voice.ui.room.AddManagerActivity
import kotlinx.android.synthetic.main.chatting_activity_chat_manager.*

@Route(path = RouterUrl.roomManager)
class ChatManagerActivity : BaseActivity(), XRecyclerView.LoadingListener {

    private lateinit var loadHelper: LoadHelper
    private lateinit var mChatId: String

    companion object {
        private const val CHAT_ID = "chat_id"
        fun getIntent(context: Context, chatId: String): Intent {
            val intent = Intent(context, ChatManagerActivity::class.java)
            intent.putExtra(CHAT_ID, chatId)
            return intent
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.chatting_activity_chat_manager
    }



    override fun initView() {
        setBaseStatusBar(false,false)
        mChatId = intent.getStringExtra(CHAT_ID)

        add_manager.setOnClickListener {
            AddManagerActivity.startActivity(this, mChatId)
        }

        user_rv.layoutManager = LinearLayoutManager(this)
        user_rv.setLoadingListener(this)
        user_rv.setLoadingMoreEnabled(false)
        user_rv.setPullRefreshEnabled(true)
        loadHelper = LoadHelper()
        loadHelper.registerLoad(user_rv)

    }

    override fun onResume() {
        super.onResume()
        loadData()
    }


    private fun loadData() {
        NetService.getInstance(this).getManagerInfo(mChatId, object : Callback<ManagerInfoBean>() {
            override fun onSuccess(nextPage: Int, bean: ManagerInfoBean, code: Int) {
                loadHelper.bindView(code)
                user_rv.refreshComplete()
                if (bean.user_list.isEmpty() && bean.manager_list.isEmpty()) {
                    loadHelper.setEmptyCallback(R.drawable.common_empty_bg, "空空如也")
                }
                val list:ArrayList<UserInfo> = ArrayList()
                list.addAll(bean.manager_list)
                list.addAll(bean.user_list)
                user_rv.adapter = ChatManagerAdapter(mChatId, list, object : ChatManagerAdapter.RefreshListen {
                    override fun onRefresh() {
                        loadData()
                    }
                })
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                loadHelper.setErrorCallback(code, View.OnClickListener {
                    loadData()
                })
                ToastUtil.error(this@ChatManagerActivity, msg)
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
