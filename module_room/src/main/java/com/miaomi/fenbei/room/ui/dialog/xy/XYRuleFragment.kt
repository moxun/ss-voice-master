package com.miaomi.fenbei.room.ui.dialog.xy

import android.text.method.ScrollingMovementMethod
import android.view.View
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.bean.BaseBean
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.util.ToastUtil
import kotlinx.android.synthetic.main.room_fragment_xy_rule.*

/**
 * Created by 
 * on 2020/4/22.
 */
class XYRuleFragment: BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.room_fragment_xy_rule
    }

    override fun initView(view: View) {
        content_tv.movementMethod = ScrollingMovementMethod.getInstance()
        back_iv.setOnClickListener { (parentFragment as XYDialog).goHome() }
        loadData()
    }

    private fun loadData() {
        NetService.getInstance(context!!).eggRule(object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
            }

            override fun onGetRuleSuccess(rule: String, code: Int) {
                content_tv.text = rule
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(requireContext(), msg)
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }
}