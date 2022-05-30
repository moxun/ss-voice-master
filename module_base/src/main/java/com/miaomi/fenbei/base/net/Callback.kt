package com.miaomi.fenbei.base.net

import android.text.TextUtils
import android.util.Log
import com.miaomi.fenbei.base.util.LoginHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class Callback<T> : Callback<Data<T>> {

    override fun onResponse(call: Call<Data<T>>, response: Response<Data<T>>) {

        val bean = response.body()

        if (null == bean) {
            Log.i("lzq","null == bean~ :")
            onError("数据异常~", NullPointerException("数据异常~"), Data.CODE_HTTP)
            return
        }

        when (bean.code){
            Data.CODE_SUC
            -> if (bean.data == null){
                onError(bean.msg, NullPointerException("数据为空"), Data.CODE_HTTP)
            }else{
                val tempCode = if (bean.data is List<*> && (bean.data as List<*>).size == 0) {
                    Data.CODE_EMPTY
                } else {
                    Data.CODE_SUC
                }
                if (!TextUtils.isEmpty(bean.rule)) {
                    onGetRuleSuccess(bean.rule, tempCode)
                } else {

                    onSuccess(bean.page.last, bean.data!!, tempCode)
                    onSuccessHasMsg(bean.msg, bean.data!!, tempCode)
                }
                if (bean.page.current == bean.page.last){
                    noMore()
                }
            }
            Data.CODE_TOKEN_INVALID -> {
                LoginHelper.INSTANCE.startLoginActivity()
                onError(bean.msg, NullPointerException(bean.msg), bean.code)
            }
            else -> onError(bean.msg,NullPointerException("请求失败"),bean.code)
        }
    }

    override fun onFailure(call: Call<Data<T>>, t: Throwable) {
        if (!isAlive()) return
        Log.i("lzq","网络异常~ :"+t.message)
        onError("网络开小差~", t, Data.CODE_HTTP)
    }


    abstract fun onSuccess(nextPage: Int, bean: T,code:Int)

    open fun onSuccessHasMsg(msg: String, bean: T,code:Int){}

    open fun onGetRuleSuccess(rule: String, code:Int) {}

    open fun noMore(){}

    abstract fun onError(msg: String, throwable: Throwable,code: Int)

    abstract fun isAlive(): Boolean
}