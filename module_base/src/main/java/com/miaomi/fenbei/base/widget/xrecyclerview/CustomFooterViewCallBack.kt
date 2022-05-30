package com.miaomi.fenbei.base.widget.xrecyclerview


import android.view.View

/**
 * 作者：林冠宏
 *
 *
 * My GitHub : https://github.com/af913337456/
 *
 *
 * My Blog   : http://www.cnblogs.com/linguanh/
 *
 *
 * on 2017/11/8.
 */

interface CustomFooterViewCallBack {

    fun onLoadingMore(yourFooterView: View)
    fun onLoadMoreComplete(yourFooterView: View)
    fun onSetNoMore(yourFooterView: View, noMore: Boolean)

}

