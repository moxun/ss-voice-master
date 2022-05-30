package com.miaomi.fenbei.base.bean


data class ExchangeListBean (
        val list: ArrayList<IntegralGoodBean>,
        val integral: Int
)

data class IntegralGoodBean (
        val id: String,
        val icon: String,
        val name: String,
        val price: String,
        val need_integral: Int
)