package com.miaomi.fenbei.room.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.room.ui.adapter.ClearMikeAdapter
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.room.ChatRoomManager
import kotlinx.android.synthetic.main.room_dialog_clear_mike.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * Created by 
 * on 2019-08-28.
 */
class ClearMikeDialog: BaseBottomDialog() {

    private var adapter: ClearMikeAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance(micNum: Int): ClearMikeDialog {
            val clearMikeDialog = ClearMikeDialog()
            val bundle = Bundle()
            bundle.putInt("micNum", micNum)
            clearMikeDialog.arguments = bundle
            return clearMikeDialog
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.room_dialog_clear_mike
    }

    override fun bindView(v: View) {
        var list = ArrayList<String>()
        for (index in 1..arguments!!.getInt("micNum", 4)) {
            list.add("${index}麦")
        }
        adapter = ClearMikeAdapter()
        v.operate_rl.adapter = adapter
        v.operate_rl.layoutManager = LinearLayoutManager(context)
        adapter!!.setData(list)
        adapter!!.setOnItemClickListener(object : ClearMikeAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("lzq","position:"+position)
                listener?.apply { clearMikeClick(position+1) }
            }
        })
        v.mic_tv_all.setOnClickListener {
            listener?.apply { clearMikeClick(9) }
        }
        if (ChatRoomManager.mRoomType == ChatRoomManager.ROOM_TYPE_RADIO){
            v.mic_tv_host.visibility = View.GONE
        }
        v.mic_tv_host.setOnClickListener {
            listener?.apply { clearMikeClick(0) }
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun refreshMicNum(data: MicNumBean) {
//        var list = ArrayList<String>()
//        if (ChatRoomManager.mRoomType != ChatRoomManager.ROOM_TYPE_RADIO){
//            list.add("房主")
//        }
//        for (index in 1..data.micNum) {
//            list.add("${index}麦")
//        }
//        list.add("全麦")
//        adapter?.apply { setData(list) }
//    }

    interface OnClearMikeClickListener {
        fun clearMikeClick(position: Int)
    }

    private var listener: OnClearMikeClickListener? = null

    fun setOnOperateClickListener (listener: OnClearMikeClickListener) {
        this.listener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }
}