package com.miaomi.fenbei.room.ui.fragment

public interface StatusListen {
    fun onDismiss(icon: String, name: String)
    fun onShow()
    fun onDestroy()
}