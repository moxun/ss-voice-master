package com.miaomi.fenbei.base.widget.xrecyclerview

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.miaomi.fenbei.base.R


class LoadingMoreFooter : LinearLayout {

    private var mText: TextView? = null
    private var loadingHint: String? = null
    private var noMoreHint: String? = null
    private var loadingDoneHint: String? = null

    //    private AVLoadingIndicatorView progressView;

    constructor(context: Context) : super(context) {
        initView()
    }

    /**
     * @param context
     * @param attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    fun destroy() {
        //        progressCon = null;
        //        if(progressView != null){
        //            progressView.destroy();
        //            progressView = null;
        //        }
    }

    //
    fun setLoadingHint(hint: String) {
        loadingHint = hint
    }

    //
    fun setNoMoreHint(hint: String) {
        noMoreHint = hint
    }

    fun setLoadingDoneHint(hint: String) {
        loadingDoneHint = hint
    }

    //
    fun initView() {
        gravity = Gravity.CENTER
        layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mText = TextView(context)
        mText!!.setPadding(0,resources.getDimension(R.dimen.textandiconmargin).toInt(),0,resources.getDimension(R.dimen.textandiconmargin).toInt())
        mText!!.text = "正在加载"

        if (loadingHint == null || loadingHint == "") {
            loadingHint = "正在加载"
        }
        if (noMoreHint == null || noMoreHint == "") {
            noMoreHint = "已经到底了哦～"
        }
        if (loadingDoneHint == null || loadingDoneHint == "") {
            loadingDoneHint = "加载完成"
        }

        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        layoutParams.setMargins(resources.getDimension(R.dimen.textandiconmargin).toInt(), 0, 0, 0)

        mText!!.layoutParams = layoutParams
        addView(mText)
    }

    fun setState(state: Int) {
        when (state) {
            STATE_LOADING -> {
                mText!!.text = loadingHint
                this.visibility = View.VISIBLE
            }
            STATE_COMPLETE -> {
                mText!!.text = loadingDoneHint
                this.visibility = View.GONE
            }
            STATE_NOMORE -> {
                mText!!.text = noMoreHint
                this.visibility = View.VISIBLE
            }
            STATE_SHOW_HINT -> {
                mText!!.text = "上拉加载更多"
                this.visibility = View.VISIBLE
            }
        }
    }

    companion object {

        //    private SimpleViewSwitcher progressCon;
        val STATE_LOADING = 0
        val STATE_COMPLETE = 1
        val STATE_NOMORE = 2
        val STATE_SHOW_HINT = 3
    }
}

