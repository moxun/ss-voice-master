package com.miaomi.fenbei.base.widget

import android.content.Context
import android.view.View
import com.miaomi.fenbei.base.R
import com.kingja.loadsir.callback.Callback

class LoadingView :Callback(){
    override fun onCreateView(): Int {
        return R.layout.common_loading_view
    }

    override fun onViewCreate(context: Context?, view: View?) {
        super.onViewCreate(context, view)
    }

}