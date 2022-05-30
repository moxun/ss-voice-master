package com.miaomi.fenbei.base.core

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



abstract class BaseFragment : Fragment() {
    val TYPE_REFRESH = 0
    val TYPE_LOADMROE = 1

    var page = 1

    var isLive: Boolean = false
    var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isLive = true
        mContext = context
    }

    override fun onPause() {
        super.onPause()
//        isLive = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLive = false
    }

    override fun onDetach() {
        super.onDetach()
        isLive = false
        mContext = null
    }


    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return View.inflate(mContext, getLayoutId(), null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

    }

    abstract fun getLayoutId(): Int
    abstract fun initView(view: View)



    protected fun getRvToPosition(rvFromPosition:Int,rvToPosition:Int):Int{
        return rvFromPosition.coerceAtMost(rvToPosition)
    }


}
