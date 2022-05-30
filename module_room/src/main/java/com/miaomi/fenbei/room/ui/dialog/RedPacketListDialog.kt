package com.miaomi.fenbei.room.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.room.ui.adapter.RedPacketListAdapter
import com.miaomi.fenbei.base.bean.RedPacketBaseBean
import com.miaomi.fenbei.base.bean.RedPacketBean
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.util.RouterUrl
import kotlinx.android.synthetic.main.room_dialog_red_packet_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by 
 * on 2020-01-03.
 */
class RedPacketListDialog(context: Context): Dialog(context, R.style.common_dialog) {

    private lateinit var data: ArrayList<RedPacketBean>
    private var mRedPacketBaseData: RedPacketBaseBean? = null
    private var adapter: RedPacketListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_dialog_red_packet_list)
        EventBus.getDefault().register(this)
        data = ArrayList()
        adapter = RedPacketListAdapter(data)
//        var emptyView = View.inflate(context, R.layout.common_empty_view, null)
//        emptyView.findViewById<TextView>(R.id.tv_empty).text = "暂无红包哦暂无红包哦"
        adapter?.bindToRecyclerView(red_packet_rl)
        red_packet_rl.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
//        adapter?.emptyView = emptyView
        adapter?.setOnItemClickListener { _, _, position ->

            var redPacketDialog = RedPacketDialog(context!!, data[position], ChatRoomManager.isFollow == 1,
                    object : RedPacketDialog.OnDismissListener {
                        override fun dismiss() {
                            onGrabRedPacketItemClickListener?.onGrabRedPacketItemClick(position)
                            getRedPacketBaseData()
                        }
                    })
            redPacketDialog?.setOnSendRedPacketClickListener(object : RedPacketDialog.OnSendRedPacketClickListener {
                override fun onSendRedPacketClick() {
                    ARouter.getInstance().build(RouterUrl.sendRedPacket)
                            .withParcelable("data", mRedPacketBaseData).navigation()
                }
            })
            redPacketDialog?.show()


//            onGrabRedPacketItemClickListener?.let { it.onGrabRedPacketItemClick(position) }
//            adapter?.notifyDataSetChanged()
//            if (data.size == 0) dismiss()
        }

        close_iv.setOnClickListener { dismiss() }

        getRedPacketBaseData()
    }

    override fun dismiss() {
        super.dismiss()
        EventBus.getDefault().unregister(this)
        adapter?.cancelAllTimer()
    }

    interface OnGrabRedPacketItemClickListener {
        fun onGrabRedPacketItemClick(position: Int)
    }

    private var onGrabRedPacketItemClickListener: OnGrabRedPacketItemClickListener? = null

    fun setOnGrabRedPacketItemClickListener(onGrabRedPacketItemClickListener: OnGrabRedPacketItemClickListener) {
        this.onGrabRedPacketItemClickListener = onGrabRedPacketItemClickListener
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        if (isShowing) dismiss()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshRedPacketData(bean: RedPacketBean) {
        getRedPacketBaseData()
    }

    /**
     * 获取红包配置数据
     */
    private fun getRedPacketBaseData() {
        NetService.getInstance(context!!).getRedPacketBaseData(ChatRoomManager.getRoomId(), object: Callback<RedPacketBaseBean>() {
            override fun onSuccess(nextPage: Int, bean: RedPacketBaseBean, code: Int) {
                mRedPacketBaseData = bean
                adapter?.cancelAllTimer()
                data?.clear()
                data?.addAll(bean.collections)
                adapter?.notifyDataSetChanged()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {

            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

}