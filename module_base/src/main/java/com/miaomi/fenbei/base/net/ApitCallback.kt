package com.miaomi.fenbei.base.net

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ApitCallback <T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val bean = response.body()
        if (null == bean) {
            onError("数据解析失败~", NullPointerException("数据解析失败~"), Data.CODE_HTTP)
            return
        }

        onSuccess(1, bean, 0)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (!isAlive()) return
        if (t is NetErrorException){
            onError(t.msg, t, t.code)
        }else{
            onError("网络异常,请检查~", t, Data.CODE_HTTP)
        }
    }


    abstract fun onSuccess(nextPage: Int, bean: T,code:Int)

    open fun onSuccessHasMsg(msg: String, bean: T,code:Int){}

    open fun onGetRuleSuccess(rule: String, code:Int) {}

    open fun noMore(){}

    abstract fun onError(msg: String, throwable: Throwable,code: Int)

    abstract fun isAlive(): Boolean
}