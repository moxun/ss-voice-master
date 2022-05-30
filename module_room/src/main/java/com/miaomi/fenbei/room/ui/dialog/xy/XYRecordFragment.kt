package com.miaomi.fenbei.room.ui.dialog.xy
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.EggRecordBean
import com.miaomi.fenbei.base.bean.RecordBean
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.widget.VerticalItemDecoration
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.util.DensityUtil
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.room_fragment_xy_record.*
import kotlinx.android.synthetic.main.room_fragment_xy_record.view.*

/**
 * Created by
 * on 2020/4/22.
 */
class XYRecordFragment: BaseFragment(), XRecyclerView.LoadingListener {

    private lateinit var adapter: WinningRecordAdapter
//    private lateinit var loadHelper: LoadHelper
    private lateinit var v: View

    override fun getLayoutId(): Int {
        return R.layout.room_fragment_xy_record
    }

    override fun initView(view: View) {

        this.v = view
        v.content_fl.setOnClickListener {
            (parentFragment as XYDialog).dismiss()
        }
        v.record_rv.layoutManager = LinearLayoutManager(context)
        v.record_rv.addItemDecoration(VerticalItemDecoration(DensityUtil.dp2px(context!!, 10f)))
        adapter = WinningRecordAdapter()
        v.record_rv.adapter = adapter
        v.record_rv.setLoadingListener(this)
        v.record_rv.setLoadingMoreEnabled(true)
        v.record_rv.setPullRefreshEnabled(true)
        loadData(TYPE_REFRESH)
//        loadHelper = LoadHelper()
//        loadHelper.registerLoad(v.record_rv)

        back_iv.setOnClickListener { (parentFragment as XYDialog).goHome() }
    }

    private fun loadData(type: Int){
        NetService.getInstance(context!!).eggRecord(page, object : Callback<List<RecordBean>>() {
            override fun onSuccess(nextPage: Int, list: List<RecordBean>, code: Int) {
//                if (bean.total == 0) {
//                    loadHelper.setEmpty2Callback("暂无中奖纪录~","快去许愿试试吧", R.drawable.common_empty_bg)
//                } else {
//                    loadHelper.bindView(Data.CODE_SUC)
//                }
                when (type){
                    TYPE_REFRESH -> {
                        adapter.setData(list)
                        v.record_rv?.refreshComplete()

                    }
                    TYPE_LOADMROE -> {
                        adapter.addData(list)
                        v.record_rv?.loadMoreComplete()
                    }
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                loadHelper.setErrorCallback(code, View.OnClickListener { loadData(TYPE_REFRESH) })
            }


            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    override fun onRefresh() {
        page = 1
        v.record_rv.setLoadingMoreEnabled(true)
        loadData(TYPE_REFRESH)
    }

    override fun onLoadMore() {
        page++
        loadData(TYPE_LOADMROE)
    }
}