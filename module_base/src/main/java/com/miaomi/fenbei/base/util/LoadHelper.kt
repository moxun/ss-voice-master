package com.miaomi.fenbei.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.miaomi.fenbei.base.R.id.*
import com.miaomi.fenbei.base.net.Data
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadLayout
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.kingja.loadsir.core.Transport
import com.miaomi.fenbei.base.FindEmptyRrfreshListener
import com.miaomi.fenbei.base.widget.*
import com.miaomi.fenbei.base.widget.*
import kotlinx.android.synthetic.main.common_empty_view.view.iv_empty
import kotlinx.android.synthetic.main.common_empty_view.view.tv_empty
import kotlinx.android.synthetic.main.common_empty_view.view.tv_secondary_msg
import kotlinx.android.synthetic.main.common_empty_view_find.view.*

class LoadHelper {

    private var loadService: LoadService<Any>? = null

    open fun registerLoad(view: View): LoadLayout {
        loadService = LoadSir.getDefault().register(view, {
            // 重新加载逻辑
        }, { code: Int ->
            var resultCode: Class<out Callback> = SuccessCallback::class.java
            when (code) {
                Data.CODE_SUC -> SuccessCallback::class.java
                Data.CODE_EMPTY -> resultCode = EmptyView::class.java
                Data.CODE_TOKEN_INVALID -> resultCode = TokenInvalidView::class.java
                Data.CODE_LOADING -> resultCode = LoadingView::class.java
                Data.CODE_EMPTY_ROOM -> resultCode = RoomEmptyView::class.java
                else -> resultCode = ErrorView::class.java
            }
            resultCode
        })
        loadService!!.showCallback(LoadingView::class.java)
        return loadService!!.loadLayout
    }

     @SuppressLint("WrongConstant")
     fun bindView(code: Int) {
        if (loadService == null) {
            LogUtil.e("使用多状态管理，先注册registerLoad")
            return
        }
         if (code == Data.CODE_TOKEN_INVALID){
             loadService!!.showWithConvertor(Data.CODE_SUC)
             LoginHelper.INSTANCE.startLoginActivity()
             return
         }
        loadService!!.showWithConvertor(code)
    }
    @SuppressLint("WrongConstant")
    fun setErrorCallback(code:Int,lister:View.OnClickListener){
        if (code == Data.CODE_TOKEN_INVALID){
            loadService!!.showWithConvertor(Data.CODE_SUC)
            LoginHelper.INSTANCE.startLoginActivity()
            return
        }
        bindView(Data.CODE_ERROR)
        loadService!!.setCallBack(ErrorView::class.java,object :Transport{
            override fun order(context: Context?, view: View?) {
                view!!.findViewById<TextView>(error_view).setOnClickListener(lister)
            }

        })
    }

    @SuppressLint("WrongConstant")
    fun setEmptyCallback(imgId:Int,content:String){
        bindView(Data.CODE_EMPTY)
        loadService!!.setCallBack(EmptyView::class.java,object :Transport{
            override fun order(context: Context?, view: View?) {
                if (imgId == null || imgId == 0) {
                    view!!.findViewById<ImageView>(iv_empty).visibility = View.GONE
                }
                view!!.findViewById<ImageView>(iv_empty).setImageResource(imgId)
                view!!.findViewById<TextView>(tv_empty).setText(content)
            }

        })
    }


    @SuppressLint("WrongConstant")
    fun setEmpty2Callback(content1:String, content2: String, @DrawableRes emptyPic: Int ?= null){
        bindView(Data.CODE_EMPTY)
        loadService!!.setCallBack(EmptyView::class.java) { _, view ->
            if (emptyPic == null) {
                view.iv_empty.visibility = View.GONE
            } else {
                view.iv_empty.visibility = View.VISIBLE
                view.iv_empty.setImageResource(emptyPic)
            }
            view.tv_empty.text = content1
            view.tv_secondary_msg.visibility = View.VISIBLE
            view.tv_secondary_msg.text = content2
        }
    }
    @SuppressLint("WrongConstant")
    fun setRoomEmptyCallback(lister:View.OnClickListener){
        bindView(Data.CODE_EMPTY_ROOM)
        loadService!!.setCallBack(RoomEmptyView::class.java,object :Transport{
            override fun order(context: Context?, view: View?) {
                view!!.findViewById<TextView>(error_view).setOnClickListener(lister)
            }
        })
    }

    @SuppressLint("WrongConstant")
    fun setFindEmptyCallback(content1:String, content2: String, @DrawableRes emptyPic: Int ?= null,lister: FindEmptyRrfreshListener){
        bindView(Data.CODE_EMPTY_FIND)
        loadService!!.setCallBack(FindEmptyView::class.java) { _, view ->
            if (emptyPic == null) {
                view.iv_empty.visibility = View.GONE
            } else {
                view.iv_empty.visibility = View.VISIBLE
                view.iv_empty.setImageResource(emptyPic)
            }
            view.swipe_refresh.setOnRefreshListener{
                lister.onRefresh(view.swipe_refresh)
            }
            view.tv_empty.text = content1
            view.tv_secondary_msg.visibility = View.VISIBLE
            view.tv_secondary_msg.text = content2
        }
    }

}