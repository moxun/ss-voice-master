package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.ui.adapter.UserListAdapter
import com.miaomi.fenbei.room.ui.callback.WordClickListener
import com.miaomi.fenbei.base.bean.UserInfo
import com.miaomi.fenbei.base.core.BaseLazyFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView

class RoomUserFragment(val onItemClickListener: OnItemClickListener) : BaseLazyFragment(), XRecyclerView.LoadingListener, WordClickListener {
    private lateinit var roomUsersList: XRecyclerView


    private fun initView() {
        roomUsersList.layoutManager = LinearLayoutManager(activity)
        roomUsersList.setLoadingListener(this)
        roomUsersList.setLoadingMoreEnabled(false)
        roomUsersList.setPullRefreshEnabled(true)
        loadData()
    }

    override fun loadData() {
        getData()
    }


    override fun getLayoutId(): Int {
        return R.layout.room_dialog_room_users
    }

    override fun initView(view: View) {
        roomUsersList = view.findViewById(R.id.room_user_list)
        initView()
    }

    private fun getData() {
        NetService.getInstance(activity!!).getUserList(ChatRoomManager.getRoomId(), object : Callback<ArrayList<UserInfo>>() {
            override fun onSuccess(nextPage: Int, bean: ArrayList<UserInfo>, code: Int) {
                if (isLive) {
//                    loadHelper.bindView(code)
                    roomUsersList.adapter = UserListAdapter(bean, this@RoomUserFragment, activity!!)
                    roomUsersList.refreshComplete()
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                roomUsersList.refreshComplete()
//                loadHelper.setErrorCallback(code, View.OnClickListener { loadData() })
                ToastUtil.error(activity!!, msg)
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

    override fun onUserItemClick(view: View, userInfo: UserInfo) {
        onItemClickListener?.onItemClick(userInfo)
    }

    override fun onEnterRoomClick(userInfo: UserInfo) {
    }

    interface OnItemClickListener {
        fun onItemClick(userInfo: UserInfo)
    }

}