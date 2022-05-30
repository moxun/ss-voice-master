package com.miaomi.fenbei.base.widget

import com.miaomi.fenbei.base.R
import com.kingja.loadsir.callback.Callback

class EmptyView : Callback() {
    override fun onCreateView(): Int {
        return R.layout.common_empty_view
    }
}